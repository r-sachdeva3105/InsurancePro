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
import entity.Policys;

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

                // Split by commas to get key-value pairs
                String[] attributes = customerString.split(",\\s*");

                String id = null, name = null, email = null, phone = null, policys = null; 
                //List<Policy> policies = new ArrayList<>();

                for (String attribute : attributes) {
                    // Split key and value
                    String[] keyValue = attribute.split(":");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        // Check if the attribute is one of the basic fields or a policy field
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
                                // Policy array starts, handle it
//                                String policyArray = attribute.substring(attribute.indexOf("[") + 1, attribute.indexOf("]")).trim();
//                                if (!policyArray.isEmpty()) {
//                                    // Split by },{ to get individual policy strings
//                                    String[] policyStrings = policyArray.split("\\},\\s*\\{");
//                                    for (String policyString : policyStrings) {
//                                        String[] policyAttributes = policyString.replaceAll("[\\{\\}]", "").split(",\\s*");
//                                        int policyNo = 0, brokerId = 0;
//                                        double premium = 0.0;
//
//                                        // Extract policy details
//                                        for (String policyAttr : policyAttributes) {
//                                            String[] policyKeyValue = policyAttr.split(":");
//                                            if (policyKeyValue.length == 2) {
//                                                String policyKey = policyKeyValue[0].trim();
//                                                String policyValue = policyKeyValue[1].trim();
//                                                
//                                                switch (policyKey) {
//                                                    case "policy_no":
//                                                        policyNo = Integer.parseInt(policyValue);
//                                                        break;
//                                                    case "broker_id":
//                                                        brokerId = Integer.parseInt(policyValue);
//                                                        break;
//                                                    case "premium":
//                                                        premium = Double.parseDouble(policyValue);
//                                                        break;
//                                                }
//                                            }
//                                        }
//                                        policies.add(new Policy(policyNo, brokerId, premium));
//                                    }
//                                }
//                                break;
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
        
        StringJoiner customerJoiner = new StringJoiner(",");
        for (Customer customer : customers) {
            StringBuilder customerJson = new StringBuilder();
            customerJson.append("{")
                .append("\"id\":\"").append(customer.getId()).append("\",")
                .append("\"name\":\"").append(customer.getName()).append("\",")
                .append("\"email\":\"").append(customer.getEmail()).append("\",")
                .append("\"phone\":\"").append(customer.getPhone()).append("\",")
                .append("\"policy\":[");

            // Handle customer policies
            //List<Policys> policies = customer.getPolicies();
            //StringJoiner policyJoiner = new StringJoiner(",");
//            for (Policys policy : policies) {
//                StringBuilder policyJson = new StringBuilder();
//                policyJson.append("{")
//                    .append("\"policy_no\":").append(policy.getPolicyNo()).append(",")
//                    .append("\"broker_id\":").append(policy.getBrokerId()).append(",")
//                    .append("\"premium\":").append(policy.getPremium())
//                    .append("}");
//                policyJoiner.add(policyJson.toString());
//            }

           // customerJson.append(policyJoiner.toString()).append("]");
            customerJson.append("]");
            
            customerJson.append("}");

            customerJoiner.add(customerJson.toString());
        }
        
        json.append(customerJoiner.toString()).append("]");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	
}