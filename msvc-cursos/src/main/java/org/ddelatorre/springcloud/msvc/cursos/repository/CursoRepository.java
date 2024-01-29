package org.ddelatorre.springcloud.msvc.cursos.repository;

import org.ddelatorre.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {


     //   MODIFIYING  para que haga el cambio en la tabla
     //   Query es para realizar consultas no es tanto para delete, upadate, insert por eso necesitamos la anotacion MODIFYING
     @Modifying
     @Query("delete from CursUsuario cu where cu.usuario=?1")
     void eliminarCursoUsuarioPorId(Long id);

     //Con JPARepository puedo utililizar esta estructura pero con CrudRepo no parece tenerlo aun
     //void deleteCursoUsuarioById(Long id);
}
