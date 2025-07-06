// // package com.scm.scm10.helpers;

// // import java.security.Principal;

// // public class Helper {
// //     public static String getEmailOfLoggedInUser(Principal principal) {

// //         // fetch email of user signed by SELF
// //         // fetch email of user signed by GOOGLE 
// //         // fetch email of user signed by GITHUB


// //         return "";
        
// //     }
// // }



// package com.scm.scm10.helpers;

// import java.security.Principal;

// import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.stereotype.Component;

// import lombok.var;

// import org.springframework.security.core.userdetails.UserDetails;

// // package com.scm.helpers;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// // import org.springframework.security.core.Authentication;
// import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.stereotype.Component;


// @Component
// public class Helper {

//     public static String getEmailOfLoggedInUser(Authentication authentication) {
//          // agar email is password se login kiya hai to : email kaise nikalenge
//         if (authentication instanceof OAuth2AuthenticationToken) {

//             var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
//             var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

//             var oauth2User = (OAuth2User) authentication.getPrincipal();
//             String username = "";

//             if (clientId.equalsIgnoreCase("google")) {

//                 // sign with google
//                 System.out.println("Getting email from google");
//                 username = oauth2User.getAttribute("email").toString();

//             } else if (clientId.equalsIgnoreCase("github")) {

//                 // sign with github
//                 System.out.println("Getting email from github");
//                 username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
//                         : oauth2User.getAttribute("login").toString() + "@gmail.com";
//             }

//             // sign with facebook
//             return username;

//         } else {
//             System.out.println("Getting data from local database");
//             return authentication.getName();
//         }


       

// }
// }



package com.scm.scm10.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        if (authentication == null) return null;

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
}
