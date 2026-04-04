package com.lauritz.biobiks.Model.intface;

import com.lauritz.biobiks.Model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);
}
