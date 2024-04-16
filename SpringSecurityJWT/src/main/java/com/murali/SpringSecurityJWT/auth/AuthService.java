package com.murali.SpringSecurityJWT.auth;

import com.murali.SpringSecurityJWT.user.User;
import com.murali.SpringSecurityJWT.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public AuthenticationResponse register(RegisterRequest registerRequest){
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(registerRequest.getRole())
                .build();
        var savedUser = userRepository.save(user);
        return null;
    }
}
