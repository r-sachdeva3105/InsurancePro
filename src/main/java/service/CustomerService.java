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

	public void addCustomer(Customer customer) throws Exception {
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