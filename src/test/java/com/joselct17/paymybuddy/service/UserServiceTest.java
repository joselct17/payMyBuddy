package com.joselct17.paymybuddy.service;


import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IUserRepository;
import com.joselct17.paymybuddy.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Mock
    IUserRepository iUserRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;


    User user1;
    User user2;

    @BeforeEach
    void initialize() {
        user1 = new User(1,"John","Doe","johndoe@mail.com","password","4545dddj",new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
        user2 = new User(2,"Jane","Doe","janedoe@mail.com","password","45445ddds",new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    }


    @Test
    void testFindByEmail() {
        // Arrange
        String email = "johndoe@mail.com";
        User user = new User(1,"John","Doe","johndoe@mail.com","password","4545dddj",new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
        when(iUserRepository.findByEmail(email)).thenReturn(user);
        // Act
        User resultUser = userServiceImpl.findByEmail(email);
        // Assert
        assertEquals(resultUser, user);
    }

    @Test
    void testFindById() {
        Optional<User> optionalUser = Optional.of(user1);
        when(iUserRepository.findById(1)).thenReturn(optionalUser);


        User result = userServiceImpl.findById(1);

        assertEquals(optionalUser.get(), result);
    }

    @Test
    void test_findById_NotFound() {
        //Arrange
        Optional<User> optUser = Optional.empty();
        when(iUserRepository.findById(1)).thenReturn(optUser);

        //Act
        User resultUser = userServiceImpl.findById(1);

        // Assert
        assertNull(resultUser);

    }
}
