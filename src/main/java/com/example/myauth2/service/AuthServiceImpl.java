package com.example.myauth2.service;

import com.example.myauth2.entity.Authorization;
import com.example.myauth2.entity.Client;
import com.example.myauth2.entity.Scope;
import com.example.myauth2.entity.User;
import com.example.myauth2.entity.dtos.request.GrantRequest;
import com.example.myauth2.entity.dtos.request.UserCredential;
import com.example.myauth2.entity.dtos.response.ApplicationScopesResponse;
import com.example.myauth2.entity.dtos.response.Credential;
import com.example.myauth2.entity.dtos.response.LoginSuccessfullyToken;
import com.example.myauth2.repository.AuthorizationRepository;
import com.example.myauth2.repository.ClientRepository;
import com.example.myauth2.repository.ScopeRepository;
import com.example.myauth2.repository.UserRepository;
import com.example.myauth2.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AuthorizationRepository authorizationRepository;
    private final ScopeRepository scopeRepository;

    public AuthServiceImpl(UserRepository userRepository, ClientRepository clientRepository, AuthorizationRepository authorizationRepository, ScopeRepository scopeRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.authorizationRepository = authorizationRepository;
        this.scopeRepository = scopeRepository;
    }


    @Override
    public LoginSuccessfullyToken login(UserCredential userCredential) throws Exception {
        User user = userRepository.findUserByUsername(userCredential.getUsername());
        Client client = clientRepository.findClientByClientId(userCredential.getClientId());
        if (user != null) {
            if (user.getPassword().equals(userCredential.getPassword())){
                return new LoginSuccessfullyToken(user.getId()+ "." +user.getUsername() + "." + client.getClientSecret());
            }
        }else {
            throw new Exception("User credentials do not match!");
        }
        return null;
    }

    @Override
    public Authorization addGrant(String token , GrantRequest grantRequest, String clientId) {
        String[] user = token.split("\\.").clone();
        Client client = clientRepository.findClientByClientId(clientId);
        Integer userId = Integer.parseInt(user[0]);
        Authorization existing = authorizationRepository.findAuthorizationByUserIdAndClient(userId, client);
        if (existing != null) {
            authorizationRepository.delete(existing);
        }
        List<Scope> scopes = new ArrayList<>();
        grantRequest.getScopeIds().forEach(i -> {
            scopes.add(scopeRepository.findScopeById(i));
        });
        User existingUser = userRepository.findUserByUsername(user[1]);
        if (existingUser != null){
            Authorization authorization = new Authorization();
            authorization.setCode(UUID.randomUUID().toString());
            authorization.setScopes(scopes);
            authorization.setClient(client);
            authorization.setUser(existingUser);
            return authorizationRepository.save(authorization);
        }
        return null;
    }

    @Override
    public Credential getAccessToken(String authorizationCode, String redirectUri, String token) {
        Authorization authorization = authorizationRepository.findAuthorizationByCode(authorizationCode);
        String clientSecret = token.split("\\.")[2];
        Client client = authorization.getClient();
        if (client.getClientSecret().equals(clientSecret) && client.getRedirectURI().equals(redirectUri)) {
            Credential credential = new Credential();
            List<String> scopesName = new ArrayList<>();
            authorization.getScopes().forEach(s -> {
                scopesName.add(s.getName());
            });
            String accessToken = JwtUtil.generateToken(authorization.getClient().getAppName(),authorization.getUser().getUsername(), scopesName, JwtUtil.ONE_MINUTE * 2);
            credential.setJwtAccessToken(accessToken);
            credential.setExpiredIn(String.format("%d", JwtUtil.ONE_MINUTE * 2));
            credential.setRedirectUri(client.getRedirectURI());
            return credential;
        }
        return null;
    }

    @Override
    public ApplicationScopesResponse getApplicationScopes(String clientId) {
        Client client = clientRepository.findClientByClientId(clientId);
        if (client != null) {
            ApplicationScopesResponse applicationScopesResponse = new ApplicationScopesResponse();
            applicationScopesResponse.setScopes(scopeRepository.findScopeByClient(client));
            applicationScopesResponse.setApplicationName(client.getAppName());
            return applicationScopesResponse;
        }else {
            return null;
        }
    }

    @Override
    public Client getClientById(String clientId) {
        return clientRepository.findClientByClientId(clientId);
    }
}
