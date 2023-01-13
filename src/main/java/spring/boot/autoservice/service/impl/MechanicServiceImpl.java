package spring.boot.autoservice.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import spring.boot.autoservice.model.Mechanic;
import spring.boot.autoservice.model.ProvidedService;
import spring.boot.autoservice.model.ProvidedServiceStatus;
import spring.boot.autoservice.repository.MechanicRepository;
import spring.boot.autoservice.repository.ProvidedServiceRepository;
import spring.boot.autoservice.service.MechanicService;

@Service
public class MechanicServiceImpl implements MechanicService {
    private static final BigDecimal SALARY_RATIO = new BigDecimal("0.4");

    private final MechanicRepository mechanicRepository;
    private final ProvidedServiceRepository providedServiceRepository;

    public MechanicServiceImpl(MechanicRepository mechanicRepository,
                               ProvidedServiceRepository providedServiceRepository) {
        this.mechanicRepository = mechanicRepository;
        this.providedServiceRepository = providedServiceRepository;
    }

    @Override
    public Mechanic save(Mechanic mechanic) {
        mechanic.setProvidedServices(new ArrayList<>());
        return mechanicRepository.save(mechanic);
    }

    @Override
    public Mechanic update(Long id, Mechanic mechanic) {
        mechanic.setId(id);
        return mechanicRepository.save(mechanic);
    }

    @Override
    public Mechanic get(Long id) {
        return mechanicRepository.getReferenceById(id);
    }

    @Override
    public List<ProvidedService> getProvidedServices(Long id) {
        return mechanicRepository.getReferenceById(id).getProvidedServices();
    }

    public BigDecimal getSalary(Long id) {
        List<ProvidedService> providedServices = mechanicRepository.getReferenceById(id)
                .getProvidedServices().stream()
                    .filter(s -> s.getProvidedServiceStatus() == ProvidedServiceStatus.UNPAID)
                    .toList();
        BigDecimal totalSalary = providedServices.stream()
                .map(s -> s.getPrice().multiply(SALARY_RATIO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        providedServices.forEach(s -> s.setProvidedServiceStatus(ProvidedServiceStatus.PAID));
        providedServiceRepository.saveAll(providedServices);
        return totalSalary;
    }
}
