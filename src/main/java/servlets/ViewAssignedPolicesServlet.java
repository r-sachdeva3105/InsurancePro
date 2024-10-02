package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;
import service.PolicyAssignmentService;
import service.PolicyService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

import entity.Customer;
import entity.Policy;
import entity.PolicyDetails;

/**
 * Servlet implementation class ViewAssignedPolicesServlet
 */
public class ViewAssignedPolicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PolicyAssignmentService assignmentService;
	private PolicyService policyService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewAssignedPolicesServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	// initialize servlet
	public void init() {
		assignmentService = new PolicyAssignmentService(getServletContext());
		policyService = new PolicyService(getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// handles request to view assigned policies to a customer
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String id = request.getParameter("customerId");

		// TODO Auto-generated method stub
		List<PolicyDetails> details = assignmentService.getDetailsForCustomer(id);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// convert to JSON format

		String json = convertToJson(details);
		response.getWriter().write(json);

	}

	// method to convert the given list to JSON format
	private String convertToJson(List<PolicyDetails> details) {
		StringBuilder json = new StringBuilder("[");

		String policyName = "";
		StringJoiner policyJoiner = new StringJoiner(",");
		for (PolicyDetails policyDetails : details) {
			policyName = policyService.getAllPolicies().stream()
					.filter(policy -> policy.getId().equals(policyDetails.getPolicyId())).map(Policy::getName)
					.findFirst().orElse("");
			StringBuilder policyJson = new StringBuilder();
			policyJson.append("{").append("\"policyName\":\"").append(policyName).append("\",")
					.append("\"brokerId\":\"").append(policyDetails.getBrokerId()).append("\",")
					.append("\"premiumAmount\":\"").append(policyDetails.getPremiumAmount()).append("\",")
					.append("\"customerId\":\"").append(policyDetails.getCustomerId()).append("\"");

			policyJson.append("}");

			policyJoiner.add(policyJson.toString());
		}

		json.append(policyJoiner.toString()).append("]");

		return json.toString();

	}

}