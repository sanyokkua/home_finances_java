INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user1@gmail.com', 'password1', 'user1', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user2@gmail.com', 'password2', 'user2', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user3@gmail.com', 'password3', 'user3', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user4@gmail.com', 'password4', 'user4', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user5@gmail.com', 'password5', 'user5', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user6@gmail.com', 'password6', 'user6', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user7@gmail.com', 'password7', 'user7', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user8@gmail.com', 'password8', 'user8', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user9@gmail.com', 'password9', 'user9', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user10@gmail.com', 'password10', 'user10', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user11@gmail.com', 'password11', 'user11', 'USER', TRUE);
INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
VALUES ('user12@gmail.com', 'password12', 'user12', 'USER', TRUE);

INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user1'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user2'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user3'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user4'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user5'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user6'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user7'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user8'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user9'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user10'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user11'), 'DEFAULT');
INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
VALUES ((SELECT "u_id"
         FROM "app_finance"."application_user"
         WHERE "app_finance"."application_user"."u_nickname" = 'user12'), 'DEFAULT');

INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user1'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user2'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user3'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user4'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user5'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user6'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user7'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user8'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user9'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user10'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user11'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');
INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category")
VALUES ((SELECT "l"."l_id"
         FROM "app_finance"."purchase_list" AS "l",
              "app_finance"."application_user" AS "u"
         WHERE "l"."l_u_id" = "u"."u_id"
           AND "u"."u_nickname" = 'user12'
           AND "l"."l_name" = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24', 'default');