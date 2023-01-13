package spring.boot.autoservice.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import spring.boot.autoservice.model.Order;
import spring.boot.autoservice.model.OrderStatus;
import spring.boot.autoservice.model.Product;
import spring.boot.autoservice.model.ProvidedService;
import spring.boot.autoservice.model.ProvidedServiceStatus;
import spring.boot.autoservice.repository.OrderRepository;
import spring.boot.autoservice.service.OrderService;
import spring.boot.autoservice.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String SERVICE_TYPE_DIAGNOSTIC = "diagnostic";
    private static final double PRODUCT_DISCOUNT = 0.01;
    private static final double SERVICE_DISCOUNT = 0.02;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(ProductService productService, OrderRepository orderRepository) {
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        order.setOrderStatus(OrderStatus.ACCEPTED);
        order.setDateOfReceiving(LocalDate.now());
        return orderRepository.save(order);
    }

    @Override
    public Order addProduct(Long id, Product product) {
        Product productToAdd = product.getId() == null ? productService.save(product) :
                productService.get(product.getId());
        Order order = orderRepository.getReferenceById(id);
        order.getProducts().add(productToAdd);
        return orderRepository.save(order);
    }

    @Override
    public Order addService(Long id, ProvidedService providedService) {
        Order order = orderRepository.getReferenceById(id);
        order.getServices().add(providedService);
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(Long id, OrderStatus orderStatus) {
        if (orderStatus.equals(OrderStatus.COMPLETED)
                || orderStatus.equals(OrderStatus.FAILED)) {
            LocalDate now = LocalDate.now();
            Order order = orderRepository.getReferenceById(id);
            order.setDateOfFinishing(now);
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        return orderRepository.updateStatus(id, orderStatus.name());
    }

    @Override
    public BigDecimal getTotalCost(Long id) {
        Order order = orderRepository.getReferenceById(id);
        BigDecimal totalCost = countTotalCost(order);
        order.setTotalCost(totalCost);
        orderRepository.save(order);
        return totalCost;
    }

    @Override
    public Order update(Long id, Order order) {
        order.setId(id);
        return orderRepository.save(order);
    }

    @Override
    public Order get(Long id) {
        return orderRepository.getReferenceById(id);
    }

    private BigDecimal countTotalCost(Order order) {
        BigDecimal serviceDiscount = BigDecimal.valueOf(1 - order.getServices().size()
                * SERVICE_DISCOUNT);
        BigDecimal productDiscount = BigDecimal.valueOf(1 - order.getProducts().size()
                * PRODUCT_DISCOUNT);
        BigDecimal productsPrice = order.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(productDiscount);
        BigDecimal servicesPrice = null;
        if (order.getServices().size() > 1) {
            servicesPrice = order.getServices().stream()
                    .filter(s -> s.getProvidedServiceStatus() == ProvidedServiceStatus.UNPAID)
                    .filter(s -> !s.getDescription().toLowerCase()
                            .contains(SERVICE_TYPE_DIAGNOSTIC))
                    .map(ProvidedService::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(serviceDiscount);
        } else if (order.getServices().stream()
                .anyMatch(s -> s.getDescription().toLowerCase()
                        .contains(SERVICE_TYPE_DIAGNOSTIC))) {
            servicesPrice = BigDecimal.valueOf(500);
        }
        return productsPrice.add(servicesPrice);
    }
}
