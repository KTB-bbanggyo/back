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

-- 사용자 선호도 테이블 (초기 가입 시 입력 및 리뷰 반영)
CREATE TABLE IF NOT EXISTS User_Preferences (
    user_id                 BIGINT PRIMARY KEY, -- 사용자 ID (외래키)
    prefers_parking         BOOLEAN DEFAULT FALSE,
    prefers_takeout         BOOLEAN DEFAULT FALSE,
    prefers_high_protein    BOOLEAN DEFAULT FALSE,
    prefers_vegan           BOOLEAN DEFAULT FALSE,
    prefers_gluten_free     BOOLEAN DEFAULT FALSE,
    prefers_quick_meal      BOOLEAN DEFAULT FALSE,
    prefers_meeting_friendly BOOLEAN DEFAULT FALSE,
    prefers_trendy_bakery   BOOLEAN DEFAULT FALSE,
    prefers_croissant       BOOLEAN DEFAULT FALSE,
    prefers_baguette        BOOLEAN DEFAULT FALSE,
    prefers_cream_bread     BOOLEAN DEFAULT FALSE,
    prefers_red_bean_bread  BOOLEAN DEFAULT FALSE,
    prefers_croffle         BOOLEAN DEFAULT FALSE,
    prefers_shikppang       BOOLEAN DEFAULT FALSE,
    prefers_sweet           BOOLEAN DEFAULT FALSE,
    prefers_savory          BOOLEAN DEFAULT FALSE,
    prefers_soft            BOOLEAN DEFAULT FALSE,
    prefers_crispy          BOOLEAN DEFAULT FALSE,
    prefers_filling         BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- 빵집 정보 (카카오맵 API 연동 가능)
CREATE TABLE IF NOT EXISTS Bakeries (
    bakery_id     BIGINT AUTO_INCREMENT PRIMARY KEY, 
    name          VARCHAR(255) NOT NULL, 
    address       VARCHAR(255) NOT NULL, 
    latitude      DECIMAL(10,7) NOT NULL, 
    longitude     DECIMAL(10,7) NOT NULL, 
    phone         VARCHAR(20), 
    rating        DECIMAL(3,2) DEFAULT 0, 
    opening_hours TEXT
);

-- 빵집 태그 (카테고리화)
CREATE TABLE IF NOT EXISTS Tags (
    tag_id        BIGINT AUTO_INCREMENT PRIMARY KEY, 
    name          VARCHAR(50) UNIQUE NOT NULL
);

-- 빵집-태그 연결 테이블 (N:N 관계)
CREATE TABLE IF NOT EXISTS Bakery_Tags (
    bakery_id     BIGINT, 
    tag_id        BIGINT, 
    PRIMARY KEY (bakery_id, tag_id),
    FOREIGN KEY (bakery_id) REFERENCES Bakeries(bakery_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES Tags(tag_id) ON DELETE CASCADE
);

-- 빵집 메뉴 정보
CREATE TABLE IF NOT EXISTS Bakery_Menus (
    menu_id       BIGINT AUTO_INCREMENT PRIMARY KEY, 
    bakery_id     BIGINT NOT NULL, 
    name          VARCHAR(255) NOT NULL, 
    price         DECIMAL(10,2), 
    image         TEXT, 
    FOREIGN KEY (bakery_id) REFERENCES Bakeries(bakery_id) ON DELETE CASCADE
);

-- 찜한 빵집 (마이페이지 - 찜.빵)
CREATE TABLE IF NOT EXISTS Favorites (
    user_id       BIGINT, 
    bakery_id     BIGINT, 
    PRIMARY KEY (user_id, bakery_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (bakery_id) REFERENCES Bakeries(bakery_id) ON DELETE CASCADE
);

-- 방문 내역 (마이페이지 - 방문 내역 == 리뷰와 연결)
CREATE TABLE IF NOT EXISTS Visits (
    visit_id      BIGINT AUTO_INCREMENT PRIMARY KEY, 
    user_id       BIGINT NOT NULL, 
    bakery_id     BIGINT NOT NULL, 
    visited_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (bakery_id) REFERENCES Bakeries(bakery_id) ON DELETE CASCADE
);

-- 리뷰 정보 (별점, 후기, 이미지 업로드 가능)
CREATE TABLE IF NOT EXISTS Reviews (
    review_id     BIGINT AUTO_INCREMENT PRIMARY KEY, 
    user_id       BIGINT NOT NULL, 
    bakery_id     BIGINT NOT NULL, 
    rating        INT CHECK (rating BETWEEN 1 AND 5), 
    review_text   TEXT, 
    images        JSON, 
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (bakery_id) REFERENCES Bakeries(bakery_id) ON DELETE CASCADE
);

-- 로그인 세션 관리 (JWT 또는 세션 기반 인증)
CREATE TABLE IF NOT EXISTS Login_Sessions (
    session_id    BIGINT AUTO_INCREMENT PRIMARY KEY, 
    user_id       BIGINT NOT NULL, 
    session_token VARCHAR(255) UNIQUE NOT NULL, 
    expires_at    TIMESTAMP NOT NULL, 
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

 
