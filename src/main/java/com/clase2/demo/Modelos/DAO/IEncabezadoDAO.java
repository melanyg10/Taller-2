
package com.clase2.demo.Modelos.DAO;

import java.util.List;

import com.clase2.demo.Modelos.Entity.Encabezado;

public interface IEncabezadoDAO {

    public List<Encabezado> findAll();

    public Encabezado findById(Long id);

    public void save(Encabezado encabezado);
}