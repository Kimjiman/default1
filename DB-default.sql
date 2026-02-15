-- =============================================
-- DB: localhost:5432/project1
-- root / 1234
-- =============================================

-- ===================
-- 기존 테이블
-- ===================

CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    login_id VARCHAR(100) DEFAULT NULL,
    password VARCHAR(100) DEFAULT NULL,
    name VARCHAR(100) DEFAULT NULL,
    use_yn VARCHAR(1) DEFAULT 'Y',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

CREATE TABLE "file" (
    id BIGSERIAL PRIMARY KEY,
    ref_path VARCHAR(100) DEFAULT NULL,
    ref_id BIGINT DEFAULT NULL,
    ori_name VARCHAR(100) DEFAULT NULL,
    new_name VARCHAR(200) DEFAULT NULL,
    save_path VARCHAR(200) DEFAULT NULL,
    ext VARCHAR(45) DEFAULT NULL,
    type VARCHAR(100) DEFAULT NULL,
    size INT DEFAULT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT NULL
);

CREATE TABLE code_group (
    id BIGSERIAL PRIMARY KEY,
    code_group VARCHAR(10) DEFAULT NULL,
    name VARCHAR(100) DEFAULT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

CREATE TABLE code (
    id BIGSERIAL PRIMARY KEY,
    code_group_id BIGINT DEFAULT NULL,
    code VARCHAR(10) DEFAULT NULL,
    name VARCHAR(100) DEFAULT NULL,
    "order" INT DEFAULT NULL,
    info VARCHAR(1000) DEFAULT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

CREATE TABLE menu (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT DEFAULT NULL,
    uri VARCHAR(200) DEFAULT NULL,
    node_path VARCHAR(200) DEFAULT NULL,
    name VARCHAR(100) DEFAULT NULL,
    "order" INT DEFAULT NULL,
    icon_path VARCHAR(200) DEFAULT NULL,
    use_yn VARCHAR(1) DEFAULT 'Y',
    roles VARCHAR(500) DEFAULT NULL,
    description VARCHAR(500) DEFAULT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

-- ===================
-- RBAC 테이블
-- ===================

CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(50) DEFAULT NULL,
    description VARCHAR(200) DEFAULT NULL,
    use_yn VARCHAR(1) DEFAULT 'Y',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

COMMENT ON COLUMN role.role_name IS '역할 식별자 (예: ADM, USR)';
COMMENT ON COLUMN role.description IS '역할 설명';
COMMENT ON COLUMN role.use_yn IS '사용 여부';

CREATE TABLE user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT DEFAULT NULL,
    role_id BIGINT DEFAULT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

COMMENT ON COLUMN user_role.user_id IS 'user 테이블 FK';
COMMENT ON COLUMN user_role.role_id IS 'role 테이블 FK';

-- ===================
-- 기본 데이터
-- ===================

-- 관리자 계정 (password: admin)
INSERT INTO "user" (login_id, password, name, use_yn, create_id)
VALUES ('admin', '$2a$10$JHRc1ScPG1dQncw9Jh8oJOyCtIzgi2uCYbQnu4OYz1QVcDs8kWpD2', '관리자', 'Y', 1);

-- 기본 역할
INSERT INTO role (role_name, description, use_yn, create_id) VALUES ('ADM', '관리자', 'Y', 1);
INSERT INTO role (role_name, description, use_yn, create_id) VALUES ('USR', '일반 사용자', 'Y', 1);

-- admin 계정에 ADM, USR 역할 부여 (user.id=1, role.id=1,2)
INSERT INTO user_role (user_id, role_id, create_id) VALUES (1, 1, 1);
INSERT INTO user_role (user_id, role_id, create_id) VALUES (1, 2, 1);
