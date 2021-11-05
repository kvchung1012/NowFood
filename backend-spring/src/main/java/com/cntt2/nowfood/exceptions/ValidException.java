package com.cntt2.nowfood.exceptions;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/5/2021 12:07 AM
 */
public class ValidException extends RuntimeException {
    public ValidException(String message){
        super((message));
    }
}
