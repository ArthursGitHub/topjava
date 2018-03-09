package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    boolean delete(int id); // false if not found
    User get(int id);   // null if not found
    User getByEmail(String email);  // null if not found
    List<User> getAll();
}