package com.example.default1.base.mapper;

import com.example.default1.base.model.BaseModel;
import com.example.default1.base.model.BaseSearchParam;

import java.util.List;

/**
 * packageName    : com.example.default1.base.mapper
 * fileName       : BaseMapper
 * author         : KIM JIMAN
 * date           : 26. 2. 10. 화요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 26. 2. 10.     KIM JIMAN      First Commit
 * @param <E> MainObject
 * @param <P> SearchParam
 * @param <T> PK
 */

public interface BaseMapper<E extends BaseModel<T>, P extends BaseSearchParam<T>, T> {
    Long countAllBy(P param);
    List<E> findAllBy(P param);
    E findById(T id);
    void create(E entity);
    void update(E entity);
    int removeById(T id);
}
