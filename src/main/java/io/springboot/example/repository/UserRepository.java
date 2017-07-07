package io.springboot.example.repository;

import io.springboot.example.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author shekh akther
 */
public interface UserRepository extends CrudRepository<User, Long> {

   List<User> findByNameIgnoreCaseContaining(String name);
}
