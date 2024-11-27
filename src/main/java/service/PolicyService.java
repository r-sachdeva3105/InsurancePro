package service; // Package for service layer classes

import java.util.List; // Importing List interface

import entity.Policy; // Importing Policy entity
import jakarta.servlet.ServletContext; // Importing ServletContext for web applications
import repository.PolicyRepository; // Importing PolicyRepository interface
import repository.PolicyRepositoryImp; // Importing PolicyRepository implementation

// Service class for policy-related operations
public class PolicyService {
    private PolicyRepository policyRepository; // Policy repository instance

    // Constructor to initialize the policy repository
    public PolicyService() {
        this.policyRepository = new PolicyRepositoryImp();
    }

    // Method to add a new policy
    public void addPolicy(Policy policy) throws Exception {
        // Business logic can go here
        policyRepository.addPolicy(policy); // Delegate to repository
    }

    // Method to retrieve a policy by ID
    public Policy getPolicyById(Integer id) {
        return policyRepository.getPolicyById(id); // Delegate to repository
    }

    // Method to get all policies
    public List<Policy> getAllPolicies() {
        return policyRepository.getAllPolicies(); // Delegate to repository
    }

    // Method to update an existing policy
    public void updatePolicy(Policy policy) {
        policyRepository.updatePolicy(policy); // Delegate to repository
    }

    // Method to delete a policy by ID
    public boolean deletePolicy(Integer id) {
        try {
            policyRepository.deletePolicy(id); // Attempt to delete policy
            return true; // Return true if successful
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace on error
            return false; // Return false if an error occurs
        }
    }
    public List<Long> getTotalPolicies() {
        return policyRepository.getTotalPolicies();
    }
}
