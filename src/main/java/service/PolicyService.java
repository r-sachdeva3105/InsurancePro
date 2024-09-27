package service;

import java.util.List;

import entity.Policy;
import repository.PolicyRepository;
import repository.PolicyRepositoryImp;

public class PolicyService {
    private PolicyRepository policyRepository;

    public PolicyService() {
        this.policyRepository = new PolicyRepositoryImp();
    }

    public void addPolicy(Policy policy) throws Exception {
        // Business logic can go here
        policyRepository.addPolicy(policy);
    }

    public Policy getPolicyById(String id) {
        return policyRepository.getPolicyById(id);
    }

    public List<Policy> getAllPolicies() {
        return policyRepository.getAllPolicies();
    }

    public void updatePolicy(Policy policy) {
        policyRepository.updatePolicy(policy);
    }

    public boolean deletePolicy(String id) {
        try {
            policyRepository.deletePolicy(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
