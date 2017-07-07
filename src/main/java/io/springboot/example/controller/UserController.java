package io.springboot.example.controller;

import io.springboot.example.component.UserComponent;
import io.springboot.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * RESTful endpoints to perform CRUD operations on user.
 *
 * @author shekh akther
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserComponent userComponent;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userComponent.getAllUsers(), HttpStatus.OK) ;
    }


    @GetMapping(params = {"name"})
    public ResponseEntity<?> getUserByName(@RequestParam(value = "name") String name) {
        return new ResponseEntity<>(userComponent.getUsersByName(name), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> storeUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userComponent.storeUser(user), HttpStatus.OK);
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user) {
        userComponent.updateUser(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userComponent.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUsers(@Valid @RequestBody List<User> users) {
        userComponent.deleteUsers(users);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
