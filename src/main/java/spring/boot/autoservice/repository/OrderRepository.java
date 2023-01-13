package spring.boot.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.boot.autoservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Query(value = "UPDATE Order o SET o.orderStatus = :status WHERE o.id = :id")
    Order updateStatus(@Param("id") Long id,@Param("status") String orderStatus);
}
