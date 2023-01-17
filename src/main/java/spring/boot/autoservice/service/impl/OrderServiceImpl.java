package spring.boot.autoservice.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
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
        List<Product> products = order.getProducts();
        products.add(productToAdd);
        order.setProducts(products);
        return orderRepository.save(order);
    }

    @Override
    public Order addService(Long id, ProvidedService providedService) {
        Order order = orderRepository.getReferenceById(id);
        List<ProvidedService> services = order.getServices();
        services.add(providedService);
        order.setServices(services);
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(Long id, OrderStatus orderStatus) {
        if (orderStatus.equals(OrderStatus.COMPLETED)
                || orderStatus.equals(OrderStatus.FAILED)) {
            Order order = orderRepository.getReferenceById(id);
            order.setDateOfFinishing(LocalDate.now());
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
        BigDecimal productPrice = countProductPrice(order);
        BigDecimal servicePrice = countServicePrice(order);
        return productPrice.add(servicePrice);
    }

    private BigDecimal countProductPrice(Order order) {
        BigDecimal productDiscount = BigDecimal.valueOf(1 - order.getProducts().size()
                * PRODUCT_DISCOUNT);
        return order.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(productDiscount);
    }

    private BigDecimal countServicePrice(Order order) {
        BigDecimal serviceDiscount = BigDecimal.valueOf(1 - order.getServices().size()
                * SERVICE_DISCOUNT);
        if (order.getServices().size() > 1) {
            return order.getServices().stream()
                    .filter(s -> s.getProvidedServiceStatus() == ProvidedServiceStatus.UNPAID)
                    .filter(s -> !s.getType().toLowerCase()
                            .contains(SERVICE_TYPE_DIAGNOSTIC))
                    .map(ProvidedService::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(serviceDiscount);
        } else if (order.getServices().stream()
                .anyMatch(s -> s.getType().toLowerCase().contains(SERVICE_TYPE_DIAGNOSTIC))) {
            return BigDecimal.valueOf(500);
        }
        return new BigDecimal(BigInteger.ZERO);
    }
}
