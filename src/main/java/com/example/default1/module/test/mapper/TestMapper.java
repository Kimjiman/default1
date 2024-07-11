package com.example.default1.module.test.mapper;

import com.example.default1.module.test.model.TestModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    TestModel testSql();
    void testInsert(TestModel testModel);
}
