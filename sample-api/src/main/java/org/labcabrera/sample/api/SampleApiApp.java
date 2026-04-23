package org.labcabrera.sample.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SampleApiApp {

	public static void main(String[] args) {
		SpringApplication.run(SampleApiApp.class, args);
	}

}
