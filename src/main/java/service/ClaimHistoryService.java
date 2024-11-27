package service;

import java.util.List;

import entity.Policy;
import repository.ClaimHistoryRepository;
import repository.ClaimHistoryRepositoryImpl;

public class ClaimHistoryService {

	private final ClaimHistoryRepository claimHistory;
	private PolicyService policyService;
	
	public ClaimHistoryService() {
		
		claimHistory = new ClaimHistoryRepositoryImpl();
		policyService = new PolicyService();
		
	}
	
	public List<Object[]> getClaimHistoryByPolicy(String policyName){
		
		int policyId = 0;
		Double premiumAmount = 0.0;
		for (Policy policy : policyService.getAllPolicies()) {

			if (policy.getName().equals(policyName)) {

				policyId = policy.getId();
				break;
			}
		}
		
		return claimHistory.getClaimsHistoryByPolicy(policyId);
	}
	
}

