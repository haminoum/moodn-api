CREATE TABLE comment
(
    id          SERIAL PRIMARY KEY,
    external_id TEXT        NOT NULL,
    created_at  timestamptz NOT NULL,
    updated_at  timestamptz,
    author      INT REFERENCES moodn_user (id),
    content     TEXT
);


--     val type = enum<MoodType>("type")

--     val comment = int("comment")
