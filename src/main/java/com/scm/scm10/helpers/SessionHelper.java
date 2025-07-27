package com.scm.scm10.helpers;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {

    public static void removeMessage() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                HttpSession session = request.getSession(false);

                if (session != null) {
                    session.removeAttribute("message");
                }
            }

        } catch (Exception e) {
            // e.printStackTrace(); // or use a logger instead
            System.out.println("Error in SessionHelper: " + e);
        }
    }
}
