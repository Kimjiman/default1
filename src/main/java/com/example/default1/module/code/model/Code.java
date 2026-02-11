package com.example.default1.module.code.model;

import com.example.default1.base.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "code")
public class Code extends BaseEntity<Long> {
    @Column(name = "code_group_id")
    private Long codeGroupId;

    @Transient
    private String codeGroup;

    @Column(name = "code")
    private String code;

    @Transient
    private String codeGroupName;

    @Column(name = "name")
    private String name;

    @Column(name = "order")
    private Integer order;

    @Column(name = "info")
    private String info;
}
