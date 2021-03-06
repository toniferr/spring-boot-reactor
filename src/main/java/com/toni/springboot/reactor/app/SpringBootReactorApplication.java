package com.toni.springboot.reactor.app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.toni.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args){
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<String> usuariosList = new ArrayList<>();
		usuariosList.add("Gonzalo Fulano");
		usuariosList.add("Toni Fulano");
		usuariosList.add("Maria Vulcana");
		usuariosList.add("Alba Vulcana");
		usuariosList.add("Manolo Willis");
		usuariosList.add("Bruce Willis");
		
		Flux<String> nombres = Flux.fromIterable(usuariosList);		
//		Flux<String> nombres = Flux.just("Gonzalo Fulano","Toni Fulano","Maria Vulcano","Alba Vulcana","Manolo Willis","Jose Willis","Manolo Sánchez");
				
		//sin asignarlo a la variable usuarios no se subscribe a usuarios. pintaría todos los nombres
		Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
			.filter(usuario -> usuario.getNombre().toLowerCase().equals("manolo"))
//				.doOnNext(System.out::println);
			.doOnNext(usuario -> {
				if (usuario == null) {
					throw new RuntimeException("Nombres no pueden ser vacíos");
				}
				System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellidos()));					
			})
			.map(usuario -> {
				String nombre = usuario.getNombre().toLowerCase();
				usuario.setNombre(nombre);
				return usuario;
				});
		
		
//		nombres.subscribe(log::info);
		nombres.subscribe(e -> log.info(e.toString()),
			error -> log.error(error.getMessage()),
			new Runnable() {

				@Override
				public void run() {
					log.info("Ha finalizado la ejecución del observable con éxito");
				}
				
			});
	};
		
}
