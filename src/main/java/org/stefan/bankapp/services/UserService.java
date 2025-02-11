package org.stefan.bankapp.services;

import org.stefan.bankapp.models.User;

public interface UserService {

    boolean existsByEmail(String email);

    void save(User user);

    User getByEmailAndDeletedAtIsNull(String email);
}
