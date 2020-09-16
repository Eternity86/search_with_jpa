package ru.eternity074.search;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.eternity074.search.service.CustomerService;

public class CustomerTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
		appContext.scan("ru.eternity074.search");
		appContext.refresh();
		
		CustomerService customerService = (CustomerService) appContext.getBean("customerService");
		customerService.test();
		
		appContext.close();
	}
}
