package com.joselct17.paymybuddy.controller;

import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ARegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ModelMapper modelMapperMock;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    IUserService userService;


    @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user_connections","user_roles","user" );
    }




    @Test
    void getRegistration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().is2xxSuccessful());
    }

    //a chaque fois que je demarre le test il me le rejete parce que la personne est deja enregistré
    @Test
    void PostRegistrationForm_shouldSucceedAndRedirected() throws Exception {
        mockMvc.perform(post("/registration")
                        .param("firstName", "Big")
                        .param("lastName", "Boy")
                        .param("email", "Big@Boy.com")
                        .param("password", "123")
                        .param("confirmPassword", "123")
                        .param("bankAccount", "1AX123456789")
                        .param("amount", "200")
                        .param("currency", "USD")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void PostRegistrationForm_shouldFailcauseDifferentPasswords() throws Exception {
        mockMvc.perform(post("/registration")
                        .param("firstName", "john")
                        .param("lastName", "doe")
                        .param("email", "test@test.com")
                        .param("password", "123")
                        .param("confirmPassword", "123456789")
                        .param("bankAccount", "1AX123456789")
                        .param("amount", "200")
                        .param("currency", "USD")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection()); //registration page reloaded
    }

    @Test
    void PostRegistrationForm_shouldFailcauseUserAlreadyExist() throws Exception {
        //ARRANGE
        when(userService.existsByEmail("BingBong@mail.com")).thenReturn(Boolean.TRUE);

        //ACT+ASSERT
        mockMvc.perform(post("/registration")
                        .param("firstname", "Bing")
                        .param("lastname", "Bong")
                        .param("email", "BingBong@mail.com")
                        .param("password", "123")
                        .param("confirmPassword", "123")
                        .param("bankaccountnumber", "1AX123456789")
                        .param("currency", "USD")
                        .with(csrf()))
                .andExpect(model().attributeErrorCount("userForm", 1)) //error to display in registration page
                .andExpect(status().isOk()); //registration page reloaded
    }



}
