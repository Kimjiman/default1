package com.example.default1.module.test.repository;

import com.example.default1.module.test.model.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestModel, Integer> {
}
