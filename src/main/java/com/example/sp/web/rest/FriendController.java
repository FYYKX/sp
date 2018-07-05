package com.example.sp.web.rest;

import com.example.sp.domain.Block;
import com.example.sp.domain.Friend;
import com.example.sp.domain.Subscribe;
import com.example.sp.repository.BlockRepository;
import com.example.sp.repository.FriendRepository;
import com.example.sp.repository.SubscribeRepository;
import com.example.sp.web.rest.errors.AlreadyUpdateException;
import com.example.sp.web.rest.errors.CannotAddException;
import com.example.sp.web.rest.errors.ConnectionAlreadyAddedException;
import com.example.sp.web.rest.errors.SPException;
import com.example.sp.web.rest.util.StringUtils;
import com.example.sp.web.rest.vm.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public SPResponse create(@Valid @RequestBody FriendConnection friendConnection) {
        String friendA = friendConnection.getFriends().get(0);
        String friendB = friendConnection.getFriends().get(1);

        if (friendRepository.findByUserAndFriend(friendA, friendB).isPresent()) {
            throw new ConnectionAlreadyAddedException();
        } else if (blockRepository.findByRequestorAndTarget(friendA, friendB).isPresent() || blockRepository.findByRequestorAndTarget(friendB, friendA).isPresent()) {
            throw new CannotAddException();
        } else {
            friendRepository.saveAll(Arrays.asList(new Friend(friendA, friendB), new Friend(friendB, friendA)));
            return new SPResponse(true);
        }
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
    public SPResponse subscribe(@Valid @RequestBody UpdateVM subscribe) {
        if (subscribeRepository.findByRequestorAndTarget(subscribe.getRequestor(), subscribe.getTarget()).isPresent()) {
            throw new AlreadyUpdateException("subscribe");
        } else {
            subscribeRepository.save(new Subscribe(subscribe));
            return new SPResponse(true);
        }
    }

    @PostMapping("/block")
    public SPResponse block(@Valid @RequestBody UpdateVM block) {
        if (blockRepository.findByRequestorAndTarget(block.getRequestor(), block.getTarget()).isPresent()) {
            throw new AlreadyUpdateException("block");
        } else {
            blockRepository.save(new Block(block));
            return new SPResponse(true);
        }
    }

    @PostMapping("/recipient")
    public RecipientList recipient(@RequestBody Message message) {
        String sender = message.getSender();
        List<String> connection = friendRepository.findByUser(sender)
                .stream()
                .map(Friend::getFriend)
                .collect(Collectors.toList());
        List<String> subscribe = subscribeRepository.findByTarget(sender)
                .stream()
                .map(Subscribe::getRequestor)
                .collect(Collectors.toList());
        List<String> mention = StringUtils.getEmail(message.getText());

        connection.addAll(subscribe);
        connection.addAll(mention);

        List<String> block = blockRepository.findByRequestorOrTarget(sender, sender)
                .stream()
                .map(b -> b.getRequestor().equalsIgnoreCase(sender) ? b.getTarget() : b.getRequestor())
                .collect(Collectors.toList());

        List<String> recipients = connection
                .stream()
                .distinct()
                .filter(e -> !block.contains(e))
                .collect(Collectors.toList());

        return new RecipientList(true, recipients);
    }

    @ExceptionHandler(SPException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SPResponse handle(Exception exception) {
        return new SPResponse(false, exception.getMessage());
    }
}
