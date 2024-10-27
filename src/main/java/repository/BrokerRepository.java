package repository;

import entity.Broker;
import java.util.Optional;

public interface BrokerRepository {
    void save(Broker broker);
    Optional<Broker> findByEmail(String email);
}
