package spring.boot.autoservice.dto.mapper;

import org.springframework.stereotype.Component;
import spring.boot.autoservice.dto.request.OrderStatusRequestDto;
import spring.boot.autoservice.model.OrderStatus;

@Component
public class OrderStatusDtoMapper {
    public OrderStatus toModel(OrderStatusRequestDto status) {
        return OrderStatus.valueOf(status.getStatusName().toUpperCase());
    }
}
