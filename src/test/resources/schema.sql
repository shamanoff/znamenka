DROP TABLE IF EXISTS Jf_trainings;
DROP TABLE IF EXISTS JF_Clients;
DROP TABLE IF EXISTS JF_Trainers;
DROP TABLE if EXISTS JF_products;
DROP TABLE IF EXISTS JF_purchase;

CREATE TABLE JF_Trainers (
  trainer_id   BIGINT       NOT NULL PRIMARY KEY IDENTITY,
  trainer_name VARCHAR(100) NOT NULL
);

CREATE TABLE JF_Clients (
  client_id   BIGINT       NOT NULL PRIMARY KEY IDENTITY,
  client_name VARCHAR(100) NOT NULL
);

CREATE TABLE JF_products
(
  product_id BIGINT PRIMARY KEY NOT NULL IDENTITY ,
  product_name VARCHAR(100) NOT NULL,
  expire_days INT NOT NULL,
  price DOUBLE NOT NULL
);

CREATE TABLE JF_purchase
(
  purchase_id BIGINT PRIMARY KEY NOT NULL IDENTITY ,
  is_provided TINYINT NOT NULL,
  client_id BIGINT NOT NULL,
  purchase_date DATE NOT NULL,
  product_id BIGINT NOT NULL,
  trainer_id BIGINT NOT NULL,
  expired TINYINT NOT NULL
);


CREATE TABLE JF_trainings
(
  training_id BIGINT PRIMARY KEY NOT NULL,
  training_plan BIGINT NOT NULL,
  client_id BIGINT NOT NULL,
  trainer_id BIGINT NOT NULL,
  purchase_id BIGINT NOT NULL,
  CONSTRAINT JF_trainings_ibfk_1 FOREIGN KEY (client_id) REFERENCES JF_clients (client_id)
);


