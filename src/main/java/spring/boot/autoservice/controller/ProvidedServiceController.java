package spring.boot.autoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.autoservice.dto.mapper.ProvidedServiceDtoMapper;
import spring.boot.autoservice.dto.mapper.ProvidedServiceStatusRequestDtoMapper;
import spring.boot.autoservice.dto.request.ProvidedServiceRequestDto;
import spring.boot.autoservice.dto.request.ProvidedServiceStatusRequestDto;
import spring.boot.autoservice.dto.response.ProvidedServiceResponseDto;
import spring.boot.autoservice.service.ProvidedServiceService;

@RestController
@RequestMapping("/services")
public class ProvidedServiceController {
    private final ProvidedServiceService providedServiceService;
    private final ProvidedServiceDtoMapper dtoMapper;
    private final ProvidedServiceStatusRequestDtoMapper statusRequestDtoMapper;

    public ProvidedServiceController(ProvidedServiceService providedServiceService,
                                     ProvidedServiceDtoMapper dtoMapper,
                                     ProvidedServiceStatusRequestDtoMapper statusRequestDtoMapper) {
        this.providedServiceService = providedServiceService;
        this.dtoMapper = dtoMapper;
        this.statusRequestDtoMapper = statusRequestDtoMapper;
    }

    @PostMapping
    @Operation(summary = "Add new service to db")
    public ProvidedServiceResponseDto add(@Parameter(description = "Provide the next fields"
            + "in JSON format: mechanicId, price, type, orderId")
                                              @RequestBody ProvidedServiceRequestDto requestDto) {
        return dtoMapper.toDto(providedServiceService.save(dtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update service with id")
    public ProvidedServiceResponseDto update(@Parameter(description = "service id")
                                                 @PathVariable Long id,
                                             @Parameter(description = "Provide the next fields"
                                                     + "in JSON format: mechanicId, price,"
                                                     + " type, orderId")
                                             @RequestBody ProvidedServiceRequestDto requestDto) {
        return dtoMapper.toDto(providedServiceService.update(id, dtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update status of order with id")
    public void updateStatus(@Parameter(description = "service id") @PathVariable Long id,
                             @Parameter(description = "Provide one of the next statuses: "
                                     + "UNPAID, PAID")
                             @RequestBody ProvidedServiceStatusRequestDto requestDto) {
        providedServiceService.updateStatus(id, statusRequestDtoMapper.toModel(requestDto));
    }
}
