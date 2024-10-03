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

//defining a Policy Assignment Repository Interface implementation
public class PolicyAssignmentDetailsRepositoryImpl implements PolicyAssignmentDetailsRepository {

	private static final String FILE_PATH = "/PolicyAssignmentDetails.json";
	private ServletContext context;

	public PolicyAssignmentDetailsRepositoryImpl(ServletContext context) {
		this.context = context;
	}

	// method to get policy details for a customer using customer Id
	@Override
	public  List<PolicyDetails> getAssignedPolicyForCustomer(String id) {
		// TODO Auto-generated method stub
		List<PolicyDetails> policyDetails = getAllPolicies();
		return policyDetails.stream().filter(det -> det.getCustomerId().equals(id)).collect(Collectors.toList());

	}

	// method to add policy detail

	@Override
	public synchronized void addPolicyDetails(PolicyDetails details) {
		// TODO Auto-generated method stub

		List<PolicyDetails> policyDetails = getAllPolicies();
		System.out.println(details.getCustomerId());

		policyDetails.add(details);
		saveToFile(policyDetails);
	}

	// method to getALLpolicy details from file

	private  List<PolicyDetails> getAllPolicies() {
		// TODO Auto-generated method stub
		List<PolicyDetails> assignmentDetails = new ArrayList<>();
		File file = new File(context.getRealPath(FILE_PATH));
		System.out.println("Looking for file at: " + file.getAbsolutePath());
		// read file
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder json = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}

			// removing extra [,{,},] and , to read the data 
			String jsonString = json.toString().trim();
			jsonString = jsonString.substring(1, jsonString.length() - 1);

			String[] detailsStrings = jsonString.split("\\},\\s*\\{");
			for (String detailString : detailsStrings) {
				detailString = detailString.replaceAll("[\\{\\}\"]", "").trim();

				String[] attributes = detailString.split(",\\s*");

				String customerId = null, policyId = null, brokerId = null, premiumAmount = null;

				for (String attribute : attributes) {

					String[] keyValue = attribute.split(":");
					if (keyValue.length == 2) {
						String key = keyValue[0].trim();
						String value = keyValue[1].trim();

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
				PolicyDetails details = new PolicyDetails(customerId, policyId, brokerId, premiumAmount);
				assignmentDetails.add(details);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return assignmentDetails;

	}

	// method to save data in json file by converting it to json format
	private synchronized void saveToFile(List<PolicyDetails> details) {
		StringBuilder json = new StringBuilder("[");

		StringJoiner policyJoiner = new StringJoiner(",");
		for (PolicyDetails policyDetails : details) {
			StringBuilder policyJson = new StringBuilder();
			policyJson.append("{").append("\"policyId\":\"").append(policyDetails.getPolicyId()).append("\",")
					.append("\"brokerId\":\"").append(policyDetails.getBrokerId()).append("\",")
					.append("\"premiumAmount\":\"").append(policyDetails.getPremiumAmount()).append("\",")
					.append("\"customerId\":\"").append(policyDetails.getCustomerId()).append("\"");

			policyJson.append("}");

			policyJoiner.add(policyJson.toString());
		}

		json.append(policyJoiner.toString()).append("]");
		File file = new File(context.getRealPath(FILE_PATH));
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(json.toString());
			System.out.println("Sam");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
