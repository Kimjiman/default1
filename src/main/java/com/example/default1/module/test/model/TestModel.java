package com.example.default1.module.test.model;

import com.example.default1.constants.YN;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestModel {
    private Integer id;
    private YN yn;
}
