package com.example.default1.base.mapper;

import com.example.default1.base.model.BaseModel;
import com.example.default1.base.model.BaseSearchParam;

import java.util.List;

/**
 * packageName    : com.example.default1.base.mapper
 * fileName       : BaseMapper
 * author         : KIM JIMAN
 * date           : 25. 8. 5. 화요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 5.     KIM JIMAN      First Commit
 */
public interface BaseMapper<T extends BaseModel, P extends BaseSearchParam> {
    boolean existsById(Long id);
    T findById(Long id);
    Long countAllBy(P p);
    List<T> findAllBy(P p);
    T create(T t);
    T update(T t);
    boolean removeById(Long id);
}
