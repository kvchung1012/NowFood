package com.cntt2.nowfood.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 11:01 AM
 */

@Getter
@Setter
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 5498689480625016163L;
    private String field;
    private int code;
    private String message;
    private MessageType type;
    private HttpHeaders httpHeaders;

    public MessageEntity(String field, String message, MessageType type) {
        this.field = field;
        this.message = message;
        this.type = type;
        this.httpHeaders = null;
    }

    public MessageEntity(String field, int code, String message, MessageType type) {
        this.field = field;
        this.code = code;
        this.message = message;
        this.type = type;
        this.httpHeaders = null;
    }

    public MessageEntity(String field, String message, HttpHeaders httpHeaders, MessageType type) {
        this.field = field;
        this.message = message;
        this.type = type;
        this.httpHeaders = httpHeaders;
    }

}
