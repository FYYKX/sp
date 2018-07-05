package com.example.sp.web.rest.vm;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class FriendConnection {
    @Size(min = 2, max = 2)
    List<String> friends;
}
