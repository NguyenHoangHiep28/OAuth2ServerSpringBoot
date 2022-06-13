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

import java.util.List;
import java.util.Set;

public interface IAuthService {
    LoginSuccessfullyToken login(UserCredential userCredential) throws Exception;
    Authorization addGrant(String token , GrantRequest grantRequest, String clientId);
    Credential getAccessToken(String authorizationCode, String redirectUri, String token);
    ApplicationScopesResponse getApplicationScopes(String clientId);
    Client getClientById(String clientId);
}
