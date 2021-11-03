package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.Category;
import com.cntt2.nowfood.dto.Category.CategoryFormDto;
import com.cntt2.nowfood.service.CategoryService;
import lombok.RequiredArgsConstructor;
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
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getList() {
        List<Category> categories = categoryService.getAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryFormDto form) {
        form.setId(null);
        Category category = categoryService.save(form);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody CategoryFormDto form) {
        Category category = categoryService.save(form);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    @DeleteMapping(value ="/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Category category = categoryService.delete(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
