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
        return em.createQuery( "from Producto p where p.activo = true").getResultList();
    }

    @Transactional 
    @Override 
    public void save(Producto producto){
        if (producto.getId() != null && producto.getId() > 0){
            em.merge(producto);//actualiza si ya existe un ID
        }else{
            em.persist(producto);//crea uno nuevo si no tiene ID
        }
    }

    @Transactional 
    @Override 
    public Producto findById(Long id){
        return em.find(Producto.class, id);
    }

    @Override 
    @Transactional
    public void eliminar(Long id){

        Producto producto = em.find(Producto.class, id);
        if (producto != null) {
            producto.setActivo(false);
            em.merge(producto);
        }
    }


    
}
