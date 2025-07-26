package com.scm.scm10.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    @Value("${server.baseUrl}")
    private String baseUrl;

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        if (authentication == null)
            return null;

        // OAuth2 Login (Google / GitHub)
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            String clientId = oauth2Token.getAuthorizedClientRegistrationId();

            OAuth2User oauth2User = oauth2Token.getPrincipal();
            String username = "";

            if ("google".equalsIgnoreCase(clientId)) {
                System.out.println("Getting email from Google");
                username = oauth2User.getAttribute("email");
            } else if ("github".equalsIgnoreCase(clientId)) {
                System.out.println("Getting email from GitHub");
                username = oauth2User.getAttribute("email") != null
                        ? oauth2User.getAttribute("email")
                        : oauth2User.getAttribute("login") + "@github.com"; // fallback
            }

            return username;
        }

        // Self login (local DB)
        System.out.println("Getting data from local database");
        return authentication.getName(); // usually email or username
    }

    public String getLinkForEmailVerificatiton(String emailToken) {

        return this.baseUrl + "/auth/verify-email?token=" + emailToken;

    }
}
