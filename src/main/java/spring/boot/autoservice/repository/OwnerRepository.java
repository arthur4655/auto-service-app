package spring.boot.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.autoservice.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
