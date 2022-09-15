CREATE TABLE user
(
    id          serial primary key,
    external_id text not null,
    username    text not null
);
--
-- alter table mood
--     ADD COLUMN user int not null REFERENCES user (id)
