package spring.boot.autoservice.dto.mapper;

import org.springframework.stereotype.Component;
import spring.boot.autoservice.dto.request.OwnerRequestDto;
import spring.boot.autoservice.dto.response.OwnerResponseDto;
import spring.boot.autoservice.model.Owner;
import spring.boot.autoservice.service.CarService;
import spring.boot.autoservice.service.OrderService;

@Component
public class OwnerDtoMapper {
    private final CarService carService;
    private final OrderService orderService;

    public OwnerDtoMapper(CarService carService, OrderService orderService) {
        this.carService = carService;
        this.orderService = orderService;
    }

    public Owner toModel(OwnerRequestDto dto) {
        Owner owner = new Owner();
        owner.setFullName(dto.getFullName());
        return owner;
    }

    public OwnerResponseDto toDto(Owner owner) {
        OwnerResponseDto dto = new OwnerResponseDto();
        dto.setId(owner.getId());
        dto.setFullName(owner.getFullName());
        return dto;
    }
}
