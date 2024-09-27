package repository;

import java.util.List;

import entity.Policy;

public interface PolicyRepository {
    void addPolicy(Policy policy) throws Exception;
    Policy getPolicyById(String id);
    List<Policy> getAllPolicies();
    void updatePolicy(Policy policy);
    void deletePolicy(String id) throws Exception;
}
