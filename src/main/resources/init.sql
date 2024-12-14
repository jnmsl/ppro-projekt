-- Create database if it doesn't exist
CREATE
DATABASE IF NOT EXISTS footballportal
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;

USE
footballportal;

-- Enable foreign key checks
SET
FOREIGN_KEY_CHECKS = 1;

-- User table
CREATE TABLE User
(
    user_id           INT PRIMARY KEY AUTO_INCREMENT,
    email             VARCHAR(255) NOT NULL UNIQUE,
    password_hash     VARCHAR(255) NOT NULL,
    username          VARCHAR(50)  NOT NULL UNIQUE,
    full_name         VARCHAR(100) NOT NULL,
    date_of_birth     DATE,
    registration_date DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    subscription_type VARCHAR(20)           DEFAULT 'FREE',
    is_active         BOOLEAN               DEFAULT TRUE,
    preferences_json  JSON,
    CONSTRAINT chk_subscription_type CHECK (subscription_type IN ('FREE', 'PREMIUM', 'PRO')),
    INDEX             idx_email (email),
    INDEX             idx_username (username)
) ENGINE=InnoDB;

-- Team table
CREATE TABLE Team
(
    team_id      INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(100) NOT NULL,
    country      VARCHAR(100) NOT NULL,
    league       VARCHAR(100) NOT NULL,
    founded_year INT,
    stadium_name VARCHAR(100),
    logo_url     VARCHAR(255),
    INDEX        idx_team_name (name),
    INDEX        idx_country_league (country, league)
) ENGINE=InnoDB;

-- Player table
CREATE TABLE Player
(
    player_id     INT PRIMARY KEY AUTO_INCREMENT,
    team_id       INT,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    birth_date    DATE,
    nationality   VARCHAR(100),
    position      VARCHAR(30),
    jersey_number VARCHAR(3),
    joined_team   DATETIME,
    FOREIGN KEY (team_id) REFERENCES Team (team_id) ON DELETE SET NULL,
    INDEX         idx_player_name (last_name, first_name),
    INDEX         idx_team_id (team_id)
) ENGINE=InnoDB;

-- Match table
CREATE TABLE Match
(
    match_id         INT PRIMARY KEY AUTO_INCREMENT,
    home_team_id     INT          NOT NULL,
    away_team_id     INT          NOT NULL,
    competition_name VARCHAR(100) NOT NULL,
    match_date       DATETIME     NOT NULL,
    venue            VARCHAR(100),
    home_score       INT,
    away_score       INT,
    status           VARCHAR(20) DEFAULT 'SCHEDULED',
    round            VARCHAR(50),
    match_stats      JSON,
    FOREIGN KEY (home_team_id) REFERENCES Team (team_id),
    FOREIGN KEY (away_team_id) REFERENCES Team (team_id),
    CONSTRAINT chk_status CHECK (status IN ('SCHEDULED', 'LIVE', 'FINISHED', 'CANCELLED', 'POSTPONED')),
    INDEX            idx_match_date (match_date),
    INDEX            idx_teams (home_team_id, away_team_id),
    INDEX            idx_competition (competition_name)
) ENGINE=InnoDB;

-- PlayerStats table
CREATE TABLE PlayerStats
(
    stats_id       INT PRIMARY KEY AUTO_INCREMENT,
    player_id      INT NOT NULL,
    match_id       INT NOT NULL,
    goals          INT DEFAULT 0,
    assists        INT DEFAULT 0,
    minutes_played INT DEFAULT 0,
    detailed_stats JSON,
    FOREIGN KEY (player_id) REFERENCES Player (player_id),
    FOREIGN KEY (match_id) REFERENCES Match (match_id) ON DELETE CASCADE,
    UNIQUE KEY uk_player_match (player_id, match_id),
    INDEX          idx_player_stats (player_id, match_id)
) ENGINE=InnoDB;

-- Prediction table
CREATE TABLE Prediction
(
    prediction_id        INT PRIMARY KEY AUTO_INCREMENT,
    user_id              INT NOT NULL,
    match_id             INT NOT NULL,
    predicted_home_score INT NOT NULL,
    predicted_away_score INT NOT NULL,
    created_at           DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (match_id) REFERENCES Match (match_id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_match (user_id, match_id),
    INDEX                idx_match_predictions (match_id)
) ENGINE=InnoDB;

-- Comment table
CREATE TABLE Comment
(
    comment_id        INT PRIMARY KEY AUTO_INCREMENT,
    user_id           INT  NOT NULL,
    match_id          INT  NOT NULL,
    content           TEXT NOT NULL,
    created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    parent_comment_id INT,
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (match_id) REFERENCES Match (match_id) ON DELETE CASCADE,
    FOREIGN KEY (parent_comment_id) REFERENCES Comment (comment_id) ON DELETE CASCADE,
    INDEX             idx_match_comments (match_id),
    INDEX             idx_user_comments (user_id)
) ENGINE=InnoDB;

-- Alert table
CREATE TABLE Alert
(
    alert_id        INT PRIMARY KEY AUTO_INCREMENT,
    user_id         INT         NOT NULL,
    match_id        INT         NOT NULL,
    alert_type      VARCHAR(50) NOT NULL,
    is_active       BOOLEAN  DEFAULT TRUE,
    conditions_json JSON,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (match_id) REFERENCES Match (match_id) ON DELETE CASCADE,
    INDEX           idx_user_alerts (user_id),
    INDEX           idx_match_alerts (match_id)
) ENGINE=InnoDB;

-- UserFavoriteTeam table
CREATE TABLE UserFavoriteTeam
(
    user_id     INT NOT NULL,
    team_id     INT NOT NULL,
    followed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, team_id),
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (team_id) REFERENCES Team (team_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- UserFavoritePlayer table
CREATE TABLE UserFavoritePlayer
(
    user_id     INT NOT NULL,
    player_id   INT NOT NULL,
    followed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, player_id),
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (player_id) REFERENCES Player (player_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create a dedicated user and grant permissions
CREATE
USER IF NOT EXISTS 'footballuser'@'%' IDENTIFIED BY 'footballpass';
GRANT
SELECT,
INSERT
,
UPDATE,
DELETE
ON footballportal.* TO 'footballuser'@'%';
FLUSH
PRIVILEGES;