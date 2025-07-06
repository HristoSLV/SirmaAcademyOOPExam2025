package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    private final Map<String, Customer> customers = new HashMap<>();

    public Customer getOrCreateCustomer(String name) {
        return customers.computeIfAbsent(name, Customer::new);
    }
}
