package com.clase2.demo.Modelos.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clase2.demo.Modelos.Entity.Cliente;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository 
public class ClienteDAOImp implements IClienteDAO{

    @PersistenceContext 
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional (readOnly=true)
    @Override 
    public List<Cliente> findAll() {
        return em.createQuery( "from Cliente").getResultList();
    }


    
}
    

