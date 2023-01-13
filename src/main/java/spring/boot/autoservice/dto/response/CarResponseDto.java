package spring.boot.autoservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponseDto {
    private Long id;
    private Long licensePlate;
    private String manufacturer;
    private String model;
    private int year;
    private Long ownerId;
}
