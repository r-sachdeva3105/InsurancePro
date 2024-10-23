package repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entity.Claims;

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
    public void updateClaim(Claims claim) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(claim);
            transaction.commit();
        }
    }

    @Override
    public Claims getClaimById(int id) {
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
    public List<Claims> getClaimsByBrokerId(String brokerId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Claim WHERE brokerId = :brokerId", Claims.class)
                          .setParameter("brokerId", brokerId)
                          .getResultList();
        }
    }

    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
