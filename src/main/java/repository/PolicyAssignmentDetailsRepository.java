package repository;

import java.util.List;

import entity.Customer;
import entity.PolicyDetails;

public interface PolicyAssignmentDetailsRepository {
	
	 List<PolicyDetails> getAssignedPolicyForCustomer(String id);
	 
	void addPolicyDetails(PolicyDetails details);
	
}

