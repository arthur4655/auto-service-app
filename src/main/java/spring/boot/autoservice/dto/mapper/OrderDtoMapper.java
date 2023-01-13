package spring.boot.autoservice.dto.mapper;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import spring.boot.autoservice.dto.request.OrderRequestDto;
import spring.boot.autoservice.dto.response.OrderResponseDto;
import spring.boot.autoservice.model.Order;
import spring.boot.autoservice.model.Product;
import spring.boot.autoservice.model.ProvidedService;
import spring.boot.autoservice.service.CarService;
import spring.boot.autoservice.service.OwnerService;

@Component
public class OrderDtoMapper {
    private final CarService carService;
    private final OwnerService ownerService;

    public OrderDtoMapper(CarService carService,
                          OwnerService ownerService) {
        this.carService = carService;
        this.ownerService = ownerService;
    }

    public Order toModel(OrderRequestDto dto) {
        Order order = new Order();
        order.setCar(carService.get(dto.getCarId()));
        order.setOwner(ownerService.get(dto.getOwnerId()));
        order.setDescription(dto.getDescription());
        return order;
    }

    public OrderResponseDto toDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setCarId(order.getCar().getId());
        dto.setOwnerId(order.getOwner().getId());
        dto.setDescription(order.getDescription());
        dto.setProductsId(order.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList()));
        dto.setServicesId(order.getServices().stream()
                .map(ProvidedService::getId)
                .collect(Collectors.toList()));
        dto.setOrderStatus(order.getOrderStatus().name());
        dto.setDateOfFinishing(order.getDateOfFinishing());
        dto.setDateOfReceiving(order.getDateOfReceiving());
        dto.setTotalCost(order.getTotalCost());
        return dto;
    }
}
