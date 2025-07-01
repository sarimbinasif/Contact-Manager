// package com.scm.scm10.services.impl;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.AutoConfigureOrder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.scm.scm10.repositories.UserRepo;

// @Service
// public class SecurityCustomDetailService implements UserDetailsService{
//     @Autowired
//     private UserRepo userRepo;



//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//     //    apnay user ko load krwana hai
//     return userRepo.findByEmail((username).orElseThrow(()-> new UsernameNotFoundException("User not found: " + username)));
//     }


// }



package com.scm.scm10.services.impl;

// import com.scm.scm10.entities.User;
import com.scm.scm10.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}



