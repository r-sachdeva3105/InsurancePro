package repository;

import java.util.List;

import entity.Claims;

public interface ClaimsRepository {
    void addClaim(Claims claim);
    void updateClaim(int claimId, String description);
    Claims getClaimById(String id);
    List<Claims> getAllClaims();
    List<Object[]>  getClaimsByBrokerId(int brokerId);
    void deleteClaim(String id) throws Exception;
}

