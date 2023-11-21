package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Role;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            userDetails = new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
                    user.get().getRoles().stream().map(Role::getName)
                            .map(SimpleGrantedAuthority::new).toList());
        } else {
            throw new UsernameNotFoundException("User wasn't found");
        }
        return userDetails;
    }
}
