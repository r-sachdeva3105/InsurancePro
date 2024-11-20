package repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import entity.Claims;
import entity.ClaimsHistory;
import entity.Customer;

import java.util.List;

public class ClaimsRepositoryImp implements ClaimsRepository {
    private SessionFactory sessionFactory;
    private ClaimHistoryRepositoryImpl claimHistory;

    public ClaimsRepositoryImp() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        this.claimHistory = new ClaimHistoryRepositoryImpl();
    }

    @Override
    public void addClaim(Claims claim) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(claim);
            transaction.commit();
            
            List<Object[]> claimDetails = getAllClaims();
            
            if(claimDetails.isEmpty())
            	claimHistory.addClaimHistory(new ClaimsHistory(1, claim.getStatus(), claim.getBrokerId(), "Submitted a new Claim"));
            else {
            	Object[] claims = claimDetails.get(claimDetails.size()-1);
            	int claimId = (int)claims[0] + 1;
            	claimHistory.addClaimHistory(new ClaimsHistory(claimId, claim.getStatus(), claim.getBrokerId(), "Submitted a new Claim"));
            }
            
        }
    }

    @Override
    public void updateClaim(int claimId, String description, Double amount) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the claim by its ID
            Claims claim = session.get(Claims.class, claimId);
            if (claim != null) {
                // Update the description
                claim.setDescription(description);
                claim.setAmount(amount);
                // Save the changes
                session.update(claim);
                transaction.commit();
                String changeMessage = "updated amount " + amount + "or Description "+ description;
                claimHistory.addClaimHistory(new ClaimsHistory(claimId, claim.getStatus(), claim.getBrokerId(),changeMessage ));
            } else {
                System.out.println("Claim with ID " + claimId + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Claims getClaimById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Claims.class, id);
        }
    }

    @Override
    public List<Object[]> getAllClaims() {
    	List<Object[]> claimDetails = null;
        
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            // Define and set up the native query
            String sql = "SELECT c.id, b.name As brokerName, cust.name AS customerName, p.name AS policyName, c.description, c.status " +
                         "FROM Claims c " +
                         "JOIN Customer cust ON c.customer_id = cust.id " +
                         "JOIN policies p ON c.policy_id = p.id " +
                         "JOIN brokers b ON c.broker_id = b.id ";
            
            NativeQuery<Object[]> query = session.createNativeQuery(sql);
            
            // Execute the query and retrieve results
            claimDetails = query.getResultList();
            
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions (e.g., log them)
        }
        
        return claimDetails;
    }

    @Override
    public List<Object[]> getClaimsByBrokerId(int brokerId) {
    	List<Object[]> claimDetails = null;
    	System.out.println(brokerId);
        
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            // Define and set up the native query
            String sql = "SELECT c.id, cust.name AS customerName, p.name AS policyName, c.description, c.status " +
                         "FROM Claims c " +
                         "JOIN Customer cust ON c.customer_id = cust.id " +
                         "JOIN policies p ON c.policy_id = p.id " +
                         "WHERE c.broker_id = :brokerId";
            
            NativeQuery<Object[]> query = session.createNativeQuery(sql);
            query.setParameter("brokerId", brokerId);
            
            // Execute the query and retrieve results
            claimDetails = query.getResultList();
            
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions (e.g., log them)
        }
        
        return claimDetails;
    }

  
	@Override
    public synchronized void deleteClaim(int id) throws Exception{
	
		try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Claims claim = session.get(Claims.class, id);
            if (claim != null) {
                session.delete(claim);
                transaction.commit();
                
            } else {
                throw new Exception("Claims with ID " + id + " not found.");
            }
        }
			
		
	}
	
	@Override
	public synchronized void approveRejectClaim(int id, String status) {
		
		try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the claim by its ID
            Claims claim = session.get(Claims.class, id);
            if (claim != null) {
                // Update the description
                claim.setStatus(status);
                // Save the changes
                session.update(claim);
                transaction.commit();
                claimHistory.addClaimHistory(new ClaimsHistory(id, status, claim.getBrokerId(), "Claim was " + status));
            } else {
                System.out.println("Claim with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	@Override
	public List<Object[]> getTotalClaims() {
	    List<Object[]> totalClaims = null;
	    try (Session session = sessionFactory.openSession()) {
	        Transaction transaction = session.beginTransaction();

	        String sql = "SELECT b.id AS broker_id, b.name AS broker_name, COUNT(c.id) AS total_claims FROM brokers b LEFT JOIN policy_details pd ON b.id = pd.broker_id LEFT JOIN Claims c ON pd.policy_id = c.policy_id AND pd.customer_id = c.customer_id GROUP BY b.id;";

	        NativeQuery<Object[]> query = session.createNativeQuery(sql);

	        // Execute the query and retrieve results
	        totalClaims = query.getResultList();

	        transaction.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle exceptions (e.g., log them)
	    }

	    return totalClaims;
	}
	
	@Override
	public List<Object[]> getClaimsRate() {
	    List<Object[]> claimsRate = null;
	    try (Session session = sessionFactory.openSession()) {
	        Transaction transaction = session.beginTransaction();

	        String sql = "SELECT SUM(CASE WHEN status = 'Approved' THEN 1 ELSE 0 END) AS approved_count, SUM(CASE WHEN status = 'Rejected' THEN 1 ELSE 0 END) AS rejected_count, COUNT(*) AS total_claims, ROUND((SUM(CASE WHEN status = 'Approved' THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(*), 0)), 2) AS approval_rate, ROUND((SUM(CASE WHEN status = 'Rejected' THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(*), 0)), 2) AS rejection_rate FROM Claims;";

	        NativeQuery<Object[]> query = session.createNativeQuery(sql);

	        // Execute the query and retrieve results
	        claimsRate = query.getResultList();

	        transaction.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle exceptions (e.g., log them)
	    }

	    return claimsRate;
	}
	
	 public void close() {
	        if (sessionFactory != null) {
	            sessionFactory.close();
	        }
	    }

	@Override
	public void deleteClaimsByCustomerId(int customerId) throws Exception {
		// TODO Auto-generated method stub
		
		   try (Session session = sessionFactory.openSession()) {
		        Transaction transaction = session.beginTransaction();
		        
		      
		        // HQL bulk delete query to delete all ClaimsHistory entries with the given claimId
		        int deletedCount = session.createNativeQuery("DELETE FROM claims WHERE customer_id = :customerId")
		                .setParameter("customerId", customerId)
		                .executeUpdate();

		        if (deletedCount == 0) {
		        	System.out.println("No claims history found for Claim ID " + customerId);
		           
		        }

		        transaction.commit();
		    } catch (Exception e) {
		        throw new Exception("Error deleting claims history for Claim ID " + customerId, e);
		    }
		
	}

	@Override
	public void deleteClaimsByPolicyId(int policyId) throws Exception {
		// TODO Auto-generated method stub
		
		 try (Session session = sessionFactory.openSession()) {
		        Transaction transaction = session.beginTransaction();

		        // HQL bulk delete query to delete all ClaimsHistory entries with the given claimId
		        int deletedCount = session.createNativeQuery("DELETE FROM claims WHERE policy_id = :policyId")
		                .setParameter("policyId", policyId)
		                .executeUpdate();

		        if (deletedCount == 0) {
		        	System.out.println("No claims history found for Claim ID " + policyId);
		           
		        }

		        transaction.commit();
		    } catch (Exception e) {
		        throw new Exception("Error deleting claims history for Claim ID " + policyId, e);
		    }
		
	}

}
