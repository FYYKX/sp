package com.example.sp;

import com.example.sp.domain.Friend;
import com.example.sp.repository.BlockRepository;
import com.example.sp.repository.FriendRepository;
import com.example.sp.repository.SubscribeRepository;
import com.example.sp.web.rest.FriendController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(FriendController.class)
@AutoConfigureRestDocs
public class FriendControllerTest {
    private static final String ANDY = "andy@example.com";
    private static final String JOHN = "john@example.com";
    private static final String COMMON = "common@example.com";
    private static final String LISA = "lisa@example.com";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FriendRepository friendRepository;
    @MockBean
    private BlockRepository blockRepository;
    @MockBean
    private SubscribeRepository subscribeRepository;

    @Test
    public void createFriendConnection() throws Exception {
        given(friendRepository.findByUserAndFriend(anyString(), anyString()))
                .willReturn(Optional.empty());
        given(blockRepository.findByRequestorAndTarget(anyString(), anyString()))
                .willReturn(Optional.empty());
        given(friendRepository.saveAll(any()))
                .willReturn(null);

        mockMvc
                .perform(post("/friend")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"friends\": [\n" +
                                "    \"andy@example.com\",\n" +
                                "    \"john@example.com\"\n" +
                                "  ]\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"success\": true\n" +
                        "}"))
                .andDo(document("create"));
    }

    @Test
    public void retrieveFriends() throws Exception {
        given(friendRepository.findByUser(anyString()))
                .willReturn(Collections.singletonList(new Friend(ANDY, JOHN)));

        mockMvc
                .perform(post("/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"email\": \"andy@example.com\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"friends\": [\n" +
                        "    \"john@example.com\"\n" +
                        "  ],\n" +
                        "  \"count\": 1\n" +
                        "}"))
                .andDo(document("retrieve"));
    }

    @Test
    public void retrieveCommonFriends() throws Exception {
        given(friendRepository.findByUserOrUser(anyString(), anyString()))
                .willReturn(Arrays.asList(new Friend(ANDY, COMMON), new Friend(JOHN, COMMON)));

        mockMvc
                .perform(post("/common")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"friends\": [\n" +
                                "    \"andy@example.com\",\n" +
                                "    \"john@example.com\"\n" +
                                "  ]\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"friends\": [\n" +
                        "    \"common@example.com\"\n" +
                        "  ],\n" +
                        "  \"count\": 1\n" +
                        "}"))
                .andDo(document("common"));
    }

    @Test
    public void subscribe() throws Exception {
        given(subscribeRepository.findByRequestorAndTarget(anyString(), anyString()))
                .willReturn(Optional.empty());
        given(subscribeRepository.save(any()))
                .willReturn(null);

        mockMvc
                .perform(post("/subscribe")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"requestor\": \"lisa@example.com\",\n" +
                                "  \"target\": \"john@example.com\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"success\": true\n" +
                        "}"))
                .andDo(document("subscribe"));
    }

    @Test
    public void block() throws Exception {
        given(blockRepository.findByRequestorAndTarget(anyString(), anyString()))
                .willReturn(Optional.empty());
        given(blockRepository.save(any()))
                .willReturn(null);
        mockMvc
                .perform(post("/block")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"requestor\": \"andy@example.com\",\n" +
                                "  \"target\": \"john@example.com\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"success\": true\n" +
                        "}"))
                .andDo(document("block"));
    }

    @Test
    public void retrieveRecipients() throws Exception {
        given(friendRepository.findByUser(anyString()))
                .willReturn(Collections.singletonList(new Friend(JOHN, LISA)));
        given(subscribeRepository.findByTarget(anyString()))
                .willReturn(Collections.emptyList());
        given(blockRepository.findByRequestorOrTarget(anyString(), anyString()))
                .willReturn(Collections.emptyList());

        mockMvc
                .perform(post("/recipient")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"sender\": \"john@example.com\",\n" +
                                "  \"text\": \"Hello World! kate@example.com\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"recipients\": [\n" +
                        "    \"lisa@example.com\",\n" +
                        "    \"kate@example.com\"\n" +
                        "  ]\n" +
                        "}"))
                .andDo(document("recipient"));
    }
}