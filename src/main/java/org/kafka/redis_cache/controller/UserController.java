package org.kafka.redis_cache.controller;


import lombok.RequiredArgsConstructor;
import org.kafka.redis_cache.dto.CreateUserDto;
import org.kafka.redis_cache.dto.UpdateUserDto;
import org.kafka.redis_cache.model.User;
import org.kafka.redis_cache.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto dto){
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserDto dto){
        return new ResponseEntity<>(userService.updateUser(dto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public User getUserById(@RequestParam Long id){
        return userService.getUserById(id);
    }
}
