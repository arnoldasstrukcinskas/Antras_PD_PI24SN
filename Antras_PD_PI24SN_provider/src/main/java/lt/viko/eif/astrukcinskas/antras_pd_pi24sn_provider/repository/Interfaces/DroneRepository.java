package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.repository.Interfaces;

import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Integer> {

    Drone findFirstByDroneName(String name);
    List<Drone> findAllByPriceGreaterThan(double price);
}

