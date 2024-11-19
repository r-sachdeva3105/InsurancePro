package repository;

import java.util.List;

import entity.Claims;

public interface ClaimsRepository {
    void addClaim(Claims claim);
    void updateClaim(int claimId, String description);
    Claims getClaimById(String id);
    List<Object[]> getAllClaims();
    List<Object[]>  getClaimsByBrokerId(int brokerId);
    void  deleteClaimsByCustomerId(int customerId) throws Exception;
    void  deleteClaimsByPolicyId(int policyId) throws Exception;
    void deleteClaim(String id) throws Exception;
    void approveRejectClaim(int id, String status);
    List<Object[]> getTotalClaims();
    List<Object[]> getClaimsRate();
}

