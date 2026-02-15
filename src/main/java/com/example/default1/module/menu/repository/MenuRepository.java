package com.example.default1.module.menu.repository;

import com.example.default1.module.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByUseYn(String useYn);
}
