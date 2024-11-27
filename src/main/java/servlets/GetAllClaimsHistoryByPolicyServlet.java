package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ClaimHistoryService;
import service.ClaimService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * Servlet implementation class GetAllClaimsHistoryByPolicyServlet
 */

public class GetAllClaimsHistoryByPolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClaimHistoryService claimHistoryService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllClaimsHistoryByPolicyServlet() {
        super();
        // TODO Auto-generated constructor stub
        
    }
    
    @Override
    public void init() throws ServletException {
    	claimHistoryService = new ClaimHistoryService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String policyName = request.getParameter("policyName");
		
		List<Object[]> claimHistory = claimHistoryService.getClaimHistoryByPolicy(policyName);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// convert to JSON format

		String json = convertToJson(claimHistory);
		response.getWriter().write(json);
		
		
	}
	
	public String convertToJson(List<Object[]> claimHistory) {
	    StringBuilder json = new StringBuilder("[");
	    StringJoiner claimJoiner = new StringJoiner(",");

	    for (Object[] row : claimHistory) {
	        String newStatus = (String) row[0];
	        Double amount = (Double) row[1];
	        Date submissionDate = (Date) row[2];
	        Date updateDate = (Date) row[3];
	        String changeReson = (String) row[4];

	        StringBuilder claimJson = new StringBuilder();
	        claimJson.append("{")
	        		.append("\"newStatus\":\"").append(newStatus).append("\",")
	                .append("\"amount\":").append(amount).append(",")
	                
	                .append("\"submissionDate\":\"").append(submissionDate).append("\",")
	                .append("\"updateDate\":\"").append(updateDate).append("\",")
	                .append("\"changeReson\":\"").append(changeReson).append("\"")
	                .append("}");

	        claimJoiner.add(claimJson.toString());
	    }

	    json.append(claimJoiner.toString()).append("]");
	    return json.toString();
	}

	

}
