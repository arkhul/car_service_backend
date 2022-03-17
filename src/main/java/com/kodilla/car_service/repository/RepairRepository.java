package com.kodilla.car_service.repository;

import com.kodilla.car_service.domain.Repair;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RepairRepository extends CrudRepository<Repair, Long> {

    @Override
    List<Repair> findAll();
}
