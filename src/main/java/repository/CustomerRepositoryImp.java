package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import entity.Customer;
import entity.PolicyDetails;
import jakarta.servlet.ServletContext;

//defining a Customer Repository Interface implementation
public class CustomerRepositoryImp implements CustomerRepository {
	// facing issue with relative path
	// will figure out later
	private static final String FILE_PATH = "customer.json";

	private ServletContext context;

	// Get the file as an InputStream using ServletContext
	// tried not working
	public CustomerRepositoryImp(ServletContext context) {
		this.context = context;
	}

	// method to add customer
	@Override
	public synchronized void  addCustomer(Customer customer) throws Exception {
		List<Customer> customers = getAllCustomers();
		List<Customer> customerCheck = customers.stream().filter(cust -> cust.getEmail().equals(customer.getEmail()))
				.collect(Collectors.toList());
		if (!customerCheck.isEmpty()) {
			throw new Exception("Customer already exsists");
		}

		customers.add(customer);
		saveToFile(customers);
	}

	// method to get customer by Id
	@Override
	public Customer getCustomerById(String id) {
		return getAllCustomers().stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
	}

	// method to get all customer
	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		File file = new File(context.getRealPath(FILE_PATH));
		System.out.println("Looking for file at: " + file.getAbsolutePath());

		// reading from file
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder json = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}

			// removing extra [,{,},] and , to read the data 
			String jsonString = json.toString().trim();
			jsonString = jsonString.substring(1, jsonString.length() - 1);

			String[] customerStrings = jsonString.split("\\},\\s*\\{");
			for (String customerString : customerStrings) {
				customerString = customerString.replaceAll("[\\{\\}\"]", "").trim();

				String[] attributes = customerString.split(",\\s*");

				String id = null, name = null, email = null, phone = null, policys = null;

				for (String attribute : attributes) {

					String[] keyValue = attribute.split(":");
					if (keyValue.length == 2) {
						String key = keyValue[0].trim();
						String value = keyValue[1].trim();

						switch (key) {
						case "id":
							id = value;
							break;
						case "name":
							name = value;
							break;
						case "email":
							email = value;
							break;
						case "phone":
							phone = value;
							break;
						case "policy":
							policys = "[]";

						}
					}
				}
				Customer customer = new Customer(id, name, email, phone, policys);
				customers.add(customer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customers;
	}

	// method to update customer
	@Override
	public synchronized void updateCustomer(Customer customer) {
		List<Customer> customers = getAllCustomers();
		boolean customerFound = false;

		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getId().equals(customer.getId())) {
				customers.set(i, customer);
				customerFound = true;
				break;
			}
		}

		if (customerFound) {
			saveToFile(customers);
		} else {

			throw new IllegalArgumentException("Customer with ID " + customer.getId() + " not found.");
		}
	}

	// method to delete customer
	@Override
	public synchronized void deleteCustomer(String id) throws Exception {
		List<Customer> customers = getAllCustomers();
		if (customers.removeIf(customer -> customer.getId().equals(id)))
			saveToFile(customers);
		else {

			throw new Exception("Customer with ID " + id + " not found.");
		}

	}

	// method to save customer data in json file by converting it in JSON format
	private synchronized void saveToFile(List<Customer> customers) {
		StringBuilder json = new StringBuilder("[");

		StringJoiner customerJoiner = new StringJoiner(",");
		for (Customer customer : customers) {
			StringBuilder customerJson = new StringBuilder();
			customerJson.append("{").append("\"id\":\"").append(customer.getId()).append("\",").append("\"name\":\"")
					.append(customer.getName()).append("\",").append("\"email\":\"").append(customer.getEmail())
					.append("\",").append("\"phone\":\"").append(customer.getPhone()).append("\",")
					.append("\"policy\":[");

			customerJson.append("]");

			customerJson.append("}");

			customerJoiner.add(customerJson.toString());
		}

		json.append(customerJoiner.toString()).append("]");
		File file = new File(context.getRealPath(FILE_PATH));
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}