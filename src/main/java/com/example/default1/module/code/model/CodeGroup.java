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
    @Column(name = "code_group")
    private String codeGroup;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany
    @JoinColumn(name = "code_group_id", insertable = false, updatable = false)
    private List<Code> codeList;
}
