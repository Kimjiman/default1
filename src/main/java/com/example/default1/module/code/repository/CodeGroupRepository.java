package com.example.default1.module.code.repository;

import com.example.default1.module.code.model.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, Long>, CodeGroupRepositoryCustom {
}
