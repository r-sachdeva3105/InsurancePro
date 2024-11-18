package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ClaimService;

import java.io.IOException;

import entity.Claims;

/**
 * Servlet implementation class ApproveRejectClaim
 */
public class ApproveRejectClaim extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClaimService claimService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApproveRejectClaim() {
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
		 int claimId = Integer.parseInt(request.getParameter("claimId"));
	        
		 String action = request.getParameter("action");
		  String status = null;

		    if ("approve".equals(action)) {
		        status = "Approved";
		    } else if ("reject".equals(action)) {
		        status = "Rejected";
		    }
	       
	       try {

				if (!claimService.approveRejectClaim(claimId, status))
					throw new Exception("Error Ouccred");
				response.sendRedirect("adminClaims.html");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());

			}
	}

}
