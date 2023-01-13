package spring.boot.autoservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long carId;
    private Long ownerId;
    private String description;
    private List<Long> servicesId;
    private List<Long> productsId;
    private String orderStatus;
    private BigDecimal totalCost;
    private LocalDate dateOfReceiving;
    private LocalDate dateOfFinishing;
}
