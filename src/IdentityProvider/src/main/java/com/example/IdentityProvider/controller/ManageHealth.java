package com.example.IdentityProvider.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
public class ManageHealth {
    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void checkIsAvailable()
    {
        return;
    }
}