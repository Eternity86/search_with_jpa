package ru.eternity074.search.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.eternity074.search.model.Customer;
import ru.eternity074.search.repository.CustomerRepository;

@Service("customerService")
public class CustomerService {
	
	private CustomerRepository repository;

	@Autowired
	public void setRepository(CustomerRepository repository) {
		this.repository = repository;
	}
	
	public void test() {
        // Save a new customer
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("John");
        newCustomer.setLastName("Smith");
         
        repository.save(newCustomer);
         
        // Find a customer by ID
        Optional<Customer> result = Optional.ofNullable(repository.findOne(1L));
        result.ifPresent(customer -> System.out.println(customer));
         
        // Find customers by last name
        List<Customer> customers = repository.findByLastName("Smith");
        customers.forEach(customer -> System.out.println(customer));
         
        // List all customers
        Iterable<Customer> iterator = repository.findAll();
        iterator.forEach(customer -> System.out.println(customer));
         
        // Count number of customer
        long count = repository.count();
        System.out.println("Number of customers: " + count);
    }
	

}
