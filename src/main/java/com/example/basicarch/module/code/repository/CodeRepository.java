package com.example.default1.module.code.repository;

import com.example.default1.module.code.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long>, CodeRepositoryCustom {
    void deleteByCodeGroupId(Long codeGroupId);
}
