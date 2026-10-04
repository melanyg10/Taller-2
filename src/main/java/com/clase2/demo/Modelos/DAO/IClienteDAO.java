package com.clase2.demo.Modelos.DAO;

import java.util.List;

import com.clase2.demo.Modelos.Entity.Cliente;

public interface IClienteDAO {

    public List<Cliente> findAll();

    public Cliente findByCorreo(String correo);//buscar si el cliente ya llenó sus datos

    public void save(Cliente cliente); //guardar-actualizar datos personales
    
}
