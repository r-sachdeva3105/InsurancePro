package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;
import service.PolicyAssignmentService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

import entity.Customer;
import entity.PolicyDetails;

/**
 * Servlet implementation class ViewAssignedPolicesServlet
 */
public class ViewAssignedPolicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PolicyAssignmentService assignmentService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAssignedPolicesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
  
    public void init() {
    	assignmentService = new PolicyAssignmentService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String id = request.getParameter("id");
		
		// TODO Auto-generated method stub
		List<PolicyDetails> details = assignmentService.getDetailsForCustomer(id);
	    
	    // Set response type to JSON
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    // Convert the list to JSON (you may need to use a library like Jackson or Gson)
	    String json = convertToJson(details); // Implement this method to convert your list to JSON
	    response.getWriter().write(json);
		
	}
	
	private String convertToJson(List<PolicyDetails> details) {
	    StringBuilder json = new StringBuilder("[");
	    
	    StringJoiner policyJoiner = new StringJoiner(",");
	    for (PolicyDetails policyDetails : details) {
	        StringBuilder policyJson = new StringBuilder();
	        policyJson.append("{")
	            .append("\"policyId\":\"").append(policyDetails.getPolicyId()).append("\",")
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
