package org.ddelatorre.springcloud.msvc.usuarios.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;

    //@Email valida el correo electronico pero valida cuando el correo no tiene dominio asique es mejor ponerle expresiones regulares
    @NotEmpty
    @Pattern(regexp=".+@.+\\.[a-z]+", message="Dirección de correo inválido!")
    @Column(unique = true)
    private String email;

    /*  En Strings es NotEmpty(message = "el mesaje que queramos") NotEmpty (message = "El campo nombre no puede ser vacio")
        OJO QUE NOS PUEDEN COLAR UN ESPACIO EN BLANCO, ES MEJOR USAR @NOTBLANK
    *   NotNull para tipos de int, Long, Date o cualquier otro objeto
    *    NotNull es la verificacion de que no se introduzcan valores nulos,
     *  NotBlank y  para que NO SE PUEDAN PONER ESPACIOS EN BLANCO como valor ni valores vacios se usa
     *
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
