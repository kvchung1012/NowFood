package com.cntt2.nowfood.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 1:23 AM
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    @ExceptionHandler({ ExceptionA.class, ExceptionB.class })  // Có thể bắt nhiều loại exception
    public ResponseEntity<String> handleExceptionA(Exception e) {
        return ResponseEntity.status(432).body(e.getMessage());
    }
    */
    // Có thêm các @ExceptionHandler khác...

    // Nên bắt cả Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // Nguyên tắc là không bao giờ được trả về chi tiết lỗi cho client, nên return một message khác
        e.printStackTrace();  // Thực tế người ta dùng logger
        return ResponseEntity.status(500).body("Unknow error");
    }
}
