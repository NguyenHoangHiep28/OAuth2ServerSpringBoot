package com.example.myauth2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="authorizations")
public class Authorization {
    @Id
    private String code;
    @ManyToMany
//            (mappedBy = "authorizations", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Scope> scopes;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
//    //1. Mối quan hệ giữa Authorization và Credential
//    @OneToOne
//    private Credential credential;
}
