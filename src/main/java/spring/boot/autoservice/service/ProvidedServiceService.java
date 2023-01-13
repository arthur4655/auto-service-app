package spring.boot.autoservice.service;

import spring.boot.autoservice.model.ProvidedService;
import spring.boot.autoservice.model.ProvidedServiceStatus;

public interface ProvidedServiceService extends AbstractService<ProvidedService, Long> {
    void updateStatus(Long id, ProvidedServiceStatus providedServiceStatus);
}
