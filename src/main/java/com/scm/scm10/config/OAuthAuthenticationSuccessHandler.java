package com.scm.scm10.config;

import java.io.IOException;
// import java.util.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm10.entities.Providers;
import com.scm.scm10.helpers.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

// import java.util.Map;
import java.util.UUID;
// import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.User;

import com.scm.scm10.entities.User;
import com.scm.scm10.repositories.UserRepo;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // logger.info("OAuthAuthenticationSuccessHandler");

        // DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();

        // ((Object) user.getAttributes()).forEach((key,value)->{
        // logger.info("{} : {}", key,value);
        // });

        // logger.info(user.getAuthorities().toString());

        logger.info("OAuthAuthenticationSuccessHandler");

        var oauthuser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthuser.getAttributes().forEach((key, value) -> {
            logger.info(key + " " + value);
        });

        // identify provider
        var oauth2AutheticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AutheticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailverified(true);
        user.setEnabled(true);
        user.setPassword("dummy");

        // google
        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            // google attr
            user.setEmail(oauthuser.getAttribute("email").toString());
            user.setProfilePic(oauthuser.getAttribute("picture").toString());
            user.setName(oauthuser.getAttribute("name").toString());
            user.setProviderUserId(oauthuser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This accoutn is created using google");
            


        }
        // github
        else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            // github attr
            // GitHub doesn't always return email unless it's public
            String email = oauthuser.getAttribute("email");
            if (email == null || email.isBlank()) {
                email = oauthuser.getAttribute("login") + "@github.com"; // fallback
            }

            user.setEmail(email);
            user.setProfilePic(oauthuser.getAttribute("avatar_url"));
            user.setName(oauthuser.getAttribute("name") != null ? oauthuser.getAttribute("name")
                    : oauthuser.getAttribute("login"));
            user.setProviderUserId(oauthuser.getAttribute("id").toString());
            user.setAbout("This account is created using GitHub.");
            user.setProvider(Providers.GITHUB);

        } else {
            logger.warn("Unknown OAuth provider");
            new DefaultRedirectStrategy().sendRedirect(request, response, "/login?error=provider");
            return;
        }

        // Save user only if not already registered
        User existingUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (existingUser == null) {
            userRepo.save(user);
            logger.info("New user saved: {}", user.getEmail());
        } else {
            logger.info("User already exists: {}", existingUser.getEmail());
        }

        /*
         * DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
         * 
         * // // Log user attributes
         * // user.getAttributes().forEach((key, value) -> {
         * // logger.info("{} : {}", key, value);
         * // });
         * 
         * // // Log authorities
         * // logger.info("Authorities: {}", user.getAuthorities());
         * // save data before redirect
         * 
         * String email = user.getAttribute("email").toString();
         * String name = user.getAttribute("name").toString();
         * String picture = user.getAttribute("picture").toString();
         * 
         * // create user and save in DB
         * User user1 = new User();
         * user1.setEmail(email);
         * user1.setName(name);
         * user1.setProfilePic(picture);
         * user1.setPassword("password");
         * user1.setProvider(Providers.GOOGLE);
         * 
         * user1.setProviderUserId(user.getName());
         * user1.setAbout("This account is created using google");
         * 
         * User user2 = userRepo.findByEmail(email).orElse(null);
         * if(user2==null)
         * {
         * userRepo.save(user1);
         * logger.info("User saved: "+email);
         * 
         * }
         */

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }

}
