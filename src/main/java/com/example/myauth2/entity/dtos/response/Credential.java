package com.example.myauth2.entity.dtos.response;

import com.example.myauth2.entity.Authorization;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Credential {
    private String jwtAccessToken;
    private String expiredIn;
    private String redirectUri;
}
