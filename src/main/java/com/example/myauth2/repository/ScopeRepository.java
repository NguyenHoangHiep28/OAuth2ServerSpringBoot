package com.example.myauth2.repository;

import com.example.myauth2.entity.Client;
import com.example.myauth2.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Integer> {
    Scope findScopeById(Integer id);
    List<Scope> findScopeByClient(Client client);
}
