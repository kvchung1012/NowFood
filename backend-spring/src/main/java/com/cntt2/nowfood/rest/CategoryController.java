package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.Category;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.category.CategoryDto;
import com.cntt2.nowfood.dto.category.CategoryFormDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/3/2021 10:22 PM
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Api(tags = "Category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        return new ResponseEntity<>(new MessageEntity(200,category), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getList() {
        List<Category> categories = categoryService.getAll();
        return new ResponseEntity<>(new MessageEntity(200,categories), HttpStatus.OK);
    }

    @PostMapping(value = "/search-adv")
    public ResponseEntity<?> getListAdv(@Valid @RequestBody SearchDto dto) {
        Page<CategoryDto> result = categoryService.findByAdvSearch(dto);
        return new ResponseEntity<>(new MessageEntity(200,result), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryFormDto form) {
        form.setId(null);
        Category category = categoryService.saveOrUpdate(form);
        return new ResponseEntity<>(new MessageEntity(200,category), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody CategoryFormDto form) {
        Category category = categoryService.saveOrUpdate(form);
        return new ResponseEntity<>(new MessageEntity(200,category), HttpStatus.OK);
    }
    @DeleteMapping(value ="/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Category category = categoryService.delete(id);
        return new ResponseEntity<>(new MessageEntity(200,category), HttpStatus.OK);
    }
}
