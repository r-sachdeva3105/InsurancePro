package servlets; // Package for servlet classes

import jakarta.servlet.ServletException; // Importing ServletException for servlet errors
import jakarta.servlet.http.HttpServlet; // Importing HttpServlet class
import jakarta.servlet.http.HttpServletRequest; // Importing HttpServletRequest for handling requests
import jakarta.servlet.http.HttpServletResponse; // Importing HttpServletResponse for handling responses
import service.PolicyService; // Importing PolicyService for policy operations
import entity.Policy; // Importing Policy entity

import java.io.IOException; // Importing IOException for input/output errors
import java.util.List; // Importing List interface
import java.util.StringJoiner; // Importing StringJoiner for managing strings

// Servlet for retrieving all policies
public class GetAllPoliciesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Unique ID for serialization
    private PolicyService policyService; // Policy service instance

    // Default constructor
    public GetAllPoliciesServlet() {
        super();
    }

    // Initializes the servlet and creates the policy service
    public void init() {
        policyService = new PolicyService(getServletContext());
    }

    // Handles GET requests to retrieve all policies
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Policy> policies = policyService.getAllPolicies(); // Retrieve all policies

        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Convert the list of policies to JSON format
        String json = convertToJson(policies); 
        response.getWriter().write(json); // Write JSON to the response
    }
    
    // Converts a list of policies to a JSON string
    private String convertToJson(List<Policy> policies) {
        StringBuilder json = new StringBuilder("["); // Start JSON array
        
        // StringJoiner to manage commas between policy objects
        StringJoiner policyJoiner = new StringJoiner(",");
        for (Policy policy : policies) {
            StringBuilder policyJson = new StringBuilder();
            policyJson.append("{")
                .append("\"id\":\"").append(policy.getId()).append("\",")
                .append("\"name\":\"").append(policy.getName()).append("\",")
                .append("\"description\":\"").append(policy.getDescription()).append("\",")
                .append("\"type\":\"").append(policy.getType()).append("\"")
                .append("}");

            // Add this policy JSON to the main JSON
            policyJoiner.add(policyJson.toString());
        }
        
        // Append all policy data and close the JSON array
        json.append(policyJoiner.toString()).append("]");
        return json.toString(); // Return JSON string
    }
}
