package servlets;

import entity.Broker;
import service.BrokerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

public class LoginBrokerServlet extends HttpServlet {
    private BrokerService brokerService;

    @Override
    public void init() {
        brokerService = new BrokerService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Broker broker = brokerService.loginBroker(email, password);

        if (broker != null && BCrypt.checkpw(password, broker.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("broker", broker);
            response.sendRedirect("dashboard.html");
        } else {
            response.sendRedirect("login.html?error=Invalid email or password.");
        }
    }

    @Override
    public void destroy() {
        brokerService.close();
    }
}
