package com.joselct17.paymybuddy.service.implementation;

import com.joselct17.paymybuddy.model.Role;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl  {

//    @Autowired
//    private IUserRepository userRepository;
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String email) {
//        User user = userRepository.findByEmail(email);
//        if (user == null) throw new UsernameNotFoundException(email);
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        }
//
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
//    }
}
