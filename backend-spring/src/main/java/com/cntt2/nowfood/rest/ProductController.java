package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.product.ProductDetailDto;
import com.cntt2.nowfood.dto.product.ProductDto;
import com.cntt2.nowfood.dto.product.ProductFormDto;
import com.cntt2.nowfood.dto.product.ProductSearchDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.exceptions.ValidException;
import com.cntt2.nowfood.mapper.ProductMapper;
import com.cntt2.nowfood.service.ProductService;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.utils.FileUploadUtil;
import com.cntt2.nowfood.utils.ValidateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
    private final ShopService shopService;
    private final ProductMapper productMapper;
    private final ValidateUtils<ProductFormDto> validateProduct;
    @Value("${resources.images-directory}")
    public String imageUrl;
    private Boolean isMain = true;

    @ApiOperation(value = "Danh sách tất cả sản phẩm theo shop [Không phân trang].")
    @GetMapping("/shop/{id}")
    public ResponseEntity<?> getByShop(@PathVariable Integer id) {
        List<ProductDto> products = productService.findByShop(id,isMain);
        return ResponseEntity.ok().body(new MessageEntity(200, products));
    }
    @ApiOperation(value = "Danh sách tất cả sản phẩm theo shop [Không phân trang].")
    @GetMapping("/shop")
    public ResponseEntity<?> getByShop(@RequestParam Boolean isMain) {
        Integer shopId = null;
        Optional<Shop> shop = shopService.getOwnerLogin(false);
        if(shop.isPresent())
            shopId = shop.get().getId();
        List<ProductDto> products = productService.findByShop(shopId,isMain);
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

    @ApiOperation(value = "Danh sách sản phẩm theo shop[Phân trang nâng cao].")
    @PostMapping(value = "/shop/search-adv")
    public ResponseEntity<?> searchAdv(@RequestBody ProductSearchDto searchDto) {
        Integer shopId = null;
        Optional<Shop> shop = shopService.getOwnerLogin(false);
        if(shop.isPresent())
            searchDto.setShopId(shop.get().getId());
        Page<ProductDto> page = productService.findByAdvSearch(searchDto);
        return new ResponseEntity<>(new MessageEntity(200, page), HttpStatus.OK);
    }
    @ApiOperation(value = "Danh sách sản phẩm [Phân trang nâng cao].")
    @PostMapping(value = "/search-adv")
    public ResponseEntity<?> searchAdvByShop(@RequestBody ProductSearchDto searchDto) {
        Page<ProductDto> page = productService.findByAdvSearch(searchDto);
        return new ResponseEntity<>(new MessageEntity(200, page), HttpStatus.OK);
    }

    @ApiOperation(value = "Thêm mới sản phẩm, 1 sản phẩm có [0,n] size ___ [1,n] danh mục sản phẩm ___ [1,n] danh mục sản phẩm của cửa hàng")
    @PostMapping()
    public ResponseEntity<?> create(String form, @RequestParam("imagesUrl") MultipartFile images) throws IOException {
        // check isImage + getFileName
        ProductFormDto data = getDataForm(form);
        String fileName = getFileName(images);
        // create
        data.setId(null);
        ProductFormDto result = saveOrUpdate(data, images, fileName);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }


    @ApiOperation(value = "Cập nhật sản phẩm, 1 sản phẩm có [0,n] size ___ [1,n] danh mục sản phẩm ___ [1,n] danh mục sản phẩm của cửa hàng")
    @PutMapping()
    public ResponseEntity<?> update(String form, @RequestParam("imagesUrl") MultipartFile images) throws IOException {
        ProductFormDto data = getDataForm(form);
        String fileName = getFileName(images);
        // create
        ProductFormDto result = saveOrUpdate(data, images, fileName);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }

    private ProductFormDto saveOrUpdate(ProductFormDto form, MultipartFile images, String fileName) throws IOException {
        form.setImage(fileName);
        // save
        ProductFormDto result = productService.saveOrUpdate(form);
        // save image
        String uploadDir = imageUrl + "/products/" + result.getId();
        FileUploadUtil.saveImage(uploadDir, fileName, images);
        return result;
    }

    @ApiOperation(value = "Chi tiết sản phẩm .")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Integer id) {
        ProductDetailDto product = productService.findDetailById(id);
        return ResponseEntity.ok().body(new MessageEntity(200, product));
    }

    @ApiOperation(value = "Xóa sản phẩm")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        // todos: xóa sản phẩm
        return ResponseEntity.ok().body(new MessageEntity(200,id.toString()));
    }
    // function utils
    private String getFileName(MultipartFile images) {
        String fileName = FileUploadUtil.getImageName(images);
        if ("".equals(fileName) || fileName == null) {
            throw new ValidException("Định dạng ảnh không hợp lệ");
        }
        return fileName;
    }

    private ProductFormDto getDataForm(String form) throws com.fasterxml.jackson.core.JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductFormDto data = objectMapper.readValue(form, ProductFormDto.class);
        // valid product
        List<String> validateMessages = validateProduct.validate(data);
        if (!validateMessages.isEmpty()) {
            throw new ValidException(validateMessages.get(0));
        }
        return data;
    }

}
