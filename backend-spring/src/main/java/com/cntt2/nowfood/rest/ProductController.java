package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.dto.product.ProductDetailDto;
import com.cntt2.nowfood.dto.product.ProductDto;
import com.cntt2.nowfood.dto.product.ProductFormDto;
import com.cntt2.nowfood.dto.product.ProductSearchDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.mapper.ProductMapper;
import com.cntt2.nowfood.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/5/2021 11:49 PM
 */
@RestController
@RequestMapping("/api/products")
@Api(tags = "Products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @ApiOperation(value = "Danh sách tất cả sản phẩm theo shop [Không phân trang].")
    @GetMapping("/shop/{id}")
    public ResponseEntity<?> getByShop(@PathVariable Integer id) {
        List<ProductDto> products = productService.findByShop(id);
        return ResponseEntity.ok().body(new MessageEntity(200, products));
    }

    @ApiOperation(value = "Danh sách tất cả sản phẩm [Không phân trang].")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ProductDto> products = productService.getAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new MessageEntity(200, products));
    }

    @ApiOperation(value = "Danh sách sản phẩm [Phân trang nâng cao].")
    @PostMapping(value = "/search-adv")
    public ResponseEntity<?> searchAdv(@RequestBody ProductSearchDto searchDto) {
        Page<ProductDto> page = productService.findByAdvSearch(searchDto);
        return new ResponseEntity<>(new MessageEntity(200, page), HttpStatus.OK);
    }

    @ApiOperation(value = "Thêm mới sản phẩm, 1 sản phẩm có [0,n] size ___ [1,n] danh mục sản phẩm ___ [1,n] danh mục sản phẩm của cửa hàng")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductFormDto form) {
        // todos: author
        ProductFormDto result = productService.saveOrUpdate(form);
        return ResponseEntity.ok().body(new MessageEntity(200, result));
    }
    @ApiOperation(value = "Chi tiết sản phẩm .")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Integer id) {
        ProductDetailDto product = productService.findDetailById(id);
        return ResponseEntity.ok().body(new MessageEntity(200,product));
    }
    @ApiOperation(value = "Cập nhật sản phẩm")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ProductFormDto form) {
        // todos: author
        ProductFormDto result = productService.saveOrUpdate(form);
        return ResponseEntity.ok().body(new MessageEntity(200,result));
    }
    @ApiOperation(value = "Xóa sản phẩm")
    @DeleteMapping(value ="/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        // todos: xóa sản phẩm
        return ResponseEntity.ok().body(new MessageEntity(200,id.toString()));
    }

}
