package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.exceptions.MessageEntity;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 8:22 PM
 */
@RestController
@RequestMapping("/api/common")
@Api(tags = "Common")
public class CommonController {
    @PostMapping(value="/initial-data")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> initialData() {
        // Todos fake data;
        return new ResponseEntity<>(new MessageEntity(200, null), HttpStatus.OK);
    }
}
