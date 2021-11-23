package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.SystemMasterData;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.mapper.SystemKeyMapper;
import com.cntt2.nowfood.service.SystemDataService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 8:22 PM
 */
@RestController
@RequestMapping("/api/common")
@Api(tags = "Common")
@RequiredArgsConstructor
public class CommonController {

    private final SystemDataService systemDataService;
    private final SystemKeyMapper systemKeyMapper;

    @PostMapping(value="/initial-data")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> initialData() {
        // Todos fake data;
        return new ResponseEntity<>(new MessageEntity(200, null), HttpStatus.OK);
    }
    @GetMapping(value = "/system-keys")
    public ResponseEntity<?> getSystemKeys() {
        List<SystemMasterData> parents = systemDataService.getByParentId(0);
        List<SystemMasterData> result = systemDataService.getAll();
        Map<String, Set<SystemMasterData>> data = new HashMap<>();
        parents.forEach(p->{
            data.put(p.getCode(),
                    result.stream().
                            filter(c->c.getParentId() == p.getId()).
                            collect(Collectors.toSet()));
        });
        return new ResponseEntity<>(new MessageEntity(200, data), HttpStatus.OK);
    }
}
