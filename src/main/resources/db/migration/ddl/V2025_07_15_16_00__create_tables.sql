CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS role (
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50)  NOT NULL UNIQUE
);

CREATE TABLE channel_user (
    id          UUID PRIMARY KEY,
    channel_id  UUID NOT NULL REFERENCES channel(id) ON DELETE CASCADE,
    user_id     UUID NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    role_id     UUID NOT NULL REFERENCES role(id)   ON DELETE RESTRICT,
    CONSTRAINT uk_channel_user UNIQUE (channel_id, user_id)
);

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'payment_status') THEN
CREATE TYPE payment_status AS ENUM
    ('PENDING', 'AWAITING_CONFIRMATION', 'PAID', 'CANCELLED');
END IF;
END$$;

CREATE TABLE IF NOT EXISTS payment (
    id            UUID PRIMARY KEY,
    amount        NUMERIC(19,2)  NOT NULL CHECK (amount > 0),
    currency      VARCHAR(3)        NOT NULL DEFAULT 'RUB',
    status        payment_status NOT NULL DEFAULT 'PENDING',
    description   TEXT,
    client_email  VARCHAR(255),
    metadata      JSONB,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ,
    paid_at       TIMESTAMPTZ,
    cancelled_at  TIMESTAMPTZ
);

INSERT INTO role(id, name, code) VALUES
    (uuid_generate_v4(), 'Читатель', 'reader'),
    (uuid_generate_v4(), 'Модератор','moder' ),
    (uuid_generate_v4(), 'Эксперт',  'expert')
ON CONFLICT (code) DO UPDATE SET name = EXCLUDED.name;

ALTER TABLE channel
ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE;