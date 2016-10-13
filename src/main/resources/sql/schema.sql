CREATE TABLE discounts
(
  discount_id BIGINT PRIMARY KEY NOT NULL,
  discount_amount BIGINT NOT NULL
);
CREATE TABLE clients
(
  client_id BIGSERIAL PRIMARY KEY NOT NULL,
  surname VARCHAR(100) NOT NULL,
  phone CHAR(11) NOT NULL,
  email VARCHAR(100) NOT NULL,
  birth_date DATE,
  name VARCHAR(100) NOT NULL,
  male BOOLEAN NOT NULL,
  comment TEXT,
  car_number VARCHAR(9)
);
CREATE TABLE training_status
(
  status_id BIGINT PRIMARY KEY NOT NULL,
  status_name VARCHAR(50) NOT NULL
);
CREATE TABLE trainers
(
  trainer_id BIGINT PRIMARY KEY NOT NULL,
  trainer_name VARCHAR(100) NOT NULL
);
CREATE TABLE purchase
(
  purchase_id BIGSERIAL PRIMARY KEY NOT NULL,
  is_provided BOOLEAN DEFAULT false,
  client_id BIGINT NOT NULL,
  purchase_date DATE NOT NULL,
  product_id BIGINT NOT NULL,
  trainer_id BIGINT NOT NULL,
  expired BOOLEAN DEFAULT false NOT NULL,
  discount_id BIGINT,
  CONSTRAINT purchase_clients_client_id_fk FOREIGN KEY (client_id) REFERENCES clients (client_id),
  CONSTRAINT purchase_trainers_trainer_id_fk FOREIGN KEY (trainer_id) REFERENCES trainers (trainer_id),
  CONSTRAINT purchase_fk3 FOREIGN KEY (discount_id) REFERENCES discounts (discount_id)
);
CREATE INDEX purchase_fk3 ON purchase (discount_id);

CREATE TABLE users
(
  username VARCHAR(50) PRIMARY KEY NOT NULL,
  password VARCHAR(150) NOT NULL,
  trainer_id BIGINT,
  name VARCHAR(30),
  CONSTRAINT users_trainer_id_fkey FOREIGN KEY (trainer_id) REFERENCES trainers (trainer_id)
);
CREATE TABLE roles
(
  role_id BIGINT PRIMARY KEY NOT NULL,
  role_name VARCHAR(50)
);
CREATE TABLE user_roles
(
  username VARCHAR(50) NOT NULL,
  role_id BIGINT NOT NULL,
  CONSTRAINT user_to_roles2 FOREIGN KEY (username) REFERENCES users (username),
  CONSTRAINT user_to_roles1 FOREIGN KEY (role_id) REFERENCES roles (role_id)
);
CREATE TABLE payments
(
  payment_id BIGSERIAL PRIMARY KEY NOT NULL,
  payment_amount BIGINT NOT NULL,
  payment_date TIMESTAMP NOT NULL,
  purchase_id BIGINT,
  CONSTRAINT payments_purchase_purchase_id_fk FOREIGN KEY (purchase_id) REFERENCES purchase (purchase_id)
);
CREATE INDEX payments_fk0 ON payments (purchase_id);
CREATE TABLE trainings
(
  training_id BIGSERIAL PRIMARY KEY NOT NULL,
  training_plan BIGINT,
  client_id BIGINT NOT NULL,
  trainer_id BIGINT NOT NULL,
  purchase_id BIGINT,
  start TIMESTAMP NOT NULL,
  status_id BIGINT NOT NULL,
  comment TEXT,
  pass_for_auto BOOLEAN,
  CONSTRAINT trainings_ibfk_1 FOREIGN KEY (client_id) REFERENCES clients (client_id),
  CONSTRAINT trainings_trainers_trainer_id_fk FOREIGN KEY (trainer_id) REFERENCES trainers (trainer_id),
  CONSTRAINT trainings_purchase_purchase_id_fk FOREIGN KEY (purchase_id) REFERENCES purchase (purchase_id),
  CONSTRAINT trainings_status_id_fkey FOREIGN KEY (status_id) REFERENCES training_status (status_id)
);
CREATE TABLE user_action_log
(
  id BIGSERIAL PRIMARY KEY NOT NULL,
  currenttime TIMESTAMP NOT NULL,
  username VARCHAR(30) NOT NULL,
  action TEXT NOT NULL,
  success BOOLEAN NOT NULL,
  message TEXT
);

CREATE TABLE abon_type (
  id   INT PRIMARY KEY NOT NULL,
  type VARCHAR(30)     NOT NULL
);
CREATE TABLE products
(
  product_id   BIGINT                 NOT NULL PRIMARY KEY,
  product_name CHARACTER VARYING(100) NOT NULL,
  price        DOUBLE PRECISION       NOT NULL
);

CREATE TABLE abonements (
  training_count INT                                  NOT NULL,
  expire_days    INT,
  abon_type      INT REFERENCES abon_type (id) NOT NULL
)
  INHERITS (products);


CREATE TABLE clients_abonements (
  id             BIGSERIAL PRIMARY KEY NOT NULL,
  product_id     BIGINT                NOT NULL REFERENCES abonements (product_id),
  client_id      BIGINT                NOT NULL REFERENCES public.jf_clients (client_id),
  purchase_id    BIGINT                NOT NULL REFERENCES public.jf_purchase (purchase_id),
  training_count INT                   NOT NULL
);

CREATE TABLE clients_history (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  client_id BIGINT NOT NULL,
  surname VARCHAR(100) NOT NULL,
  phone VARCHAR(11) NOT NULL,
  email VARCHAR(100),
  birth_date DATE,
  name VARCHAR(100) NOT NULL,
  comment TEXT,
  male BOOLEAN,
  car_number VARCHAR(10),
  change_date TIMESTAMP NOT NULL
);
