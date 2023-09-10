package com.example.IdentityProvider.service;

import com.example.IdentityProvider.model.MyUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<MyUser> getUsers();

    Optional<MyUser> getUserByUsername(String username);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    MyUser validateAndGetUserByUsername(String username);

    MyUser saveUser(MyUser user);

    void deleteUser(MyUser user);
}
