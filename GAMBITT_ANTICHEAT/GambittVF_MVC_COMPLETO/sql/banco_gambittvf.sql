CREATE DATABASE IF NOT EXISTS gambittvf;
USE gambittvf;

CREATE TABLE IF NOT EXISTS jogador (
    id INT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL,
    banido BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS processo (
    pid INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    acessando_memoria BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS alerta (
    id_alerta INT AUTO_INCREMENT PRIMARY KEY,
    jogador_id INT NOT NULL,
    processo_pid INT NOT NULL,
    gravidade VARCHAR(100) NOT NULL,
    data_alerta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (jogador_id) REFERENCES jogador(id),
    FOREIGN KEY (processo_pid) REFERENCES processo(pid)
);
