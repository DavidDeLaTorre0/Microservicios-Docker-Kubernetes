package org.ddelatorre.springcloud.msvc.cursos.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Usuario {

    //ESTA CLASE NO ESTA EN ENTITY PORQUE ES UNA CLASE, UN POJO UNA CLASE DE MODELO,
    //ES DATO, SON CLASES SON POJOS INDEPENDIENTEMENTE DE SI SON ENTITY O NO
    //CONTIENE LOS MISMOS ATRIBUTOS QUE LA ENTITY USUARIO DE MCSV-USUARIO


    private Long id;

    private String name;

    private String email;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
