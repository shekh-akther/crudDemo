package io.springboot.example.component;

import io.springboot.example.model.User;
import io.springboot.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User Service component
 *
 * @author shekh akther
 */
@Component
@Slf4j
public class UserComponent {

    @Autowired
    private UserRepository userRepository;

    public User storeUser(User user) {
      user = userRepository.save(user);
      log.info("Save user: {}", user.toString());
      return user;
    }

    public List<User> getUsersByName(String name) {
        return userRepository.findByNameIgnoreCaseContaining(name);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public void updateUser(Long id, User user) {
        if(userRepository.exists(id)) {
            user.setId(id);
            userRepository.save(user);
        }
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public void deleteUsers(List<User> users) {
        userRepository.delete(users);
    }
}
