CREATE TABLE Athena.user
(
  id        INT PRIMARY KEY                       NOT NULL,
  username  VARCHAR(64)                           NOT NULL,
  password  VARCHAR(32)                           NOT NULL,
  wechat_id VARCHAR(64)                           NOT NULL,
  email     VARCHAR(64)                           NOT NULL,
  identity  SET ('SUPERADMIN', 'ADMIN', 'READER') NOT NULL DEFAULT 'READER'
)