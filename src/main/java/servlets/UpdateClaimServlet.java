package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ClaimService;

import java.io.IOException;

/**
 * Servlet implementation class UpdateClaimServlet
 */
public class UpdateClaimServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClaimService claimService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateClaimServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		claimService = new ClaimService(getServletContext());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int claimId = Integer.parseInt(request.getParameter("id"));
		String description = request.getParameter("description");

		try {

			if (!claimService.updateClaim(claimId, description))
				throw new Exception("Error Ouccred");
			response.sendRedirect("customers.html");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());

		}
	}

}
