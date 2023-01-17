package spring.boot.autoservice.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvidedServiceRequestDto {
    private Long mechanicId;
    private BigDecimal price;
    private String type;
    private Long orderId;
}
