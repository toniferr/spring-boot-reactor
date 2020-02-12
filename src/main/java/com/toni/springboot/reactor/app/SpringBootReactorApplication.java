package com.toni.springboot.reactor.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner{

	public static void main(String[] args){
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<String> nombres = Flux.just("Gonzalo","Toni","Jose","Manolo")
//				.doOnNext(elemento -> System.out.println(elemento));
				.doOnNext(System.out::println);
		nombres.subscribe();
		
	}

}