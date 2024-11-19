package service;

import java.util.Date;
import java.util.List;

import entity.Customer;
import entity.Policy;
import entity.PolicyDetails;
import jakarta.servlet.ServletContext;
import repository.PolicyAssignmentDetailsRepositoryImpl;

// defining service class
public class PolicyAssignmentService {

	PolicyAssignmentDetailsRepositoryImpl assignmentRepo;
	PolicyService policyService;

	// performing dependency injection using constructor
	public PolicyAssignmentService(ServletContext context) {

		assignmentRepo = new PolicyAssignmentDetailsRepositoryImpl();
		policyService = new PolicyService(context);
	}

// add policy details
	public boolean addPolicyDetails(int customerId, String policyName, int brokerId, Date startDate, Date endDate, String status)
			throws Exception {

		int policyId = 0;
		Double premiumAmount = 0.0;
		for (Policy policy : policyService.getAllPolicies()) {

			if (policy.getName().equals(policyName)) {

				policyId = policy.getId();
				premiumAmount = policy.getBaseRate() * policy.getCoverageAmount() * policy.getTermFactor();
;				System.out.println(policyId);
				break;
			}

		}
		if (policyId != 0) {

			
			PolicyDetails details = new PolicyDetails(customerId, policyId, brokerId, premiumAmount, startDate, endDate, status);
			assignmentRepo.addPolicyDetails(details);
			System.out.println("vbnm,yuijk");
			return true;
		}
		System.out.println("sdfghq345trd");
		return false;

	}

// get policy details for customer
	public List<Object[]> getDetailsForCustomer(int id, int brokerId) {
		// TODO Auto-generated method stub
		return assignmentRepo.getAssignedPolicyForCustomer(id, brokerId);
	}

}
