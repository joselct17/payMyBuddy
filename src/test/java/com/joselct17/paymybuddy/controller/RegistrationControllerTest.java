package com.joselct17.paymybuddy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRegistration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void PostRegistrationForm_shouldSucceedAndRedirected() throws Exception {
        mockMvc.perform(post("/registration")
                        .param("firstName", "john")
                        .param("lastName", "doe")
                        .param("email", "johndoe@mail.com")
                        .param("password", "123")
                        .param("confirmPassword", "123")
                        .param("bankAccount", "1AX123456789")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }


}
