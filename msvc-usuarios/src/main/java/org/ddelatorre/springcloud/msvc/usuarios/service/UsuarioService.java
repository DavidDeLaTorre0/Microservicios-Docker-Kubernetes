package org.ddelatorre.springcloud.msvc.usuarios.service;

import org.ddelatorre.springcloud.msvc.usuarios.entities.Usuario;


import java.util.List;
import java.util.Optional;

public interface UsuarioService {


    /*
Optional de Java 8, es un wrapper, es una clase que envuelve
 el objeto para saber si esta presente en la consulta y
 evitar que sea nulo y evitar el error NULL POINTED EXCEPTION
 */
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    //Guardar, para insertar o editar
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Optional <Usuario> findByEmail(String email);
}
