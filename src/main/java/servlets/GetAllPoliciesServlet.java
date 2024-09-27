package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PolicyService;
import entity.Customer;
import entity.Policy;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class GetAllPoliciesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PolicyService policyService;

    public GetAllPoliciesServlet() {
        super();
    }

    public void init() {
        policyService = new PolicyService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Policy> policies = policyService.getAllPolicies();
     // Set response type to JSON
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    // Convert the list to JSON 
	    String json = convertToJson(policies); 
	    response.getWriter().write(json);
    }
    
    private String convertToJson(List<Policy> policies) {
	    StringBuilder json = new StringBuilder("[");
	    
	    // StringJoiner to manage commas between policy objects
	    StringJoiner policyJoiner = new StringJoiner(",");
	    for (Policy policy : policies) {
	        StringBuilder policyJson = new StringBuilder();
	        policyJson.append("{")
	            .append("\"id\":\"").append(policy.getId()).append("\",")
	            .append("\"name\":\"").append(policy.getName()).append("\",")
	            .append("\"description\":\"").append(policy.getDescription()).append("\",")
	            .append("\"type\":\"").append(policy.getType()).append("\",");


	        // Append policies and close customer JSON
	       // customerJson.append(policyJoiner.toString()).append("]");
	        policyJson.append("]");
	        policyJson.append("}");

	        // Add this customer JSON to the main JSON
	        policyJoiner.add(policyJson.toString());
	    }
	    
	    // Append all customer data and close the JSON array
	    json.append(policyJoiner.toString()).append("]");
	    
	    return json.toString();
	}
}
