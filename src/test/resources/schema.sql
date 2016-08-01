DROP TABLE IF EXISTS Jf_trainings;
DROP TABLE IF EXISTS JF_Clients;
DROP TABLE IF EXISTS JF_Trainers;

CREATE TABLE JF_Trainers (
  trainer_id   BIGINT       NOT NULL PRIMARY KEY IDENTITY,
  trainer_name VARCHAR(100) NOT NULL
);

CREATE TABLE JF_Clients (
  client_id   BIGINT       NOT NULL PRIMARY KEY IDENTITY,
  client_name VARCHAR(100) NOT NULL
);

CREATE TABLE Jf_trainings (
  training_id   BIGINT NOT NULL PRIMARY KEY IDENTITY,
  training_plan BIGINT NOT NULL,
  client_id     BIGINT NOT NULL,
  trainer_id    BIGINT NOT NULL,
  purchase_id   BIGINT NOT NULL,
  FOREIGN KEY (trainer_id) REFERENCES JF_Trainers (trainer_id),
  FOREIGN KEY (client_id) REFERENCES JF_Clients (client_id)
);