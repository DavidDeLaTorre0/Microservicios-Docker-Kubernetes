package org.ddelatorre.springcloud.msvc.cursos.service;

import org.ddelatorre.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.ddelatorre.springcloud.msvc.cursos.models.Usuario;
import org.ddelatorre.springcloud.msvc.cursos.models.entity.Curso;
import org.ddelatorre.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.ddelatorre.springcloud.msvc.cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioClientRest clientHttp;
    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>)cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuarioACurso(Usuario usuario, Long cursoId) {

        Optional<Curso> o = cursoRepository.findById(cursoId);

        if(o.isPresent()){
            Usuario usuarioMsvc = clientHttp.detalle(usuario.getId());

            Curso curso = o.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);//curso con nuevo alumno

            cursoRepository.save(curso);

            //Devolvemos el usuario y lo mostramos
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuarioACurso(Usuario usuario, Long cursoId) {

        Optional<Curso> o = cursoRepository.findById(cursoId);

        if(o.isPresent()){
            Usuario usuarioNuevoMsvc = clientHttp.crear(usuario);

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);

            cursoRepository.save(curso);

            return Optional.of(usuarioNuevoMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuarioACurso(Usuario usuario, Long cursoId) {

        Optional<Curso> o = cursoRepository.findById(cursoId);

        if(o.isPresent()){
            Usuario usuarioMsvc = clientHttp.detalle(usuario.getId());

            Curso curso = o.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.removeCursoUsuario(cursoUsuario);//curso con nuevo alumno

            cursoRepository.save(curso);

            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }


}
