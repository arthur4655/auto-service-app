package spring.boot.autoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.autoservice.dto.mapper.CarDtoMapper;
import spring.boot.autoservice.dto.request.CarRequestDto;
import spring.boot.autoservice.dto.response.CarResponseDto;
import spring.boot.autoservice.service.CarService;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final CarDtoMapper dtoMapper;

    public CarController(CarService carService, CarDtoMapper dtoMapper) {
        this.carService = carService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    @Operation(summary = "Add new car to db")
    public CarResponseDto add(@Parameter(description = "Provide required the next fields in"
            + " JSON format: licensePlate, manufacturer, model, year, ownerId")
                                  @RequestBody CarRequestDto requestDto) {
        return dtoMapper.toDto(carService.save(dtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update car with provided id in db")
    public CarResponseDto update(@Parameter(description = "id of car") @PathVariable Long id,
                                 @Parameter(description = "Provide required the next fields in"
                                         + " JSON format: licensePlate, manufacturer,"
                                         + " model, year, ownerId")
                                 @RequestBody CarRequestDto requestDto) {
        return dtoMapper.toDto(carService.update(id, dtoMapper.toModel(requestDto)));
    }
}
