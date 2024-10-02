package servlets; // Package for servlet classes

import jakarta.servlet.ServletException; // Importing ServletException for servlet errors
import jakarta.servlet.http.HttpServlet; // Importing HttpServlet class
import jakarta.servlet.http.HttpServletRequest; // Importing HttpServletRequest for handling requests
import jakarta.servlet.http.HttpServletResponse; // Importing HttpServletResponse for handling responses
import service.PolicyService; // Importing PolicyService for policy operations
import entity.Policy; // Importing Policy entity

import java.io.IOException; // Importing IOException for input/output errors

// Servlet for adding a new policy
public class AddPolicyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Unique ID for serialization
    private PolicyService policyService; // Policy service instance

    // Default constructor
    public AddPolicyServlet() {
        super();
    }

    // Initializes the servlet and creates the policy service
    public void init() {
        policyService = new PolicyService(getServletContext());
    }

    // Handles POST requests to add a new policy
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String id = request.getParameter("policyId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String type = request.getParameter("type");

        // Create a new Policy object
        Policy policy = new Policy(id, name, description, type);
        try {
            policyService.addPolicy(policy); // Add policy using the service
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage()); // Set error message
        }
        
        response.sendRedirect("policies.html"); // Redirect to policies page
    }
}
