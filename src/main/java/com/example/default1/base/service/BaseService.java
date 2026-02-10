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
 * @param <E> MainObject
 * @param <P> SearchParam
 * @param <T> PK
 */
public interface BaseService<E extends BaseModel<T>, P extends BaseSearchParam<T>, T> {
    boolean existsById(T id);
    E findById(T id);
    Long countAllBy(P p);
    List<E> findAllBy(P p);
    Long create(E e);
    Long update(E e);
    boolean removeById(T id);
}
