CREATE TABLE mood
(
    id          SERIAL PRIMARY KEY,
    external_id TEXT        NOT NULL,
    type        TEXT        NOT NULL,
    created_at  timestamptz NOT NULL,
    updated_at  timestamptz,
    comment     TEXT
);
