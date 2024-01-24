package org.ddelatorre.springcloud.msvc.usuarios.repository;

import org.ddelatorre.springcloud.msvc.usuarios.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}
