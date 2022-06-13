package com.example.myauth2.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
@Builder
public class Client {
    @Id
    @GenericGenerator(name = "clientG", strategy = "uuid2")
    @GeneratedValue(generator = "clientG")
    private String clientId;
    private String clientSecret;
    private String appName;
    private String appLogo;
    private String redirectURI;
    @OneToMany(mappedBy = "client")
    private Set<Scope> scopes;
//    @OneToMany(mappedBy = "client")
//    private Set<Authorization> authorizations;
}
