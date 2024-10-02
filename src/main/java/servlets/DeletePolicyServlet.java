package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PolicyService;

import java.io.IOException;

public class DeletePolicyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PolicyService policyService;

    public DeletePolicyServlet() {
        super();
    }

    public void init() {
        policyService = new PolicyService(getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("policyId");
        try {
            policyService.deletePolicy(id);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        }

        response.sendRedirect("policies.html");
    }
}
