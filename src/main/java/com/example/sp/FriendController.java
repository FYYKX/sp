package com.example.sp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class FriendController {
    private final FriendRepository friendRepository;
    private final SubscribeRepository subscribeRepository;
    private final BlockRepository blockRepository;

    public FriendController(FriendRepository friendRepository, SubscribeRepository subscribeRepository, BlockRepository blockRepository) {
        this.friendRepository = friendRepository;
        this.subscribeRepository = subscribeRepository;
        this.blockRepository = blockRepository;
    }

    @PostMapping("/friend")
    public SPResponse create(@RequestBody FriendConnection friendConnection) {
        String friendA = friendConnection.getFriends().get(0);
        String friendB = friendConnection.getFriends().get(1);

        friendRepository.saveAll(Arrays.asList(new Friend(friendA, friendB), new Friend(friendB, friendA)));
        return new SPResponse(true);
    }

    @PostMapping("/list")
    public FriendList retrieve(@Valid @RequestBody RetrieveFriend retrieveFriend) {
        String email = retrieveFriend.getEmail();
        List<String> friends = friendRepository.findByUser(email)
                .stream()
                .map(Friend::getFriend)
                .collect(Collectors.toList());
        return new FriendList(true, friends);
    }

    @PostMapping("/common")
    public FriendList common(@RequestBody FriendConnection friendConnection) {
        String friendA = friendConnection.getFriends().get(0);
        String friendB = friendConnection.getFriends().get(1);

        List<String> friends = friendRepository.findByUserOrUser(friendA, friendB)
                .stream()
                .map(Friend::getFriend)
                .collect(Collectors.toList());

        Set<String> common = friends
                .stream()
                .filter(friend -> Collections.frequency(friends, friend) > 1)
                .collect(Collectors.toSet());

        return new FriendList(true, new ArrayList<>(common));
    }

    @PostMapping("/subscribe")
    public SPResponse subscribe(@RequestBody Subscribe subscribe) {
        subscribeRepository.save(subscribe);
        return new SPResponse(true);
    }

    @PostMapping("/block")
    public SPResponse block(@RequestBody Block block) {
        blockRepository.save(block);
        return new SPResponse(true);
    }

    @PostMapping("/recipient")
    public RecipientList recipient(@RequestBody Message message) {
        String sender = message.getSender();
        // Connection
        List<String> connection = friendRepository.findByUser(sender)
                .stream()
                .map(Friend::getFriend)
                .collect(Collectors.toList());
        // Subscribe
        List<String> subscribe = subscribeRepository.findByTarget(sender)
                .stream()
                .map(Update::getRequestor)
                .collect(Collectors.toList());
        // Mentioned
        List<String> mention = StringUtils.getEmail(message.getText());
        // Block
        List<String> block = blockRepository.findByTarget(sender)
                .stream()
                .map(Block::getRequestor)
                .collect(Collectors.toList());

        connection.addAll(subscribe);
        connection.addAll(mention);
        connection.addAll(block);

        Set<String> recipients = connection
                .stream()
                .filter(email -> Collections.frequency(connection, email) > 1)
                .collect(Collectors.toSet());

        return new RecipientList(true, new ArrayList<>(recipients));
    }


}
