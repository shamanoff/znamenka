INSERT INTO jf_clients VALUES (1, 'Николай Петренко');
INSERT INTO jf_clients VALUES (2, 'Алексей Петренко');

INSERT INTO JF_products (product_id, product_name, expire_days, price) VALUES (1, 'prob', 1, 950);

INSERT INTO JF_purchase (purchase_id, is_provided, client_id, purchase_date, product_id, trainer_id, expired) VALUES (1,0, 1, '2016-07-14', 1, 1, 0);

INSERT INTO JF_trainers (trainer_id, trainer_name) VALUES (1,'trainer1');
INSERT INTO JF_trainers (trainer_name) VALUES ('trainer2');

INSERT INTO JF_trainings (training_id, training_plan, client_id, trainer_id, purchase_id) VALUES (1, 1, 1, 1, 1);
INSERT INTO JF_trainings (training_id, training_plan, client_id, trainer_id, purchase_id) VALUES (2, 2, 2, 2, 2);