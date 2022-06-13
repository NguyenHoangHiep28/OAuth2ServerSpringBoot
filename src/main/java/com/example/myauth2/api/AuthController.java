package com.example.myauth2.api;

import com.example.myauth2.entity.Authorization;
import com.example.myauth2.entity.Client;
import com.example.myauth2.entity.dtos.request.ExchangeTokenRequest;
import com.example.myauth2.entity.dtos.request.GrantRequest;
import com.example.myauth2.entity.dtos.request.UserCredential;
import com.example.myauth2.entity.dtos.response.ApplicationScopesResponse;
import com.example.myauth2.entity.dtos.response.Credential;
import com.example.myauth2.entity.dtos.response.LoginSuccessfullyToken;
import com.example.myauth2.service.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1")
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserCredential userCredential) throws Exception {
        LoginSuccessfullyToken token = authService.login(userCredential);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/consent", method = RequestMethod.POST)
    public ResponseEntity<?> getAuthCode(@RequestHeader(name = "Auth") String token,
                                         @RequestBody GrantRequest grantRequest,
                                         @RequestParam(name = "clientId") String clientId) {
        Authorization authorization = authService.addGrant(token, grantRequest, clientId);
        if (authorization != null) {
            return ResponseEntity.ok(authorization.getCode());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @RequestMapping(path = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(
            @RequestHeader(name = "Auth") String token,
//            @RequestParam(name = "code") String authCode
            @RequestBody ExchangeTokenRequest request
            ) {
        if (token.equals("null")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Credential credential = authService.getAccessToken(request.getAuthCode(), request.getRedirectUri(),token);
        if (credential != null) {
            return ResponseEntity.ok(credential);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = "/client", method = RequestMethod.GET)
    public ResponseEntity<?> getClientName(
            @RequestParam(name = "clientId") String clientId
    ) {
        Client client = authService.getClientById(clientId);
        if (client != null) {
            return ResponseEntity.ok(client.getAppName());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(path = "/scopes", method = RequestMethod.GET)
    public ResponseEntity<?> getClientScopes(
            @RequestHeader(name = "Auth") String token,
            @RequestParam(name = "clientId") String clientId
    ) {
        if (token.equals("null")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(clientId);
        }
        ApplicationScopesResponse response = authService.getApplicationScopes(clientId);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
