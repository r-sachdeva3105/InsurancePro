package repository;

import entity.PolicyDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PolicyAssignmentDetailsRepositoryImpl implements PolicyAssignmentDetailsRepository {

    private SessionFactory sessionFactory;

    public PolicyAssignmentDetailsRepositoryImpl() {
        // Initialize SessionFactory using Hibernate configuration (hibernate.cfg.xml)
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Method to get policy details for a customer using customer Id
    @Override
    public List<PolicyDetails> getAssignedPolicyForCustomer(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM PolicyDetails WHERE customerId = :customerId", PolicyDetails.class)
                          .setParameter("customerId", id)
                          .getResultList();
        }
    }

    // Method to add policy detail
    @Override
    public synchronized void addPolicyDetails(PolicyDetails details) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(details); // Save the policy details to the database
            transaction.commit();
        }
    }

    // Method to get all policy details
    public List<PolicyDetails> getAllPolicies() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM PolicyDetails", PolicyDetails.class).getResultList();
        }
    }

    // Close the sessionFactory when done
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
