package org.ddelatorre.springcloud.msvc.cursos.controller;

import jakarta.validation.Valid;
import org.ddelatorre.springcloud.msvc.cursos.entity.Curso;
import org.ddelatorre.springcloud.msvc.cursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(name = "/")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    /*
        Se puede poner sol List<Curso>, pero asi es mas implicito
    */
    @GetMapping
    public ResponseEntity<List<Curso>> listar(){
       return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id){
        Optional<Curso> o = cursoService.porId(id);
        if(o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    /*
            VALID
       - valida el usuario que llega del request,
       - aun asi hay que manejar el mensaje de error para esto hay un atributo/objeto especializado que se llama
       - BlindingResult
    */
    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid  @RequestBody Curso curso,BindingResult result){

        if(result.hasErrors()){
            return validarError(result);
        }
        Curso cursoDb = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso,BindingResult result, @PathVariable Long id){

        if(result.hasErrors()){
            return validarError(result);
        }

        Optional<Curso> o = cursoService.porId(id);
        if(o.isPresent()){
            Curso cursoDb = o.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/id")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Curso> o = cursoService.porId(id);
        if(o.isPresent()){
            cursoService.eliminar(id);
            //cursoService.eliminar(o.get().getId());
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    //Con el correo verifica que tenga @, sea una direccion de formato correo
    // y muestra los mensajes de los valores vacios introducidos explicando su error
    private static ResponseEntity<Map<String, String>> validarError(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            //Se detecta automaticamente el lenguaje de mi S.O
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
