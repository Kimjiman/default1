package com.example.default1.module.code.repository;

import com.example.default1.module.code.model.CodeGroup;
import com.example.default1.module.code.model.CodeGroupSearchParam;
import com.example.default1.module.code.model.QCodeGroup;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CodeGroupRepositoryImpl implements CodeGroupRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CodeGroup> findAllBy(CodeGroupSearchParam param) {
        QCodeGroup codeGroup = QCodeGroup.codeGroup1;
        BooleanBuilder builder = buildWhere(param);

        return queryFactory.selectFrom(codeGroup)
                .leftJoin(codeGroup.codeList).fetchJoin()
                .where(builder)
                .orderBy(codeGroup.id.asc())
                .distinct()
                .fetch();
    }

    private BooleanBuilder buildWhere(CodeGroupSearchParam param) {
        QCodeGroup codeGroup = QCodeGroup.codeGroup1;
        BooleanBuilder builder = new BooleanBuilder();

        if (param.getName() != null) {
            builder.and(codeGroup.name.contains(param.getName()));
        }

        return builder;
    }
}
