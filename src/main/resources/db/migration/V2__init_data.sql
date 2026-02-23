-- =============================================
-- V2: 기본 데이터 삽입
-- =============================================

-- 관리자 계정 (password: admin)
INSERT INTO "user" (login_id, password, name, use_yn, create_id)
VALUES ('admin', '$2a$10$JHRc1ScPG1dQncw9Jh8oJOyCtIzgi2uCYbQnu4OYz1QVcDs8kWpD2', '관리자', 'Y', 1);

-- 기본 역할
INSERT INTO role (role_name, description, use_yn, create_id) VALUES ('ADM', '관리자', 'Y', 1);
INSERT INTO role (role_name, description, use_yn, create_id) VALUES ('USR', '일반 사용자', 'Y', 1);

-- admin 계정에 ADM, USR 역할 부여 (user.id=1, role.id=1,2)
INSERT INTO user_role (user_id, role_id, create_id) VALUES (1, 1, 1);
INSERT INTO user_role (user_id, role_id, create_id) VALUES (1, 2, 1);
