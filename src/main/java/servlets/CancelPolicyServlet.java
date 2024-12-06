package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PolicyAssignmentService;

import java.io.IOException;

/**
 * Servlet implementation class CancelPolicyServlet
 */

public class CancelPolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PolicyAssignmentService assignmentService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelPolicyServlet() {
        super();
        assignmentService = new PolicyAssignmentService(null);
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		assignmentService.cancelPolicy(id);
		response.sendRedirect("customers.html");
		
	}

}
