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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "code_group")
public class CodeGroup extends BaseEntity<Long> {
    @Column(name = "code_group", nullable = false)
    private String codeGroup;

    @Column(name = "name", nullable = false)
    private String name;

    @ToString.Exclude
    @OneToMany
    @JoinColumn(name = "code_group_id", insertable = false, updatable = false)
    private List<Code> codeList;
}
