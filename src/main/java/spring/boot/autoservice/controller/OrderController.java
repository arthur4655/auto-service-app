package spring.boot.autoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.autoservice.dto.mapper.OrderDtoMapper;
import spring.boot.autoservice.dto.mapper.OrderStatusDtoMapper;
import spring.boot.autoservice.dto.mapper.ProductDtoMapper;
import spring.boot.autoservice.dto.request.OrderRequestDto;
import spring.boot.autoservice.dto.request.OrderStatusRequestDto;
import spring.boot.autoservice.dto.request.ProductRequestDto;
import spring.boot.autoservice.dto.response.OrderResponseDto;
import spring.boot.autoservice.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;
    private final ProductDtoMapper productDtoMapper;
    private final OrderStatusDtoMapper statusDtoMapper;

    public OrderController(OrderService orderService, OrderDtoMapper orderDtoMapper,
                           ProductDtoMapper productDtoMapper,
                           OrderStatusDtoMapper statusDtoMapper) {
        this.orderService = orderService;
        this.orderDtoMapper = orderDtoMapper;
        this.productDtoMapper = productDtoMapper;
        this.statusDtoMapper = statusDtoMapper;
    }

    @PostMapping
    @Operation(summary = "Add new order to db")
    public OrderResponseDto add(@Parameter(description = "Provide the next fields in JSON format: "
                                    + "orderId, ownerId, description")
                                    @RequestBody OrderRequestDto requestDto) {
        return orderDtoMapper.toDto(orderService.save(orderDtoMapper.toModel(requestDto)));
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add product to db")
    public OrderResponseDto addProduct(@Parameter(description = "order id") @PathVariable Long id,
                                       @Parameter(description = "Provide the next product"
                                               + " fields in JSON format: id, price, name")
                                       @RequestBody ProductRequestDto requestDto) {
        return orderDtoMapper.toDto(orderService.addProduct(id,
                    productDtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update order in db")
    public OrderResponseDto update(@Parameter(description = "order id") @PathVariable Long id,
                                   @Parameter(description = "Provide the next fields in "
                                           + "JSON format: orderId, ownerId, description")
                                   @RequestBody OrderRequestDto requestDto) {
        return orderDtoMapper.toDto(orderService.update(id, orderDtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update status of order")
    public OrderResponseDto updateStatus(@Parameter(description = "order id")
                                             @PathVariable Long id,
                                         @Parameter(description = "provide one of the "
                                                 + "next statuses : IN_PROCESS, COMPLETED,"
                                                 + " FAILED, PAID")
                                         @RequestBody OrderStatusRequestDto orderStatusRequestDto) {
        return orderDtoMapper.toDto(orderService.updateStatus(id,
                statusDtoMapper.toModel(orderStatusRequestDto)));
    }

    @GetMapping("/{id}/sum")
    @Operation(summary = "Get total cost of order")
    public BigDecimal getTotalCost(@Parameter(description = "order id") @PathVariable Long id) {
        return orderService.getTotalCost(id);
    }
}
