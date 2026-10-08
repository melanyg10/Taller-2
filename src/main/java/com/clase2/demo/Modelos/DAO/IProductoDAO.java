package com.clase2.demo.Modelos.DAO;

import java.util.List;

import com.clase2.demo.Modelos.Entity.Producto;

public interface IProductoDAO {

    public List<Producto> findAll();

    public void save(Producto producto);

    public Producto findById(Long id);

    public void eliminar(Long id);
    
}
