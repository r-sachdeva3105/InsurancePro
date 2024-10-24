package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;

import java.io.IOException;

import entity.Customer;

/**
 * Servlet implementation class AddCustomerServlet
 */

public class AddCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCustomerServlet() {
        super();
    }

    // Initializing the servlet
    @Override
    public void init() throws ServletException {
        customerService = new CustomerService(getServletContext());
    }

    /**
     * Handles the POST request for adding a new customer.
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // Creating a new customer object
        Customer customer = new Customer(name, email, phone,1);

        try {
            // Adding the customer using CustomerService
            customerService.addCustomer(customer);

            // Redirecting to a success page or showing a success message
            request.setAttribute("successMessage", "Customer added successfully!");
           

        } catch (Exception e) {
            // Handling exceptions and showing an error message
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        response.sendRedirect("customers.html");
    }
}
