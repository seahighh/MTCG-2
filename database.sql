create table public.messages
(
    id      serial
        primary key,
    message varchar(255)
);

alter table public.messages
    owner to swe1user;

create table public.users
(
    id            serial
        primary key,
    username      varchar(255)       not null,
    status        varchar(255),
    password      varchar(255)       not null,
    token         varchar(255),
    coins         integer default 20 not null,
    total_battles integer default 0,
    won_battles   integer default 0,
    lost_battles  integer default 0,
    elo           integer default 100,
    call_name     varchar(255),
    bio           varchar(255),
    image         varchar(255),
    rank          varchar(255)
);

alter table public.users
    owner to swe1user;

create table public.packages
(
    id    serial
        primary key,
    price integer default 5 not null,
    legal integer
);

alter table public.packages
    owner to swe1user;

create table public.cards
(
    id           varchar(255)  not null
        primary key,
    name         varchar(255)  not null,
    damage       numeric(6, 2) not null,
    element_type varchar(255),
    card_type    varchar(255),
    package_id   integer
        constraint fk_package
            references public.packages,
    user_id      integer
        constraint fk_user
            references public.users,
    in_deck      boolean default false,
    is_locked    boolean default false
);

alter table public.cards
    owner to swe1user;

create table public.battles
(
    id          serial
        primary key,
    player_a    varchar(255),
    player_b    varchar(255),
    winner      integer,
    card_1      varchar(255),
    card_2      varchar(255),
    winner_card varchar(255)
);

alter table public.battles
    owner to swe1user;

