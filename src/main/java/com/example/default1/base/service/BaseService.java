package com.example.default1.base.service;

import com.example.default1.base.model.BaseSearchParam;

import java.util.List;
import java.util.Optional;

public interface BaseService<E, P extends BaseSearchParam<?>, T> {
    boolean existsById(T id);
    Optional<E> findById(T id);
    Long countAllBy(P p);
    List<E> findAllBy(P p);
    E save(E entity);
    void deleteById(T id);
}
