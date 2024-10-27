package servlets;

import entity.Broker;
import service.BrokerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterBrokerServlet extends HttpServlet {
    private BrokerService brokerService;

    @Override
    public void init() {
        brokerService = new BrokerService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register.html?error=Passwords do not match.");
            return;
        }

        // Attempt registration
        if (brokerService.registerBroker(name, email, password)) {
            response.sendRedirect("login.html");
        } else {
            response.sendRedirect("register.html?error=Email already registered.");
        }
    }

    @Override
    public void destroy() {
        brokerService.close();
    }
}
