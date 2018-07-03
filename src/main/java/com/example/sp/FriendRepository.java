package com.example.sp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends CrudRepository<Friend, String> {
    List<Friend> findByUser(String user);

    List<Friend> findByUserOrUser(String userA, String userB);
}
