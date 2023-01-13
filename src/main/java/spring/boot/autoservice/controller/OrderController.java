package spring.boot.autoservice.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "add new order to db")
    public OrderResponseDto add(@RequestBody OrderRequestDto requestDto) {
        return orderDtoMapper.toDto(orderService.save(orderDtoMapper.toModel(requestDto)));
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "add product to order with id")
    public OrderResponseDto addProduct(@PathVariable Long id,
                                       @RequestBody ProductRequestDto requestDto) {
        return orderDtoMapper.toDto(orderService.addProduct(id,
                    productDtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update order with id")
    public OrderResponseDto update(@PathVariable Long id,
                                   @RequestBody OrderRequestDto requestDto) {
        return orderDtoMapper.toDto(orderService.update(id, orderDtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}/status")
    @ApiOperation(value = "update status of order with id")
    public OrderResponseDto updateStatus(@PathVariable Long id,
                                         @RequestBody OrderStatusRequestDto orderStatusRequestDto) {
        return orderDtoMapper.toDto(orderService.updateStatus(id,
                statusDtoMapper.toModel(orderStatusRequestDto)));
    }

    @GetMapping("/{id}/sum")
    @ApiOperation(value = "get total cost of order")
    public BigDecimal getTotalCost(@PathVariable Long id) {
        return orderService.getTotalCost(id);
    }
}
