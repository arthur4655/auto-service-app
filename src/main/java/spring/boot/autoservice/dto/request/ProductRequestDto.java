package spring.boot.autoservice.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    private Long id;
    private String name;
    private BigDecimal price;
}
