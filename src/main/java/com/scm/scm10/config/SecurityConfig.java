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

// FOR MAKING GOOGLE ALWAYS ASK U TO SELECT ACCOUNT
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomDetailService customUserDetailsService;

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    // FOR MAKING GOOGLE ALWAYS ASK U TO SELECT ACCOUNT

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // config
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home", "/signup", "services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form default login page
        // httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.formLogin(formLogin -> {
            formLogin
                    .loginPage("/login") // to show our own login page
                    .loginProcessingUrl("/authenticate") // // login form processing on below URL
                    .defaultSuccessUrl("/user/dashboard", true) // true = always redirect here after login
                    .failureUrl("/login?error=true") // change failureForwardUrl to failureUrl
                    .usernameParameter("email")
                    .passwordParameter("password");

            // // failure handler
            // formLogin.failureHandler(new AuthenticationFailureHandler() {
            // @Override
            // public void onAuthenticationFailure(HttpServletRequest request,
            // HttpServletResponse response,
            // AuthenticationException exception) throws IOException, ServletException {
            // // Redirect to login page with error
            // response.sendRedirect("/login?error=true");
            // }
            // });

            // // success handler
            // formLogin.successHandler(new AuthenticationSuccessHandler() {
            // @Override
            // public void onAuthenticationSuccess(HttpServletRequest request,
            // HttpServletResponse response,
            // Authentication authentication) throws IOException, ServletException {
            // // Redirect to homepage or dashboard after login
            // response.sendRedirect("/dashboard");
            // }
            // });

        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        // oauth configurations
        // httpSecurity.oauth2Login(Customizer.withDefaults()); will redirect to default
        // login page

        // httpSecurity.oauth2Login(oauth -> {
        // oauth.loginPage("/login");
        // oauth.successHandler(handler);
        // });

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.authorizationEndpoint(auth -> auth.authorizationRequestResolver(
                    customAuthorizationRequestResolver(clientRegistrationRepository)));
            oauth.successHandler(handler);
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // FOR MAKING GOOGLE ALWAYS ASK U TO SELECT ACCOUNT

    @Bean
    public OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository) {

        DefaultOAuth2AuthorizationRequestResolver defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository, "/oauth2/authorization");

        return new OAuth2AuthorizationRequestResolver() {
            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                OAuth2AuthorizationRequest originalRequest = defaultResolver.resolve(request);
                return customizeRequest(originalRequest);
            }

            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
                OAuth2AuthorizationRequest originalRequest = defaultResolver.resolve(request, clientRegistrationId);
                return customizeRequest(originalRequest);
            }

            private OAuth2AuthorizationRequest customizeRequest(OAuth2AuthorizationRequest request) {
                if (request == null)
                    return null;
                return OAuth2AuthorizationRequest.from(request)
                        .additionalParameters(params -> params.put("prompt", "select_account"))
                        .build();
            }
        };
    }

}
