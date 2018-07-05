package com.example.sp.repository;

import com.example.sp.domain.Subscribe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends CrudRepository<Subscribe, Long> {
    List<Subscribe> findByTarget(String target);

    Optional<Subscribe> findByRequestorAndTarget(String requestor, String target);
}
