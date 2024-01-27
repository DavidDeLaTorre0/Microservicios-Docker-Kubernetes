package org.ddelatorre.springcloud.msvc.cursos.clients;

import org.ddelatorre.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Con FeingClient, el nombre del servicio que vamos a utilizar y la ruta de donde está el microservicio
// la url se quita cuando se integra SpingCloud con kubernetes, lo haria el solo automatico
@FeignClient(name="msvc-usuarios",url="localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);

}
