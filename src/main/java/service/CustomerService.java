package service;

import java.util.List;

import entity.Customer;
import repository.CustomerRepository;
import repository.CustomerRepositoryImp;

public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = new CustomerRepositoryImp();
    }

    public void addCustomer(Customer customer) {
        // Business logic can go here
        customerRepository.addCustomer(customer);
    }

    public Customer getCustomerById(String id) {
        return customerRepository.getCustomerById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public void updateCustomer(Customer customer) {
        customerRepository.updateCustomer(customer);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteCustomer(id);
    }
}