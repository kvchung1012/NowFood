package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.Category;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.category.CategoryDto;
import com.cntt2.nowfood.dto.category.CategoryFormDto;
import com.cntt2.nowfood.mapper.CategoryMapper;
import com.cntt2.nowfood.repository.CategoryRepository;
import com.cntt2.nowfood.service.CategoryService;
import com.cntt2.nowfood.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/3/2021 10:24 PM
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends GenericServiceImpl<Category, Integer> implements
        CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category saveOrUpdate(CategoryFormDto form) {
        Category category = null;
        if(null != form.getId()){
            category = categoryRepository.findById(form.getId()).orElse(null);
        }else{
            category = new Category();
        }
        category.setCode(form.getCode());
        category.setName(form.getName());
        return categoryRepository.save(category);
    }

    @Override
    public Page<CategoryDto> findByAdvSearch(SearchDto dto) {
        Pageable pageable = CommonUtils.getPageRequest(dto);
        Page<Category> entities = categoryRepository.findByAdvSearch(dto,pageable);
        return entities.map(categoryMapper::toDto);
    }
}
