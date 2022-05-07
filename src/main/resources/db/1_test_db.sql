PRAGMA journal_mode= WAL;
pragma synchronous = normal;
pragma temp_store = memory;
PRAGMA busy_timeout = 5000;

create table tickers
(
    symbol text primary key,
    name   text NOT NULL
);

CREATE VIRTUAL TABLE tickers_search USING fts5
(
    symbol,
    name,
);

create table tickers
(
    symbol primary key not null,
    name               not null,
    exchange           not null,
    exchangeName       not null,
    currency           not null,
    url
);