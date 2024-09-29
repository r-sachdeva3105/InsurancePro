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
import java.io.PrintWriter;

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
    public void init() {
    	assignmentService = new PolicyAssignmentService();
        
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String customerId = request.getParameter("customerId");
		String policyName = request.getParameter("policyName");
		String brokerId = request.getParameter("brokerId");
		String premiumAmount = request.getParameter("premiumAmount");
        
        try {
			
        	if(!assignmentService.addPolicyDetails(customerId, policyName, brokerId, premiumAmount))
        		throw new Exception ("asdfgh");
        	
			out.println("  <p>asdfghju765rdfc</p>");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
			out.println(" <p>sadfgh</p>");
			
		}
	}

}
