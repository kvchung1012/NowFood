package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.domain.Size;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.size.SizeDto;
import com.cntt2.nowfood.dto.size.SizeFormDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.mapper.SizeMapper;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.service.SizeService;
import com.cntt2.nowfood.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 4:36 PM
 */
@RestController
@RequestMapping("/api/sizes")
@RequiredArgsConstructor
@Api(tags = "Size")
public class SizeController {
    private final SizeService sizeService;
    private final ShopService shopService;
    private final SizeMapper sizeMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        SizeDto size = sizeService.findById(id);
        return new ResponseEntity<>(new MessageEntity(200,size), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getList() {
        List<SizeDto> sizes = sizeService.getAll()
                .stream().map(sizeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new MessageEntity(200,sizes), HttpStatus.OK);
    }
    @ApiOperation(value = "Tìm kiếm nâng cao kích thước [Phân trang].")
    @PostMapping("/search-adv")
    public ResponseEntity<?> getListAdv(@Valid @RequestBody SearchDto dto) {
        Page<SizeDto> result = sizeService.findByAdvSearch(dto);
        return new ResponseEntity<>(new MessageEntity(200,result), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SizeFormDto form) {
        form.setId(null);
        SizeDto size = sizeService.saveOrUpdate(form);
        return new ResponseEntity<>(new MessageEntity(200,size), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody SizeFormDto form) {
        SizeDto size = sizeService.saveOrUpdate(form);
        return new ResponseEntity<>(new MessageEntity(200,size), HttpStatus.OK);
    }
    @DeleteMapping(value ="/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Size result = sizeService.deleteById(id);
        return new ResponseEntity<>(new MessageEntity(200, !CommonUtils.isNull(result)), HttpStatus.OK);
    }
}
