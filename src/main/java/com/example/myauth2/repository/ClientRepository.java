package com.example.myauth2.repository;

import com.example.myauth2.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Client findClientByClientId(String clientId);
}
