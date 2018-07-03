package com.example.sp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockRepository extends CrudRepository<Block, Long> {
    List<Block> findByTarget(String target);

    Optional<Block> findByRequestorOrTarget(String requestor, String target);
}
