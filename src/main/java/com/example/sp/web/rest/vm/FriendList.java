package com.example.sp.web.rest.vm;

import lombok.Data;

import java.util.List;

@Data
public class FriendList {
    private boolean success;
    private List<String> friends;
    private int count;


    public FriendList(boolean success, List<String> friend) {
        this.success = success;
        this.friends = friend;
    }

    public int getCount() {
        return friends.size();
    }
}
