package com.clase2.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clase2.demo.Modelos.DAO.IClienteDAO;
import com.clase2.demo.Modelos.Entity.Cliente;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequestMapping("/Cliente")
public class ClienteController {

    @Autowired 
    private IClienteDAO clienteDAO;

    @GetMapping("/listar")
    public String Listar(HttpSession session, Model model) {

        String rolSesion = (String)
        session.getAttribute("usuarioRol");
        if(rolSesion == null || !rolSesion.equalsIgnoreCase("ADMIN")) return "redirect:/login";

       List<Cliente> clientes = clienteDAO.findAll();
       model.addAttribute("clientes", clientes);
       return "listar";
    }

    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model){

        // Validar que solo entre un CLIENTE
        String rol = (String) session.getAttribute("usuarioRol");
        if (rol == null || !rol.equalsIgnoreCase("CLIENTE")) {
            return "redirect:/login"; 
        }

        // Extraer el correo de la sesión actual
        String correoLogueado = (String) session.getAttribute("usuarioCorreo");

        Cliente clienteBD = clienteDAO.findByCorreo(correoLogueado);

        if(clienteBD == null){
            clienteBD = new Cliente();
            clienteBD.setCorreo(correoLogueado);
            model.addAttribute("mensajeInfo", "Bienvenido, es tu primer ingreso. Por favor, completa tus datos personales para habilitar las compras.");
        }

        model.addAttribute("cliente", clienteBD);
        return "perfil-cliente";
    }

    @PostMapping("/guardar")
    public String guardarPerfil(Cliente cliente, HttpSession session ){

        String rol = (String) session.getAttribute("usuarioRol");
        if (rol == null || !rol.equalsIgnoreCase("CLIENTE")) return "redirect:/login";

        cliente.setCorreo((String) session.getAttribute("usuarioCorreo"));
        cliente.setUsuarioId((Long) session.getAttribute("usuarioId"));

        if (cliente.getCreateAt() == null) cliente.setCreateAt(new java.util.Date());
        clienteDAO.save(cliente);
        return "redirect:/Cliente/perfil"; // Recarga la página después de guardar

       
    }
}
    

