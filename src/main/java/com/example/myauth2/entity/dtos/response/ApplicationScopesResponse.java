package com.example.myauth2.entity.dtos.response;

import com.example.myauth2.entity.Scope;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationScopesResponse {
    private List<Scope> scopes;
    private String applicationName;
}
