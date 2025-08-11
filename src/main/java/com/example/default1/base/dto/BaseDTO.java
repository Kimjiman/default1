package com.example.default1.base.dto;

import com.example.default1.base.model.BaseObject;
import com.example.default1.utils.SessionUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseDTO extends BaseObject {
    private Long id;
    private LocalDateTime createTime;
    @Builder.Default
    private Long createId = 0L;
    private LocalDateTime updateTime;
    @Builder.Default
    private Long updateId = 0L;

    public void setSystemUser() {
        this.setSystemUserCreateId();
        this.setSystemUserUpdateId();
    }

    public void setSystemUserCreateId() {
        this.setCreateId(0L);
    }

    public void setSystemUserUpdateId() {
        this.setUpdateId(0L);
    }

    public void setCurrentUser() {
        this.setCurrentUserCreateId();
        this.setCurrentUserUpdateId();
    }

    public void setCurrentUserCreateId() {
        this.setCreateId(SessionUtils.getUserId());
    }

    public void setCurrentUserUpdateId() {
        this.setUpdateId(SessionUtils.getUserId());
    }
}
