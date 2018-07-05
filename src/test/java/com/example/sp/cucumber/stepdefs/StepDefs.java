package com.example.sp.cucumber.stepdefs;

import com.example.sp.SpApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = SpApplication.class)
public abstract class StepDefs {
    protected ResultActions actions;
}
