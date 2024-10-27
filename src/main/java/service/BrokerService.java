package service;

import entity.Broker;
import repository.BrokerRepository;
import repository.BrokerRepositoryImp;
import org.mindrot.jbcrypt.BCrypt;

public class BrokerService {
    private BrokerRepository brokerRepository;

    public BrokerService() {
        this.brokerRepository = new BrokerRepositoryImp();
    }

    public boolean registerBroker(String name, String email, String password) {
        if (brokerRepository.findByEmail(email).isPresent()) {
            return false; // Broker with this email already exists
        }
        
        // Hash the password before saving
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        brokerRepository.save(new Broker(name, email, hashedPassword));
        return true;
    }

    public Broker loginBroker(String email, String password) {
        Broker broker = brokerRepository.findByEmail(email).orElse(null);
        
        // Verify password if broker is found
        if (broker != null && BCrypt.checkpw(password, broker.getPassword())) {
            return broker;
        }
        return null; // Return null if authentication fails
    }

    public void close() {
        if (brokerRepository instanceof BrokerRepositoryImp) {
            ((BrokerRepositoryImp) brokerRepository).close();
        }
    }
}
