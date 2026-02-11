package com.example.default1.module.code.repository;

import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeSearchParam;
import com.example.default1.module.code.model.QCode;
import com.example.default1.module.code.model.QCodeGroup;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Code> findAllBy(CodeSearchParam param) {
        return createBaseQuery(param)
                .orderBy(QCode.code1.codeGroupId.asc(), QCode.code1.order.asc())
                .fetch();
    }

    @Override
    public Page<Code> findAllBy(CodeSearchParam param, Pageable pageable) {
        QCode code = QCode.code1;

        List<Code> content = createBaseQuery(param)
                .orderBy(code.codeGroupId.asc(), code.order.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(code.count())
                .from(code)
                .where(buildWhere(param))
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    @Override
    public Integer findMaxOrderByCodeGroupId(Long codeGroupId) {
        QCode code = QCode.code1;
        return queryFactory.select(code.order.max())
                .from(code)
                .where(code.codeGroupId.eq(codeGroupId))
                .fetchOne();
    }

    private JPAQuery<Code> createBaseQuery(CodeSearchParam param) {
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
                .where(builder);
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
