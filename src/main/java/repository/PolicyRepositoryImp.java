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

import entity.Policy;

public class PolicyRepositoryImp implements PolicyRepository {
	private static final String FILE_PATH = "C:\\j2ee\\Assignment 1\\InsurancePro\\policy.json";



    @Override
    public void addPolicy(Policy policy) throws Exception {
        List<Policy> policies = getAllPolicies();
        policies.add(policy);
        saveToFile(policies);
        System.out.println("Adding policy: " + policy.getId() + ", " + policy.getName());
    }

    @Override
    public Policy getPolicyById(String id) {
        return getAllPolicies().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Policy> getAllPolicies() {
        List<Policy> policies = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            // Trim the JSON array brackets
            String jsonString = json.toString().trim();
            jsonString = jsonString.substring(1, jsonString.length() - 1); // Remove the outer brackets

            // Split by "}, {" to get individual policy strings
            String[] policyStrings = jsonString.split("\\},\\s*\\{");
            for (String policyString : policyStrings) {
                policyString = policyString.replaceAll("[\\{\\}\"]", "").trim();

                // Split by commas to get key-value pairs
                String[] attributes = policyString.split(",\\s*");

                String id = null, name = null, description = null, type = null;

                for (String attribute : attributes) {
                    // Split key and value
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
                            case "description":
                                description = value;
                                break;
                            case "type":
                                type = value;
                                break;
                        }
                    }
                }
                Policy policy = new Policy(id, name, description, type);
                policies.add(policy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return policies;
    }

    @Override
    public void updatePolicy(Policy policy) {
        List<Policy> policies = getAllPolicies();
        
        boolean policyFound = false;

        for (int i = 0; i < policies.size(); i++) {
            if (policies.get(i).getId().equals(policy.getId())) {
                policies.set(i, policy); // Replace the existing policy with the updated one
                policyFound = true;
                break;
            }
        }

        if (policyFound) {
            saveToFile(policies);
        } else {
            throw new IllegalArgumentException("Policy with ID " + policy.getId() + " not found.");
        }
    }

    @Override
    public void deletePolicy(String id) throws Exception {
        List<Policy> policies = getAllPolicies();
        if (policies.removeIf(policy -> policy.getId().equals(id))) {
            saveToFile(policies);
        } else {
            throw new Exception("Policy with ID " + id + " not found.");
        }
    }

    private void saveToFile(List<Policy> policies) {
        StringBuilder json = new StringBuilder("[");
        
        StringJoiner policyJoiner = new StringJoiner(",");
        for (Policy policy : policies) {
            StringBuilder policyJson = new StringBuilder();
            policyJson.append("{")
                .append("\"id\":\"").append(policy.getId()).append("\",")
                .append("\"name\":\"").append(policy.getName()).append("\",")
                .append("\"description\":\"").append(policy.getDescription()).append("\",")
                .append("\"type\":\"").append(policy.getType()).append("\"")
                .append("}");

            policyJoiner.add(policyJson.toString());
        }
        
        json.append(policyJoiner.toString()).append("]");
        System.out.println("Saving to file: " + json.toString());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
