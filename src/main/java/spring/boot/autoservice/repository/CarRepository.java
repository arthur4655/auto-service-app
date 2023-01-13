package spring.boot.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.autoservice.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
