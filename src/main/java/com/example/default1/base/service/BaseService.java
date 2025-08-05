package com.example.default1.base.service;

import com.example.default1.base.model.BaseModel;
import com.example.default1.base.model.BaseSearchParam;

import java.util.List;

/**
 * packageName    : com.example.default1.base.service
 * fileName       : BaseService
 * author         : KIM JIMAN
 * date           : 25. 8. 5. 화요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 5.     KIM JIMAN      First Commit
 */
public interface BaseService<T extends BaseModel, P extends BaseSearchParam> {
    T findById(Long id);
    Long countAllBy(P p);
    List<T> findAllBy(P p);
    int create(T t);
    int update(T t);
    int removeById(Long id);
}
