package repository; // Package for data repository classes

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import entity.Policy; // Importing the Policy entity
import jakarta.servlet.ServletContext; // Servlet context for web application

// Implementation of the PolicyRepository interface
public class PolicyRepositoryImp implements PolicyRepository {
	// File path for the JSON file to store policies
	private static final String FILE_PATH = "/policy.json"; // Adjust this path
	private ServletContext context; // Servlet context

	// Constructor to initialize PolicyRepository with ServletContext
	public PolicyRepositoryImp(ServletContext context) {
		this.context = context;
	}

	// Method to add a new policy
	@Override
	public synchronized void addPolicy(Policy policy) throws Exception {
		List<Policy> policies = getAllPolicies(); // Retrieve existing policies
		policies.add(policy); // Add the new policy
		saveToFile(policies); // Save updated policy list to file
		System.out.println("Adding policy: " + policy.getId() + ", " + policy.getName());
	}

	// Method to get a policy by its ID
	@Override
	public Policy getPolicyById(String id) {
		return getAllPolicies().stream() // Stream through policies
				.filter(p -> p.getId().equals(id)) // Filter by ID
				.findFirst() // Get first match
				.orElse(null); // Return null if not found
	}

	// Method to retrieve all policies from the file 
	@Override
	public List<Policy> getAllPolicies() {
		List<Policy> policies = new ArrayList<>(); // List to hold policies

		// Reading the policies from the JSON file
		File file = new File(context.getRealPath(FILE_PATH));
		System.out.println("Looking for file at: " + file.getAbsolutePath());
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder json = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				json.append(line); // Append each line to the JSON string
			}

			// Trim the JSON array brackets
			String jsonString = json.toString().trim();
			jsonString = jsonString.substring(1, jsonString.length() - 1); // Remove outer brackets

			// Split by "}, {" to get individual policy strings
			String[] policyStrings = jsonString.split("\\},\\s*\\{");
			for (String policyString : policyStrings) {
				policyString = policyString.replaceAll("[\\{\\}\"]", "").trim(); // Clean up string

				// Split by commas to get key-value pairs
				String[] attributes = policyString.split(",\\s*");

				String id = null, name = null, description = null, type = null;

				for (String attribute : attributes) {
					// Split key and value
					String[] keyValue = attribute.split(":");
					if (keyValue.length == 2) {
						String key = keyValue[0].trim();
						String value = keyValue[1].trim();

						// Assign values based on key
						switch (key) {
							case "id": id = value; break;
							case "name": name = value; break;
							case "description": description = value; break;
							case "type": type = value; break;
						}
					}
				}
				Policy policy = new Policy(id, name, description, type); // Create Policy object
				policies.add(policy); // Add to list
			}
		} catch (IOException e) {
			e.printStackTrace(); // Print stack trace on error
		}
		return policies; // Return list of policies
	}

	// Method to update an existing policy
	@Override
	public synchronized void updatePolicy(Policy policy) {
		List<Policy> policies = getAllPolicies(); // Retrieve existing policies

		boolean policyFound = false; // Flag for policy existence

		for (int i = 0; i < policies.size(); i++) {
			if (policies.get(i).getId().equals(policy.getId())) { // Check if policy exists
				policies.set(i, policy); // Update policy
				policyFound = true; // Set flag to true
				break;
			}
		}

		if (policyFound) {
			saveToFile(policies); // Save changes if updated
		} else {
			throw new IllegalArgumentException("Policy with ID " + policy.getId() + " not found."); // Error if not found
		}
	}

	// Method to delete a policy by its ID
	@Override
	public synchronized void deletePolicy(String id) throws Exception {
		List<Policy> policies = getAllPolicies(); // Retrieve existing policies
		if (policies.removeIf(policy -> policy.getId().equals(id))) { // Remove policy if exists
			saveToFile(policies); // Save updated list
		} else {
			throw new Exception("Policy with ID " + id + " not found."); // Error if not found
		}
	}

	// Method to save policies to the JSON file
	private synchronized void saveToFile(List<Policy> policies) {
		StringBuilder json = new StringBuilder("["); // Start JSON array

		StringJoiner policyJoiner = new StringJoiner(","); // Join policies as JSON
		for (Policy policy : policies) {
			StringBuilder policyJson = new StringBuilder();
			policyJson.append("{")
				.append("\"id\":\"").append(policy.getId()).append("\",")
				.append("\"name\":\"").append(policy.getName()).append("\",")
				.append("\"description\":\"").append(policy.getDescription()).append("\",")
				.append("\"type\":\"").append(policy.getType()).append("\"")
				.append("}"); // Build JSON for each policy

			policyJoiner.add(policyJson.toString()); // Add to joiner
		}

		json.append(policyJoiner.toString()).append("]"); // Complete JSON array
		System.out.println("Saving to file: " + json.toString());
		// Write JSON string to file
		File file = new File(context.getRealPath(FILE_PATH));
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace(); // Print stack trace on error
		}
	}
}
