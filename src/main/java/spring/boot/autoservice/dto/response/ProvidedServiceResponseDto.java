package spring.boot.autoservice.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProvidedServiceResponseDto {
    private Long id;
    private String description;
    private Long mechanicId;
    private BigDecimal price;
    private String providedServiceStatus;
}
