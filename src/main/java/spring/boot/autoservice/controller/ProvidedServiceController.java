package spring.boot.autoservice.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "add new service to db")
    public ProvidedServiceResponseDto add(@RequestBody ProvidedServiceRequestDto requestDto) {
        return dtoMapper.toDto(providedServiceService.save(dtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update existing service with id")
    public ProvidedServiceResponseDto update(@PathVariable Long id,
                                             @RequestBody ProvidedServiceRequestDto requestDto) {
        return dtoMapper.toDto(providedServiceService.update(id, dtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}/status")
    @ApiOperation(value = "update status of service with id")
    public void updateStatus(@PathVariable Long id,
                             @RequestBody ProvidedServiceStatusRequestDto requestDto) {
        providedServiceService.updateStatus(id, statusRequestDtoMapper.toModel(requestDto));
    }
}
