package com.example.basicarch.module.code.entity;

import com.example.basicarch.base.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "code", uniqueConstraints = {
        @UniqueConstraint(name = "uk_code_group_code", columnNames = {"code_group_id", "code"}),
})
public class Code extends BaseEntity<Long> {
    @Column(name = "code_group_id", nullable = false)
    private Long codeGroupId;

    @Transient
    private String codeGroup;

    @Column(name = "code", nullable = false)
    private String code;

    @Transient
    private String codeGroupName;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "info")
    private String info;
}
