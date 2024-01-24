package org.ddelatorre.springcloud.msvc.cursos.repository;

import org.ddelatorre.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {
}
