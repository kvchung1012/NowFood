package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.product.ProductDetailDto;
import com.cntt2.nowfood.dto.product.ProductDto;
import com.cntt2.nowfood.dto.product.ProductFormDto;
import com.cntt2.nowfood.dto.product.ProductSearchDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.service.ProductService;
import com.cntt2.nowfood.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/5/2021 11:49 PM
 */
@RestController
@RequestMapping("/api/products")
@Api( tags = "Products")
public class ProductController {
    private final ProductService productService;

    private final ShopService shopService;

    @Autowired
    public ProductController(ProductService productService, ShopService shopService) {
        this.productService = productService;
        this.shopService = shopService;
    }
    @ApiOperation(value = "Danh sách sản phẩm [Phân trang cơ bản].")
    @GetMapping
    public ResponseEntity<?> getList(@RequestParam("pageIndex") Integer pageIndex,
                                     @RequestParam("pageSize") Integer pageSize) {
        Optional<Shop> shop = shopService.getOwnerLogin();
        SearchDto searchDto = new SearchDto();
        searchDto.setPageIndex(pageIndex);
        searchDto.setPageSize(pageSize);
        Page<ProductDto> page = productService.findByShop(searchDto,shop.orElse(null));
        return ResponseEntity.ok().body(new MessageEntity(200,page));
    }
    @ApiOperation(value = "Danh sách sản phẩm [Phân trang nâng cao].")
    @PostMapping(value = "/searchAdv")
    public ResponseEntity<?> getByPage(@RequestBody ProductSearchDto searchDto) {
        // todos: test api get product
        Page<ProductDto> page = productService.findByAdvSearch(searchDto);
        return new ResponseEntity<>(new MessageEntity(200,page), HttpStatus.OK);
    }
    @ApiOperation(value = "Thêm mới sản phẩm, 1 sản phẩm có [0,n] size ___ [1,n] danh mục sản phẩm ___ [1,n] danh mục sản phẩm của cửa hàng")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductFormDto form) {
        // todos: author
        ProductFormDto result = productService.create(form);
        return ResponseEntity.ok().body(new MessageEntity(200,result));
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
        ProductFormDto result = productService.create(form);
        return ResponseEntity.ok().body(new MessageEntity(200,result));
    }
    @ApiOperation(value = "Xóa sản phẩm")
    @DeleteMapping(value ="/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        // todos: xóa sản phẩm
        return ResponseEntity.ok().body(new MessageEntity(200,id.toString()));
    }

}
