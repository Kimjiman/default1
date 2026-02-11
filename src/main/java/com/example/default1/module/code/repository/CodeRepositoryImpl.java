package com.example.default1.module.code.repository;

import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeSearchParam;
import com.example.default1.module.code.model.QCode;
import com.example.default1.module.code.model.QCodeGroup;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long countAllBy(CodeSearchParam param) {
        QCode code = QCode.code1;
        BooleanBuilder builder = buildWhere(param);

        return queryFactory.select(code.count())
                .from(code)
                .where(builder)
                .fetchOne();
    }

    @Override
    public List<Code> findAllBy(CodeSearchParam param) {
        QCode code = QCode.code1;
        QCodeGroup codeGroup = QCodeGroup.codeGroup1;
        BooleanBuilder builder = buildWhere(param);

        return queryFactory.select(Projections.fields(Code.class,
                        code.id,
                        code.codeGroupId,
                        code.code,
                        code.name,
                        code.order,
                        code.info,
                        code.createTime,
                        code.createId,
                        code.updateTime,
                        code.updateId,
                        codeGroup.codeGroup.as("codeGroup"),
                        codeGroup.name.as("codeGroupName")
                ))
                .from(code)
                .leftJoin(codeGroup).on(code.codeGroupId.eq(codeGroup.id))
                .where(builder)
                .orderBy(code.codeGroupId.asc(), code.order.asc())
                .fetch();
    }

    @Override
    public Integer findMaxOrderByCodeGroupId(Long codeGroupId) {
        QCode code = QCode.code1;
        return queryFactory.select(code.order.max())
                .from(code)
                .where(code.codeGroupId.eq(codeGroupId))
                .fetchOne();
    }

    private BooleanBuilder buildWhere(CodeSearchParam param) {
        QCode code = QCode.code1;
        BooleanBuilder builder = new BooleanBuilder();

        if (param.getCodeGroupId() != null) {
            builder.and(code.codeGroupId.eq(param.getCodeGroupId()));
        }
        if (param.getName() != null) {
            builder.and(code.name.contains(param.getName()));
        }

        return builder;
    }
}
