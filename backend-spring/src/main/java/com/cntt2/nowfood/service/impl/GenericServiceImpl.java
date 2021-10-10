package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 10:53 PM
 */

@Transactional
public class GenericServiceImpl<T, Idt extends Serializable> implements GenericService<T, Idt> {
    @Autowired
    public JpaRepository<T, Idt> repository;
    @Autowired
    public EntityManager manager;

    public GenericServiceImpl() {
    }

    public T delete(Idt id) {
        T result = this.repository.findById(id).get();
        if (result != null) {
            this.repository.deleteById(id);
        }

        return result;
    }

    public T save(T t) {
        T result = this.repository.save(t);
        return result;
    }

    public Page<T> getList(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return this.repository.findAll(pageable);
    }

    @Override
    public List<T> getAll() {
        return this.repository.findAll();
    }

    public T getById(Idt id) {
        return this.repository.findById(id).get();
    }
}

