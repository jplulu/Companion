package com.lustermaniacs.companion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class CompanionApplication {
	public static void main(String[] args) {
		SpringApplication.run(CompanionApplication.class, args);
	}

}
