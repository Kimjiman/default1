package com.example.default1.module.menu.model;

import com.example.default1.base.model.BaseModel;
import com.example.default1.base.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "menu")
public class Menu extends BaseModel<Long> {
    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "uri")
    private String uri;

    @Column(name = "node_path")
    private String nodePath;

    @Column(name = "name")
    private String name;

    @Column(name = "order")
    private Integer order;

    @Column(name = "icon_path")
    private String iconPath;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "roles")
    private String roles;

    @Column(name = "description")
    private String description;

    @Transient
    private Boolean isChild;

    @Transient
    private List<String> roleList;

    public List<String> getRoleList() {
        if (null == this.roleList && StringUtils.isNotBlank(roles)) {
            this.roleList = Arrays.stream(roles.split(",")).toList();
        }
        return this.roleList;
    }
}
