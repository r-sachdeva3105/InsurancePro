package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PolicyService;
import entity.Policy;

import java.io.IOException;

public class AddPolicyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PolicyService policyService;

    public AddPolicyServlet() {
        super();
    }

    public void init() {
        policyService = new PolicyService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("policyId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String type = request.getParameter("type");

        Policy policy = new Policy(id, name, description, type);
        try {
            policyService.addPolicy(policy);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        }
        
        response.sendRedirect("policies.html");
    }
}
