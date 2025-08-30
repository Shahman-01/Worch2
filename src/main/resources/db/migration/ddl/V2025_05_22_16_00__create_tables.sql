CREATE TABLE IF NOT EXISTS "user"
(
    id         UUID PRIMARY KEY,
    login      VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    email      VARCHAR(100),
    phone      VARCHAR(50),
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    image      BYTEA,
    birthday   TIMESTAMP WITH TIME ZONE,
                             language   VARCHAR(10),
    created_at TIMESTAMP WITH TIME ZONE
                             );

CREATE TABLE IF NOT EXISTS channel
(
    id             UUID PRIMARY KEY,
    name           VARCHAR(200),
    description    TEXT,
    is_private     BOOLEAN,
    password       VARCHAR(100),
    age_restricted BOOLEAN,
    owner_id       UUID,
    created_at     TIMESTAMP WITH TIME ZONE
                                 );

CREATE TABLE IF NOT EXISTS "group"
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(200),
    owner_id   UUID,
    parent_id  UUID,
    created_at TIMESTAMP WITH TIME ZONE
                             );

CREATE TABLE IF NOT EXISTS group_user
(
    group_id UUID,
    user_id  UUID,
    PRIMARY KEY (group_id, user_id)
    );

CREATE TABLE IF NOT EXISTS choice
(
    id          UUID PRIMARY KEY,
    creator_id  UUID,
    channel_id  UUID,
    title       VARCHAR(300),
    description TEXT,
    image       BYTEA,
    is_personal BOOLEAN,
    status      VARCHAR(20),
    deadline    TIMESTAMP WITH TIME ZONE,
    created_at  TIMESTAMP WITH TIME ZONE
                              );

CREATE TABLE IF NOT EXISTS choice_option
(
    id        UUID PRIMARY KEY,
    choice_id UUID,
    text      TEXT,
    position  INT
);

CREATE TABLE IF NOT EXISTS vote
(
    id        UUID PRIMARY KEY,
    choice_id UUID,
    option_id UUID,
    user_id   UUID,
    voted_at  TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS expert_application
(
    id           UUID PRIMARY KEY,
    user_id      UUID,
    motivation   TEXT,
    status       VARCHAR(20),
    submitted_at TIMESTAMP WITH TIME ZONE
                               );

CREATE TABLE IF NOT EXISTS expert_profile
(
    id           UUID PRIMARY KEY,
    user_id      UUID,
    is_incognito BOOLEAN,
    price        INTEGER,
    rating       REAL
);

CREATE TABLE IF NOT EXISTS stack
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(255),
    description TEXT,
    creator_id  UUID,
    is_quiz     BOOLEAN,
    published   BOOLEAN,
    created_at  TIMESTAMP WITH TIME ZONE
                              );

CREATE TABLE IF NOT EXISTS stack_item
(
    id        UUID PRIMARY KEY,
    stack_id  UUID,
    choice_id UUID,
    position  INT
);

CREATE TABLE IF NOT EXISTS session
(
    id              UUID PRIMARY KEY,
    jwt             TEXT,
    user_id         UUID,
    create_datetime TIMESTAMP WITH TIME ZONE,
    end_datetime    TIMESTAMP WITH TIME ZONE
);