package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.Token;
import com.cntt2.nowfood.repository.TokenRepository;
import com.cntt2.nowfood.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 9:46 AM
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token createToken(Token token){
        return tokenRepository.saveAndFlush(token);
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
