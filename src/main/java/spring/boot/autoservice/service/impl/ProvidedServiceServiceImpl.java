package spring.boot.autoservice.service.impl;

import org.springframework.stereotype.Service;
import spring.boot.autoservice.model.ProvidedService;
import spring.boot.autoservice.model.ProvidedServiceStatus;
import spring.boot.autoservice.repository.ProvidedServiceRepository;
import spring.boot.autoservice.service.ProvidedServiceService;

@Service
public class ProvidedServiceServiceImpl implements ProvidedServiceService {
    private final ProvidedServiceRepository serviceRepository;

    public ProvidedServiceServiceImpl(ProvidedServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public ProvidedService save(ProvidedService providedService) {
        providedService.setProvidedServiceStatus(ProvidedServiceStatus.UNPAID);
        return serviceRepository.save(providedService);
    }

    @Override
    public ProvidedService update(Long id, ProvidedService providedService) {
        providedService.setId(id);
        return serviceRepository.save(providedService);
    }

    @Override
    public ProvidedService get(Long id) {
        return serviceRepository.getReferenceById(id);
    }

    @Override
    public void updateStatus(Long id, ProvidedServiceStatus providedServiceStatus) {
        serviceRepository.updateStatus(id, providedServiceStatus);
    }
}
