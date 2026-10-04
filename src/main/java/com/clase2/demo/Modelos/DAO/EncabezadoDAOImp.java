package com.clase2.demo.Modelos.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.clase2.demo.Modelos.Entity.Encabezado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


@Repository
public class EncabezadoDAOImp implements IEncabezadoDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Encabezado> findAll() {
        TypedQuery<Encabezado> query = em.createQuery(
                "select e from Encabezado e join fetch e.cliente " +
                "order by e.fecha desc, e.id desc",
                Encabezado.class);

        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Encabezado findById(Long id) {
        TypedQuery<Encabezado> query = em.createQuery(
                "select e from Encabezado e join fetch e.cliente " +
                "where e.id = :id",
                Encabezado.class);

        query.setParameter("id", id);

        List<Encabezado> resultados = query.getResultList();

        if (resultados.isEmpty()) {
            return null;
        }

        return resultados.get(0);
    }

    @Transactional
    @Override
    public void save(Encabezado encabezado) {
        em.persist(encabezado);
    }
}


