package com.clase2.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clase2.demo.Modelos.DAO.IClienteDAO;
import com.clase2.demo.Modelos.Entity.Cliente;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequestMapping("/Cliente")
public class ClienteController {

    @Autowired 
    private IClienteDAO uss;

    @GetMapping("/listar")
    public String Listar(Model model) {

       List<Cliente> clientes = uss.findAll();
       //model.addAttribute("titulo", "Listar cliente");
       model.addAttribute("clientes", clientes);
       //Cliente C1 = new Cliente(1234L, "Melany", "Giraldo", "melany.giraldo@example", new Date());
       //model.addAttribute("cliente", C1);
       return "listar";

    }
    
}
