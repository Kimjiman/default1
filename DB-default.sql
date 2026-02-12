-- =============================================
-- DB: localhost:3306/test?serverTimezone=Asia/Seoul
-- root / 1234
-- =============================================

-- ===================
-- 기존 테이블
-- ===================

CREATE TABLE `user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `login_id` varchar(100) DEFAULT NULL,
    `password` varchar(100) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `use_yn` varchar(1) DEFAULT 'Y',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT 0,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_id` bigint DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `file` (
    `id` bigint NOT NULL,
    `ref_path` varchar(100) DEFAULT NULL,
    `ref_id` bigint DEFAULT NULL,
    `ori_name` varchar(100) DEFAULT NULL,
    `new_name` varchar(200) DEFAULT NULL,
    `save_path` varchar(200) DEFAULT NULL,
    `ext` varchar(45) DEFAULT NULL,
    `type` varchar(100) DEFAULT NULL,
    `size` int DEFAULT NULL,
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `code_group` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code_group` varchar(10) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT 0,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_id` bigint DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `code` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code_group_id` bigint DEFAULT NULL,
    `code` varchar(10) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `order` int DEFAULT NULL,
    `info` varchar(1000) DEFAULT NULL,
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT 0,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_id` bigint DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `menu` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `parent_id` bigint DEFAULT NULL,
    `uri` varchar(200) DEFAULT NULL,
    `node_path` varchar(200) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `order` int DEFAULT NULL,
    `icon_path` varchar(200) DEFAULT NULL,
    `use_yn` varchar(1) DEFAULT 'Y',
    `roles` varchar(500) DEFAULT NULL,
    `description` varchar(500) DEFAULT NULL,
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT 0,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_id` bigint DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ===================
-- RBAC 테이블
-- ===================

CREATE TABLE `role` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `role_name` varchar(50) DEFAULT NULL COMMENT '역할 식별자 (예: ADM, USR)',
    `description` varchar(200) DEFAULT NULL COMMENT '역할 설명',
    `use_yn` varchar(1) DEFAULT 'Y' COMMENT '사용 여부',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT 0,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_id` bigint DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_role` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `user_id` bigint DEFAULT NULL COMMENT 'user 테이블 FK',
    `role_id` bigint DEFAULT NULL COMMENT 'role 테이블 FK',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT 0,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_id` bigint DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ===================
-- 기본 데이터
-- ===================

-- 관리자 계정 (password: admin)
INSERT INTO `user` (login_id, password, name, use_yn, create_id)
VALUES ('admin', '$2a$10$JHRc1ScPG1dQncw9Jh8oJOyCtIzgi2uCYbQnu4OYz1QVcDs8kWpD2', '관리자', 'Y', 1);

-- 기본 역할
INSERT INTO `role` (role_name, description, use_yn, create_id) VALUES ('ADM', '관리자', 'Y', 1);
INSERT INTO `role` (role_name, description, use_yn, create_id) VALUES ('USR', '일반 사용자', 'Y', 1);

-- admin 계정에 ADM, USR 역할 부여 (user.id=1, role.id=1,2)
INSERT INTO `user_role` (user_id, role_id, create_id) VALUES (1, 1, 1);
INSERT INTO `user_role` (user_id, role_id, create_id) VALUES (1, 2, 1);
