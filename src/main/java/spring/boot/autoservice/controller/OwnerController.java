package spring.boot.autoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.autoservice.dto.mapper.OrderDtoMapper;
import spring.boot.autoservice.dto.mapper.OwnerDtoMapper;
import spring.boot.autoservice.dto.request.OwnerRequestDto;
import spring.boot.autoservice.dto.response.OrderResponseDto;
import spring.boot.autoservice.dto.response.OwnerResponseDto;
import spring.boot.autoservice.service.OwnerService;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;
    private final OwnerDtoMapper ownerDtoMapper;
    private final OrderDtoMapper orderDtoMapper;

    public OwnerController(OwnerService ownerService,
                           OwnerDtoMapper ownerDtoMapper, OrderDtoMapper orderDtoMapper) {
        this.ownerService = ownerService;
        this.ownerDtoMapper = ownerDtoMapper;
        this.orderDtoMapper = orderDtoMapper;
    }

    @PostMapping
    @Operation(summary = "Add new owner to db")
    public OwnerResponseDto add(@RequestBody OwnerRequestDto requestDto) {
        return ownerDtoMapper.toDto(ownerService.save(ownerDtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update owner with id")
    public OwnerResponseDto update(@Parameter(description = "owner id") @PathVariable Long id,
                                   @Parameter(description = "Provide the next fields in"
                                           + " JSON format: fullName")
                                   @RequestBody OwnerRequestDto requestDto) {
        return ownerDtoMapper.toDto(ownerService.update(id, ownerDtoMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get orders of owner with id")
    public List<OrderResponseDto> getOrders(@Parameter(description = "owner id")
                                                @PathVariable Long id) {
        return ownerService.getOwnerProducts(id).stream()
                .map(orderDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
