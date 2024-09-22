package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import entity.Customer;

/**
 * Servlet implementation class CustomerServlets
 */
public class ViewCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService customerService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewCustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() {
        customerService = new CustomerService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Customer> customers = customerService.getAllCustomers();
	    
	    // Set response type to JSON
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    // Convert the list to JSON (you may need to use a library like Jackson or Gson)
	    String json = convertToJson(customers); // Implement this method to convert your list to JSON
	    response.getWriter().write(json);
	}


	
	private String convertToJson(List<Customer> customers) {
	    StringBuilder json = new StringBuilder("[");
	    for (int i = 0; i < customers.size(); i++) {
	        Customer customer = customers.get(i);
	        json.append("{")
	            .append("\"id\":\"").append(customer.getId()).append("\",")
	            .append("\"name\":\"").append(customer.getName()).append("\",")
	            .append("\"email\":\"").append(customer.getEmail()).append("\",")
	            .append("\"phone\":\"").append(customer.getPhone()).append("\"")
	            .append("}");
	        if (i < customers.size() - 1) json.append(",");
	    }
	    json.append("]");
	    return json.toString();
	}

}
