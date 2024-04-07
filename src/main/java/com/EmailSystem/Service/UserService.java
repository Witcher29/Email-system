package com.EmailSystem.Service;

import com.EmailSystem.domain.User;

public interface UserService {
    User seveUser(User user);
    Boolean verifyToken (String token);
}
