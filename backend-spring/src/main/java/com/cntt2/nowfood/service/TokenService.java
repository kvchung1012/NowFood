package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Token;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 9:45 AM
 */
public interface TokenService {
    Token createToken(Token token);
    Token findByToken(String token);
}
