package com.clase2.demo.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import com.clase2.demo.Modelos.DAO.IClienteDAO;
import com.clase2.demo.Modelos.DAO.IDetalleDAO;
import com.clase2.demo.Modelos.DAO.IEncabezadoDAO;
import com.clase2.demo.Modelos.DAO.IProductoDAO;
import com.clase2.demo.Modelos.Entity.Cliente;
import com.clase2.demo.Modelos.Entity.Detalle;
import com.clase2.demo.Modelos.Entity.Encabezado;
import com.clase2.demo.Modelos.Entity.Producto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/compra")
public class CompraController {

    @Autowired private IProductoDAO productoDAO;
    @Autowired private IClienteDAO clienteDAO;
    @Autowired private IEncabezadoDAO encabezadoDAO;
    @Autowired private IDetalleDAO detalleDAO;

    //  Mostrar el catálogo para comprar
    @GetMapping
    public String mostrarPantallaCompra(HttpSession session, Model model) {
        // Guardia de seguridad
        String rol = (String) session.getAttribute("usuarioRol");
        if (rol == null || !rol.equalsIgnoreCase("CLIENTE")) return "redirect:/login";

        // No dejar comprar si no ha llenado sus datos
        String correo = (String) session.getAttribute("usuarioCorreo");
        Cliente cliente = clienteDAO.findByCorreo(correo);
        if (cliente == null || cliente.getNombre() == null) {
            return "redirect:/Cliente/perfil"; 
        }

        // Mostrar solo productos con stock mayor a cero
        List<Producto> todos = productoDAO.findAll();
        List<Producto> disponibles = new ArrayList<>();
        for (Producto p : todos) {
            if (p.getStock() > 0) disponibles.add(p);
        }

        model.addAttribute("productos", disponibles);
        model.addAttribute("cliente", cliente);
        return "compra";
    }

    //  Procesar el carrito 
    @Transactional 
    @PostMapping("/confirmar")
    public String confirmarCompra(
            @RequestParam(value = "productoIds", required = false) Long[] productoIds,
            @RequestParam(value = "cantidades", required = false) int[] cantidades,
            HttpSession session) {
        
        String correo = (String) session.getAttribute("usuarioCorreo");
        Cliente cliente = clienteDAO.findByCorreo(correo);
        if (cliente == null || cliente.getNombre() == null) return "redirect:/Cliente/perfil";


        if (productoIds == null || cantidades == null) return "redirect:/compra";

        // Crear la cabecera de la factura en ceros
        Encabezado encabezado = new Encabezado();
        encabezado.setFecha(new Date());
        encabezado.setCliente(cliente);
        encabezado.setTotal(0.0);
        encabezadoDAO.save(encabezado);

        double totalFactura = 0.0;
        boolean comproAlgo = false;

        //  Leer las cantidades que el usuario seleccionó en la pantalla
        for (int i = 0; i < productoIds.length; i++) {
            int cantidadPedida = cantidades[i];
            
            if (cantidadPedida > 0) {
                Producto prod = productoDAO.findById(productoIds[i]);
                
                // No vender más de lo que hay en stock
                if (prod != null && prod.getStock() >= cantidadPedida) {
                    
                    // A. Crear el detalle del producto
                    Detalle detalle = new Detalle();
                    detalle.setEncabezado(encabezado);
                    detalle.setProducto(prod);
                    detalle.setCantidad(cantidadPedida);
                    
                    double subtotal = prod.getValorUnitario() * cantidadPedida;
                    detalle.setSubtotal(subtotal);
                    detalleDAO.save(detalle); // Se inserta en Detalle
                    
                    // B. Descontar del inventario
                    prod.setStock(prod.getStock() - cantidadPedida);
                    productoDAO.save(prod); // Se actualiza Producto
                    
                    totalFactura += subtotal;
                    comproAlgo = true;
                }
            }
        }

        // si eligió productos, actualizamos el total.
        if (comproAlgo) {
            encabezado.setTotal(totalFactura);
            encabezadoDAO.save(encabezado); // Actualiza total en Encabezado
        }

        return "redirect:/compra/factura/" + encabezado.getId();
    }

    @GetMapping("/factura/{id}")
    public String mostrarFactura(@PathVariable Long id, Model model, HttpSession session) {

        Encabezado compra = encabezadoDAO.findById(id);

        model.addAttribute("compra", compra);

        model.addAttribute("detalles", detalleDAO.findByEncabezadoId(id));

        return "factura";
    }
}