package repository;

import entity.Broker;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Optional;

public class BrokerRepositoryImp implements BrokerRepository {
    private SessionFactory sessionFactory;

    public BrokerRepositoryImp() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public void save(Broker broker) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(broker);
            transaction.commit();
        }
    }

    @Override
    public Optional<Broker> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Broker broker = session.createQuery("FROM Broker WHERE email = :email", Broker.class)
                                   .setParameter("email", email)
                                   .uniqueResult();
            return Optional.ofNullable(broker);
        }
    }

    public Optional<Broker> findByEmailAndPassword(String email, String passwordHash) {
        try (Session session = sessionFactory.openSession()) {
            Broker broker = session.createQuery("FROM Broker WHERE email = :email AND password = :password", Broker.class)
                                   .setParameter("email", email)
                                   .setParameter("password", passwordHash)
                                   .uniqueResult();
            return Optional.ofNullable(broker);
        }
    }

    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
