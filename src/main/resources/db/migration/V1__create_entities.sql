CREATE TABLE "user"
(
    id         UUID         NOT NULL,
    phone      VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    image      BYTEA,
    birthday   TIMESTAMP WITHOUT TIME ZONE,
    language   VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,

    login      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uc_user_login UNIQUE (login)
);

CREATE TABLE channel
(
    id             UUID         NOT NULL,
    name           VARCHAR(255) NOT NULL,
    description    VARCHAR(255),
    is_private     BOOLEAN,
    password       VARCHAR(255),
    age_restricted BOOLEAN      NOT NULL,
    owner_id       UUID,
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_channel PRIMARY KEY (id),
    CONSTRAINT FK_CHANNEL_ON_OWNER FOREIGN KEY (owner_id) REFERENCES "user" (id)
);

CREATE TABLE "group"
(
    id         UUID NOT NULL,
    name       VARCHAR(255),
    owner_id   UUID,
    parent_id  UUID,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_group PRIMARY KEY (id),
    CONSTRAINT FK_GROUP_ON_OWNER FOREIGN KEY (owner_id) REFERENCES "user" (id),
    CONSTRAINT FK_GROUP_ON_PARENT FOREIGN KEY (parent_id) REFERENCES "group" (id)
);

CREATE TABLE group_user(
                           group_id   UUID REFERENCES "group"(id),
                           user_id    UUID REFERENCES "user"(id),
                           CONSTRAINT pk_group_user PRIMARY KEY (group_id,user_id)
);

CREATE TABLE choice
(
    id          UUID NOT NULL,
    creator_id  UUID,
    channel_id  UUID,
    title       VARCHAR(300),
    description TEXT,
    image       BYTEA,
    is_personal BOOLEAN,
    status      VARCHAR(255),
    deadline    TIMESTAMP WITHOUT TIME ZONE,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_choice PRIMARY KEY (id),
    CONSTRAINT FK_CHOICE_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES "user" (id),
    CONSTRAINT FK_CHOICE_ON_CHANNEL FOREIGN KEY (channel_id) REFERENCES channel (id)
);

CREATE TABLE choice_option(
                              id          UUID NOT NULL,
                              choice_id   UUID NOT NULL,
                              text        VARCHAR(255),
                              position    INT,
                              CONSTRAINT pk_choice_option PRIMARY KEY (id),
                              CONSTRAINT FK_CHOICE_OPTION_ON_CHOICE FOREIGN KEY (choice_id) REFERENCES choice (id)
);

CREATE TABLE vote
(
    id        UUID NOT NULL,
    choice_id UUID,
    option_id UUID,
    user_id   UUID,
    voted_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_vote PRIMARY KEY (id),
    CONSTRAINT FK_VOTE_ON_CHOICE FOREIGN KEY (choice_id) REFERENCES choice (id),
    CONSTRAINT FK_VOTE_ON_OPTION FOREIGN KEY (option_id) REFERENCES choice_option (id),
    CONSTRAINT FK_VOTE_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE expert_application(
                                   id          UUID NOT NULL,
                                   user_id     UUID,
                                   motivation  VARCHAR(255),
                                   status      VARCHAR(255),
                                   submittedAt TIMESTAMP WITH TIME ZONE,
                                   CONSTRAINT pk_expert_application PRIMARY KEY (id),
                                   CONSTRAINT FK_EXPERT_APPLICATION_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE expert_profile
(
    id           UUID NOT NULL,
    user_id      UUID,
    is_incognito BOOLEAN,
    price        INTEGER,
    rating       FLOAT,
    CONSTRAINT pk_expert_profile PRIMARY KEY (id),
    CONSTRAINT FK_EXPERT_PROFILE_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE stack
(
    id          UUID NOT NULL,
    title       VARCHAR(255),
    description TEXT,
    creator_id  UUID,
    is_quiz     BOOLEAN,
    published   BOOLEAN,
    created_at  TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_stack PRIMARY KEY (id),
    CONSTRAINT FK_STACK_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES "user" (id)
);

CREATE TABLE stack_item
(
    id        UUID NOT NULL,
    stack_id  UUID,
    choice_id UUID,
    position  INTEGER,
    CONSTRAINT pk_stack_item PRIMARY KEY (id),
    CONSTRAINT FK_STACK_ITEM_ON_STACK FOREIGN KEY (stack_id) REFERENCES stack (id),
    CONSTRAINT FK_STACK_ITEM_ON_CHOICE FOREIGN KEY (choice_id) REFERENCES choice (id)
);
