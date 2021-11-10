package com.cntt2.nowfood.rest;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 12:34 AM
 */
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
@Api(tags = "Common")
@RequiredArgsConstructor
public class CommonController {
}
