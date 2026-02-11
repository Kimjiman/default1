package com.example.default1.base.mybatis;

import com.example.default1.base.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * packageName    : com.example.default1.base.model
 * fileName       : MyBatisInterceptor
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
@Slf4j
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MyBatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        String sqlId = mappedStatement.getId();
        String sqlCommandType = mappedStatement.getSqlCommandType().name();

        log.info("sqlId: {}, sqlCommandType: {}", sqlId, sqlCommandType);

        if ("INSERT".equalsIgnoreCase(sqlCommandType)) {
            if (parameter instanceof BaseModel<?> model) {
                model.setCurrentUser();
                model.setCreateTime(LocalDateTime.now());
                model.setUpdateTime(LocalDateTime.now());
            }
        } else if ("UPDATE".equalsIgnoreCase(sqlCommandType)) {
            if (parameter instanceof BaseModel<?> model) {
                model.setCurrentUserUpdateId();
                model.setUpdateTime(LocalDateTime.now());
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
