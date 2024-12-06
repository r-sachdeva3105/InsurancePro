package repository;

import entity.Claims;
import entity.Customer;
import entity.Policy;
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
            String sql = "SELECT pd.id, p.name As policyName, pd.broker_id, pd.premium_amount, pd.term_length " +
                         "FROM policy_details pd " +
                         "JOIN policies p ON pd.policy_id = p.id "
                         + "WHERE pd.customer_id = :id and" +
                         " pd.broker_id = :brokerId and pd.status = 'Active'";
            
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

    @Override
    public synchronized void addPolicyDetails(PolicyDetails details) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Validate policy_id
            Policy policy = session.get(Policy.class, details.getPolicyId());
            if (policy == null) {
                throw new IllegalArgumentException("Policy ID " + details.getPolicyId() + " does not exist in the policies table.");
            }

            // Native SQL insert query
            String sql = "INSERT INTO policy_details (policy_id, customer_id, broker_id, premium_amount, start_date, end_date, term_length, term_factor, status) " +
                         "VALUES (:policyId, :customerId, :brokerId, :premiumAmount, :startDate, :endDate, :termLength, :termFactor, :status)";
            NativeQuery<?> query = session.createNativeQuery(sql);
            query.setParameter("policyId", details.getPolicyId());
            query.setParameter("customerId", details.getCustomerId());
            query.setParameter("brokerId", details.getBrokerId());
            query.setParameter("premiumAmount", details.getPremiumAmount());
            query.setParameter("startDate", details.getStartDate());
            query.setParameter("endDate", details.getEndDate());
            query.setParameter("termLength", details.getTermLength());
            query.setParameter("termFactor", details.getTermFactor());
            query.setParameter("status", details.getStatus());

            query.executeUpdate();
            transaction.commit();
            System.out.println("Policy details added successfully.");
        } catch (Exception e) {
            System.out.println("Error while adding policy details: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error adding policy details", e);
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

	@Override
	public List<Object[]> getPoliciesforRenewal(int brokerId) {
		// TODO Auto-generated method stub
		
		  List<Object[]> policyDetails = null;
	        
	        try (Session session = sessionFactory.openSession()) {
	            Transaction transaction = session.beginTransaction();
	            
	            // Define and set up the native query
	            String sql = "select pd.id,p.id, p.name, c.name, pd.premium_amount, pd.term_length, pd.start_date, pd.end_date from policies p Join policy_details pd  on p.id = pd.policy_id Join customer c on pd.customer_id = c.id where end_date < current_timestamp() and status = \"Active\" and pd.broker_id = :brokerId";
	            
	            NativeQuery<Object[]> query = session.createNativeQuery(sql);
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

	@Override
	public PolicyDetails getDetailById(int id) {
		// TODO Auto-generated method stub
		  try (Session session = sessionFactory.openSession()) {
	            return session.get(PolicyDetails.class, id);
	        }
	}
	
	@Override
	public synchronized void updatePolicyDetails(PolicyDetails details) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();

			PolicyDetails existingCustomer = session.get(PolicyDetails.class, details.getId());
			if (existingCustomer != null) {
				session.merge(details);
				transaction.commit();
			} else {
				throw new IllegalArgumentException("Customer with ID " + details.getId() + " not found.");
			}
		}
	}

	@Override
	public void cancelPolicyForCustomer(int id) {
		// TODO Auto-generated method stub
		
		try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                String sql = "UPDATE policy_details SET status = :status WHERE id = :id";
                NativeQuery<?> query = session.createNativeQuery(sql);
                query.setParameter("status", "Cancelled");
                query.setParameter("id", id);

                // Execute the query
                int rowsUpdated = query.executeUpdate();
                System.out.println("Rows updated in policy details table: " + rowsUpdated);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
		
	}

}
