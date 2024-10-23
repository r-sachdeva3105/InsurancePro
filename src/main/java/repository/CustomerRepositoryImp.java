package repository;

import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
                    .setParameter("email", customer.getEmail())
                    .getResultList();

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
    public List<Customer> getAllCustomers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Customer", Customer.class).getResultList();
        }
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

    // Close the sessionFactory when done
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
