package repository; // Package for data repository interfaces

import java.util.List; // Importing List interface

import entity.Policy; // Importing Policy entity

// Interface for policy repository operations
public interface PolicyRepository {
    void addPolicy(Policy policy) throws Exception; // Method to add a policy
    Policy getPolicyById(String id); // Method to retrieve a policy by ID
    List<Policy> getAllPolicies(); // Method to get all policies
    void updatePolicy(Policy policy); // Method to update an existing policy
    void deletePolicy(String id) throws Exception; // Method to delete a policy by ID
}
