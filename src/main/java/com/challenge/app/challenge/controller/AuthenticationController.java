package com.challenge.app.challenge.controller;

import com.challenge.app.challenge.dto.auth.AuthenticationRequest;
import com.challenge.app.challenge.dto.auth.AuthenticationResponse;
import com.challenge.app.challenge.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para la gestión de usuarios")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(
            summary = "Validar Token",
            description = "Valida si el toquen que se le pasa por parámetro es válido")
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @Operation(
            summary = "Autenticar",
            description = "Permite autenticar usuario mediante Username y Password")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest){
        AuthenticationResponse rsp = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);
    }



}
