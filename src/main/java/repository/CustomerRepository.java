package repository;

import java.util.List;

import entity.Customer;

public interface CustomerRepository {
    void addCustomer(Customer customer);
    Customer getCustomerById(String id);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(String id);
}