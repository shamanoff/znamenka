INSERT INTO jf_clients VALUES (1, 'Николай Петренко');
INSERT INTO jf_clients VALUES (2, 'Алексей Петренко');
INSERT INTO jf_clients VALUES (3, 'Максим Петренко');

INSERT INTO JF_products (product_id, product_name, expire_days, price) VALUES (1, 'prob', 1, 950);
INSERT INTO JF_products (product_id, product_name, expire_days, price) VALUES (2, 'training', 1, 2550);

INSERT INTO JF_purchase (purchase_id, is_provided, client_id, purchase_date, product_id, trainer_id, expired) VALUES (1,0, 1, '2017-07-14', 1, 1, 0);
INSERT INTO JF_purchase (purchase_id, is_provided, client_id, purchase_date, product_id, trainer_id, expired) VALUES (2,0, 1, '2017-07-15', 2, 1, 0);
INSERT INTO JF_purchase (purchase_id, is_provided, client_id, purchase_date, product_id, trainer_id, expired) VALUES (3,0, 1, '2018-07-13', 2, 1, 0);
INSERT INTO JF_purchase (purchase_id, is_provided, client_id, purchase_date, product_id, trainer_id, expired) VALUES (4,0, 2, '2017-07-15', 1, 1, 0);
INSERT INTO JF_purchase (purchase_id, is_provided, client_id, purchase_date, product_id, trainer_id, expired) VALUES (5,0, 3, '2017-07-15', 1, 1, 0);
INSERT INTO JF_purchase (purchase_id, is_provided, client_id, purchase_date, product_id, trainer_id, expired) VALUES (6,0, 3, '2017-09-21', 2, 1, 0);

INSERT INTO JF_trainers (trainer_id, trainer_name) VALUES (1,'trainer1');
INSERT INTO JF_trainers (trainer_name) VALUES ('trainer2');

INSERT INTO JF_trainings (training_id, training_plan, client_id, trainer_id, purchase_id) VALUES (1, 1, 1, 1, 1);
