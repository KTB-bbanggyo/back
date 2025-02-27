-- 데이터베이스 생성 (존재하지 않으면 생성)
CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

-- 사용자 테이블 (OAuth 및 로컬 로그인 포함)
CREATE TABLE IF NOT EXISTS Users (
    user_id         BIGINT AUTO_INCREMENT PRIMARY KEY, -- 내부적으로 사용하는 사용자 고유 ID
    kakao_id       BIGINT UNIQUE, -- 카카오 로그인에서 제공하는 고유 ID (NULL 허용: 로컬 회원가입 가능)
    username       VARCHAR(255) UNIQUE, -- 로컬 로그인 시 유저네임
    user_password  VARCHAR(255), -- 로컬 로그인 시 비밀번호 (암호화 필요)
    email          VARCHAR(255), -- 이메일
    provider       ENUM('LOCAL', 'KAKAO') NOT NULL, -- 로그인 방식
    nickname       VARCHAR(50), -- 사용자 닉네임
    profile_image  TEXT, -- 프로필 이미지 URL
    age_range      VARCHAR(20), -- 연령대 ("20-29", "30-39" 등)
    gender         ENUM('male', 'female', 'other'), -- 성별
    llm_thread_id  VARCHAR(100) -- LLM 대화방 ID
);

-- 빵집 정보 
CREATE TABLE IF NOT EXISTS Bakery (
    bakery_id     BIGINT AUTO_INCREMENT PRIMARY KEY, 
    name          VARCHAR(255) NOT NULL, 
    address       VARCHAR(255) NOT NULL, 
    latitude      DECIMAL(10,7) NOT NULL, 
    longitude     DECIMAL(10,7) NOT NULL, 
    phone         VARCHAR(20), 
    rating        DECIMAL(3,2) DEFAULT 0, 
    opening_hours TEXT, 
    keywords      JSON -- 키워드 정보 저장 (예: ["크로와상", "디저트 맛집", "주차 가능"])
);


-- 찜한 빵집 (마이페이지 - 찜.빵)
CREATE TABLE IF NOT EXISTS FavoriteBackery (
    user_id       BIGINT, 
    bakery_id     BIGINT, 
    PRIMARY KEY (user_id, bakery_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (bakery_id) REFERENCES Bakery(bakery_id) ON DELETE CASCADE
);

-- 로그인 세션 관리 (JWT 또는 세션 기반 인증)
CREATE TABLE IF NOT EXISTS Login_Sessions (
    session_id    BIGINT AUTO_INCREMENT PRIMARY KEY, 
    user_id       BIGINT NOT NULL, 
    session_token VARCHAR(255) UNIQUE NOT NULL, 
    expires_at    TIMESTAMP NOT NULL, 
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

