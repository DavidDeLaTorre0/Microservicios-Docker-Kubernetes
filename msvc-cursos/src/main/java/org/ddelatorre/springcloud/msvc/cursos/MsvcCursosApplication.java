package org.ddelatorre.springcloud.msvc.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//EnableFeignClients habilitamos en nuestra aplicacion el contexto de feing y para implementar nuestra ApiRest de forma declarativa,
//Como funciona: Es una interfaz muy parecido a CRUDRepository
@EnableFeignClients
@SpringBootApplication
public class MsvcCursosApplication {

	public static void main(String[] args) {

		SpringApplication.run(MsvcCursosApplication.class, args);

	}

}
