package com.example.myauth2.entity.dtos.request;

import com.example.myauth2.entity.Scope;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrantRequest {
    private List<Integer> scopeIds;
}
