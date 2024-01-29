package org.ddelatorre.springcloud.msvc.cursos.service;

import org.ddelatorre.springcloud.msvc.cursos.models.Usuario;
import org.ddelatorre.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface CursoService {
    List <Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);

    Optional<Curso> porIdConUsuario(Long id);

    Optional<Usuario> asignarUsuarioACurso(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuarioACurso(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuarioACurso(Usuario usuario, Long cursoId);
}
