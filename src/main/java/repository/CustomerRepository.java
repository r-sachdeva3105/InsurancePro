package repository;

import java.util.List;

import entity.Customer;

// defining a Customer Repository Interface
public interface CustomerRepository {
    void addCustomer(Customer customer) throws Exception;
    Customer getCustomerById(String id);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(String id) throws Exception;
}