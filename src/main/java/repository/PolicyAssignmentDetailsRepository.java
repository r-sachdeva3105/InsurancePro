package repository;

import java.util.List;

import entity.Customer;
import entity.PolicyDetails;

//defining a Policy Assignment Repository Interface
public interface PolicyAssignmentDetailsRepository {

	List<Object[]> getAssignedPolicyForCustomer(int id, int brokerId);
	void  deletePolicyDetailsByCustomerId(int customerId) throws Exception;
    void  deletePolicyDetailsByPolicyId(int policyId) throws Exception;
    PolicyDetails getDetailsforCustomer(int customerId, int policyId, int brokerId);
	void addPolicyDetails(PolicyDetails details);

}
