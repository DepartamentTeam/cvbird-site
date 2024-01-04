-- Table users
CREATE table user_account (
   id BIGSERIAL NOT NULL PRIMARY KEY,
   email VARCHAR (255) NOT NULL UNIQUE,
   password VARCHAR (255) NOT NULL,
   enabled BOOLEAN NOT NULL
);

CREATE INDEX idx__user_account__id
    ON user_account (id);

CREATE INDEX idx__user_account__email
    ON user_account (email);