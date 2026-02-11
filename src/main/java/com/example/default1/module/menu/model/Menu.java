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

import java.util.Arrays;
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
public class Menu extends BaseModel<Long> {
    private Long parentId; // 부모아이디
    private String uri; // 라우터, Uri
    private String nodePath; // 노드 총 경로
    private String name; // 명칭
    private Integer order; // 순서
    private String iconPath; // 아이콘 path (등록)
    private String useYn; // 사용
    private String roles; // 권한
    private String description; // 설명
    private Boolean isChild; // 자식유무

    private List<String> roleList;

    public List<String> getRoleList() {
        if (null == this.roleList && StringUtils.isNotBlank(roles)) {
            this.roleList = Arrays.stream(roles.split(",")).toList();
        }
        return this.roleList;
    }
}
