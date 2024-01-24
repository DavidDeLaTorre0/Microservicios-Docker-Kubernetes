package org.ddelatorre.springcloud.msvc.usuarios.controller;

import feign.Response;
import jakarta.validation.Valid;
import org.ddelatorre.springcloud.msvc.usuarios.entities.Usuario;
import org.ddelatorre.springcloud.msvc.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*  DIFERENCIA DE @RESTCONTROLLER Y @CONTROLLER
*   Controller es para MVC vistas, que utiliza thymeleaf
*   RESTCONTROLLER es para trabajar con APIREST, DEVUELVE POR DEFECTO LOS METODOS "HANDLER"
*   O PROCESAR UNA PETICION CON UN METODO POST GET DELETE....
*
*   DEVUELVEN EL CONTENIDO EN FORMATO JSON o XML, JSON SOBRETODO
*/
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /*Para decir que este metodo es un handler, para un request, para manejar una peticion de usuario
    * POR ESO PONEMOS GETMAPPING*/
    @GetMapping
    public List<Usuario> listar(){
        return usuarioService.listar();
    }

    /*id es una variable que recibimos
    *   @PathVariable para utilizar el id necesitamos esto
    *   Si NO usamos el mismo nobre de la variable, para que spring sepa donde poner el valor
    *   dentro de PathVariable escribimos el name o el value
    *       @PathVariable(name="id") Long ida
    */

    /*    @GetMapping("/{id}")
      public Usuario detalle(@PathVariable Long id){
      Optional <Usuario> usuarioOptional = usuarioService.porId(id);

      if (usuarioOptional.isPresent()){
            return usuarioOptional.get();
      }
        return null;
    }*/

    /*  ResponseEntity<?>
    *   En vez de devolver un objeto al ususario como arriba, queremos darle una respuesta
    *   que contenga el objeto usuario, convertido al JSON
    *   y si no está presente el objeto usuario en vez de devolver null devolvemos un tipo de contenido no encontrado
    *   "error 404 no encontrado"
    *
    *   ResponseEntity<?> la ? se le dice que es como un void, la respuesta http 200, 404, 401, etc
    *   PERO si encuentra el objeto lo devuelve si no no devuelve nada
    *  */
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional <Usuario> usuarioOptional = usuarioService.porId(id);

        if (usuarioOptional.isPresent()){

            /* ok es un status http 200 todo ha salido bien
            *  body es para guardar en el cuerpo el objeto usuario
            *
            *  las dos son iguales, despues del ok autmaticamente se lo pasa al cuerpo
            *  return ResponseEntity.ok().body(usuarioOptional.get());
            */
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return  ResponseEntity.notFound().build();
    }


    // Tu usuario es el que lo envia en el cuerpo del request, hay que indicarlo @RequestBody
    //  De forma automatica el contenido que este en el requestBody se inyecta en usuario y se convierte el JSON que se ve en la peticion HTTP
    // se convierte de forma automatica en este objeto usuario
    // SIEMOPRE CUANDO LOS ATRIBUTOS DEL JSON SE LLAMEN IGUAL QUE LA CLASE USUARIO

    //Devolvemos un mensaje de creado con el https.created, se puede hacer de las dos formas
    //@ResponseStatus(HttpStatus.CREATED)
        /*
            VALID
       - valida el usuario que llega del request,
       - aun asi hay que manejar el mensaje de error para esto hay un atributo/objeto especializado que se llama
       - BlindingResult
    */

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){

        if(result.hasErrors()){
            return validarError(result);
        }
        //para evitar hacer las validaciones de si el correo esta vacío,
        //es solo poner la validación de existe después de result.hasErrors()
        //ya que este tiene esa validación en la configuración de la entidad
        if(usuarioService.findByEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Ya existe un usuario con ese correo electronico!"));//Lo devuelv como un json "error":"msg"
        }

        //hasErrors si tiene error, lo tenemos que pasar a un JSON, pero solo el mensaje de error

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }



    /*          BindingResult resul

            BindingResult resul
            Siempre despues del ObBJETO
     */
    @PutMapping("/{id}")
    //Valid valida los parametros
    //BindingResult está relacionado a la validacion
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validarError(result);
        }

        Optional<Usuario> o = usuarioService.porId(id);
        if(o.isPresent()){
            Usuario usuarioDb = o.get();
            if(!usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) && usuarioService.findByEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("error", "Ya existe un usuario con ese correo electronico!"));
            }

            usuarioDb.setName(usuario.getName());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            //Devolvemos el JSON si no 404
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@Valid @PathVariable Long id){
        Optional<Usuario> o = usuarioService.porId(id);
        if(o.isPresent()){
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //getMapResponseEntity o validarerror
    private static ResponseEntity<Map<String, String>> validarError(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        //getFielErrors devuelve una lista que es iterable, con cada campo con error

        //Obtengo todos los errores y hago un foreach para sacar uno a uno los campoS
        //ERR -> {} es como un arrow function
        result.getFieldErrors().forEach(err -> {
            //EL NOMBRE DEL ERROR, COMO VALOR PONEMOS EL MENSAJE, ERR.GETDEFAULT.. ES EL MENSAJE
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        //va a decir que el request no fue correcto y en el cuerpo de la respuesta colocamos errores
        return ResponseEntity.badRequest().body(errores);
    }
}
