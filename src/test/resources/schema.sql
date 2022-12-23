CREATE SCHEMA IF NOT EXISTS "app_finance";

CREATE TABLE IF NOT EXISTS "app_finance"."user_auth"
(
    "u_id"       SERIAL UNIQUE PRIMARY KEY NOT NULL,
    "u_email"    VARCHAR UNIQUE            NOT NULL,
    "u_password" VARCHAR                   NOT NULL
);

CREATE TABLE IF NOT EXISTS "app_finance"."user"
(
    "u_id"       BIGINT PRIMARY KEY NOT NULL,
    "u_nickname" VARCHAR UNIQUE     NOT NULL
);

CREATE TABLE IF NOT EXISTS "app_finance"."list"
(
    "l_id"   SERIAL UNIQUE PRIMARY KEY NOT NULL,
    "l_u_id" INT                       NOT NULL,
    "l_name" VARCHAR                   NOT NULL
);

CREATE TABLE IF NOT EXISTS "app_finance"."purchase"
(
    "p_id"       SERIAL UNIQUE PRIMARY KEY NOT NULL,
    "p_l_id"     INT                       NOT NULL,
    "p_name"     VARCHAR                   NOT NULL,
    "p_coins"    INT                       NOT NULL,
    "p_currency" VARCHAR                   NOT NULL,
    "p_date"     DATE                      NOT NULL
);

CREATE INDEX ON "app_finance"."user_auth" ("u_id");

CREATE INDEX ON "app_finance"."user_auth" ("u_email");

CREATE INDEX ON "app_finance"."user" ("u_id");

CREATE INDEX ON "app_finance"."user" ("u_nickname");

CREATE INDEX ON "app_finance"."list" ("l_id");

CREATE INDEX ON "app_finance"."list" ("l_u_id", "l_name");

CREATE INDEX ON "app_finance"."purchase" ("p_id");

CREATE INDEX ON "app_finance"."purchase" ("p_name");

ALTER TABLE "app_finance"."user"
    ADD FOREIGN KEY ("u_id") REFERENCES "app_finance"."user_auth" ("u_id") ON DELETE CASCADE;

ALTER TABLE "app_finance"."list"
    ADD FOREIGN KEY ("l_u_id") REFERENCES "app_finance"."user" ("u_id") ON DELETE CASCADE;

ALTER TABLE "app_finance"."purchase"
    ADD FOREIGN KEY ("p_l_id") REFERENCES "app_finance"."list" ("l_id") ON DELETE CASCADE;