package com.nthing.nthing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan({"data.*"})
@MapperScan({"data.*"})
public class NthingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NthingApplication.class, args);
	}

}
