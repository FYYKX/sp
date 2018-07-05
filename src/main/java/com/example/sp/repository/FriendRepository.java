package com.example.sp.repository;

import com.example.sp.domain.Friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends CrudRepository<Friend, String> {
    Optional<Friend> findByUserAndFriend(String user, String friend);

    List<Friend> findByUser(String user);

    List<Friend> findByUserOrUser(String userA, String userB);
}
