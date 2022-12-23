INSERT INTO app_finance.user_auth (u_email, u_password)
VALUES ('user1@gmail.com', 'password1');

INSERT INTO app_finance.user_auth (u_email, u_password)
VALUES ('user2@gmail.com', 'password2');

INSERT INTO app_finance.user_auth (u_email, u_password)
VALUES ('user3@gmail.com', 'password3');

INSERT INTO app_finance.user_auth (u_email, u_password)
VALUES ('user4@gmail.com', 'password4');

INSERT INTO app_finance.user_auth (u_email, u_password)
VALUES ('user5@gmail.com', 'password5');

INSERT INTO app_finance.user_auth (u_email, u_password)
VALUES ('user6@gmail.com', 'password6');

INSERT INTO app_finance."user" (u_id, u_nickname)
VALUES ((SELECT u_id FROM app_finance.user_auth WHERE app_finance.user_auth.u_email = 'user1@gmail.com'), 'user1');

INSERT INTO app_finance."user" (u_id, u_nickname)
VALUES ((SELECT u_id FROM app_finance.user_auth WHERE app_finance.user_auth.u_email = 'user2@gmail.com'), 'user2');

INSERT INTO app_finance."user" (u_id, u_nickname)
VALUES ((SELECT u_id FROM app_finance.user_auth WHERE app_finance.user_auth.u_email = 'user3@gmail.com'), 'user3');

INSERT INTO app_finance."user" (u_id, u_nickname)
VALUES ((SELECT u_id FROM app_finance.user_auth WHERE app_finance.user_auth.u_email = 'user4@gmail.com'), 'user4');

INSERT INTO app_finance."user" (u_id, u_nickname)
VALUES ((SELECT u_id FROM app_finance.user_auth WHERE app_finance.user_auth.u_email = 'user5@gmail.com'), 'user5');

INSERT INTO app_finance."user" (u_id, u_nickname)
VALUES ((SELECT u_id FROM app_finance.user_auth WHERE app_finance.user_auth.u_email = 'user6@gmail.com'), 'user6');

INSERT INTO app_finance.list (l_u_id, l_name)
VALUES ((SELECT u_id FROM app_finance."user" WHERE app_finance."user".u_nickname = 'user1'), 'DEFAULT');

INSERT INTO app_finance.list (l_u_id, l_name)
VALUES ((SELECT u_id FROM app_finance."user" WHERE app_finance."user".u_nickname = 'user2'), 'DEFAULT');

INSERT INTO app_finance.list (l_u_id, l_name)
VALUES ((SELECT u_id FROM app_finance."user" WHERE app_finance."user".u_nickname = 'user3'), 'DEFAULT');

INSERT INTO app_finance.list (l_u_id, l_name)
VALUES ((SELECT u_id FROM app_finance."user" WHERE app_finance."user".u_nickname = 'user4'), 'DEFAULT');

INSERT INTO app_finance.list (l_u_id, l_name)
VALUES ((SELECT u_id FROM app_finance."user" WHERE app_finance."user".u_nickname = 'user5'), 'DEFAULT');

INSERT INTO app_finance.list (l_u_id, l_name)
VALUES ((SELECT u_id FROM app_finance."user" WHERE app_finance."user".u_nickname = 'user6'), 'DEFAULT');

INSERT INTO app_finance.purchase (p_l_id, p_name, p_coins, p_currency, p_date)
VALUES ((SELECT l.l_id
         FROM app_finance.list as l,
              app_finance."user" as u
         WHERE l.l_u_id = u.u_id
           and u.u_nickname = 'user1'
           and l.l_name = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24');

INSERT INTO app_finance.purchase (p_l_id, p_name, p_coins, p_currency, p_date)
VALUES ((SELECT l.l_id
         FROM app_finance.list as l,
              app_finance."user" as u
         WHERE l.l_u_id = u.u_id
           and u.u_nickname = 'user2'
           and l.l_name = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24');

INSERT INTO app_finance.purchase (p_l_id, p_name, p_coins, p_currency, p_date)
VALUES ((SELECT l.l_id
         FROM app_finance.list as l,
              app_finance."user" as u
         WHERE l.l_u_id = u.u_id
           and u.u_nickname = 'user3'
           and l.l_name = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24');

INSERT INTO app_finance.purchase (p_l_id, p_name, p_coins, p_currency, p_date)
VALUES ((SELECT l.l_id
         FROM app_finance.list as l,
              app_finance."user" as u
         WHERE l.l_u_id = u.u_id
           and u.u_nickname = 'user4'
           and l.l_name = 'DEFAULT'), 'Vegetables', 100, 'UAH', '2022-02-24');
