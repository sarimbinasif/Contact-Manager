// package com.scm.scm10.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.NoOpPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// public class SecurityConfig {

//     // @Bean
//     // public InMemoryUserDetailsManager userDetailsService() {
//     //     UserDetails user1 = User.withUsername("admin")
//     //             // .withDefaultPasswordEncoder()
//     //             .password("password1") // use password encoder in real apps!
//     //             .roles("ADMIN")
//     //             .build();
        
//     //      UserDetails user2 = User.withUsername("user")
//     //             .password("password2") // use password encoder in real apps!
//     //             // .roles("ADMIN")
//     //             .build();

//     //     return new InMemoryUserDetailsManager(user1, user2);
//     // }

//     // Only for testing (do not use NoOpPasswordEncoder in production)
//     // @Bean
//     // public static NoOpPasswordEncoder passwordEncoder() {
//     //     return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//     // }

//     @Autowired
//     private SecurityCustomerUserDetailService UserDetailService;

//     @Bean
//     public AuthenticationProvider authenticationProvider(){
//         DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        
//         // user detail service ka object
//         // daoAuthenticationProvider.setUser(userDetailsService);
//          daoAuthenticationProvider.setUserDetailsService(UserDetailService);
        
//         // password encoder ka object
//         daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        
//         return daoAuthenticationProvider;
//     }

//     // private UserDetailsService userDetailsService() {
//     //     // TODO Auto-generated method stub
//     //     throw new UnsupportedOperationException("Unimplemented method 'userDetailsService'");
//     // }

//     @Bean
//     public PasswordEncoder passwordEncoder(){
//         return new BCryptPasswordEncoder();
//     }
// }


package com.scm.scm10.config;

import com.scm.scm10.services.impl.SecurityCustomDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomDetailService customUserDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        
        // config
        httpSecurity.authorizeHttpRequests(authorize ->{
            // authorize.requestMatchers("/home", "/signup", "services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form default logins
        httpSecurity.formLogin(Customizer.withDefaults());
        
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

