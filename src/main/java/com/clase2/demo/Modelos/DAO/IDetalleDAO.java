package com.clase2.demo.Modelos.DAO;

import java.util.List;

import com.clase2.demo.Modelos.Entity.Detalle;

public interface IDetalleDAO {

    public List<Detalle> findByEncabezadoId(Long encabezadoId);

    public void save(Detalle detalle);
}
