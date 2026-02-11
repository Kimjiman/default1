package com.example.default1.base.mapper;

import com.example.default1.base.model.BaseDTO;
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
 * @param <D> DTO
 * @param <P> SearchParam
 * @param <T> PK
 */

public interface BaseMapper<D extends BaseDTO<T>, P extends BaseSearchParam<T>, T> {
    Long countAllBy(P param);
    List<D> findAllBy(P param);
    D findById(T id);
    void create(D dto);
    void update(D dto);
    int removeById(T id);
}
