package com.example.default1.module.code.repository;

import com.example.default1.module.code.model.CodeGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, Long>, CodeGroupRepositoryCustom {
    @EntityGraph(attributePaths = {"codeList"})
    Optional<CodeGroup> findWithCodesById(Long id);
}
