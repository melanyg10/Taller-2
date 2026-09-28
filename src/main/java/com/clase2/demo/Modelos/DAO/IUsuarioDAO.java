package com.clase2.demo.Modelos.DAO;

import com.clase2.demo.Modelos.Entity.Usuario;

public interface IUsuarioDAO {

    // Busca un usuario por su correo electrónico
    // Retorna null si no existe ningún usuario con ese correo
    public Usuario findByCorreo(String correo);

    public void save(Usuario usuario);

}
