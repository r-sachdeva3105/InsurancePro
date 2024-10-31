package repository;

import entity.PolicyDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class PolicyAssignmentDetailsRepositoryImpl implements PolicyAssignmentDetailsRepository {

    private SessionFactory sessionFactory;

    public PolicyAssignmentDetailsRepositoryImpl() {
        // Initialize SessionFactory using Hibernate configuration (hibernate.cfg.xml)
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Method to get policy details for a customer using customer Id
    @Override
    public List<Object[]> getAssignedPolicyForCustomer(int id, int brokerId) {
        
        
        List<Object[]> policyDetails = null;
        
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            // Define and set up the native query
            String sql = "SELECT p.name As policyName, pd.broker_id, pd.premium_amount " +
                         "FROM policy_details pd " +
                         "JOIN policies p ON pd.policy_id = p.id "
                         + "WHERE pd.customer_id = :id and" +
                         " pd.broker_id = :brokerId";
            
            NativeQuery<Object[]> query = session.createNativeQuery(sql);
            query.setParameter("id", id);
            query.setParameter("brokerId", brokerId);
            
            // Execute the query and retrieve results
            policyDetails = query.getResultList();
            
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions (e.g., log them)
        }
       
        return policyDetails;
    
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
