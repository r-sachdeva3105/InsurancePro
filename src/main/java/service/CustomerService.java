package service;

import java.util.List;

import entity.Customer;
import entity.Policy;
import entity.PolicyDetails;
import jakarta.servlet.ServletContext;
import repository.CustomerRepository;
import repository.CustomerRepositoryImp;

// defining service class
public class CustomerService {
	private CustomerRepository customerRepository;
	private PolicyService policyService;

	// performing dependency injection using constructor
	public CustomerService(ServletContext context) {
		this.customerRepository = new CustomerRepositoryImp(context);
		policyService = new PolicyService(context);
	}

	// add customer
	public void addCustomer(Customer customer) throws Exception {

		customerRepository.addCustomer(customer);
	}

	// get customer by ID
	public Customer getCustomerById(String id) {
		return customerRepository.getCustomerById(id);
	}

	// get all customer
	public List<Customer> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}

	// update Customer
	public void updateCustomer(Customer customer) {

		customerRepository.updateCustomer(customer);

	}

	// delete Customer
	public boolean deleteCustomer(String id) {
		try {
			customerRepository.deleteCustomer(id);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}