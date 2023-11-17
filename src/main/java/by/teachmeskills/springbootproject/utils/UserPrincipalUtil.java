package by.teachmeskills.springbootproject.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class UserPrincipalUtil {
    private UserPrincipalUtil() {
    }

    public static String getEmail() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = null;
        if (o instanceof UserDetails) {
            email = ((UserDetails) o).getUsername();
        }
        return email;
    }
}
