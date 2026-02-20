-- =============================================
-- DB: localhost:5432/postgres
-- root / 1234
-- =============================================

-- ===================
-- 기존 테이블
-- ===================

CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    login_id VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    use_yn VARCHAR(1) NOT NULL DEFAULT 'Y',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

COMMENT ON TABLE "user" IS '사용자';
COMMENT ON COLUMN "user".id IS '사용자 PK';
COMMENT ON COLUMN "user".login_id IS '로그인 ID';
COMMENT ON COLUMN "user".password IS '비밀번호 (BCrypt 암호화)';
COMMENT ON COLUMN "user".name IS '사용자 이름';
COMMENT ON COLUMN "user".use_yn IS '사용 여부 (Y/N)';
COMMENT ON COLUMN "user".create_time IS '생성 일시';
COMMENT ON COLUMN "user".create_id IS '생성자 ID';
COMMENT ON COLUMN "user".update_time IS '수정 일시';
COMMENT ON COLUMN "user".update_id IS '수정자 ID';

CREATE TABLE "file" (
    id BIGSERIAL PRIMARY KEY,
    ref_path VARCHAR(100) DEFAULT NULL,
    ref_id BIGINT DEFAULT NULL,
    ori_name VARCHAR(100) DEFAULT NULL,
    new_name VARCHAR(200) DEFAULT NULL,
    save_path VARCHAR(200) DEFAULT NULL,
    ext VARCHAR(45) DEFAULT NULL,
    type VARCHAR(100) DEFAULT NULL,
    size BIGINT DEFAULT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

COMMENT ON TABLE "file" IS '파일';
COMMENT ON COLUMN "file".id IS '파일 PK';
COMMENT ON COLUMN "file".ref_path IS '참조 경로 (연관 모듈 식별)';
COMMENT ON COLUMN "file".ref_id IS '참조 ID (연관 레코드 PK)';
COMMENT ON COLUMN "file".ori_name IS '원본 파일명';
COMMENT ON COLUMN "file".new_name IS '저장 파일명 (UUID 등)';
COMMENT ON COLUMN "file".save_path IS '저장 경로';
COMMENT ON COLUMN "file".ext IS '파일 확장자';
COMMENT ON COLUMN "file".type IS '파일 MIME 타입';
COMMENT ON COLUMN "file".size IS '파일 크기 (bytes)';
COMMENT ON COLUMN "file".create_time IS '생성 일시';
COMMENT ON COLUMN "file".create_id IS '생성자 ID';
COMMENT ON COLUMN "file".update_time IS '수정 일시';
COMMENT ON COLUMN "file".update_id IS '수정자 ID';

CREATE TABLE code_group (
    id BIGSERIAL PRIMARY KEY,
    code_group VARCHAR(10) NOT NULL,
    name VARCHAR(100) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

COMMENT ON TABLE code_group IS '코드 그룹';
COMMENT ON COLUMN code_group.id IS '코드 그룹 PK';
COMMENT ON COLUMN code_group.code_group IS '코드 그룹 식별자';
COMMENT ON COLUMN code_group.name IS '코드 그룹명';
COMMENT ON COLUMN code_group.create_time IS '생성 일시';
COMMENT ON COLUMN code_group.create_id IS '생성자 ID';
COMMENT ON COLUMN code_group.update_time IS '수정 일시';
COMMENT ON COLUMN code_group.update_id IS '수정자 ID';

CREATE TABLE code (
    id BIGSERIAL PRIMARY KEY,
    code_group_id BIGINT NOT NULL,
    code VARCHAR(10) NOT NULL,
    name VARCHAR(100) NOT NULL,
    info VARCHAR(1000) DEFAULT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0,
    CONSTRAINT uk_code_group_code UNIQUE (code_group_id, code)
);

COMMENT ON TABLE code IS '코드';
COMMENT ON COLUMN code.id IS '코드 PK';
COMMENT ON COLUMN code.code_group_id IS '코드 그룹 FK (code_group.id)';
COMMENT ON COLUMN code.code IS '코드 값';
COMMENT ON COLUMN code.name IS '코드명';
COMMENT ON COLUMN code.info IS '코드 부가 정보';
COMMENT ON COLUMN code.create_time IS '생성 일시';
COMMENT ON COLUMN code.create_id IS '생성자 ID';
COMMENT ON COLUMN code.update_time IS '수정 일시';
COMMENT ON COLUMN code.update_id IS '수정자 ID';

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

COMMENT ON TABLE menu IS '메뉴';
COMMENT ON COLUMN menu.id IS '메뉴 PK';
COMMENT ON COLUMN menu.parent_id IS '상위 메뉴 ID (self FK)';
COMMENT ON COLUMN menu.uri IS '메뉴 URI 경로';
COMMENT ON COLUMN menu.node_path IS '노드 경로 (트리 구조 표현)';
COMMENT ON COLUMN menu.name IS '메뉴명';
COMMENT ON COLUMN menu."order" IS '정렬 순서';
COMMENT ON COLUMN menu.icon_path IS '아이콘 경로';
COMMENT ON COLUMN menu.use_yn IS '사용 여부 (Y/N)';
COMMENT ON COLUMN menu.roles IS '접근 가능 역할 (콤마 구분, 예: ADM,USR)';
COMMENT ON COLUMN menu.description IS '메뉴 설명';
COMMENT ON COLUMN menu.create_time IS '생성 일시';
COMMENT ON COLUMN menu.create_id IS '생성자 ID';
COMMENT ON COLUMN menu.update_time IS '수정 일시';
COMMENT ON COLUMN menu.update_id IS '수정자 ID';

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

COMMENT ON TABLE role IS '역할(권한)';
COMMENT ON COLUMN role.id IS '역할 PK';
COMMENT ON COLUMN role.role_name IS '역할 식별자 (예: ADM, USR)';
COMMENT ON COLUMN role.description IS '역할 설명';
COMMENT ON COLUMN role.use_yn IS '사용 여부 (Y/N)';
COMMENT ON COLUMN role.create_time IS '생성 일시';
COMMENT ON COLUMN role.create_id IS '생성자 ID';
COMMENT ON COLUMN role.update_time IS '수정 일시';
COMMENT ON COLUMN role.update_id IS '수정자 ID';

CREATE TABLE user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT DEFAULT NULL,
    role_id BIGINT DEFAULT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_id BIGINT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id BIGINT DEFAULT 0
);

COMMENT ON TABLE user_role IS '사용자-역할 매핑';
COMMENT ON COLUMN user_role.id IS '매핑 PK';
COMMENT ON COLUMN user_role.user_id IS '사용자 FK (user.id)';
COMMENT ON COLUMN user_role.role_id IS '역할 FK (role.id)';
COMMENT ON COLUMN user_role.create_time IS '생성 일시';
COMMENT ON COLUMN user_role.create_id IS '생성자 ID';
COMMENT ON COLUMN user_role.update_time IS '수정 일시';
COMMENT ON COLUMN user_role.update_id IS '수정자 ID';

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
