package spring.boot.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.autoservice.model.Mechanic;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {
}
