package com.example.default1.module.user.repository;

import com.example.default1.module.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUseYn(String useYn);
}
