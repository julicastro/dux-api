package com.challenge.app.challenge.service.auth;

import com.challenge.app.challenge.dto.auth.AuthenticationRequest;
import com.challenge.app.challenge.dto.auth.AuthenticationResponse;
import com.challenge.app.challenge.exception.CanNotLoginException;
import com.challenge.app.challenge.perseistence.entity.User;
import com.challenge.app.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorities", user.getAuthorities());
        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest autRequest) {
        AuthenticationResponse authRsp;
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    autRequest.getUsername(), autRequest.getPassword());
            authenticationManager.authenticate(authentication);
            UserDetails user = userService.findOneByUsername(autRequest.getUsername()).get();
            String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));
            authRsp = new AuthenticationResponse();
            authRsp.setJwt(jwt);
        } catch (Exception e) {
            throw new CanNotLoginException("Usuario y o password incorrectos");
        }
        return authRsp;
    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
