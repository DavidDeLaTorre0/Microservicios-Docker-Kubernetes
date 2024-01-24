package org.ddelatorre.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.ddelatorre.springcloud.msvc.cursos.models.Usuario;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String nombre;

    //orphanRemoval es para que no queden huerfanos,
    // si elimino un curso la idea es que se eliminen o desasignar a los alumnos de ese curso
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //JOINCOLUMN crea la clave foranea en la tabla cursoUsuario
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;


    //@TRANSIENT
    // ESTA ANOTACION HACE QUE EL ATRIBUTO usuarios no este mapeado a la persistencia, a tablas, es solamente un campo que no es parte del contexto de hibernate ni de jpa
    //Solo lo vamos a utilizar para poder poblar los datos completos de los usuarios no solo el id
    @Transient
    private List<Usuario> usuarios;
    public Long getId() {
        return id;
    }

    public Curso() {
        cursoUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarios.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarios.remove(cursoUsuario);
    }

    public List<CursoUsuario> getCursoUsuario() {
        return cursoUsuarios;
    }

    public void setCursoUsuario(List<CursoUsuario> cursoUsuario) {
        this.cursoUsuarios = cursoUsuario;
    }
}
