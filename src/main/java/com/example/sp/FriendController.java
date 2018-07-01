package com.example.sp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

@RestController
public class FriendController {
    @PostMapping("/friend")
    public SPResponse create(@RequestBody FriendConnection friendConnection) {
        return new SPResponse(true);
    }

    @PostMapping("/list")
    public FriendList retrieve(@RequestBody RetrieveFriend retrieveFriend) {
        return new FriendList(true, Collections.singletonList("john@example.com"));
    }

    @PostMapping("/common")
    public FriendList common(@RequestBody FriendConnection friendConnection) {
        return new FriendList(true, Collections.singletonList("common@example.com"));
    }

    @PostMapping("/subscribe")
    public SPResponse subscribe(@RequestBody Update subscribe) {
        return new SPResponse(true);
    }

    @PostMapping("/block")
    public SPResponse block(@RequestBody Update block) {
        return new SPResponse(true);
    }

    @PostMapping("/recipient")
    public RecipientList recipient(@RequestBody Message message) {
        return new RecipientList(true, Arrays.asList("lisa@example.com", "kate@example.com"));
    }
}
