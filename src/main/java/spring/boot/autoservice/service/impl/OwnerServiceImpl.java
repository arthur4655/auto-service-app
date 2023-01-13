package spring.boot.autoservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import spring.boot.autoservice.model.Order;
import spring.boot.autoservice.model.Owner;
import spring.boot.autoservice.repository.OwnerRepository;
import spring.boot.autoservice.service.OwnerService;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner save(Owner owner) {
        owner.setOrders(new ArrayList<>());
        return ownerRepository.save(owner);
    }

    @Override
    public Owner update(Long id, Owner owner) {
        owner.setId(id);
        return ownerRepository.save(owner);
    }

    @Override
    public Owner get(Long id) {
        return ownerRepository.getReferenceById(id);
    }

    @Override
    public List<Order> getOwnerProducts(Long id) {
        return ownerRepository.getReferenceById(id).getOrders();
    }
}
