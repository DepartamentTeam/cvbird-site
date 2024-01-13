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
   token VARCHAR (255) NOT NULL,
   expiry_date DATE NOT NULL,
   user_id BIGSERIAL NOT NULL,
   FOREIGN KEY (user_id) REFERENCES  user_account (id),
);

CREATE INDEX idx__verification_token__id
    ON verification_token (id);

CREATE INDEX idx__verification_token__verification_token
    ON verification_token (verification_token);

CREATE INDEX idx__verification_token__expiry_date
    ON verification_token (expiry_date);