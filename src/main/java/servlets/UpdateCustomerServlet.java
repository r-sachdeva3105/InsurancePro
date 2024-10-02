package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;

import java.io.IOException;
import java.io.PrintWriter;

import entity.Customer;

/**
 * Servlet implementation class UpdateCustomerServlet
 */
public class UpdateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private CustomerService customerService;

	public UpdateCustomerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		customerService = new CustomerService(getServletContext());
	}

	/**
	 * @return 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		PrintWriter out = response.getWriter();
		
		Customer updatedCustomer = new Customer(id, name, email, phone);
		 try {
	            customerService.updateCustomer(updatedCustomer);
	            // If update is successful, redirect to viewCustomers.jsp
	            response.sendRedirect("customers.html");
	        } catch (IllegalArgumentException e) {
	        	
	            // If customer is not found, set the error message as a request attribute
	        	request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
	            request.getRequestDispatcher("/UpdateCustomerServlet").forward(request, response);
	        	
	        }
	}

}
