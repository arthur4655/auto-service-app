package spring.boot.autoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.autoservice.dto.mapper.MechanicDtoMapper;
import spring.boot.autoservice.dto.mapper.ProvidedServiceDtoMapper;
import spring.boot.autoservice.dto.request.MechanicRequestDto;
import spring.boot.autoservice.dto.response.MechanicResponseDto;
import spring.boot.autoservice.dto.response.ProvidedServiceResponseDto;
import spring.boot.autoservice.service.MechanicService;

@RestController
@RequestMapping("/mechanics")
public class MechanicController {
    private final MechanicService mechanicService;
    private final MechanicDtoMapper mechanicDtoMapper;
    private final ProvidedServiceDtoMapper providedServiceDtoMapper;

    public MechanicController(MechanicService mechanicService,
                              MechanicDtoMapper mechanicDtoMapper,
                              ProvidedServiceDtoMapper providedServiceDtoMapper) {
        this.mechanicService = mechanicService;
        this.mechanicDtoMapper = mechanicDtoMapper;
        this.providedServiceDtoMapper = providedServiceDtoMapper;
    }

    @PostMapping
    @Operation(summary = "Add new mechanic to db")
    public MechanicResponseDto add(@Parameter(description = "Provide the next fields in JSON format"
            + ": fullName")
                                       @RequestBody MechanicRequestDto requestDto) {
        return mechanicDtoMapper.toDto(mechanicService.save(mechanicDtoMapper
                .toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update mechanic with id")
    public MechanicResponseDto update(@Parameter(description = "mechanic id") @PathVariable Long id,
                                      @Parameter(description = "Provide the next fields in "
                                              + "JSON format : fullName")
                                      @RequestBody MechanicRequestDto requestDto) {
        return mechanicDtoMapper.toDto(mechanicService.update(id,
                mechanicDtoMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}/services")
    @Operation(summary = "Get list of completed orders of mechanic on id")
    public List<ProvidedServiceResponseDto> getOrders(@Parameter(description = "mechanic id")
                                                          @PathVariable Long id) {
        return mechanicService.getProvidedServices(id).stream()
                .map(providedServiceDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/salary")
    @Operation(summary = "Get salary of mechanic with id")
    public BigDecimal getSalary(@Parameter(description = "mechanic id") @PathVariable Long id) {
        return mechanicService.getSalary(id);
    }
}
