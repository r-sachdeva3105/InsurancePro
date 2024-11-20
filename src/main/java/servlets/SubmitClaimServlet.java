package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.ClaimsRepositoryImp;
import service.ClaimService;
import service.CustomerService;

import java.io.IOException;

import entity.Claims;

/**
 * Servlet implementation class SubmitClaimServlet
 */
public class SubmitClaimServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClaimService claimService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitClaimServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	claimService = new ClaimService(getServletContext());
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String policyName = request.getParameter("policyName");
	     int brokerId = Integer.parseInt(request.getParameter("brokerId"));
	     int customerId = Integer.parseInt(request.getParameter("customerId"));
	     String description = request.getParameter("description");
	     Double amount = Double.parseDouble(request.getParameter("amount"));
	     
	     
	     
	 	try {

			if (!claimService.addClaim(policyName, brokerId, customerId,  description, amount))
				throw new Exception("Error Ouccred");
			response.sendRedirect("customers.html");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());

		}
	}

}
