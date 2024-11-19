package repository;

import java.util.List;

import entity.Customer;

// defining a Customer Repository Interface
public interface CustomerRepository {
    void addCustomer(Customer customer) throws Exception;
    Customer getCustomerById(Integer id);
    List<Customer> getAllCustomersByBrokerId(int brokerId);
    void updateCustomer(Customer customer);
    void deleteCustomer(Integer id) throws Exception;
    List<Object[]> getCustomerCountByBrokerID();

}