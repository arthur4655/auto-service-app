package spring.boot.autoservice.dto.mapper;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import spring.boot.autoservice.dto.request.MechanicRequestDto;
import spring.boot.autoservice.dto.response.MechanicResponseDto;
import spring.boot.autoservice.model.Mechanic;
import spring.boot.autoservice.model.ProvidedService;

@Component
public class MechanicDtoMapper {
    public Mechanic toModel(MechanicRequestDto dto) {
        Mechanic mechanic = new Mechanic();
        mechanic.setFullName(dto.getFullName());
        return mechanic;
    }

    public MechanicResponseDto toDto(Mechanic mechanic) {
        MechanicResponseDto dto = new MechanicResponseDto();
        dto.setId(mechanic.getId());
        dto.setFullName(mechanic.getFullName());
        dto.setProvidedServicesIds(mechanic.getProvidedServices().stream()
                .map(ProvidedService::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}
