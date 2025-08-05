package com.example.default1.base.menu;

import com.example.default1.base.model.BaseModel;
import com.example.default1.utils.CollectionUtils;
import com.example.default1.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.example.default1.base.menu
 * fileName       : Menu
 * author         : KIM JIMAN
 * date           : 24. 7. 11. 목요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 24. 7. 11.     KIM JIMAN      First Commit
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu extends BaseModel {
    private Long id;
    private Long parentId;
    private String uri;
    private String nodePath;
    private String name;
    private Integer order;
    private String iconPath;
    private String useYn;
    private String roles;
    private String description;
    private Boolean isChild;

    private List<String> roleList;

    public List<String> getRoleList() {
        StringUtils.ifNotBlank(roles, it -> roleList = CollectionUtils.arrayToList(roles.split(",")));
        return CollectionUtils.arrayToList(roles.split(","));
    }
}
