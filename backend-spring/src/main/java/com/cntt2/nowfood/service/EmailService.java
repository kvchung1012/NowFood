package com.cntt2.nowfood.service;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/29/2021 12:27 AM
 */
public interface EmailService {
    void confirmEmail(String to, String name, String link);
}
