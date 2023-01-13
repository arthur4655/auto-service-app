package spring.boot.autoservice.dto.mapper;

import org.springframework.stereotype.Component;
import spring.boot.autoservice.dto.request.CarRequestDto;
import spring.boot.autoservice.dto.response.CarResponseDto;
import spring.boot.autoservice.model.Car;
import spring.boot.autoservice.service.OwnerService;

@Component
public class CarDtoMapper {
    private OwnerService ownerService;

    public CarDtoMapper(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    public Car toModel(CarRequestDto dto) {
        Car car = new Car();
        car.setLicensePlate(dto.getLicensePlate());
        car.setModel(dto.getModel());
        car.setYear(dto.getYear());
        car.setManufacturer(dto.getManufacturer());
        car.setOwner(ownerService.get(dto.getOwnerId()));
        return car;
    }

    public CarResponseDto toDto(Car car) {
        CarResponseDto dto = new CarResponseDto();
        dto.setId(car.getId());
        dto.setLicensePlate(car.getLicensePlate());
        dto.setManufacturer(car.getManufacturer());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setOwnerId(car.getOwner().getId());
        return dto;
    }
}
