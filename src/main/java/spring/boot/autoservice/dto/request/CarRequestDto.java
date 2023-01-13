package spring.boot.autoservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRequestDto {
    private Long licensePlate;
    private String manufacturer;
    private String model;
    private int year;
    private Long ownerId;
}
