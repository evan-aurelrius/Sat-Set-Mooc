package com.satset.mooc.util;

import com.satset.mooc.model.JwtResponse;
import com.satset.mooc.model.LoginRequest;
import com.satset.mooc.model.SignupRequest;
import com.satset.mooc.model.dto.AdminDto;
import com.satset.mooc.security.jwt.JwtUtils;
import com.satset.mooc.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserUtil {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    public <T> JwtResponse getJwt(T user) {
        Authentication authentication = null;
        Boolean isAdmin = false;
        if(user instanceof AdminDto) {
            isAdmin = true;
            AdminDto adminDto = (AdminDto) user;
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminDto.getEmail(), adminDto.getPassword()));
        } else {
            LoginRequest loginRequest = (LoginRequest) user;
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        if(Boolean.TRUE.equals(isAdmin)) {
            return new JwtResponse(principal.getId(), principal.getName(), principal.getEmail(), principal.getRole(), token);
        } else {
            return new JwtResponse(principal.getId(), principal.getName(), principal.getGender(), principal.getImage(), principal.getEmail(), principal.getRole(), principal.getCreatedAt(), token);
        }
    }

}
