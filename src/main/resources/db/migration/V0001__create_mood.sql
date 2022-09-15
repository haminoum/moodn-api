CREATE TABLE mood
(
    id          serial primary key,
    external_id text not null,
    type        text not null,
    created_at  timestamptz not null
);
