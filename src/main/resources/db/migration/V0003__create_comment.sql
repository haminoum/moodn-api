CREATE TABLE comment
(
    id          SERIAL PRIMARY KEY,
    external_id TEXT        NOT NULL,
    created_at  timestamptz NOT NULL,
    updated_at  timestamptz,
    mood      INT REFERENCES mood (id),
    content     TEXT
);
