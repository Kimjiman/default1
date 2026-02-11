package com.example.default1.base.service;

import com.example.default1.base.model.BaseModel;
import com.example.default1.base.model.BaseSearchParam;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseModel<ID>, P extends BaseSearchParam<ID>, ID> {
    boolean existsById(ID id);
    Optional<T> findById(ID id);
    Long countAllBy(P p);
    List<T> findAllBy(P p);
    T save(T entity);
    T update(T entity);
    void deleteById(ID id);
}
