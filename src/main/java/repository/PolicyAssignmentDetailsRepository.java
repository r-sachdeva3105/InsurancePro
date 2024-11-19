package repository;

import java.util.List;

import entity.Customer;
import entity.PolicyDetails;

//defining a Policy Assignment Repository Interface
public interface PolicyAssignmentDetailsRepository {

	List<Object[]> getAssignedPolicyForCustomer(int id, int brokerId);
	void  deleteClaimsByCustomerId(int customerId) throws Exception;
    void  deleteClaimsByPolicyId(int policyId) throws Exception;

	void addPolicyDetails(PolicyDetails details);

}
