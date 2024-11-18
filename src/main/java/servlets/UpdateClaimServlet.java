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

    @Override
    public void init() throws ServletException {
        claimService = new ClaimService(getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int claimId = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status"); // Change this to fetch the correct parameter

        try {
            // Assuming updateClaim now accepts claimId and status
            if (!claimService.updateClaim(claimId, status)) {
                throw new Exception("Error occurred while updating claim status.");
            }
            response.setStatus(HttpServletResponse.SC_OK); // Send OK response
        } catch (Exception e) {
            // Log the error message for debugging
            System.err.println("Error in UpdateClaimServlet: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Send error response
        }
    }
}
