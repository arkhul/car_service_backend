package com.kodilla.car_service.repository;

import com.kodilla.car_service.domain.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Override
    List<Client> findAll();
}
