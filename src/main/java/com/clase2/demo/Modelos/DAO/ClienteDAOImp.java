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

    @SuppressWarnings("unchecked")
    @Transactional (readOnly=true)
    @Override 
    public Cliente findByCorreo(String correo){
        // Busca en la tabla 'clientes' si ya existe alguien con ese correo
        List<Cliente> resultados = em.createQuery("from Cliente where correo = :correo")
        .setParameter("correo", correo)
        .getResultList();

        if(resultados.isEmpty()){
            return null;// Si no lo encuentra, retorna nulo (Primer Ingreso)
        }

        return resultados.get(0);// Si lo encuentra, devuelve sus datos
    }

    @Transactional 
    @Override 
    public void save(Cliente cliente){

        if(cliente.getId() != null && cliente.getId() > 0){
            em.merge(cliente);
        }else{
            em.persist(cliente);
        }
    }




    
}
    

