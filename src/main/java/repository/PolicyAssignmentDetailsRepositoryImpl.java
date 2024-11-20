package repository;

import entity.Claims;
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

	@Override
	public void deletePolicyDetailsByCustomerId(int customerId) throws Exception {
		// TODO Auto-generated method stub
	
		
		try (Session session = sessionFactory.openSession()) {
	        Transaction transaction = session.beginTransaction();

	        // HQL bulk delete query to delete all ClaimsHistory entries with the given claimId
	        int deletedCount = session.createNativeQuery("DELETE FROM policy_details WHERE customer_id = :customerId")
	                .setParameter("customerId", customerId)
	                .executeUpdate();

	        if (deletedCount == 0) {
	        	System.out.println("No assignment history found for Claim ID " + customerId);
	           
	        }

	        transaction.commit();
	    } catch (Exception e) {
	        throw new Exception("Error deleting assignment history for Claim ID " + customerId, e);
	    }
		
	}

	@Override
	public void deletePolicyDetailsByPolicyId(int policyId) throws Exception {
		// TODO Auto-generated method stub
		try (Session session = sessionFactory.openSession()) {
	        Transaction transaction = session.beginTransaction();

	        // HQL bulk delete query to delete all ClaimsHistory entries with the given claimId
	        int deletedCount = session.createNativeQuery("DELETE FROM policy_details WHERE policy_id = :policyId")
	                .setParameter("policyId", policyId)
	                .executeUpdate();

	        if (deletedCount == 0) {
	        	System.out.println("No assignment history found for Claim ID " + policyId);
	           
	        }

	        transaction.commit();
	    } catch (Exception e) {
	        throw new Exception("Error deleting assignment history for Claim ID " + policyId, e);
	    }
		
	}

	@Override
	public PolicyDetails getDetailsforCustomer(int customerId, int policyId, int brokerId) {
	    PolicyDetails customerDetails = null;
	    try (Session session = sessionFactory.openSession()) {
	        // Define the native SQL query
	        String sql = "SELECT * FROM policy_details WHERE customer_id = :customerId AND policy_id = :policyId AND broker_id = :brokerId";

	        // Create a native query and map the result to PolicyDetails
	        NativeQuery<PolicyDetails> query = session.createNativeQuery(sql, PolicyDetails.class);
	        query.setParameter("customerId", customerId);
	        query.setParameter("policyId", policyId);
	        query.setParameter("brokerId", brokerId);

	        // Get a single result
	        customerDetails = query.uniqueResult();
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Optionally, handle exceptions (e.g., logging)
	    }

	    return customerDetails;
	}

}
