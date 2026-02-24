package com.example.basicarch.module.menu.repository;

import com.example.basicarch.module.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByUseYn(String useYn);
    List<Menu> findByParentId(Long parentId);
    void deleteByParentId(Long parentId);
}
