package repository;

import entity.Claims;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class CustomerRepositoryImp implements CustomerRepository {

	private SessionFactory sessionFactory;

	public CustomerRepositoryImp() {
		// Initialize SessionFactory using Hibernate configuration (hibernate.cfg.xml)
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	// Method to add customer
	@Override
	public synchronized void addCustomer(Customer customer) throws Exception {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();

			// Check if customer with same email already exists
			List<Customer> customerCheck = session.createQuery("FROM Customer WHERE email = :email", Customer.class)
					.setParameter("email", customer.getEmail()).getResultList();

			if (!customerCheck.isEmpty()) {
				throw new Exception("Customer already exists");
			}

			session.persist(customer);
			transaction.commit();
		}
	}

	// Method to get customer by Id
	@Override
	public Customer getCustomerById(String id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Customer.class, id);
		}
	}

	// Method to get all customers
	@Override
	public List<Customer> getAllCustomersByBrokerId(int brokerId) {
	    List<Customer> customerDetails = null;
	    try (Session session = sessionFactory.openSession()) {
	        Transaction transaction = session.beginTransaction();

	        String sql = "SELECT * FROM Customer WHERE broker_id = :brokerId";

	        NativeQuery<Customer> query = session.createNativeQuery(sql, Customer.class);
	        query.setParameter("brokerId", brokerId);

	        // Execute the query and retrieve results
	        customerDetails = query.getResultList();

	        transaction.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle exceptions (e.g., log them)
	    }

	    return customerDetails;
	}


	// Method to update customer
	@Override
	public synchronized void updateCustomer(Customer customer) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();

			Customer existingCustomer = session.get(Customer.class, customer.getId());
			if (existingCustomer != null) {
				session.merge(customer);
				transaction.commit();
			} else {
				throw new IllegalArgumentException("Customer with ID " + customer.getId() + " not found.");
			}
		}
	}

	// Method to delete customer
	@Override
	public synchronized void deleteCustomer(String id) throws Exception {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();

			Customer customer = session.get(Customer.class, id);
			if (customer != null) {
				session.delete(customer);
				transaction.commit();
			} else {
				throw new Exception("Customer with ID " + id + " not found.");
			}
		}
	}
	
	@Override
	public List<Object[]> getCustomerCountByBrokerID() {
	    List<Object[]> customerDetails = null;
	    try (Session session = sessionFactory.openSession()) {
	        Transaction transaction = session.beginTransaction();

	        String sql = "SELECT c.broker_id,b.name AS broker_name, COUNT(c.id) AS customer_count FROM Customer c JOIN Brokers b ON c.broker_id=b.id GROUP BY broker_id;";

	        NativeQuery<Object[]> query = session.createNativeQuery(sql);

	        // Execute the query and retrieve results
	        customerDetails = query.getResultList();

	        transaction.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle exceptions (e.g., log them)
	    }

	    return customerDetails;
	}
	
	
	// Close the sessionFactory when done
	public void close() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}
