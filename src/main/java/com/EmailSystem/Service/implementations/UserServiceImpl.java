package com.EmailSystem.Service.implementations;

import com.EmailSystem.Service.UserService;
import com.EmailSystem.domain.Confirmation;
import com.EmailSystem.domain.User;
import com.EmailSystem.repository.ConfirmationRepository;
import com.EmailSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    @Override
    public User seveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {throw new RuntimeException("Email have already created"); }
        user.setEnabled(false);
        userRepository.save(user);

        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);

        //we need to send email with token to user
        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        return Boolean.TRUE;
    }
}
