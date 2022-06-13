package com.example.myauth2.repository;

import com.example.myauth2.entity.Authorization;
import com.example.myauth2.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, String> {
    Authorization findAuthorizationByCode(String code);
    Authorization findAuthorizationByUserIdAndClient(Integer userId, Client client);
}
