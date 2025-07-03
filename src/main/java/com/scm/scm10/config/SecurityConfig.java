package com.scm.scm10.config;

import com.scm.scm10.services.impl.SecurityCustomDetailService;

import jakarta.servlet.http.HttpServletRequest;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.AuthenticationFailureHandler;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// // FOR MAKING GOOGLE ALWAYS ASK U TO SELECT ACCOUNT
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomDetailService customUserDetailsService;

    @Autowired
    private OAuthAuthenticationSuccessHandler oAuthHandler;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/user/dashboard", true)
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
            )
            .logout(logout -> logout
                .logoutUrl("/do-logout")
                .logoutSuccessUrl("/login?logout=true")
            )
            .oauth2Login(oauth -> oauth
                .loginPage("/login")
                .authorizationEndpoint(auth -> auth
                    .authorizationRequestResolver(customAuthorizationRequestResolver(clientRegistrationRepository)))
                .successHandler(oAuthHandler)
            )
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

@Bean
public OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver(
        ClientRegistrationRepository repo) {

    DefaultOAuth2AuthorizationRequestResolver defaultResolver =
        new DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization");

    return new OAuth2AuthorizationRequestResolver() {
        @Override
        public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
            OAuth2AuthorizationRequest req = defaultResolver.resolve(request);
            return customize(req);
        }

        @Override
        public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientId) {
            OAuth2AuthorizationRequest req = defaultResolver.resolve(request, clientId);
            return customize(req);
        }

        private OAuth2AuthorizationRequest customize(OAuth2AuthorizationRequest request) {
            if (request == null) return null;
            return OAuth2AuthorizationRequest.from(request)
                .additionalParameters(params -> params.put("prompt", "select_account"))
                .build();
        }
    };
}


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
