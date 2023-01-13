package spring.boot.autoservice.service.impl;

import org.springframework.stereotype.Service;
import spring.boot.autoservice.model.Car;
import spring.boot.autoservice.repository.CarRepository;
import spring.boot.autoservice.service.CarService;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car update(Long id, Car car) {
        car.setId(id);
        return carRepository.save(car);
    }

    @Override
    public Car get(Long id) {
        return carRepository.getReferenceById(id);
    }
}
