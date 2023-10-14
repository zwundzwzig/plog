package com.sokuri.plog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private EventController eventController;

    @BeforeEach
    public void setup() {
      this.mvc = mvc;
    }

    @Test
    public void 모집_중인_행사_테스트() throws Exception {
      mvc.perform(MockMvcRequestBuilders.get("/v1.0/event")
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}
