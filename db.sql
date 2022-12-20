create schema if not exists app_finance;

create table if not exists purchase
(
    p_id       serial       not null,
    p_name     varchar(255) not null,
    p_coins    int          not null,
    p_currency varchar(255) not null,
    p_date     date         not null,
    primary key (p_id)
);
