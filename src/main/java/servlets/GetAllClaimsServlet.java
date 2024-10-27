package servlets;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ClaimService;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

/**
 * Servlet implementation class GetAllClaimsServlet
 */
public class GetAllClaimsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClaimService claimService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllClaimsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init() throws ServletException {
    	claimService = new ClaimService(getServletContext());
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		List<Object[]> details = claimService.getAllClaims();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// convert to JSON format

		String json = convertToJson(details);
		response.getWriter().write(json);
	}

	public String convertToJson(List<Object[]> claimDetails) {
	    StringBuilder json = new StringBuilder("[");
	    StringJoiner claimJoiner = new StringJoiner(",");

	    for (Object[] row : claimDetails) {
	        int claimId = (int) row[0];
	        String brokerName = (String) row[1];
	        String customerName = (String) row[2];
	        String policyName = (String) row[3];
	        String claimStatus = (String) row[4];

	        StringBuilder claimJson = new StringBuilder();
	        claimJson.append("{")
	                .append("\"claimId\":").append(claimId).append(",")
	                .append("\"brokerName\":\"").append(brokerName).append("\",")
	                .append("\"customerName\":\"").append(customerName).append("\",")
	                .append("\"policyName\":\"").append(policyName).append("\",")
	                .append("\"status\":\"").append(claimStatus).append("\"")
	                .append("}");

	        claimJoiner.add(claimJson.toString());
	    }

	    json.append(claimJoiner.toString()).append("]");
	    return json.toString();
	}


}
