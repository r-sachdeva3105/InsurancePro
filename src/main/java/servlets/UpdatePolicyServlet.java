package servlets; // Package for servlet classes

import jakarta.servlet.ServletException; // Importing ServletException for servlet errors
import jakarta.servlet.http.HttpServlet; // Importing HttpServlet class
import jakarta.servlet.http.HttpServletRequest; // Importing HttpServletRequest for handling requests
import jakarta.servlet.http.HttpServletResponse; // Importing HttpServletResponse for handling responses
import service.PolicyService; // Importing PolicyService for policy operations
import entity.Policy; // Importing Policy entity

import java.io.IOException; // Importing IOException for input/output errors

// Servlet for updating an existing policy
public class UpdatePolicyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Unique ID for serialization
    private PolicyService policyService; // Policy service instance

    // Default constructor
    public UpdatePolicyServlet() {
        super();
    }

    // Initializes the servlet and creates the policy service
    public void init() {
        policyService = new PolicyService(getServletContext());
    }

    // Handles POST requests to update a policy
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve policy details from the request
        String id = request.getParameter("policyId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String type = request.getParameter("type");

        // Create a new Policy object with the updated details
        Policy policy = new Policy(id, name, description, type);
        try {
            policyService.updatePolicy(policy); // Update the policy using the service
        } catch (Exception e) {
            // Set an error message if an exception occurs
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        }

        // Redirect to the update policy test page
        response.sendRedirect("TestUpdatePolicy.html");
    }
}
