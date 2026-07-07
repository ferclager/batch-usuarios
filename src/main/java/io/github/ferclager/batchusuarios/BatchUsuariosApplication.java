package io.github.ferclager.batchusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchUsuariosApplication.class, args);
		System.out.println(">>> accede a http://localhost:8080/h2-console");
	}
}
