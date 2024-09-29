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
import java.util.StringJoiner;

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
	    
	    // StringJoiner to manage commas between customer objects
	    StringJoiner customerJoiner = new StringJoiner(",");
	    for (Customer customer : customers) {
	        StringBuilder customerJson = new StringBuilder();
	        customerJson.append("{")
	            .append("\"id\":\"").append(customer.getId()).append("\",")
	            .append("\"name\":\"").append(customer.getName()).append("\",")
	            .append("\"email\":\"").append(customer.getEmail()).append("\",")
	            .append("\"phone\":\"").append(customer.getPhone()).append("\",")
	            .append("\"policy\":[");
	        customerJson.append("]");
	        customerJson.append("}");

	        // Add this customer JSON to the main JSON
	        customerJoiner.add(customerJson.toString());
	    }
	    
	    // Append all customer data and close the JSON array
	    json.append(customerJoiner.toString()).append("]");
	    
	    return json.toString();
	}
	
	


}
