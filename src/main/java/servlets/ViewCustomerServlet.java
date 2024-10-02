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

	// initialize the servlet
	public void init() {
		customerService = new CustomerService(getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// handles request to view all customer
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Customer> customers = customerService.getAllCustomers();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String json = convertToJson(customers);
		response.getWriter().write(json);
	}

	// method to convert the given list to JSON format
	private String convertToJson(List<Customer> customers) {
		StringBuilder json = new StringBuilder("[");
		StringJoiner customerJoiner = new StringJoiner(",");
		for (Customer customer : customers) {
			StringBuilder customerJson = new StringBuilder();
			customerJson.append("{").append("\"id\":\"").append(customer.getId()).append("\",").append("\"name\":\"")
					.append(customer.getName()).append("\",").append("\"email\":\"").append(customer.getEmail())
					.append("\",").append("\"phone\":\"").append(customer.getPhone()).append("\",")
					.append("\"policy\":[");
			customerJson.append("]");
			customerJson.append("}");
			customerJoiner.add(customerJson.toString());
		}

		json.append(customerJoiner.toString()).append("]");

		return json.toString();
	}

}
