package com.dribas.service;

import com.dribas.ValidationException;
import com.dribas.dao.UsersStorage;
import com.dribas.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsersService {

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 255;
    private static final Pattern LOGIN_PATTERN = Pattern.compile("\\w{6,20}");
    private static final Pattern PASS_PATTERN = Pattern.compile("\\w{6,20}");

    private UsersStorage storage;

    public UsersService(UsersStorage storage) {
        this.storage = storage;
    }

    public List<User> getAll() {
        return storage.getAll();
    }

    public User getById(int userId) {
        return storage.getById(userId);
    }

    public void save(User user) {
        validate(user);
        storage.save(user);
    }

    public void update(User user) {
        validate(user);
        storage.update(user);
    }

    public boolean delete(int userId) {
        return storage.delete(userId);
    }

    private void validate(User user) {
        checkNameLength(user.getFirstName());
        checkNameLength(user.getLastName());
        checkLogin(user.getLogin());
        checkPass(user.getPassword());
    }

    private void checkNameLength(String name) {
        if (name.length() <= MIN_NAME_LENGTH) {
            throw new ValidationException(
                    String.format("User name is too short. " +
                                    "It must be longer than %s character.",
                            MIN_NAME_LENGTH)
            );
        }
        if (name.length() >= MAX_NAME_LENGTH) {
            throw new ValidationException(
                    String.format("User name is too long. " +
                                    "It must be shooter than %s character.",
                            MAX_NAME_LENGTH)
            );
        }
    }

    private void checkLogin(String login) {
        Matcher m = LOGIN_PATTERN.matcher(login);
        if (!m.matches()) {
            throw new ValidationException("Invalid login!");
        }
    }

    private void checkPass(String pass) {
        Matcher m = PASS_PATTERN.matcher(pass);
        if (!m.matches()) {
            throw new ValidationException("Invalid password!");
        }
    }
}