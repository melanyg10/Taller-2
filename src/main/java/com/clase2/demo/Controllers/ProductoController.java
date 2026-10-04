package com.clase2.demo.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clase2.demo.Modelos.DAO.IProductoDAO;
import com.clase2.demo.Modelos.Entity.Producto;

import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping ("/Producto")
public class ProductoController {

    @Autowired
    private IProductoDAO productoDAO;

    @GetMapping ("/listar")
    public String ListarProductos(HttpSession session, Model model){

        if (!esAdmin(session)) return "redirect:/login";

        
        List<Producto> productos = productoDAO.findAll();
        model.addAttribute("Producto", productos);
        return "producto";
        
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(HttpSession session, Model model) {

        if (!esAdmin(session)) return "redirect:/login";

        
        model.addAttribute("producto", new Producto());
        return "formulario-producto"; 
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable("id") Long id, HttpSession session, Model model){
        if (!esAdmin(session)) return "redirect:/login";

        Producto producto = productoDAO.findById(id);
        if(producto == null){
            return "redirect:/Producto/listar";
        }

        model.addAttribute("producto", producto);
        return "formulario-producto";

    }

    
    @PostMapping("/guardar")
    public String guardarProducto(Producto producto, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";

        if(producto.getValorUnitario() == null || producto.getValorUnitario() < 0){
            producto.setValorUnitario(0.0);
        }

        if(producto.getStock() < 0){
            producto.setStock(0);
        }
        
        productoDAO.save(producto);
        return "redirect:/Producto/listar";
    }

    private boolean esAdmin(HttpSession session){
        String rol = (String)session.getAttribute("usuarioRol");
        return rol != null && rol.equalsIgnoreCase("ADMIN");
    }
}
