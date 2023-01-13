package spring.boot.autoservice.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MechanicResponseDto {
    private Long id;
    private String fullName;
    private List<Long> providedServicesIds;
}
