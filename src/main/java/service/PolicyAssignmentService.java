package service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		policyService = new PolicyService();
	}

// add policy details
	public boolean addPolicyDetails(int customerId, String policyName, int brokerId, Date startDate, Date endDate, String status, String termLength) throws Exception {
	    if (policyName == null || policyName.trim().isEmpty()) {
	        System.out.println("Invalid policy name provided.");
	        throw new IllegalArgumentException("Policy name cannot be null or empty");
	    }

	    Policy policyNew = null;
	    for (Policy policy : policyService.getAllPolicies()) {
	        if (policy.getName().equals(policyName)) {
	            policyNew = policy;
	            break;
	        }
	    }

	    if (policyNew != null) {
	    	PolicyDetails details = new PolicyDetails();
	        double premiumAmount = policyNew.getBaseRate() * policyNew.getCoverageAmount() * details.calculateTermFactor(termLength);
	         details = new PolicyDetails(policyNew.getId(),customerId, brokerId, premiumAmount, startDate, endDate, status, termLength);

	        assignmentRepo.addPolicyDetails(details); // Save policy details
	        System.out.println("Policy details added successfully for customer ID: " + customerId);
	        return true;
	    }

	    System.out.println("Policy not found for the name: " + policyName);
	    return false;
	}

// get policy details for customer
	public List<Object[]> getDetailsForCustomer(int id, int brokerId) {
		// TODO Auto-generated method stub
		return assignmentRepo.getAssignedPolicyForCustomer(id, brokerId);
	}
	
	public List<Object[]> getPoliciesforRenewal(int brokerId) {
		// TODO Auto-generated method stub
		return assignmentRepo.getPoliciesforRenewal(brokerId);
	}
	
	public void renewPolicy(int id, String termLength) {
	    // Fetch the policy details and policy
	    PolicyDetails detail = assignmentRepo.getDetailById(id);
	    if (detail == null) {
	        throw new IllegalArgumentException("No policy details found for ID: " + id);
	    }
	    Policy policyNew = policyService.getPolicyById(detail.getPolicyId());
	    
	    // Extract the term length using regex
	    detail.setTermLength(termLength);
	    detail.setTermFactor(detail.calculateTermFactor(termLength));
	    System.out.println(detail.getTermFactor() + " " + detail.getTermLength());
	    detail.setPremiumAmount(policyNew.getBaseRate() * policyNew.getCoverageAmount() * detail.getTermFactor());
	    String regex = "(\\d+)\\s+(\\w+)";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(termLength);

	    if (matcher.find()) {
	        int number = Integer.parseInt(matcher.group(1)); // Extract the numeric part
	        LocalDateTime now = LocalDateTime.now();
	        LocalDateTime futureDateTime = now.plusYears(number); // Add the extracted years

	        // Update the policy detail's end date
	        detail.setEndDate(Date.from(futureDateTime.atZone(ZoneId.systemDefault()).toInstant()));
	        
	        assignmentRepo.updatePolicyDetails(detail);
	    } else {
	        throw new IllegalArgumentException("Invalid term length format: " + termLength);
	    }
	}
	
	
	public void cancelPolicy(int id) {
		assignmentRepo.cancelPolicyForCustomer(id);
	}

}
