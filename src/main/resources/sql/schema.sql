DROP TABLE IF EXISTS Jf_trainings;
DROP TABLE IF EXISTS JF_training_status;
DROP TABLE IF EXISTS JF_Clients;
DROP TABLE IF EXISTS JF_payments;
DROP TABLE IF EXISTS JF_user_roles;
DROP TABLE IF EXISTS JF_roles;
DROP TABLE IF EXISTS JF_users;
DROP TABLE IF EXISTS JF_Trainers;
DROP TABLE IF EXISTS JF_products;
DROP TABLE IF EXISTS JF_purchase;
DROP TABLE IF EXISTS JF_discounts;
DROP INDEX IF EXISTS JF_purchase_fk3;
DROP INDEX IF EXISTS JF_payments_fk0;
DROP TABLE IF EXISTS JF_duty_plan_type;
DROP TABLE IF EXISTS JF_duty_shedule;
DROP TABLE IF EXISTS JF_training_plan;
DROP TABLE IF EXISTS JF_exercises;


CREATE TABLE JF_Trainers (
  trainer_id   BIGSERIAL    NOT NULL PRIMARY KEY,
  trainer_name VARCHAR(100) NOT NULL
);

CREATE TABLE JF_clients
(
  client_id  BIGSERIAL PRIMARY KEY NOT NULL,
  surname    VARCHAR(100)          NOT NULL,
  phone      INT                   NOT NULL,
  email      VARCHAR(100)          NOT NULL,
  birth_date DATE,
  name       VARCHAR(100)          NOT NULL
);

CREATE TABLE JF_products
(
  product_id   BIGSERIAL PRIMARY KEY NOT NULL,
  product_name VARCHAR(100)          NOT NULL,
  expire_days  INT                   NOT NULL,
  price        DOUBLE PRECISION      NOT NULL
);


CREATE TABLE JF_discounts
(
  discount_id     BIGSERIAL PRIMARY KEY NOT NULL,
  discount_amount BIGINT                NOT NULL
);

CREATE TABLE JF_purchase
(
  purchase_id   BIGSERIAL PRIMARY KEY NOT NULL,
  is_provided   BOOLEAN DEFAULT FALSE,
  client_id     BIGINT                NOT NULL,
  purchase_date DATE                  NOT NULL,
  product_id    BIGINT                NOT NULL,
  trainer_id    BIGINT                NOT NULL,
  expired       BOOLEAN DEFAULT FALSE,
  discount_id   BIGINT,
  CONSTRAINT JF_purchase_fk3 FOREIGN KEY (discount_id) REFERENCES JF_discounts (discount_id)
);
CREATE INDEX JF_purchase_fk3
  ON JF_purchase (discount_id);

CREATE TABLE JF_payments
(
  payment_id     BIGSERIAL PRIMARY KEY NOT NULL,
  payment_amount BIGINT                NOT NULL,
  payment_date   TIMESTAMP             NOT NULL,
  purchase_id    BIGINT,
  CONSTRAINT JF_payments_fk0 FOREIGN KEY (purchase_id) REFERENCES JF_purchase (purchase_id)
);
CREATE INDEX JF_payments_fk0
  ON JF_payments (purchase_id);

CREATE TABLE JF_training_status (
  status_id   BIGSERIAL PRIMARY KEY NOT NULL,
  status_name VARCHAR(50)           NOT NULL
);

CREATE TABLE JF_trainings
(
  training_id   BIGSERIAL PRIMARY KEY NOT NULL,
  training_plan BIGINT,
  client_id     BIGINT                NOT NULL,
  trainer_id    BIGINT                NOT NULL,
  purchase_id   BIGINT,
  start         TIMESTAMP             NOT NULL,
  status_id     BIGINT                NOT NULL REFERENCES JF_training_status (status_id),
  CONSTRAINT JF_trainings_ibfk_1 FOREIGN KEY (client_id) REFERENCES JF_clients (client_id),
  CONSTRAINT JF_trainings_ibfk_2 FOREIGN KEY (purchase_id) REFERENCES JF_purchase (purchase_id)
);

CREATE TABLE JF_training_plan
(
  id_training_plan   BIGINT PRIMARY KEY NOT NULL,
  repetitions_amount INT                NOT NULL,
  exercise_id        BIGINT             NOT NULL
);

CREATE TABLE JF_exercises
(
  exercise_id BIGSERIAL PRIMARY KEY NOT NULL,
  description VARCHAR(100)          NOT NULL,
  name        VARCHAR(100)          NOT NULL
);

CREATE TABLE JF_duty_plan_type
(
  duty_plan_type_id BIGINT NOT NULL,
  duty_plan_name    BIGINT NOT NULL
);

CREATE TABLE JF_duty_shedule
(
  duty_id        BIGSERIAL PRIMARY KEY NOT NULL,
  trainer_id     BIGINT                NOT NULL,
  datetime_start TIMESTAMP             NOT NULL,
  datetime_end   TIMESTAMP             NOT NULL,
  duty_plan_type BOOLEAN               NOT NULL
);

CREATE TABLE JF_roles (
  role_id   BIGSERIAL PRIMARY KEY NOT NULL,
  role_name VARCHAR(50)
);


CREATE TABLE JF_users (
  username   VARCHAR(50) PRIMARY KEY NOT NULL,
  password   VARCHAR(150)            NOT NULL,
  trainer_id BIGINT REFERENCES JF_Trainers (trainer_id)
);

CREATE TABLE JF_user_roles (
  username VARCHAR(50) NOT NULL,
  role_id  BIGINT      NOT NULL,
  CONSTRAINT JF_user_to_roles1 FOREIGN KEY (role_id) REFERENCES JF_roles (role_id),
  CONSTRAINT JF_user_to_roles2 FOREIGN KEY (username) REFERENCES JF_users (username)
)


