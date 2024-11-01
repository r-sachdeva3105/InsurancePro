package service;

import java.util.List;

import entity.Customer;
import jakarta.servlet.ServletContext;
import repository.CustomerRepository;
import repository.CustomerRepositoryImp;

// Defining service class
public class CustomerService {
    private CustomerRepository customerRepository;
    private PolicyService policyService;

    // Performing dependency injection using constructor
    public CustomerService(ServletContext context) {
        this.customerRepository = new CustomerRepositoryImp();  // Passing context to repository
        this.policyService = new PolicyService(context);
    }

    // Add customer
    public void addCustomer(Customer customer) throws Exception {
        try {
            customerRepository.addCustomer(customer);
        } catch (Exception e) {
            throw new Exception("Error while adding customer: " + e.getMessage(), e);
        }
    }

    // Get customer by ID
    public Customer getCustomerById(String id) {
        return customerRepository.getCustomerById(id);
    }

    // Get all customers
    public List<Customer> getAllCustomers(int brokerId) {
        return customerRepository.getAllCustomersByBrokerId(brokerId);
    }

    // Update customer
    public void updateCustomer(Customer customer) {
        try {
            customerRepository.updateCustomer(customer);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating customer: " + e.getMessage(), e);
        }
    }

    // Delete customer
    public boolean deleteCustomer(String id) {
        try {
            customerRepository.deleteCustomer(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
        
    }
    public List<Object[]> getCustomerCountByBrokerID() {
        return customerRepository.getCustomerCountByBrokerID();
    }
}
