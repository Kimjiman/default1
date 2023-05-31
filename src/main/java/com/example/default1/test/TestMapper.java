package com.example.default1.test;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface TestMapper {
    TestModel testSql();
    void testInsert(TestModel testModel);
}
