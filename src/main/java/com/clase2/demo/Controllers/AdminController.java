package com.clase2.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clase2.demo.Modelos.DAO.IUsuarioDAO;
import com.clase2.demo.Modelos.DAO.IEncabezadoDAO;
import com.clase2.demo.Modelos.DAO.IDetalleDAO;
import com.clase2.demo.Modelos.Entity.Encabezado;
import com.clase2.demo.Modelos.Entity.Detalle;
import com.clase2.demo.Modelos.Entity.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private IEncabezadoDAO encabezadoDAO;

    @Autowired
    private IDetalleDAO detalleDAO;

    @GetMapping
    public String mostrarPanelAdmin(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        List<Usuario> usuariosPendientes =
                usuarioDAO.findByEstado("Pendiente/Inactivo");

        model.addAttribute("usuarios", usuariosPendientes);

        return "admin";
    }

    @PostMapping("/activar")
    public String activarUsuario(
            @RequestParam("idUsuario") String correoUsuario,
            @RequestParam("nuevoRol") String nuevoRol,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        Usuario usuarioAprobado =
                usuarioDAO.findByCorreo(correoUsuario);

        if (usuarioAprobado != null) {
            usuarioAprobado.setEstado("ACTIVO");
            usuarioAprobado.setRol(nuevoRol);
            usuarioDAO.save(usuarioAprobado);
        }

        return "redirect:/admin";
    }

    @GetMapping("/compras")
    public String listarCompras(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("compras", encabezadoDAO.findAll());

        return "compras-admin";
    }

    @GetMapping("/compras/{id}")
    public String verDetalleCompra(
            @PathVariable("id") Long id,
            HttpSession session,
            Model model) {

        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        Encabezado encabezado = encabezadoDAO.findById(id);

        if (encabezado == null) {
            return "redirect:/admin/compras";
        }

        List<Detalle> detalles =
                detalleDAO.findByEncabezadoId(id);

        model.addAttribute("encabezado", encabezado);
        model.addAttribute("detalles", detalles);

        return "detalle-compra-admin";
    }

    private boolean esAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("usuarioRol");

        return rol != null && rol.equalsIgnoreCase("ADMIN");
    }
}
