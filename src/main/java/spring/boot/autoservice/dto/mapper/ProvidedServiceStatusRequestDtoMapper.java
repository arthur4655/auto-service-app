package spring.boot.autoservice.dto.mapper;

import org.springframework.stereotype.Component;
import spring.boot.autoservice.dto.request.ProvidedServiceStatusRequestDto;
import spring.boot.autoservice.model.ProvidedServiceStatus;

@Component
public class ProvidedServiceStatusRequestDtoMapper {
    public ProvidedServiceStatus toModel(ProvidedServiceStatusRequestDto statusRequestDto) {
        return ProvidedServiceStatus.valueOf(statusRequestDto.getStatusName().toUpperCase());
    }
}
