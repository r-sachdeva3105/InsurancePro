package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.CustomerRepository;
import repository.CustomerRepositoryImp;
import service.CustomerService;

import java.io.IOException;

/**
 * Servlet implementation class DeleteCustomerServlet
 */
public class DeleteCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private CustomerService customerService;

	public DeleteCustomerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	// initialize servlet
	public void init() {
		customerService = new CustomerService(getServletContext());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// handles request to delete customer
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			boolean isDeleted = customerService.deleteCustomer(id);

			if (isDeleted) {
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID.");
		}
	}

}
