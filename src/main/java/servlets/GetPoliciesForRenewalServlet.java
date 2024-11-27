package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.PolicyAssignmentService;
import service.PolicyService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * Servlet implementation class GetPoliciesForRenewalServlet
 */

public class GetPoliciesForRenewalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PolicyAssignmentService assignmentService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPoliciesForRenewalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() {
		assignmentService = new PolicyAssignmentService(getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpSession session = request.getSession();
        //int brokerId = (int) session.getAttribute("brokerId");
        
        List<Object[]> renewalDetails = assignmentService.getPoliciesforRenewal(1);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// convert to JSON format

		String json = convertToJson(renewalDetails);
		response.getWriter().write(json);
	}

	public String convertToJson(List<Object[]> renewalDetails) {
	    StringBuilder json = new StringBuilder("[");
	    StringJoiner policyJoiner = new StringJoiner(",");

	    for (Object[] row : renewalDetails) {
	    	int id = (int) row[0];
	        String policyName = (String) row[1];
	        String customerName = (String) row[2];
	        Double premium = (Double) row[3];
	        String termLength = (String) row[4];
	        Date startDate = (Date) row[5];
	        Date endDate = (Date) row[6];

	        StringBuilder policyJson = new StringBuilder();
	        policyJson.append("{")
	        		.append("\"id\":\"").append(id).append("\",")
	        		.append("\"policyName\":\"").append(policyName).append("\",")
	        		.append("\"customerName\":\"").append(customerName).append("\",")
	                .append("\"premium\":\"").append(premium).append("\",")
	                .append("\"termLength\":\"").append(termLength).append("\",")
	                .append("\"startDate\":\"").append(startDate).append("\",")
	                .append("\"endDate\":\"").append(endDate).append("\"")
	                .append("}");

	        policyJoiner.add(policyJson.toString());
	    }

	    json.append(policyJoiner.toString()).append("]");
	    return json.toString();
	}



}
