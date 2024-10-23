package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

import entity.Customer;

/**
 * Servlet implementation class ViewCustomerServlet
 */

public class ViewCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService;

    /**
     * Default constructor
     */
    public ViewCustomerServlet() {
        super();
    }

    // Initialize the servlet
    @Override
    public void init() throws ServletException {
        customerService = new CustomerService(getServletContext());
    }

    /**
     * Handles the GET request to view all customers.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Retrieve all customers
            List<Customer> customers = customerService.getAllCustomers();

            // Set response type to JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Convert customers to JSON and write it to response
            String json = convertToJson(customers);
            response.getWriter().write(json);

            // Set HTTP status code to 200 (OK)
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            // Handle any exceptions and set error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred while fetching customers.\"}");
        }
    }

    /**
     * Converts the list of customers to JSON format.
     */
    private String convertToJson(List<Customer> customers) {
        StringBuilder json = new StringBuilder("[");
        StringJoiner customerJoiner = new StringJoiner(",");

        for (Customer customer : customers) {
            // Building JSON for each customer
            StringBuilder customerJson = new StringBuilder();
            customerJson.append("{")
                    .append("\"id\":\"").append(customer.getId()).append("\",")
                    .append("\"name\":\"").append(customer.getName()).append("\",")
                    .append("\"email\":\"").append(customer.getEmail()).append("\",")
                    .append("\"phone\":\"").append(customer.getPhone()).append("\"");
            
            customerJson.append("}");

            customerJoiner.add(customerJson.toString());
        }

        json.append(customerJoiner.toString()).append("]");

        return json.toString();
    }

}
