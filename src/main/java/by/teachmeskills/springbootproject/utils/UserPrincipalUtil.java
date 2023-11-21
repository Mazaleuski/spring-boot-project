package by.teachmeskills.springbootproject.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public final class UserPrincipalUtil {
    public static String getEmail() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = null;
        if (o instanceof UserDetails) {
            email = ((UserDetails) o).getUsername();
        }
        return email;
    }
}
