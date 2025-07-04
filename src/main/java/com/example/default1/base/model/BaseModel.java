package com.example.default1.base.model;

import com.example.default1.utils.SessionUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseModel extends Base {
    private LocalDateTime createTime;
    private Long createId;
    private LocalDateTime updateTime;
    private Long updateId;

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
