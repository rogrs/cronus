package br.com.rogrs.cronus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CronusApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronusApplication.class, args);
	}

}
