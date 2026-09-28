package com.clase2.demo.Modelos.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clase2.demo.Modelos.Entity.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UsuarioDAOImp implements IUsuarioDAO {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Usuario findByCorreo(String correo) {
        // Busca en la tabla usuarios el registro cuyo correo coincida
        // La query usa el nombre de la clase Java (Usuario), no el nombre de la tabla
        List<Usuario> resultados = em.createQuery(
            "from Usuario where correo = :correo"
        )
        .setParameter("correo", correo)
        .getResultList();

        // Si encontró al menos uno, retorna el primero; si no, retorna null
        if (resultados.isEmpty()) {
            return null;
        }
        return resultados.get(0);
    }

    @Transactional 
    @Override 
    public void save(Usuario usuario){
        if(usuario.getId() != null && usuario.getId() > 0){
            em.merge(usuario);
    
        }else{
            em.persist(usuario);
        }
    }
}
