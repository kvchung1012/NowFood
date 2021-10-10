package com.cntt2.nowfood.service;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 10:51 PM
 */
public interface GenericService<T, Idt extends Serializable> {
    T delete(Idt id);
    T save(T t);
    Page<T> getList(int pageIndex, int pageSize);
    List<T> getAll();
    T getById(Idt id);
}

