CREATE TABLE moodn_user
(
    id          SERIAL PRIMARY KEY,
    external_id TEXT NOT NULL,
    username    TEXT NOT NULL,
    created_at timestamptz not null
);

CREATE UNIQUE INDEX moodn_user_external_id ON moodn_user(external_id);
CREATE UNIQUE INDEX moodn_user_external_id_username_idx ON moodn_user(external_id, username);

-- Apply changes to mood table
ALTER TABLE  mood
    ADD COLUMN moodn_user INT REFERENCES moodn_user (id);

UPDATE mood
SET moodn_user = 0 WHERE moodn_user IS NULL;
ALTER TABLE mood
    ALTER COLUMN moodn_user SET NOT NULL;