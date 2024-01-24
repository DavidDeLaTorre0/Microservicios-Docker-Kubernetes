package org.ddelatorre.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name="cursos_usuarios")
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //id usuario
    @Column(name="usuario_id", unique = true)
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Override
    public boolean equals(Object obj) {
        //esto dice : la instancia es igual al objeto?
        if(this==obj){
            return true;
        }
        //El objeto no es una instancia de curso usuario? dev false
        if( !(obj instanceof CursoUsuario)){
            return false;
        }

        //Ahora comparamos por id
        CursoUsuario o = (CursoUsuario) obj;
        return this.usuarioId != null && this.usuarioId.equals(o.usuarioId);
    }
}
