package com.clase2.demo.Modelos.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clase2.demo.Modelos.Entity.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository 
public class ProductoDAOImp implements IProductoDAO{

    @PersistenceContext 
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional (readOnly=true)
    @Override 
    public List<Producto> findAll() {
        return em.createQuery( "from Producto").getResultList();
    }


    
}
