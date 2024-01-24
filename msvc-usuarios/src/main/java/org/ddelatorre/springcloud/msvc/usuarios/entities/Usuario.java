package org.ddelatorre.springcloud.msvc.usuarios.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    /*  NotNull es la verificacion de que no se introduzcan valores nulos, en Strings es NotEmpty("el mesaje que queramos") NotEmpty (message = "El campo nombre no puede ser vacio")
    *   para tipos de int, Long, Date o cualquier otro objeto es NotNull
    *
    *  Y para que NO SE PUEDAN PONER ESPACIOS EN BLANCO como valor ni valores vacios se usa NotBlank
    */
    @NotBlank
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
