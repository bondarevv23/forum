create table if not exists user_tab (
    id bigserial primary key,
    nickname varchar(255) not null,
    email varchar(255) not null,
    registered_at timestamp(6) not null
);

create table if not exists post_tab (
    id bigserial primary key,
    title varchar(255) not null,
    text text not null,
    author_id bigint references user_tab(id) on delete cascade not null ,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);
