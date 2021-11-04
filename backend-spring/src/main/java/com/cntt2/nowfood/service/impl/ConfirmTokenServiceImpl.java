package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.ConfirmationToken;
import com.cntt2.nowfood.domain.User;
import com.cntt2.nowfood.repository.ConfirmTokenRepository;
import com.cntt2.nowfood.service.ConfirmTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/29/2021 12:23 AM
 */
@Service
@AllArgsConstructor
public class ConfirmTokenServiceImpl implements ConfirmTokenService {
    private final ConfirmTokenRepository confirmTokenRepository;

    @Override
    public boolean delete(String username) {
        return confirmTokenRepository.deteleByUsername(username);
    }

    @Override
    public String generateToken(User user) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmTokenRepository.save(
                confirmationToken);
        return token;
    }

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
