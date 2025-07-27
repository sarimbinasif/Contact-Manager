package com.scm.scm10.config;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm10.entities.Providers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;
import java.util.List;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import com.scm.scm10.entities.User;
import com.scm.scm10.repositories.UserRepo;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        String registrationId = token.getAuthorizedClientRegistrationId();
        String email = oauthUser.getAttribute("email");

        User user = userRepo.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setEnabled(true);
            newUser.setEmailverified(true);
            newUser.setRoleList(List.of("ROLE_USER"));
            newUser.setUserId(UUID.randomUUID().toString());
            newUser.setProviderUserId(oauthUser.getName());
            newUser.setName(oauthUser.getAttribute("name") != null ? oauthUser.getAttribute("name") : email);
            newUser.setPassword("dummy");

            if (registrationId.equals("google")) {
                newUser.setProvider(Providers.GOOGLE);
                newUser.setProfilePic(oauthUser.getAttribute("picture"));
                newUser.setAbout("Signed in with Google");
            } else if (registrationId.equals("github")) {
                newUser.setProvider(Providers.GITHUB);
                newUser.setProfilePic(oauthUser.getAttribute("avatar_url"));
                newUser.setAbout("Signed in with GitHub");
            }

            return userRepo.save(newUser);
        });

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
