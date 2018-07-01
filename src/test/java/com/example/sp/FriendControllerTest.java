package com.example.sp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(FriendController.class)
@AutoConfigureRestDocs
public class FriendControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createFriendConnection() throws Exception {
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