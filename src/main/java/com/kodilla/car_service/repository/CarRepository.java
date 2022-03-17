package com.kodilla.car_service.repository;

import com.kodilla.car_service.domain.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CarRepository extends CrudRepository<Car, String> {

    @Override
    List<Car> findAll();

    Car findByVin(String vin);
}
