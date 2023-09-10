package com.example.IdentityProvider.controller;

import com.example.IdentityProvider.CurrentUserToken;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class IPcontroller {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/auth/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        CurrentUserToken.token = token;
        return new AuthResponse(token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/auth/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
            throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new DuplicatedUserInfoException(String.format("Email %s already been used", signUpRequest.getEmail()));
        }

        userService.saveUser(mapSignUpRequestToUser(signUpRequest));

        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
        CurrentUserToken.token = token;
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

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/createUser")
//    public String createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
//            throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.getUsername()));
//        }
//        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
//            throw new DuplicatedUserInfoException(String.format("Email %s already been used", signUpRequest.getEmail()));
//        }
//
//        userService.saveUser(mapSignUpRequestToUser(signUpRequest));
//
//        //String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
//        return "user successfully created";
//    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createUser")
    public String createUser(@Valid @RequestBody SignUpRequest signUpRequest, Authentication authentication) {
        System.out.println(String.format("Это сообщение об ошибке. ваша роль: %s", authentication.getAuthorities().iterator().next().getAuthority()));
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            // Если текущий пользователь не администратор, возвращаем ошибку доступа
            String currentRole = "Unknown";
            if (!authentication.getAuthorities().isEmpty()) {
                currentRole = authentication.getAuthorities().iterator().next().getAuthority();
            }
            throw new AccessDeniedException("You do not have permission to create users. Current role: " + currentRole);
        }

        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
            throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new DuplicatedUserInfoException(String.format("Email %s already been used", signUpRequest.getEmail()));
        }

        userService.saveUser(mapSignUpRequestToUser(signUpRequest));
        return "User successfully created";
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
}
