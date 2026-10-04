package com.clase2.demo.Modelos.Entity;

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
    private Long id;
    
    private String nombre;
    private String descripcion;
    private Double valorUnitario;
    private int stock;
  

    public Producto() {// constructor vacio para poder crear productos
    }


    public Producto(Long id, String nombre, String descripcion, Double valorUnitario, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valorUnitario = valorUnitario;
        this.stock = stock;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Double getValorUnitario() {
        return valorUnitario;
    }


    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }


    public int getStock() {
        return stock;
    }


    public void setStock(int stock) {
        this.stock = stock;
    }

    

    

    
}

 