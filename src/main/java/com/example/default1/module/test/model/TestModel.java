package com.example.default1.module.test.model;

import com.example.default1.base.constants.YN;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestModel {
    private Integer id;
    private YN yn;
}
