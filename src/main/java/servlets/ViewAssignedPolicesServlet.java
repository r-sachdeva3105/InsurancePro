package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.PolicyAssignmentService;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

/**
 * Servlet implementation class ViewAssignedPoliciesServlet
 */

public class ViewAssignedPolicesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PolicyAssignmentService assignmentService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAssignedPolicesServlet() {
        super();
    }

    // Initialize the servlet
    public void init() {
        assignmentService = new PolicyAssignmentService(getServletContext());
    }

    /**
     * Handles GET requests to view assigned policies for a customer.
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve broker ID from session and customer ID from the request
        HttpSession session = request.getSession();
        int brokerId = (int) session.getAttribute("brokerId");
        int customerId = Integer.parseInt(request.getParameter("customerId"));

        // Retrieve the assigned policy details using the service
        List<Object[]> details = assignmentService.getDetailsForCustomer(customerId, brokerId);

        // Set response content type and encoding
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convert the results to JSON format and send the response
        String json = convertToJson(details);
        response.getWriter().write(json);
    }

    /**
     * Converts a list of policy details into a JSON-formatted string.
     */
    public String convertToJson(List<Object[]> details) {
        StringBuilder json = new StringBuilder("[");
        StringJoiner policyJoiner = new StringJoiner(",");

        for (Object[] row : details) {
        	int policyDetailsId = (int) row[0];
            String policyName = (String) row[1];
            int brokerId = (int) row[2];
            double premiumAmount = (double) row[3]; // Updated type to double
            String termLength = (String) row[4]; // Added termLength field

            StringBuilder policyJson = new StringBuilder();
            policyJson.append("{")
            		.append("\"policyDetailsId\":").append(policyDetailsId).append(",")
                    .append("\"policyName\":\"").append(policyName).append("\",")
                    .append("\"brokerId\":").append(brokerId).append(",")
                    .append("\"premiumAmount\":").append(premiumAmount).append(",")
                    .append("\"termLength\":\"").append(termLength).append("\"") // Included termLength
                    .append("}");

            policyJoiner.add(policyJson.toString());
        }

        json.append(policyJoiner.toString()).append("]");
        return json.toString();
    }
}
