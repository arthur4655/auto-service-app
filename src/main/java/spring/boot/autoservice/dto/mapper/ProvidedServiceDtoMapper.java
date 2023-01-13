package spring.boot.autoservice.dto.mapper;

import org.springframework.stereotype.Component;
import spring.boot.autoservice.dto.request.ProvidedServiceRequestDto;
import spring.boot.autoservice.dto.response.ProvidedServiceResponseDto;
import spring.boot.autoservice.model.ProvidedService;
import spring.boot.autoservice.service.MechanicService;
import spring.boot.autoservice.service.OrderService;

@Component
public class ProvidedServiceDtoMapper {
    private final MechanicService mechanicService;
    private final OrderService orderService;

    public ProvidedServiceDtoMapper(MechanicService mechanicService,
                                    OrderService orderService) {
        this.mechanicService = mechanicService;
        this.orderService = orderService;
    }

    public ProvidedService toModel(ProvidedServiceRequestDto dto) {
        ProvidedService service = new ProvidedService();
        service.setPrice(dto.getPrice());
        service.setMechanic(mechanicService.get(dto.getMechanicId()));
        service.setDescription(dto.getDescription());
        service.setOrder(orderService.get(dto.getOrderId()));
        return service;
    }

    public ProvidedServiceResponseDto toDto(ProvidedService providedService) {
        ProvidedServiceResponseDto dto = new ProvidedServiceResponseDto();
        dto.setId(providedService.getId());
        dto.setDescription(providedService.getDescription());
        dto.setMechanicId(providedService.getMechanic().getId());
        dto.setPrice(providedService.getPrice());
        dto.setProvidedServiceStatus(providedService.getProvidedServiceStatus().name());
        return dto;
    }
}
