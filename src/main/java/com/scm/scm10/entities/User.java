package com.scm.scm10.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
// import java.util.stream.Collector;
import java.util.stream.Collectors;
// import java.util.stream.Collectors;

@Entity(name = "user")
@Table(name = "users") // by default table name was users this will change to users
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User implements UserDetails {
    // var here are table columns
    @Id
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Column(length = 1000)
    private String about;
    private String profilePic;
    private String phoneNumber;

    // for verfication
    @Getter(value = AccessLevel.NONE)
    private boolean enabled = true;
    
    private boolean emailverified = false;
    private boolean phoneVerified = false;

    @Enumerated(value = EnumType.STRING)
    // self signup, google, github etc
    private Providers provider = Providers.SELF;
    private String providerUserId;

    // one user have many contact
    // cascade type all , means user delete pe sary contacts delete
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();


    // public void setPhoneVerified(boolean phoneNumber2) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setPhoneVerified'");
    // }

    // email id wahi hamare username
    @Override
    public String getUsername() {
        return this.email; // not email() — just email field
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList=new ArrayList<>();

    @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    //     // list of roles [user, admin]
    //     // collection of aurthorities
    //     Collections<SimpleGrantedAuthority> roles=roleList.stream().map(role-> new 
    //     SimpleGrantedAuthority(role)
    //     ).collect(Collector.toList());
        
    //     return roles; 
    // }
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return roleList.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
}

}
