package com.linkometrics.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {

	public static void main(String[] args)
	{
		SpringApplication.run(Main.class, args);

		//ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	//Spring starts its container.

		//Car car = context.getBean(Car.class);

		//car.Drive();

	}

}


		/**
		Without Spring
		Engine engine = new Engine();

		Car car = new Car();

		car.engine = engine;

		You manually create and connect objects
		 **/


