package ru.eternity074.search.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.eternity074.search.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByLastName(String lastName);
}
