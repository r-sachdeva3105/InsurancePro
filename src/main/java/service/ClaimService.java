package service;

import java.util.List;

import entity.Claims;
import entity.Customer;
import entity.Policy;
import entity.PolicyDetails;
import jakarta.servlet.ServletContext;
import repository.ClaimsRepositoryImp;
import repository.CustomerRepository;
import repository.CustomerRepositoryImp;
import repository.PolicyAssignmentDetailsRepositoryImpl;

public class ClaimService {
	
	private ClaimsRepositoryImp claimsRepository;
    private PolicyService policyService;

    // Performing dependency injection using constructor
    public ClaimService(ServletContext context) {
        this.claimsRepository = new ClaimsRepositoryImp(); 
        this.policyService = new PolicyService(context);
    }

    // Add customer
    public boolean addClaim(String policyName, int brokerId,int customerId, String description, double amount) throws Exception {
     
        int policyId = 0;
        double coverageAmount = 0.0;
        System.out.println(policyName);
		for (Policy policy : policyService.getAllPolicies()) {

			if (policy.getName().equals(policyName)) {

				policyId = policy.getId();
				coverageAmount = policy.getCoverageAmount();
				System.out.println(policyId);
				break;
			}

		}
		if (policyId != 0 && amount <= coverageAmount) {

			Claims claims = new Claims(policyId, brokerId,customerId ,description, amount);
			claimsRepository.addClaim(claims);
			System.out.println("vbnm,yuijk");
			return true;
		}
		System.out.println("sdfghq345trd");
		return false;
    }

    // Get customer by ID
    public Claims getClaimById(int id) {
        return claimsRepository.getClaimById(id);
    }

    // Get all customers
    public List<Object[]> getAllClaimsByBrokerId(int id) {
        return claimsRepository.getClaimsByBrokerId(id);
    }
    // Get all customers
    public List<Object[]> getAllClaims() {
        return claimsRepository.getAllClaims();
    }

    // Update customer
    public boolean updateClaim(int claimId, String policyName, String description, Double amount) {
        try {
        	
        	int policyId = 0;
            double coverageAmount = 0.0;
            System.out.println(policyName);
    		for (Policy policy : policyService.getAllPolicies()) {

    			if (policy.getName().equals(policyName)) {

    				policyId = policy.getId();
    				coverageAmount = policy.getCoverageAmount();
    				System.out.println(policyId);
    				break;
    			}

    		}
    		if (policyId != 0 && amount <= coverageAmount) {
    			claimsRepository.updateClaim(claimId, description, amount);
    			return true;
    		}
    		return false;
        	
        } catch (Exception e) {
        	return false;
            
        }
    }

    // Delete customer
    public boolean deleteClaim(int id) {
        try {
        	claimsRepository.deleteClaim(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }

    public boolean approveRejectClaim(int id, String status) {
    	 try {
         	claimsRepository.approveRejectClaim(id, status);
         	return true;
         } catch (Exception e) {
         	return false;
             
         }
    	
    }
    public List<Object[]> getTotalClaims() {
        return claimsRepository.getTotalClaims();
    }
    
    public List<Object[]> getClaimsRate() {
        return claimsRepository.getClaimsRate();
    }
}
