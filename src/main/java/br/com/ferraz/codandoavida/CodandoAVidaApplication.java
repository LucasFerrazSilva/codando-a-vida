package br.com.ferraz.codandoavida;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CodandoAVidaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodandoAVidaApplication.class, args);
	}

}
