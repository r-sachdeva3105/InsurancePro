package repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import entity.Claims;
import entity.Customer;

import java.util.List;

public class ClaimsRepositoryImp implements ClaimsRepository {
    private SessionFactory sessionFactory;

    public ClaimsRepositoryImp() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public void addClaim(Claims claim) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(claim);
            transaction.commit();
        }
    }

    @Override
    public void updateClaim(int claimId, String description) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the claim by its ID
            Claims claim = session.get(Claims.class, claimId);
            if (claim != null) {
                // Update the description
                claim.setDescription(description);
                // Save the changes
                session.update(claim);
                transaction.commit();
            } else {
                System.out.println("Claim with ID " + claimId + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Claims getClaimById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Claims.class, id);
        }
    }

    @Override
    public List<Claims> getAllClaims() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Claim", Claims.class).getResultList();
        }
    }

    @Override
    public List<Object[]> getClaimsByBrokerId(int brokerId) {
    	List<Object[]> claimDetails = null;
    	System.out.println(brokerId);
        
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            // Define and set up the native query
            String sql = "SELECT c.id, cust.name AS customerName, p.name AS policyName, c.status " +
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
    public synchronized void deleteClaim(String id) throws Exception{
	
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
	
	 public void close() {
	        if (sessionFactory != null) {
	            sessionFactory.close();
	        }
	    }

}
