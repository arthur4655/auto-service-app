package spring.boot.autoservice.service;

import java.math.BigDecimal;
import spring.boot.autoservice.model.Order;
import spring.boot.autoservice.model.OrderStatus;
import spring.boot.autoservice.model.Product;
import spring.boot.autoservice.model.ProvidedService;

public interface OrderService extends AbstractService<Order, Long> {
    Order addProduct(Long id, Product product);

    Order addService(Long id, ProvidedService providedService);

    Order updateStatus(Long id, OrderStatus orderStatus);

    BigDecimal getTotalCost(Long id);
}
