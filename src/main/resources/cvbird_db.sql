-- Table user_account
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

-- Table verification_token
CREATE table verification_token (
   id BIGSERIAL NOT NULL PRIMARY KEY,
   verification_token VARCHAR (255) NOT NULL,
   expiry_date DATE NOT NULL,
   user_id BIGSERIAL NOT NULL,
   FOREIGN KEY (user_id) REFERENCES  user_account (id)
);

CREATE INDEX idx__verification_token__id
    ON verification_token (id);

CREATE INDEX idx__verification_token__verification_toke
    ON verification_token (verification_token);

CREATE INDEX idx__verification_token__expiry_date
    ON verification_token (expiry_date);

CREATE TABLE IF NOT EXISTS telegram_user(
 id BIGSERIAL NOT NULL PRIMARY KEY ,
 email text unique,
 registration_date TIMESTAMP DEFAULT CURRENT_DATE,
 telegram_id text not null unique,
 telegram_first_name text,
 telegram_is_bot boolean,
 telegram_user_name text,
 telegram_last_name text,
 telegram_language_code text,
 cvbird_user_id BIGSERIAL,

 UNIQUE(cvbird_user_id),
 FOREIGN KEY(cvbird_user_id) REFERENCES user_account(id)
);

CREATE INDEX idx__telegram_user__id
    ON telegram_user (id);

CREATE INDEX idx__telegram_user__email
    ON telegram_user (email);

--user cv data
CREATE TABLE cv_data (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  cvbird_user_id INT NOT NULL,
  cv_file bytea,
  cv_description TEXT,
  CONSTRAINT fk_author FOREIGN KEY(cvbird_user_id) REFERENCES user_account(id)
);

CREATE INDEX idx__cv_data__id
    ON cv_data (id);

CREATE INDEX idx__cv_data__email
    ON cv_data (cvbird_user_id);

-- TODO
--user cv digest
CREATE TABLE cv_data (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  user_id INT NOT NULL,
  cv_file TEXT,
  cv_description TEXT,
  CONSTRAINT fk_author FOREIGN KEY(user_id) REFERENCES user_account(id)
)
--user data
CREATE TABLE user_data (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  user_id INT NOT NULL,
  cv_file TEXT NOT NULL,
  cv_description TEXT NOT NULL,
  CONSTRAINT fk_author FOREIGN KEY(user_id) REFERENCES user_account(id)
)