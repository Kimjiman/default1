package com.example.default1.module.test.model;

import com.example.default1.base.constants.YN;
import com.example.default1.base.mybatis.typeHandler.YnAttributeConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "test")
public class TestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "yn")
    @Convert(converter = YnAttributeConverter.class)
    private YN yn;
}
