package spring.boot.autoservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spring.boot.autoservice.model.Mechanic;
import spring.boot.autoservice.model.ProvidedService;
import spring.boot.autoservice.model.ProvidedServiceStatus;
import spring.boot.autoservice.repository.MechanicRepository;
import spring.boot.autoservice.repository.ProvidedServiceRepository;
import spring.boot.autoservice.service.MechanicService;

class MechanicServiceImplTest {
    MechanicService mechanicService;
    MechanicRepository mechanicRepository;
    ProvidedServiceRepository serviceRepository;

    @BeforeEach
    void setUp() {
        mechanicRepository = Mockito.mock(MechanicRepository.class);
        serviceRepository = Mockito.mock(ProvidedServiceRepository.class);
        mechanicService = new MechanicServiceImpl(mechanicRepository, serviceRepository);
    }

    @Test
    void getSalary_ok() {
        Mechanic mechanic = new Mechanic();
        mechanic.setId(1L);
        List<ProvidedService> completedServices = new ArrayList<>();
        BigDecimal expected = new BigDecimal(BigInteger.ZERO);
        for (int i = 1; i < 4; i++) {
            ProvidedService service = new ProvidedService();
            service.setProvidedServiceStatus(ProvidedServiceStatus.UNPAID);
            service.setPrice(new BigDecimal(500 + i * 100));
            BigDecimal multiply = service.getPrice().multiply(new BigDecimal("0.4"));
            expected = expected.add(multiply);
            completedServices.add(service);
        }
        mechanic.setProvidedServices(completedServices);

        Mockito.when(mechanicRepository.getReferenceById(mechanic.getId())).thenReturn(mechanic);

        BigDecimal actual = mechanicService.getSalary(mechanic.getId());
        List<ProvidedServiceStatus> expectedStatuses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            expectedStatuses.add(ProvidedServiceStatus.PAID);
        }
        List<ProvidedServiceStatus> actualStatuses = mechanic.getProvidedServices().stream()
                .map(ProvidedService::getProvidedServiceStatus)
                .toList();

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(expectedStatuses, actualStatuses);
    }

    @Test
    void getSalaryOneServiceIsPaid_ok() {
        Mechanic mechanic = new Mechanic();
        mechanic.setId(1L);
        List<ProvidedService> completedServices = new ArrayList<>();
        BigDecimal expected = BigDecimal.ZERO;
        for (int i = 1; i < 4; i++) {
            ProvidedService service = new ProvidedService();
            service.setProvidedServiceStatus(ProvidedServiceStatus.UNPAID);
            service.setPrice(new BigDecimal(300 + i * 100));
            BigDecimal multiply = service.getPrice().multiply(new BigDecimal("0.4"));
            expected = expected.add(multiply);
            completedServices.add(service);
        }
        ProvidedService paidService = new ProvidedService();
        paidService.setPrice(new BigDecimal(500));
        paidService.setProvidedServiceStatus(ProvidedServiceStatus.PAID);
        completedServices.add(paidService);

        mechanic.setProvidedServices(completedServices);
        Mockito.when(mechanicRepository.getReferenceById(mechanic.getId())).thenReturn(mechanic);

        BigDecimal actual = mechanicService.getSalary(mechanic.getId());

        List<ProvidedServiceStatus> expectedStatuses = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            expectedStatuses.add(ProvidedServiceStatus.PAID);
        }
        List<ProvidedServiceStatus> actualStatuses = mechanic.getProvidedServices().stream()
                .map(ProvidedService::getProvidedServiceStatus)
                .toList();

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(expectedStatuses, actualStatuses);
    }
}