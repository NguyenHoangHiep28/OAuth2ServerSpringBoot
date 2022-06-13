package com.example.myauth2.util;

import com.example.myauth2.entity.Client;
import com.example.myauth2.entity.Scope;
import com.example.myauth2.entity.User;
import com.example.myauth2.repository.AuthorizationRepository;
import com.example.myauth2.repository.ClientRepository;
import com.example.myauth2.repository.ScopeRepository;
import com.example.myauth2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

//@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AuthorizationRepository authorizationRepository;
    private final ScopeRepository scopeRepository;

    public DataSeeder(UserRepository userRepository, ClientRepository clientRepository, AuthorizationRepository authorizationRepository, ScopeRepository scopeRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.authorizationRepository = authorizationRepository;
        this.scopeRepository = scopeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder().username("harry").password("123456").build();
        System.out.println(userRepository.save(user));
        Client client = Client.builder().clientSecret(UUID.randomUUID().toString()).appLogo("logo.png").appName("myApp").build();
        System.out.println(clientRepository.save(client));
        Scope scope = new Scope();
        scope.setName("view_basic_info");
        scope.setClient(client);
        Scope scope1 = new Scope();
        scope1.setName("view_photo");
        scope1.setClient(client);
       scopeRepository.save(scope);
        scopeRepository.save(scope1);

    }
}
