package com.kodilla.car_service.repository;

import com.kodilla.car_service.domain.ServiceTechnician;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ServiceTechnicianRepository extends CrudRepository<ServiceTechnician, Long> {

    @Override
    List<ServiceTechnician> findAll();

    ServiceTechnician findByName(String name);
}