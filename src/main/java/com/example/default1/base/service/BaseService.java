package com.example.default1.base.service;

import com.example.default1.base.model.BaseDTO;
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
 * @param <D> DTO
 * @param <P> SearchParam
 * @param <T> PK
 */
public interface BaseService<D extends BaseDTO<T>, P extends BaseSearchParam<T>, T> {
    boolean existsById(T id);
    D findById(T id);
    Long countAllBy(P p);
    List<D> findAllBy(P p);
    Long create(D dto);
    Long update(D dto);
    boolean removeById(T id);
}
