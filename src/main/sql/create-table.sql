CREATE TABLE Athena.user
(
  id        INT PRIMARY KEY                       NOT NULL,
  username  VARCHAR(64)                           NOT NULL,
  password  VARCHAR(32)                           NOT NULL,
  wechat_id VARCHAR(64)                           NOT NULL,
  email     VARCHAR(64)                           NOT NULL,
  identity  SET ('SUPERADMIN', 'ADMIN', 'READER') NOT NULL DEFAULT 'READER'
);
CREATE TABLE Athena.book
(
  isbn         VARCHAR(32) PRIMARY KEY NOT NULL,
  name         VARCHAR(128)            NOT NULL,
  author       VARCHAR(128)            NOT NULL
  COMMENT 'Multiple author should divided with comma',
  publisher_id BIGINT,
  publish_date DATE                    NOT NULL,
  category_id  VARCHAR(32)             NOT NULL,
  version      INT DEFAULT 1           NOT NULL,
  cover_url    VARCHAR(128),
  preface      TEXT,
  ditrctory    TEXT                    NOT NULL
  COMMENT 'Should be json',
  introduction TEXT
);
CREATE TABLE Athena.publisher
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name INT NOT NULL
);