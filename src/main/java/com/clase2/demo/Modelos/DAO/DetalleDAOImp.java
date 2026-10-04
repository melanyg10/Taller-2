package com.clase2.demo.Modelos.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clase2.demo.Modelos.Entity.Detalle;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class DetalleDAOImp implements IDetalleDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Detalle> findByEncabezadoId(Long encabezadoId) {
        TypedQuery<Detalle> query = em.createQuery(
                "select d from Detalle d join fetch d.producto " +
                "where d.encabezado.id = :encabezadoId " +
                "order by d.id",
                Detalle.class);

        query.setParameter("encabezadoId", encabezadoId);

        return query.getResultList();
    }

    @Transactional
    @Override
    public void save(Detalle detalle) {
        em.persist(detalle);
    }
}