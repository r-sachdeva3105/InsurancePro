package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;
import service.PolicyAssignmentService;
import service.PolicyService;

import java.io.IOException;

import entity.Customer;
import entity.Policy;
import entity.PolicyDetails;

/**
 * Servlet implementation class AssignPolicyServlet
 */
public class AssignPolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PolicyAssignmentService assignmentService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AssignPolicyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Initializing servlet
	public void init() {
		assignmentService = new PolicyAssignmentService(getServletContext());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// handles assign policy to a customer request
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html");

		String customerId = request.getParameter("customerId");
		String policyName = request.getParameter("policyName");
		System.out.println("policyname" + policyName);
		String brokerId = request.getParameter("brokerId");
		String premiumAmount = request.getParameter("premiumAmount");

		try {

			if (!assignmentService.addPolicyDetails(customerId, policyName, brokerId, premiumAmount))
				throw new Exception("Error Ouccred");
			response.sendRedirect("customers.html");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());

		}

	}

}
