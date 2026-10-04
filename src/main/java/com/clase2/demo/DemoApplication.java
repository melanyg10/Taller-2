package com.clase2.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.boot.CommandLineRunner initData(
            com.clase2.demo.Modelos.DAO.IUsuarioDAO usuarioDAO,
            com.clase2.demo.Modelos.DAO.IProductoDAO productoDAO,
            com.clase2.demo.Modelos.DAO.IClienteDAO clienteDAO) {
        return args -> {
            
            // 1. Crear Administrador por defecto
            if (usuarioDAO.findByCorreo("admin@email.com") == null) {
                com.clase2.demo.Modelos.Entity.Usuario admin = new com.clase2.demo.Modelos.Entity.Usuario();
                admin.setCorreo("admin@email.com");
                admin.setContrasena("admin123");
                admin.setEstado("ACTIVO");
                admin.setRol("ADMIN");
                usuarioDAO.save(admin);
                System.out.println("✅ Administrador cargado.");
            }

            // 2. Crear Cliente de prueba ya activado
            if (usuarioDAO.findByCorreo("cliente@email.com") == null) {
                // A. Crear sus credenciales de login
                com.clase2.demo.Modelos.Entity.Usuario userCli = new com.clase2.demo.Modelos.Entity.Usuario();
                userCli.setCorreo("cliente@email.com");
                userCli.setContrasena("cliente123");
                userCli.setEstado("ACTIVO");
                userCli.setRol("CLIENTE");
                usuarioDAO.save(userCli);

                // B. Crear su perfil con nombre y apellido
                com.clase2.demo.Modelos.Entity.Cliente perfilCli = new com.clase2.demo.Modelos.Entity.Cliente();
                perfilCli.setCorreo("cliente@email.com");
                perfilCli.setNombre("Melany");
                perfilCli.setApellido("Giraldo");
                clienteDAO.save(perfilCli);
                System.out.println("✅ Cliente de prueba cargado.");
            }

            // 3. Crear Catálogo de Productos si está vacío
            if (productoDAO.findAll().isEmpty()) {
               com.clase2.demo.Modelos.Entity.Producto p1 = new com.clase2.demo.Modelos.Entity.Producto();
               p1.setNombre("Laptop Dell XPS");
               p1.setDescripcion("Computador portátil de 16GB RAM y 512GB SSD.");
               p1.setValorUnitario(4500000.0);
               p1.setStock(10);
               productoDAO.save(p1);

             com.clase2.demo.Modelos.Entity.Producto p2 = new com.clase2.demo.Modelos.Entity.Producto();
             p2.setNombre("Audífonos Sony");
             p2.setDescripcion("Audífonos inalámbricos con cancelación de ruido.");
             p2.setValorUnitario(850000.0);
             p2.setStock(25);
             productoDAO.save(p2);

             com.clase2.demo.Modelos.Entity.Producto p3 = new com.clase2.demo.Modelos.Entity.Producto();
             p3.setNombre("Monitor LED 24 pulgadas");
             p3.setDescripcion("Monitor Full HD de 24 pulgadas para trabajo y estudio.");
             p3.setValorUnitario(680000.0);
             p3.setStock(12);
             productoDAO.save(p3);

             com.clase2.demo.Modelos.Entity.Producto p4 = new com.clase2.demo.Modelos.Entity.Producto();
             p4.setNombre("Teclado mecánico");
             p4.setDescripcion("Teclado mecánico USB con iluminación y distribución en español.");
             p4.setValorUnitario(210000.0);
             p4.setStock(18);
             productoDAO.save(p4);

             com.clase2.demo.Modelos.Entity.Producto p5 = new com.clase2.demo.Modelos.Entity.Producto();
             p5.setNombre("Mouse inalámbrico");
             p5.setDescripcion("Mouse óptico inalámbrico con receptor USB.");
             p5.setValorUnitario(75000.0);
             p5.setStock(30);
             productoDAO.save(p5);

             com.clase2.demo.Modelos.Entity.Producto p6 = new com.clase2.demo.Modelos.Entity.Producto();
             p6.setNombre("Cámara web HD");
             p6.setDescripcion("Cámara web HD con micrófono integrado para videollamadas.");
             p6.setValorUnitario(180000.0);
             p6.setStock(14);
             productoDAO.save(p6);

              com.clase2.demo.Modelos.Entity.Producto p7 = new com.clase2.demo.Modelos.Entity.Producto();
             p7.setNombre("Unidad SSD 1 TB");
             p7.setDescripcion("Unidad de estado sólido de 1 TB para almacenamiento de archivos.");
             p7.setValorUnitario(310000.0);
             p7.setStock(16);
             productoDAO.save(p7);

             com.clase2.demo.Modelos.Entity.Producto p8 = new com.clase2.demo.Modelos.Entity.Producto();
             p8.setNombre("Parlante Bluetooth");
             p8.setDescripcion("Parlante portátil inalámbrico con conexión Bluetooth.");
             p8.setValorUnitario(320000.0);
             p8.setStock(20);
             productoDAO.save(p8);

             com.clase2.demo.Modelos.Entity.Producto p9 = new com.clase2.demo.Modelos.Entity.Producto();
             p9.setNombre("Silla ergonómica");
             p9.setDescripcion("Silla ajustable con soporte lumbar para escritorio.");
             p9.setValorUnitario(780000.0);
             p9.setStock(8);
             productoDAO.save(p9);

             com.clase2.demo.Modelos.Entity.Producto p10 = new com.clase2.demo.Modelos.Entity.Producto();
             p10.setNombre("Tablet 10 pulgadas");
             p10.setDescripcion("Tablet de 10 pulgadas con 128 GB de almacenamiento.");
             p10.setValorUnitario(1250000.0);
             p10.setStock(9);
             productoDAO.save(p10);
   }
            System.out.println("✅ Catálogo de productos cargado.");
            };
        };
}


