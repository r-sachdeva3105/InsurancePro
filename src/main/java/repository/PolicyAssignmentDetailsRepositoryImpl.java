package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import entity.Customer;
import entity.PolicyDetails;
import jakarta.servlet.ServletContext;

public class PolicyAssignmentDetailsRepositoryImpl implements PolicyAssignmentDetailsRepository {
	
	 private static final String FILE_PATH = "C:\\Users\\samch\\OneDrive\\Documents\\Humber\\Sem3\\J2EE\\InsurancePro\\PolicyAssignmentDetails.json";
	 private ServletContext context;
	 
	 public PolicyAssignmentDetailsRepositoryImpl(ServletContext context) {
		 this.context = context;
	 }


	@Override
	public List<PolicyDetails> getAssignedPolicyForCustomer(String id) {
		// TODO Auto-generated method stub
		List<PolicyDetails> policyDetails = getAllPolicies();
		return policyDetails.stream().filter(det -> det.getCustomerId().equals(id))
         .collect(Collectors.toList());
		
		
	}

	@Override
	public void addPolicyDetails(PolicyDetails details) {
		// TODO Auto-generated method stub
		
		List<PolicyDetails> policyDetails = getAllPolicies();
		System.out.println(details.getCustomerId());
        	
		policyDetails.add(details);
        saveToFile(policyDetails);
	}

	private void saveToFile(List<PolicyDetails> details) {
	    StringBuilder json = new StringBuilder("[");
	    
	    StringJoiner policyJoiner = new StringJoiner(",");
	    for (PolicyDetails policyDetails : details) {
	        StringBuilder policyJson = new StringBuilder();
	        policyJson.append("{")
	            .append("\"policyId\":\"").append(policyDetails.getPolicyId()).append("\",")
	            .append("\"brokerId\":\"").append(policyDetails.getBrokerId()).append("\",")
	            .append("\"premiumAmount\":\"").append(policyDetails.getPremiumAmount()).append("\",")
	            .append("\"customerId\":\"").append(policyDetails.getCustomerId()).append("\"");
	        
	        policyJson.append("}");

	        policyJoiner.add(policyJson.toString());
	    }
	    
	    json.append(policyJoiner.toString()).append("]");
	    //File file = new File(context.getRealPath(FILE_PATH));
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
	        writer.write(json.toString());
	        System.out.println("Sam");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

		// TODO Auto-generated method stub
		
	

	private List<PolicyDetails> getAllPolicies() {
		// TODO Auto-generated method stub
		 List<PolicyDetails> assignmentDetails = new ArrayList<>();
		 //File file = new File(context.getRealPath(FILE_PATH));
	        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
	            StringBuilder json = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                json.append(line);
	            }

	            // Trim the JSON array brackets
	            String jsonString = json.toString().trim();
	            jsonString = jsonString.substring(1, jsonString.length() - 1); // Remove the outer brackets

	            // Split by "}, {" to get individual customer strings
	            String[] detailsStrings = jsonString.split("\\},\\s*\\{");
	            for (String detailString : detailsStrings) {
	            	detailString = detailString.replaceAll("[\\{\\}\"]", "").trim();

	                // Split by commas to get key-value pairs
	                String[] attributes = detailString.split(",\\s*");

	                String customerId = null, policyId = null, brokerId = null, premiumAmount = null;
	                //List<Policy> policies = new ArrayList<>();

	                for (String attribute : attributes) {
	                    // Split key and value
	                    String[] keyValue = attribute.split(":");
	                    if (keyValue.length == 2) {
	                        String key = keyValue[0].trim();
	                        String value = keyValue[1].trim();

	                        // Check if the attribute is one of the basic fields or a policy field
	                        switch (key) {
	                            case "customerId":
	                            	customerId = value;
	                                break;
	                            case "policyId":
	                            	policyId = value;
	                                break;
	                            case "brokerId":
	                            	brokerId = value;
	                                break;
	                            case "premiumAmount":
	                            	premiumAmount = value;
	                                break;
	                       }
	                    }
	                }
	                PolicyDetails details = new PolicyDetails(customerId,policyId, brokerId, premiumAmount );
	                assignmentDetails.add(details);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return assignmentDetails;
	    
	}

}
