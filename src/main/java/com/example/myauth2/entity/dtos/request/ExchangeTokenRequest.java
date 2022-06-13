package com.example.myauth2.entity.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeTokenRequest {
    private String authCode;
    private String redirectUri;
    private String clientId;
//    private String clientSecret;
    private String grantType;
}
