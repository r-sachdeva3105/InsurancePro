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

public class ClaimService {
	
	private ClaimsRepositoryImp claimsRepository;
    private PolicyService policyService;

    // Performing dependency injection using constructor
    public ClaimService(ServletContext context) {
        this.claimsRepository = new ClaimsRepositoryImp();  // Passing context to repository
        this.policyService = new PolicyService(context);
    }

    // Add customer
    public boolean addClaim(String policyName, int brokerId,int customerId, String description) throws Exception {
     
        int policyId = 0;
        System.out.println(policyName);
		for (Policy policy : policyService.getAllPolicies()) {

			if (policy.getName().equals(policyName)) {

				policyId = policy.getId();
				System.out.println(policyId);
				break;
			}

		}
		if (policyId != 0) {

			Claims claims = new Claims(policyId, brokerId,customerId ,description);
			claimsRepository.addClaim(claims);
			System.out.println("vbnm,yuijk");
			return true;
		}
		System.out.println("sdfghq345trd");
		return false;
    }

    // Get customer by ID
    public Claims getClaimById(String id) {
        return claimsRepository.getClaimById(id);
    }

    // Get all customers
    public List<Object[]> getAllClaimsByBrokerId(int id) {
        return claimsRepository.getClaimsByBrokerId(id);
    }
    // Get all customers
    public List<Claims> getAllClaims() {
        return claimsRepository.getAllClaims();
    }

    // Update customer
    public boolean updateClaim(int claimId, String description) {
        try {
        	claimsRepository.updateClaim(claimId, description);
        	return true;
        } catch (Exception e) {
        	return false;
            
        }
    }

    // Delete customer
    public boolean deleteClaim(String id) {
        try {
        	claimsRepository.deleteClaim(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }


}
