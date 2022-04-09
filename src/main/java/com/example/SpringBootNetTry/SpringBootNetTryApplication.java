package com.example.SpringBootNetTry;

import com.example.SpringBootNetTry.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringBootNetTryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootNetTryApplication.class, args);
	}

}
