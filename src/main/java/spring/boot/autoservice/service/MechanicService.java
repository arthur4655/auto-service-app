package spring.boot.autoservice.service;

import java.math.BigDecimal;
import java.util.List;
import spring.boot.autoservice.model.Mechanic;
import spring.boot.autoservice.model.ProvidedService;

public interface MechanicService extends AbstractService<Mechanic, Long> {
    List<ProvidedService> getProvidedServices(Long id);

    BigDecimal getSalary(Long id);
}
