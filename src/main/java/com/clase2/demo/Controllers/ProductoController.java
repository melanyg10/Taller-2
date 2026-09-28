package com.clase2.demo.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clase2.demo.Modelos.DAO.IProductoDAO;
import com.clase2.demo.Modelos.Entity.Producto;

@Controller 
@RequestMapping ("/Producto")
public class ProductoController {

    @Autowired
    private IProductoDAO uss;

    @GetMapping ("/listar")
    public String ListarProductos(Model model){

        //ProductoDAOImp uss = new ProductoDAOImp()
        List<Producto> productos = uss.findAll();
        model.addAttribute("Producto", productos);
        return "producto";
        
    }

    @GetMapping("/crear")
    public String crearProducto(Model model) {
        
        Producto productoVacio = new Producto();
        
        model.addAttribute("producto", productoVacio);
        model.addAttribute("titulo", "Formulario de Producto");
        
        return "formulario-producto"; 
    }
    

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        //el stock no puede ser negativo
        if(producto.getStock() < 0){

            producto.setStock(0);
        }
        
        uss.save(producto);

        return "redirect:/Producto/listar";
    }
}
