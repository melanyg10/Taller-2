package com.clase2.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clase2.demo.Modelos.DAO.IUsuarioDAO;
import com.clase2.demo.Modelos.Entity.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    // Muestra el formulario de login
    @GetMapping
    public String mostrarLogin() {
        return "login";
    }

    // Procesa el formulario de login
    @PostMapping
    public String procesarLogin(
            @RequestParam String correo,
            @RequestParam String contrasena,
            HttpSession session,
            Model model) {

        // Paso 1: Buscar el usuario por correo en la base de datos
        Usuario usuario = usuarioDAO.findByCorreo(correo);

        // Paso 2: Si no existe ningún usuario con ese correo → credenciales incorrectas
        if (usuario == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "login";
        }

        // Paso 3: Validar que la contraseña ingresada coincida con la almacenada
        if (!usuario.getContrasena().equals(contrasena)) {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "login";
        }

        // Paso 4 y 5: Validar si la cuenta está activa
        if (!usuario.getEstado().equalsIgnoreCase("ACTIVO")) {
            model.addAttribute("error", "Tu cuenta no está activa. Estado actual: " + usuario.getEstado());
            return "login";
        }

        // Paso 6 y 7: Credenciales correctas y cuenta activa → guardar sesión
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioCorreo", usuario.getCorreo());
        session.setAttribute("usuarioRol", usuario.getRol());

        // Redirigir según el rol del usuario
        if (usuario.getRol().equalsIgnoreCase("ADMIN")) {
            return "redirect:/admin";
        } else if (usuario.getRol().equalsIgnoreCase("CLIENTE")) {
            return "redirect:/compra"; // Lo enviamos a los productos
        } else {
            return "redirect:/login"; // Por seguridad
        }
    }

    // Cierra la sesión del usuario actual
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/registro")//muestra form registro
    public String mostrarRegistro(Model model){
        model.addAttribute("usuario", new Usuario());// se pasa un usuario vacio al form
        return "registro";
    }

    @PostMapping("/registro")//procesa y guarda el registro
    public String procesarRegistro(
        @RequestParam String correo,
        @RequestParam String contrasena,
        Model model){

            if(usuarioDAO.findByCorreo(correo) != null){
                model.addAttribute("error", "El correo ya está registrado");
                return "registro";
            }

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setEstado("Pendiente/Inactivo");//asi es el estado inicial requerido
            nuevoUsuario.setRol("Por asignar");

            usuarioDAO.save(nuevoUsuario);
            model.addAttribute("mensaje", "Registro exitoso, tu cuenta debe ser aprobada por un administrador");
            return "login";
        }
}
