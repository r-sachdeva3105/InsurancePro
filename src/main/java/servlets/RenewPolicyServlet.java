package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PolicyAssignmentService;

import java.io.IOException;

/**
 * Servlet implementation class RenewPolicyServlet
 */

public class RenewPolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PolicyAssignmentService assignmentService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RenewPolicyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

 // Initializing servlet
 	public void init() {
 		assignmentService = new PolicyAssignmentService(getServletContext());

 	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		String termLength = request.getParameter("termLength");
	
		assignmentService.renewPolicy(id, termLength);
	}

}
