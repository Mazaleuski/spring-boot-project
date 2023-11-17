package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.entities.Role;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static by.teachmeskills.springbootproject.enums.PagesPathEnum.REGISTRATION_PAGE;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ModelAndView create(User entity) throws AuthorizationException {
        ModelAndView modelAndView = new ModelAndView(REGISTRATION_PAGE.getPath());
        if ((userRepository.findByEmailAndPassword(entity.getEmail(), entity.getPassword())) != null) {
            throw new AuthorizationException("Пользователь уже зарегистрирован. Войдите в систему.");
        } else {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            List<Role> roles = new ArrayList<>();
            roles.add(userRepository.findRoleById(1));
            entity.setRoles(roles);
            userRepository.save(entity);
            modelAndView.addObject("info", "Пользователь успешно зарегистрирован. Войдите в систему.");
        }
        return modelAndView;
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

//    @Override
//    public User findByEmailAndPassword(String email, String password) {
//        return userRepository.findByEmailAndPassword(email, password);
//    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String name) {
        return userRepository.findByEmail(name).orElse(null);
    }
}
