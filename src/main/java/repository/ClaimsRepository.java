package repository;

import java.util.List;

import entity.Claims;

public interface ClaimsRepository {
    void addClaim(Claims claim);
    void updateClaim(Claims claim);
    Claims getClaimById(int id);
    List<Claims> getAllClaims();
    List<Claims> getClaimsByBrokerId(String brokerId);
}

