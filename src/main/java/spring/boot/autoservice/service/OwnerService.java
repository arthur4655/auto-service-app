package spring.boot.autoservice.service;

import java.util.List;
import spring.boot.autoservice.model.Order;
import spring.boot.autoservice.model.Owner;

public interface OwnerService extends AbstractService<Owner, Long> {
    List<Order> getOwnerProducts(Long id);
}
