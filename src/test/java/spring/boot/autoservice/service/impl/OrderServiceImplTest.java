package spring.boot.autoservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spring.boot.autoservice.model.Order;
import spring.boot.autoservice.model.OrderStatus;
import spring.boot.autoservice.model.Product;
import spring.boot.autoservice.model.ProvidedService;
import spring.boot.autoservice.model.ProvidedServiceStatus;
import spring.boot.autoservice.repository.OrderRepository;
import spring.boot.autoservice.service.OrderService;
import spring.boot.autoservice.service.ProductService;


class OrderServiceImplTest {
    OrderService orderService;
    OrderRepository orderRepository;
    ProductService productService;

    @BeforeEach
    void setUp() {
       orderRepository = Mockito.mock(OrderRepository.class);
       productService = Mockito.mock(ProductService.class);
       orderService = new OrderServiceImpl(productService, orderRepository);
    }

    @Test
    void getTotalCost_ok() {
        Order order = new Order();
        order.setId(1L);

        List<ProvidedService> services = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ProvidedService service = new ProvidedService();
            service.setType("repair");
            service.setProvidedServiceStatus(ProvidedServiceStatus.UNPAID);
            service.setPrice(new BigDecimal(100 + i * 100));
            services.add(service);
        }
        order.setServices(services);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Product product = new Product();
            product.setPrice(new BigDecimal(100 + i * 50));
            products.add(product);
        }
        order.setProducts(products);

        BigDecimal expected = new BigDecimal("1000.50");

        Mockito.when(orderRepository.getReferenceById(order.getId())).thenReturn(order);

        BigDecimal actual = orderService.getTotalCost(order.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getTotalCost_OneParameterDiagnostic_ok() {
        Order order = new Order();
        order.setId(1L);

        List<ProvidedService> services = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ProvidedService service = new ProvidedService();
            service.setType("repair");
            if (i == 2) {
                service.setType("Diagnostic");
            }
            service.setProvidedServiceStatus(ProvidedServiceStatus.UNPAID);
            service.setPrice(new BigDecimal(100 + i * 100));
            services.add(service);
        }
        order.setServices(services);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Product product = new Product();
            product.setPrice(new BigDecimal(100 + i * 50));
            products.add(product);
        }
        order.setProducts(products);

        BigDecimal expected = new BigDecimal("718.50");

        Mockito.when(orderRepository.getReferenceById(order.getId())).thenReturn(order);

        BigDecimal actual = orderService.getTotalCost(order.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getTotalCost_OnlyOneDiagnosticService_ok() {
        Order order = new Order();
        order.setId(1L);

        List<ProvidedService> services = new ArrayList<>();
        ProvidedService service = new ProvidedService();
        service.setType("Diagnostic");
        service.setPrice(new BigDecimal(500));
        services.add(service);
        order.setServices(services);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Product product = new Product();
            product.setPrice(new BigDecimal(100 + i * 50));
            products.add(product);
        }
        order.setProducts(products);

        BigDecimal expected = new BigDecimal("936.50");

        Mockito.when(orderRepository.getReferenceById(order.getId())).thenReturn(order);

        BigDecimal actual = orderService.getTotalCost(order.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateStatusToCompleted_ok() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.IN_PROCESS);

        Mockito.when(orderRepository.getReferenceById(1L)).thenReturn(order);

        OrderStatus expectedStatus = OrderStatus.COMPLETED;
        orderService.updateStatus(1L, expectedStatus);
        OrderStatus actualStatus = order.getOrderStatus();
        LocalDate actualDate = order.getDateOfFinishing();

        LocalDate expectedDate = LocalDate.now();

        assertEquals(expectedStatus, actualStatus);
        assertEquals(actualDate, expectedDate);
    }

    @Test
    void updateStatusToFailed_ok() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.IN_PROCESS);

        Mockito.when(orderRepository.getReferenceById(1L)).thenReturn(order);

        OrderStatus expectedStatus = OrderStatus.FAILED;
        orderService.updateStatus(1L, expectedStatus);
        OrderStatus actualStatus = order.getOrderStatus();
        LocalDate actualDate = order.getDateOfFinishing();

        LocalDate expectedDate = LocalDate.now();

        assertEquals(expectedStatus, actualStatus);
        assertEquals(actualDate, expectedDate);
    }

    @Test
    void addProductWithId_ok() {
        Product product = new Product();
        product.setId(1L);

        Order order = new Order();
        order.setId(1L);

        Mockito.when(productService.save(product)).thenReturn(product);
        Mockito.when(productService.get(1L)).thenReturn(product);
        Mockito.when(orderRepository.getReferenceById(1L)).thenReturn(order);

        orderService.addProduct(1L, product);

        int actual = order.getProducts().size();
        int expected = 1;

        assertEquals(expected, actual);
    }

    @Test
    void addProductWithoutId_ok() {
        Product product = new Product();

        Order order = new Order();
        order.setId(1L);

        Product productWithId = new Product();
        productWithId.setId(1L);
        Mockito.when(productService.save(product)).thenReturn(productWithId);
        Mockito.when(productService.get(1L)).thenReturn(product);
        Mockito.when(orderRepository.getReferenceById(1L)).thenReturn(order);

        orderService.addProduct(1L, product);

        int actual = order.getProducts().size();
        int expected = 1;

        assertEquals(expected, actual);
    }
}