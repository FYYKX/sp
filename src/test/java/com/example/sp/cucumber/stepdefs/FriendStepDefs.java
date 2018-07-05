package com.example.sp.cucumber.stepdefs;

import com.example.sp.repository.BlockRepository;
import com.example.sp.repository.FriendRepository;
import com.example.sp.repository.SubscribeRepository;
import com.example.sp.web.rest.FriendController;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FriendStepDefs extends StepDefs {
    @Autowired
    private FriendController friendController;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private SubscribeRepository subscribeRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(friendController).build();
    }

    @After
    public void clearData() {
        blockRepository.deleteAll();
        friendRepository.deleteAll();
        subscribeRepository.deleteAll();
    }

    @When("^(.*) add (.*)$")
    public void addConnection(String user, String friend) throws Throwable {
        actions = mockMvc.perform(
                post("/friend")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"friends\": [\n" +
                                "    \"" + user + "@example.com\",\n" +
                                "    \"" + friend + "@example.com\"\n" +
                                "  ]\n" +
                                "}"));
    }

    @Then("^success is true$")
    public void successIsTrue() throws Throwable {
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Then("^success is false$")
    public void successIsFalse() throws Throwable {
        actions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Given("^(.*) block (.*)$")
    public void johnBlockAndy(String requestor, String target) throws Throwable {
        actions = mockMvc.perform(
                post("/block")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"requestor\": \"" + requestor + "@example.com\",\n" +
                                "  \"target\": \"" + target + "@example.com\"\n" +
                                "}"));
    }

    @When("^get common for andy and john$")
    public void getCommonForAndyAndJohn() throws Throwable {
        actions = mockMvc.perform(
                post("/common")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"friends\": [\n" +
                                "    \"andy@example.com\",\n" +
                                "    \"john@example.com\"\n" +
                                "  ]\n" +
                                "}"));
    }

    @Then("^friend is common")
    public void friendIsCommon() throws Throwable {
        actions
                .andExpect(jsonPath("$.friends[0]").value("common@example.com"))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Then("^no common friend$")
    public void noCommonFriend() throws Throwable {
        actions
                .andExpect(jsonPath("$.friends").isEmpty())
                .andExpect(jsonPath("$.count").value(0));
    }

    @When("^lisa subscribe john$")
    public void lisaSubscribeJohn() throws Throwable {
        actions = mockMvc.perform(
                post("/subscribe")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"requestor\": \"lisa@example.com\",\n" +
                                "  \"target\": \"john@example.com\"\n" +
                                "}"));
    }

    @When("^john send '(.*)'$")
    public void johnSend(String text) throws Throwable {
        actions = mockMvc.perform(
                post("/recipient")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"sender\": \"john@example.com\",\n" +
                                "  \"text\": \"" + text + "\"\n" +
                                "}"));
    }

    @And("^recipient is '(.*)'$")
    public void recipientIsLisa(String recipient) throws Throwable {
        actions
                .andExpect(jsonPath("$.recipients[0]").value(recipient + "@example.com"));
    }

    @And("^recipient is empty$")
    public void recipientIsEmpty() throws Throwable {
        actions
                .andExpect(jsonPath("$.recipients").isEmpty());
    }
}
