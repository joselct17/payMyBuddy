package com.joselct17.paymybuddy.service;


import com.joselct17.paymybuddy.model.Role;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IRolesRepository;
import com.joselct17.paymybuddy.repository.IUserRepository;
import com.joselct17.paymybuddy.service.implementation.UserServiceImpl;
import com.joselct17.paymybuddy.service.interfaces.ISecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Mock
    IUserRepository iUserRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    IRolesRepository iRolesRepository;

    @Mock
    ISecurityService iSecurityService;


    User user1;
    User user2;

    @BeforeEach
    void initialize() {
        user1 = new User(1,"John","Doe","johndoe@mail.com","password","4545dddj", Currency.getInstance("USD"),new HashSet<>(), new HashSet<>(),new HashSet<>());
        user2 = new User(2,"Jane","Doe","janedoe@mail.com","password","45445ddds",Currency.getInstance("EUR"),new HashSet<>(),new HashSet<>(), new HashSet<>());
    }


    @Test
    void testFindByEmail() {
        // Arrange
        String email = "johndoe@mail.com";
        User user = new User(1,"John","Doe","johndoe@mail.com","password","4545dddj", Currency.getInstance("USD"),new HashSet<>(), new HashSet<>(),new HashSet<>());
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

    @Test
    void testExistsByEmail() {
        // Arrange
        String email = "johndoe@mail.com";
        when(iUserRepository.existsByEmail(email)).thenReturn(true);
        // Act
        Boolean result = userServiceImpl.existsByEmail(email);
        // Assert
        assertTrue(result);
    }

    @Test
    void createUser() {
        User user = new User(null, "Marc", "Anthony", "marc@email.com", "password", "1454",Currency.getInstance("USD"), new HashSet<>(),  new HashSet<>(), new HashSet<>());

        User userExpected = new User(null, "Marc", "Anthony", "marc@email.com", "passwordEncrypted", "1454", Currency.getInstance("USD"), new HashSet<>(),  new HashSet<>(), new HashSet<>());

        HashSet<Role> hashSet = new HashSet<>();

        hashSet.add(new Role(1, "USER"));
        userExpected.setRoles(hashSet);

        when(bCryptPasswordEncoder.encode("password")).thenReturn("passwordEncrypted");
        when(iRolesRepository.findByroleName("USER")).thenReturn(new Role(1, "USER"));


        userServiceImpl.create(user);

        verify(iUserRepository, times(1)).save(user);

        assertNull(user.getId());
        assertEquals(userExpected.getFirstName(),user.getFirstName());
        assertEquals(userExpected.getLastName(),user.getLastName());
        assertEquals(userExpected.getBankAccount(),user.getBankAccount());
        assertEquals(userExpected.getEmail(),user.getEmail());
        assertEquals(userExpected.getPassword(),user.getPassword());
        assertEquals(userExpected.getRoles().size(),user.getRoles().size());

    }

    @Test
    void testGetCurrentUser() {
        // Arrange
        when(iSecurityService.getCurrentUserDetailsUserName()).thenReturn("johndoe@mail.com");
        when(iUserRepository.findByEmail("johndoe@mail.com")).thenReturn(user1);
        // Act
        User resultUser = userServiceImpl.getCurrentUser();
        // Assert
        assertEquals(resultUser, user1);
    }
}
