CREATE SCHEMA exp
  AUTHORIZATION postgres;

COMMENT ON SCHEMA exp
IS 'для экпериментов';

CREATE TABLE exp.abon_type (
  id   INT PRIMARY KEY NOT NULL,
  type VARCHAR(30)     NOT NULL
);
CREATE TABLE exp.products
(
  product_id   BIGINT                 NOT NULL PRIMARY KEY,
  product_name CHARACTER VARYING(100) NOT NULL,
  price        DOUBLE PRECISION       NOT NULL
);

CREATE TABLE exp.abonements (
  training_count INT                                  NOT NULL,
  expire_days    INT,
  abon_type      INT REFERENCES exp.abon_type (id) NOT NULL
)
  INHERITS (exp.products);


CREATE TABLE exp.clients_abonements (
  id             BIGSERIAL PRIMARY KEY NOT NULL,
  product_id     BIGINT                NOT NULL REFERENCES exp.abonements (product_id),
  client_id      BIGINT                NOT NULL REFERENCES public.jf_clients (client_id),
  purchase_id    BIGINT                NOT NULL REFERENCES public.jf_purchase (purchase_id),
  training_count INT                   NOT NULL
);

CREATE OR REPLACE FUNCTION ins_abon_for_client_after_purchase()
  RETURNS TRIGGER AS '
DECLARE t_count INT;
BEGIN
  IF (SELECT count(1)
      FROM exp.abonements a
      WHERE a.product_id = new.product_id) > 0
  THEN t_count := (SELECT training_count
                   FROM exp.abonements a
                   WHERE a.product_id = new.product_id);
    INSERT INTO exp.clients_abonements (product_id, client_id, training_count, purchase_id)
    VALUES (new.product_id, new.client_id, t_count, new.purchase_id);
  END IF;
  RETURN new;
END;'
LANGUAGE plpgsql;

CREATE TRIGGER tr_ins_abon_for_client
AFTER INSERT ON public.jf_purchase
FOR EACH ROW EXECUTE PROCEDURE ins_abon_for_client_after_purchase();

CREATE OR REPLACE FUNCTION decrement_training_for_client_abon()
  RETURNS TRIGGER AS '
DECLARE prod_id BIGINT;
        ca_id   BIGINT;
        t_count INT;
BEGIN
  prod_id := (SELECT min(product_id)
              FROM jf_purchase p
              WHERE p.purchase_id = old.purchase_id);
  ca_id:= (SELECT min(id)
           FROM exp.clients_abonements ca
           WHERE ca.client_id = old.client_id AND ca.product_id = prod_id AND ca.purchase_id = new.purchase_id);
  t_count:=(SELECT training_count
            FROM exp.clients_abonements
            WHERE id = ca_id);
  IF new.status_id IN (2, 3) AND t_count > 0 AND old.status_id IN (1, 4)
  THEN
    UPDATE exp.clients_abonements
    SET training_count = training_count - 1
    WHERE id = ca_id;
  END IF;
  IF old.status_id IN (2, 3)
  THEN RETURN old;
  END IF;
  RETURN new;
END;'
LANGUAGE plpgsql;

CREATE TRIGGER tr_upd_for_decrement_training_count BEFORE UPDATE ON jf_trainings
FOR EACH ROW EXECUTE PROCEDURE decrement_training_for_client_abon();
CREATE TABLE exp.abon_type (
  id   INT PRIMARY KEY NOT NULL,
  type VARCHAR(30)     NOT NULL
);
CREATE TABLE exp.products
(
  product_id   BIGINT                 NOT NULL PRIMARY KEY,
  product_name CHARACTER VARYING(100) NOT NULL,
  price        DOUBLE PRECISION       NOT NULL
);

CREATE TABLE exp.abonements (
  training_count INT                                  NOT NULL,
  expire_days    INT,
  abon_type      INT REFERENCES exp.abon_type (id) NOT NULL
)
  INHERITS (exp.products);


CREATE TABLE exp.clients_abonements (
  id             BIGSERIAL PRIMARY KEY NOT NULL,
  product_id     BIGINT                NOT NULL REFERENCES exp.abonements (product_id),
  client_id      BIGINT                NOT NULL REFERENCES public.jf_clients (client_id),
  purchase_id    BIGINT                NOT NULL REFERENCES public.jf_purchase (purchase_id),
  training_count INT                   NOT NULL
);

CREATE OR REPLACE FUNCTION ins_abon_for_client_after_purchase()
  RETURNS TRIGGER AS '
DECLARE t_count INT;
BEGIN
  IF (SELECT count(1)
      FROM exp.abonements a
      WHERE a.product_id = new.product_id) > 0
  THEN t_count := (SELECT training_count
                   FROM exp.abonements a
                   WHERE a.product_id = new.product_id);
    INSERT INTO exp.clients_abonements (product_id, client_id, training_count, purchase_id)
    VALUES (new.product_id, new.client_id, t_count, new.purchase_id);
  END IF;
  RETURN new;
END;'
LANGUAGE plpgsql;

CREATE TRIGGER tr_ins_abon_for_client
AFTER INSERT ON public.jf_purchase
FOR EACH ROW EXECUTE PROCEDURE ins_abon_for_client_after_purchase();

CREATE OR REPLACE FUNCTION decrement_training_for_client_abon()
  RETURNS TRIGGER AS '
DECLARE prod_id BIGINT;
        ca_id   BIGINT;
        t_count INT;
BEGIN
  prod_id := (SELECT min(product_id)
              FROM jf_purchase p
              WHERE p.purchase_id = old.purchase_id);
  ca_id:= (SELECT min(id)
           FROM exp.clients_abonements ca
           WHERE ca.client_id = old.client_id AND ca.product_id = prod_id AND ca.purchase_id = new.purchase_id);
  t_count:=(SELECT training_count
            FROM exp.clients_abonements
            WHERE id = ca_id);
  IF new.status_id IN (2, 3) AND t_count > 0 AND old.status_id IN (1, 4)
  THEN
    UPDATE exp.clients_abonements
    SET training_count = training_count - 1
    WHERE id = ca_id;
  END IF;
  IF old.status_id IN (2, 3)
  THEN RETURN old;
  END IF;
  RETURN new;
END;'
LANGUAGE plpgsql;

CREATE TRIGGER tr_upd_for_decrement_training_count BEFORE UPDATE ON jf_trainings
FOR EACH ROW EXECUTE PROCEDURE decrement_training_for_client_abon();

CREATE TABLE exp.clients_history (
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

CREATE OR REPLACE FUNCTION upd_client()
  RETURNS TRIGGER AS '
BEGIN
  INSERT INTO exp.clients_history(client_id, surname, phone, email, birth_date, name, comment, male, car_number, change_date)
  VALUES (old.client_id, old.surname, old.phone, old.email, old.birth_date, old.name, old.comment, old.male, old.car_number, CURRENT_TIMESTAMP);
  RETURN new;
END;'
LANGUAGE plpgsql;

CREATE TRIGGER tr_upd_client
AFTER UPDATE OR DELETE ON public.jf_clients
FOR EACH ROW EXECUTE PROCEDURE upd_client();