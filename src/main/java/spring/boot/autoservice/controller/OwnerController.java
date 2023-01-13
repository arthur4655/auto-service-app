package spring.boot.autoservice.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "add new car owner to db")
    public OwnerResponseDto add(@RequestBody OwnerRequestDto requestDto) {
        return ownerDtoMapper.toDto(ownerService.save(ownerDtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update existing car owner in db")
    public OwnerResponseDto update(@PathVariable Long id,
                                   @RequestBody OwnerRequestDto requestDto) {
        return ownerDtoMapper.toDto(ownerService.update(id, ownerDtoMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get list of orders of owner with id")
    public List<OrderResponseDto> getOrders(@PathVariable Long id) {
        return ownerService.getOwnerProducts(id).stream()
                .map(orderDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
