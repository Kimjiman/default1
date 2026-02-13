package com.example.default1.base.model;

import com.example.default1.base.utils.SessionUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @Transient
    private Long rowNum;

    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @Column(name = "create_id", updatable = false)
    @Builder.Default
    private Long createId = 0L;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "update_id")
    @Builder.Default
    private Long updateId = 0L;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.setCurrentUser();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
        this.setCurrentUserUpdateId();
    }

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
        this.setCreateId(SessionUtils.getId());
    }

    public void setCurrentUserUpdateId() {
        this.setUpdateId(SessionUtils.getId());
    }
}
