package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.ConfirmationToken;
import com.cntt2.nowfood.domain.User;

import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/29/2021 12:22 AM
 */
public interface ConfirmTokenService {

    boolean delete(String username);

    String generateToken(User user);

    void saveConfirmationToken(ConfirmationToken token);

    Optional<ConfirmationToken> getToken(String token);

    int setConfirmedAt(String token);
}
