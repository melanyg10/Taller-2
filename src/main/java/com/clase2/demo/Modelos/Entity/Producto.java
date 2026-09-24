package com.clase2.demo.Modelos.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="Productos")
public class Producto {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;
    
    private String Nombre;
    private Double precio;
    
    @Column(name = "create_at")
    private Date createAt;

    public Producto() {// constructor vacio para poder crear productos
    }

    public Producto(Long id, String nombre, Double precio, Date createAt) {
        Id = id;
        Nombre = nombre;
        this.precio = precio;
        this.createAt = createAt;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    

    
    
}

 