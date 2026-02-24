package com.example.basicarch.module.menu.model;

import com.example.basicarch.base.constants.YN;
import com.example.basicarch.base.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuModel extends BaseModel<Long> {
    private Long parentId;
    private String uri;
    private String nodePath;
    private String name;
    private Integer order;
    private String iconPath;
    private YN useYn;
    private String roles;
    private String description;
    private List<String> roleList;
    private List<MenuModel> children;
}
