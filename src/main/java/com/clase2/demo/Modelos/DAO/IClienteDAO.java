package com.clase2.demo.Modelos.DAO;

import java.util.List;

import com.clase2.demo.Modelos.Entity.Cliente;

public interface IClienteDAO {

    public List<Cliente> findAll();
    
}
