package com.scm.scm10.entities;

import jakarta.persistence.CascadeType;

// import org.apache.catalina.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
// import java.util.*;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// import com.scm.scm10.entities.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(length=1000)
    private String description;
    private boolean favorite=false;

    private String websiteLink;
    private String linkedInLink;
    // private List<String> sociallnks= new ArrayList<>();
     private String cloudinaryImagePublicId;

    // 1 contact is associated with one user 
    @ManyToOne
    private User user;
    
    
    // multiple social links of 
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();


    // public void setCloudinaryImagePublicId(String filename) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setCloudinaryImagePublicId'");
    // }
}
