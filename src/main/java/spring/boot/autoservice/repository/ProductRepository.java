package spring.boot.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.autoservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
