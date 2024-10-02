package servlets; // Package for servlet classes

import jakarta.servlet.ServletException; // Importing ServletException for servlet errors
import jakarta.servlet.http.HttpServlet; // Importing HttpServlet class
import jakarta.servlet.http.HttpServletRequest; // Importing HttpServletRequest for handling requests
import jakarta.servlet.http.HttpServletResponse; // Importing HttpServletResponse for handling responses
import service.PolicyService; // Importing PolicyService for policy operations

import java.io.IOException; // Importing IOException for input/output errors

// Servlet for deleting a policy
public class DeletePolicyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Unique ID for serialization
    private PolicyService policyService; // Policy service instance

    // Default constructor
    public DeletePolicyServlet() {
        super();
    }

    // Initializes the servlet and creates the policy service
    public void init() {
        policyService = new PolicyService(getServletContext());
    }

    // Handles POST requests to delete a policy
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("policyId"); // Retrieve policy ID from request
        try {
            policyService.deletePolicy(id); // Attempt to delete the policy
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage()); // Set error message if an exception occurs
        }

        response.sendRedirect("policies.html"); // Redirect to policies page
    }
}
