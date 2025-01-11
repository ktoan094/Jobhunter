package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.RestResponse;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.Error.IdInvalidException;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User fetchUser = this.userService.fetchUserById(id);
        // return ResponseEntity.ok(fetchUser);
        return ResponseEntity.status(HttpStatus.OK).body(fetchUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        // return
        // ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser());
        return ResponseEntity.ok(this.userService.fetchAllUser());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postManUser) {
        String hashpasswrod = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashpasswrod);
        User newUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User ericUser = this.userService.handleUpdateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(ericUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<RestResponse> deleteUser(@PathVariable("id") long id) throws IdInvalidException {

        if (id >= 1500) {
            throw new IdInvalidException("ID không được lớn hơn 1500");
        }

        this.userService.handleDeleteUser(id);

        RestResponse response = new RestResponse();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}