package com.example.sp.repository;

import com.example.sp.domain.Block;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockRepository extends CrudRepository<Block, Long> {
    List<Block> findByTarget(String target);

    List<Block> findByRequestorOrTarget(String requestor, String target);

    Optional<Block> findByRequestorAndTarget(String requestor, String target);
}
