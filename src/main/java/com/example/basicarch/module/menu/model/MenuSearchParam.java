package com.example.default1.module.menu.model;

import com.example.default1.base.model.BaseSearchParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MenuSearchParam extends BaseSearchParam<Long> {
    private String name;
    private String useYn;
    private Long parentId;
}
