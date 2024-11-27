package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
		policyService = new PolicyService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// handles request to view assigned policies to a customer
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        int brokerId = (int) session.getAttribute("brokerId");
		int id = Integer.parseInt(request.getParameter("customerId"));

		// TODO Auto-generated method stub
		List<Object[]> details = assignmentService.getDetailsForCustomer(id, brokerId);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// convert to JSON format

		String json = convertToJson(details);
		response.getWriter().write(json);

	}

	public String convertToJson(List<Object[]> details) {
	    StringBuilder json = new StringBuilder("[");
	    StringJoiner policyJoiner = new StringJoiner(",");

	    for (Object[] row : details) {
	        String policyName = (String) row[0];
	        int brokerId = (int) row[1];
	        String premiumAmount = (String) row[2];

	        StringBuilder policyJson = new StringBuilder();
	        policyJson.append("{")
	        		.append("\"policyName\":\"").append(policyName).append("\",")
	                .append("\"brokerId\":\"").append(brokerId).append("\",")
	                .append("\"premiumAmount\":\"").append(premiumAmount).append("\"")
	                .append("}");

	        policyJoiner.add(policyJson.toString());
	    }

	    json.append(policyJoiner.toString()).append("]");
	    return json.toString();
	}


}