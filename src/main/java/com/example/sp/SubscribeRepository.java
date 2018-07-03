package com.example.sp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends CrudRepository<Subscribe, Long> {
    List<Subscribe> findByTarget(String target);
}
