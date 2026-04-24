package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.repository.Interfaces;

import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    Manager findFirstByUsername(String username);
}
