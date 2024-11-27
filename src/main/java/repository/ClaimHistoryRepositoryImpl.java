package repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import entity.Claims;
import entity.ClaimsHistory;

public class ClaimHistoryRepositoryImpl implements ClaimHistoryRepository{
	
	private SessionFactory sessionFactory;
	
	

	public ClaimHistoryRepositoryImpl() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	@Override
	public void addClaimHistory(ClaimsHistory claim) {
		// TODO Auto-generated method stub
		try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(claim);
            transaction.commit();
        }
		
	}
	

	@Override
	public void deleteClaimHistorybyClaimId(int claimId) throws Exception {
	    try (Session session = sessionFactory.openSession()) {
	        Transaction transaction = session.beginTransaction();

	        // HQL bulk delete query to delete all ClaimsHistory entries with the given claimId
	        int deletedCount = session.createNativeQuery("DELETE FROM claims_history WHERE claim_id = :claimId")
	                .setParameter("claimId", claimId)
	                .executeUpdate();

	        if (deletedCount == 0) {
	        	System.out.println("No claims history found for Claim ID " + claimId);
	           
	        }

	        transaction.commit();
	    } catch (Exception e) {
	        throw new Exception("Error deleting claims history for Claim ID " + claimId, e);
	    }
	}

	@Override
	public List<Object[]> getClaimsHistoryByPolicy(int policyId) {
	    List<Object[]> claimsHistory = null;

	    try (Session session = sessionFactory.openSession()) {
	        // SQL query to fetch claims history
	        String sql = "SELECT ch.new_status, c.amount, c.created_at AS Submission_Date, " +
	                     "ch.updated_at AS Update_Date, ch.change_reason " +
	                     "FROM claims c " +
	                     "JOIN claim_history ch ON c.id = ch.claim_id " +
	                     "WHERE c.policy_id = :policyId";

	        // Create native query
	        NativeQuery<Object[]> query = session.createNativeQuery(sql);
	        query.setParameter("policyId", policyId); // Set the parameter

	        // Execute the query and retrieve results
	        claimsHistory = query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace(); // Log exception
	        // Optionally rethrow as a custom exception
	    }

	    return claimsHistory;
	}



}
