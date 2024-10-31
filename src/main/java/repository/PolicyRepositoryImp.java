package repository;

import entity.Customer;
import entity.Policy;
import jakarta.servlet.ServletContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class PolicyRepositoryImp implements PolicyRepository {

    private SessionFactory sessionFactory;

    public PolicyRepositoryImp() {
        // Initialize SessionFactory using Hibernate configuration (hibernate.cfg.xml)
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    

    // Method to get a policy by ID
    @Override
    public Policy getPolicyById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Policy.class, id); // Fetch Policy by primary key (id)
        }
    }

    // Method to add a new policy
    @Override
    public synchronized void addPolicy(Policy policy) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(policy); // Save the new policy to the database
            transaction.commit();
        }
    }

    // Method to update an existing policy
    @Override
    public synchronized void updatePolicy(Policy policy) {
    	try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Policy policy1 = session.get(Policy.class, policy.getId());
            if (policy1 != null) {
                session.merge(policy);
                transaction.commit();
            } else {
                throw new IllegalArgumentException("Customer with ID " + policy.getId() + " not found.");
            }
        }
    }

    // Method to delete a policy by ID
    @Override
    public synchronized void deletePolicy(String id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Policy policy = session.get(Policy.class, id); // Fetch the policy to delete
            if (policy != null) {
                session.remove(policy); // Delete policy if found
            }
            transaction.commit();
        }
    }

    // Method to retrieve all policies
    public List<Policy> getAllPolicies() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Policy", Policy.class).getResultList(); // Fetch all policies
        }
    }

    @Override
	public List<Long> getTotalPolicies() {
	    List<Long> totalPolicy = null;
	    try (Session session = sessionFactory.openSession()) {
	        Transaction transaction = session.beginTransaction();

	        String sql = "Select Count(id) AS total_policies from policies;";

	        NativeQuery<Long> query = session.createNativeQuery(sql);

	        // Execute the query and retrieve results
	        totalPolicy = query.getResultList();

	        transaction.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle exceptions (e.g., log them)
	    }

	    return totalPolicy;
	}
    // Close the sessionFactory when done
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
