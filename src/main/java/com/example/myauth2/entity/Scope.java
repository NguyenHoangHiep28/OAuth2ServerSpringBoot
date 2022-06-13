package com.example.myauth2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "scopes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Scope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany
//    @JoinTable(name = "authorizations_scopes",
//            joinColumns = {@JoinColumn(name = "scope_id")},
//            inverseJoinColumns = {@JoinColumn(name = "authorization_id")})
    @JsonBackReference
    private Set<Authorization> authorizations;
    @ManyToOne
    @JoinColumn(name = "clientId")
    @JsonBackReference
    private Client client;
}
