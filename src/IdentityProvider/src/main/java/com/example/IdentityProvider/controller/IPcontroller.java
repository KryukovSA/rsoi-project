package com.example.IdentityProvider.controller;

import com.example.IdentityProvider.controller.dto.AuthResponse;
import com.example.IdentityProvider.controller.dto.LoginRequest;
import com.example.IdentityProvider.controller.dto.SignUpRequest;
import com.example.IdentityProvider.exception.DuplicatedUserInfoException;
import com.example.IdentityProvider.model.MyUser;
import com.example.IdentityProvider.security.TokenProvider;
import com.example.IdentityProvider.security.WebSecurityConfig;
import com.example.IdentityProvider.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class IPcontroller {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
            throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new DuplicatedUserInfoException(String.format("Email %s already been used", signUpRequest.getEmail()));
        }

        userService.saveUser(mapSignUpRequestToUser(signUpRequest));

        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
        return new AuthResponse(token);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    private MyUser mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        MyUser user = new MyUser();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setRole(WebSecurityConfig.USER);
        return user;
    }



























    @GetMapping("/authorize")
    public String authorize() {
        // Authorization code generation logic
        return "ok";
    }

//    @GetMapping("/callback")
//    public String callback(@RequestParam("code") String code) {
//        // Validate the authorization code and generate the JWT token
//        String jwtToken = generateJwtToken(code);
//        return jwtToken;
//    }

    @PostMapping(value = "/newUser")
    public String createUser(@RequestBody MyUser user){
        //code
        return "successfully create";
    }
}
