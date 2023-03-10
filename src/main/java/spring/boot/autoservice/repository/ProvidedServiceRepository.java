package spring.boot.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.boot.autoservice.model.ProvidedService;
import spring.boot.autoservice.model.ProvidedServiceStatus;

public interface ProvidedServiceRepository extends JpaRepository<ProvidedService, Long> {
    @Modifying
    @Query(value = "UPDATE ProvidedService ps SET ps.providedServiceStatus = :status "
            + "WHERE ps.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") ProvidedServiceStatus status);
}
