package com.scm.scm10.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Entity(name = "user")
@Table(name="users") // by default table name was users this will change to users
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    // var here are table columns
    @Id
    private String userId;

    @Column(name="user_name", nullable=false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    
    @Column(length=1000)
    private String about;
    private String profilePic;
    private String phoneNumber;
    
    //for verfication 
    private boolean enabled=false;
    private boolean emailverified=false;
    private boolean phoneVerfied=false;

    @Enumerated(value = EnumType.STRING)
    // self signup, google, github etc
    private Providers provider= Providers.SELF;
    private String providerUserId;

    // one user have many contact
    // cascade type all , means user delete pe sary contacts delete 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    public void setPhoneVerified(boolean phoneNumber2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPhoneVerified'");
    }

    
}
