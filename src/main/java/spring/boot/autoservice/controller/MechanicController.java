package spring.boot.autoservice.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "add mechanic to db")
    public MechanicResponseDto add(@RequestBody MechanicRequestDto requestDto) {
        return mechanicDtoMapper.toDto(mechanicService.save(mechanicDtoMapper
                .toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update existing mechanic in db")
    public MechanicResponseDto update(@PathVariable Long id,
                                      @RequestBody MechanicRequestDto requestDto) {
        return mechanicDtoMapper.toDto(mechanicService.update(id,
                mechanicDtoMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}/services")
    @ApiOperation(value = "get list of orders of mechanic with id")
    public List<ProvidedServiceResponseDto> getOrders(@PathVariable Long id) {
        return mechanicService.getProvidedServices(id).stream()
                .map(providedServiceDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/salary")
    @ApiOperation(value = "get salary of mechanic with id")
    public BigDecimal getSalary(@PathVariable Long id) {
        return mechanicService.getSalary(id);
    }
}
