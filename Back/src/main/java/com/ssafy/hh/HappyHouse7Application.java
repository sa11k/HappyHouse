package com.ssafy.hh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.ssafy.hh.model")
public class HappyHouse7Application {

	public static void main(String[] args) {
		SpringApplication.run(HappyHouse7Application.class, args);
	}

}
