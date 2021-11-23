package com.cntt2.nowfood.rest;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 12:34 AM
 */

import com.cntt2.nowfood.domain.SystemMasterData;
import com.cntt2.nowfood.dto.systemdata.SystemKeyDataFormDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.mapper.SystemKeyMapper;
import com.cntt2.nowfood.service.SystemDataService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/system-data")
@Api(tags = "SystemData")
@RequiredArgsConstructor
public class SystemDataController {
    private final SystemDataService systemDataService;
    private final SystemKeyMapper systemKeyMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        SystemMasterData result = systemDataService.getById(id);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }

    @GetMapping(value="/")
    public ResponseEntity<?> getByParentId(@RequestParam("parent-id") Integer parentId) {
        List<SystemMasterData> result = systemDataService.getByParentId(parentId);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }
    @GetMapping(value="/code/")
    public ResponseEntity<?> getByParentCode(@RequestParam("code") String code) {
        List<SystemMasterData> result = systemDataService.getByParentCode(code);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }
    @GetMapping(value="/parents")
    public ResponseEntity<?> getParents() {
        List<SystemMasterData> result = systemDataService.getByParentId(0);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<SystemMasterData> result = systemDataService.getAll();
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }


    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','COMMON_CREATE')")
    public ResponseEntity<?> create(@Valid @RequestBody SystemKeyDataFormDto form) {
        form.setId(null);
        SystemMasterData entity = systemKeyMapper.toEntity(form);
        SystemMasterData result = systemDataService.save(entity);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> update(@Valid @RequestBody SystemMasterData form) {
        SystemMasterData result = systemDataService.save(form);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }
}
