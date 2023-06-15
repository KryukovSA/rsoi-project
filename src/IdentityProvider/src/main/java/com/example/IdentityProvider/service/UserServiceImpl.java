package com.example.IdentityProvider.service;

import com.example.IdentityProvider.exception.UserNotFoundException;
import com.example.IdentityProvider.model.MyUser;
import com.example.IdentityProvider.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final MyUserRepository myUserRepository;

    @Override
    public List<MyUser> getUsers() {
        return myUserRepository.findAll();
    }

    @Override
    public Optional<MyUser> getUserByUsername(String username) {
        return myUserRepository.findByUsername(username);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return myUserRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return myUserRepository.existsByEmail(email);
    }

    @Override
    public MyUser validateAndGetUserByUsername(String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }

    @Override
    public MyUser saveUser(MyUser user) {
        return myUserRepository.save(user);
    }

    @Override
    public void deleteUser(MyUser user) {
        myUserRepository.delete(user);
    }
}
