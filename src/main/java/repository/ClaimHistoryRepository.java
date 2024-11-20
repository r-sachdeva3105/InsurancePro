package repository;

import java.util.List;

import entity.ClaimsHistory;

public interface ClaimHistoryRepository {
	
	 void addClaimHistory(ClaimsHistory claim);
	 
	 List<Object[]> getClaimsHistoryByPolicy(int policyId);
	  
	 void  deleteClaimHistorybyClaimId(int claimId) throws Exception;
	    

}
