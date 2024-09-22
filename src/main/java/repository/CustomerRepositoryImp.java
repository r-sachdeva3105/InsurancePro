package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.Customer;

public class CustomerRepositoryImp implements CustomerRepository {
	//facing issue with relative path
	//will figure out later
    private static final String FILE_PATH = "C:\\Users\\samch\\OneDrive\\Documents\\Humber\\Sem3\\J2EE\\InsurancePro\\customer.json";
    

    

    @Override
    public void addCustomer(Customer customer) throws Exception{
        List<Customer> customers = getAllCustomers();
        List<Customer> customerCheck = customers.stream()
                .filter(cust -> cust.getEmail().equals(customer.getEmail()))
                .collect(Collectors.toList());
        if(!customerCheck.isEmpty()) {
        	throw new Exception("Customer already exsists");
        }
        	
        customers.add(customer);
        saveToFile(customers);
    }
    
    @Override
    public Customer getCustomerById(String id) {
        return getAllCustomers().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Customer> getAllCustomers() {
    	List<Customer> customers = new ArrayList<>();
    	File file = new File(FILE_PATH);
        System.out.println("Looking for file at: " + file.getAbsolutePath());
    	try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    	    StringBuilder json = new StringBuilder();
    	    String line;
    	    while ((line = reader.readLine()) != null) {
    	        json.append(line);
    	    }
    	    // Trim the JSON array brackets
    	    String jsonString = json.toString().trim();
    	    jsonString = jsonString.substring(1, jsonString.length() - 1); // Remove the outer brackets

    	    // Split by "}, {" to get individual customer strings
    	    String[] customerStrings = jsonString.split("\\},\\s*\\{");
    	    for (String customerString : customerStrings) {
    	        customerString = customerString.replaceAll("[\\{\\}\"]", "").trim();
    	        String[] attributes = customerString.split(",\\s*");
    	        
    	        String id = null, name = null, email = null, phone = null;
    	        for (String attribute : attributes) {
    	            String[] keyValue = attribute.split(":");
    	            if (keyValue.length == 2) {
    	                switch (keyValue[0].trim()) {
    	                    case "id":
    	                        id = keyValue[1].trim();
    	                        break;
    	                    case "name":
    	                        name = keyValue[1].trim();
    	                        break;
    	                    case "email":
    	                        email = keyValue[1].trim();
    	                        break;
    	                    case "phone":
    	                        phone = keyValue[1].trim();
    	                        break;
    	                }
    	            }
    	        }
    	        customers.add(new Customer(id, name, email, phone));
    	    }
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	return customers;
    }

    @Override
    public void updateCustomer(Customer customer) {
    	 List<Customer> customers = getAllCustomers();
    	    boolean customerFound = false;

    	    // Iterate through the customer list to find and update the customer
    	    for (int i = 0; i < customers.size(); i++) {
    	        if (customers.get(i).getId().equals(customer.getId())) {
    	            customers.set(i, customer); // Replace the existing customer with the updated one
    	            customerFound = true;
    	            break;
    	        }
    	    }

    	    // If the customer was found and updated, save the updated list back to the file
    	    if (customerFound) {
    	        saveToFile(customers);
    	    } else {
    	        // Handle the case when the customer is not found
    	        throw new IllegalArgumentException("Customer with ID " + customer.getId() + " not found.");
    	    }
    }

    @Override
    public void deleteCustomer(String id) throws Exception {
        List<Customer> customers = getAllCustomers();
        if(customers.removeIf(customer -> customer.getId().equals(id)))
        	saveToFile(customers);
        else {
	        // Handle the case when the customer is not found
	        throw new Exception("Customer with ID " + id + " not found.");
	    }
        
    }

    private void saveToFile(List<Customer> customers) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            json.append("{")
                .append("\"id\":\"").append(customer.getId()).append("\",")
                .append("\"name\":\"").append(customer.getName()).append("\",")
                .append("\"email\":\"").append(customer.getEmail()).append("\",")
                .append("\"phone\":\"").append(customer.getPhone()).append("\"")
                .append("}");
            if (i < customers.size() - 1) json.append(",");
        }
        json.append("]");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	
}