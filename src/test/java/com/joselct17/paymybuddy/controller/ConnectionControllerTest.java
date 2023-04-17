package com.joselct17.paymybuddy.controller;


import com.joselct17.paymybuddy.config.SpringWebTestConfig;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import com.joselct17.paymybuddy.utils.paging.Paged;
import com.joselct17.paymybuddy.utils.paging.Paging;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ConnectionController.class)
@Import(SpringWebTestConfig.class)
public class ConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    User user1;
    User user2;
    User user3;
    Paged<User> paged;

    @BeforeEach
    void setup() {
        user1 = new User(1, "firstname1", "lastname1", "user1e@mail.com","password1", "1AX256", Currency.getInstance("USD"), new BigDecimal(100), true, LocalDateTime.of(2023,01,05,01,00,50), new HashSet<>(), new HashSet<>(), new ArrayList<>() ,new HashSet<>());
        user2 = new User(2, "firstname2", "lastname2", "user2e@mail.com", "password2",  "1AX256", Currency.getInstance("USD"), new BigDecimal(100), true, LocalDateTime.of(2023,01,05,01,00,50),new HashSet<>() ,new HashSet<>(), new ArrayList<>(), new HashSet<>() );
        user3 = new User(3, "firstname3", "lastname3", "user3e@mail.com","password3", "1AX256", Currency.getInstance("USD"), new BigDecimal(100), true, LocalDateTime.of(2023,01,05,01,00,50), new HashSet<>() ,new HashSet<>(),  new ArrayList<>(), new HashSet<>() );

        User[] userArray = {user1,user2,user3};
        List<User> users = Arrays.asList(userArray);
        Page<User> pagedUser = new PageImpl<User>(users);

        Paging paging = Paging.of(1, 1);//, 5);
        paged = new Paged<User>(pagedUser, paging);

    }

    @WithUserDetails("jane@doe.com") //user from SpringSecurityWebTestConfig.class
    @Test
    void GetConnectionPage_shouldSucceed() throws Exception {
        //ARRANGE:
        when(userService.getCurrentUserConnectionPage(1, 5)).thenReturn(paged); //display list of connections

        //ACT+ASSERT:
        mockMvc.perform(get("/connection"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("connection"))
                .andExpect(model().attributeExists("paged"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().size(2));

    }





    @WithUserDetails("jane@doe.com") //user from SpringSecurityWebTestConfig.class
    @Test
    void PostConnectionPage_shouldFail_connectionUnknown() throws Exception {
        //ARRANGE:
        when(userService.getCurrentUser()).thenReturn(user1);//user1 is current connected user
        when(userService.existsByEmail(user2.getEmail())).thenReturn(false);//check user2 new connection exists in DB
        when(userService.getCurrentUserConnectionPage(1, 5)).thenReturn(paged); //display list of connections

        //ACT+ASSERT:
        mockMvc.perform(post("/connection")
                        .param("email", user2.getEmail()) //try to add user2 as new connection
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("connection"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("paged"))
                .andExpect(model().attribute("paged", paged))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Email Unknown"))
        ;

        assertFalse(user1.getConnections().contains(user2));
    }

}
