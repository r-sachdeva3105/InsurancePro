package service;

import java.util.List;

import entity.Customer;
import entity.Policy;
import entity.PolicyDetails;
import repository.PolicyAssignmentDetailsRepositoryImpl;

public class PolicyAssignmentService {
	
	PolicyAssignmentDetailsRepositoryImpl assignmentRepo;
	PolicyService policyService;
	
	public PolicyAssignmentService() {
		
		assignmentRepo = new PolicyAssignmentDetailsRepositoryImpl();
		policyService = new PolicyService();
	}
	

	public boolean addPolicyDetails(String customerId, String policyName, String brokerId, String premiumAmount) throws Exception {
		
		String policyId = "";
		for(Policy policy: policyService.getAllPolicies()){
			
			if(policy.getName().equals(policyName)) {
				
				policyId = policy.getId();
				System.out.println(policyId);
				break;
			}
			
		}
		if(policyId != "") {
			
			PolicyDetails details = new PolicyDetails(customerId,policyId,brokerId,premiumAmount );
					// Business logic can go here
		    assignmentRepo.addPolicyDetails(details);
		    System.out.println("vbnm,yuijk");
		    return true;
		}
		System.out.println("sdfghq345trd");
		return false;
		
	}


	public List<PolicyDetails> getDetailsForCustomer(String id) {
		// TODO Auto-generated method stub
		return assignmentRepo.getAssignedPolicyForCustomer(id);
	}

	
	

}
