package com.dribas.controller;

import com.dribas.entity.User;
import com.dribas.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UsersRestController {

    private UsersService service;

    public UsersRestController(UsersService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id) {
        User user = service.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> list = service.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody User user) {
        service.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> patch(@RequestBody User user) {
        service.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
