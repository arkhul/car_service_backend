package com.kodilla.car_service.repository;

import com.kodilla.car_service.domain.Cost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public interface CostRepository extends CrudRepository<Cost, Long> {

    @Override
    List<Cost> findAll();

    Cost findByTotalCost(BigDecimal totalCost);
}
