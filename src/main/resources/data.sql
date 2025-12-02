CREATE TABLE vertices (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE edges (
    id INT AUTO_INCREMENT PRIMARY KEY,
    origin_id VARCHAR(10) NOT NULL,
    dest_id VARCHAR(10) NOT NULL,
    risk INT NOT NULL,
    distance INT NOT NULL,
    FOREIGN KEY (origin_id) REFERENCES vertices(id),
    FOREIGN KEY (dest_id) REFERENCES vertices(id)
);

INSERT INTO vertices (id, name) VALUES
('AG-01', 'Agencia Central'),
('AG-02', 'Agencia Norte'),
('AG-03', 'Agencia Sul');

INSERT INTO vertices (id, name) VALUES
('ATM-01', 'Shopping Plaza ATM'),
('ATM-02', 'Posto Shell ATM'),
('ATM-03', 'Hospital Central ATM'),
('ATM-04', 'Aeroporto Internacional ATM'),
('ATM-05', 'Universidade Federal ATM'),
('ATM-06', 'Terminal Rodoviario ATM'),
('ATM-07', 'Praia de Iracema ATM');

INSERT INTO vertices (id, name) VALUES
('CRZ-01', 'Cruzamento A - Centro'),
('CRZ-02', 'Cruzamento B - Av. Principal'),
('CRZ-03', 'Cruzamento C - Zona Industrial'),
('CRZ-04', 'Cruzamento D - Bairro Residencial');

INSERT INTO vertices (id, name) VALUES
('CD-01', 'Centro de Distribuicao Principal'),
('CD-02', 'Centro de Distribuicao Secundario');

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('AG-01', 'CRZ-01', 10, 500);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-01', 'AG-01', 10, 500);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('AG-02', 'CRZ-03', 25, 1200);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-03', 'AG-02', 25, 1200);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('AG-03', 'CRZ-04', 12, 600);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-04', 'AG-03', 12, 600);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('ATM-01', 'CRZ-01', 5, 200);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-01', 'ATM-01', 5, 200);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('ATM-02', 'CRZ-02', 8, 300);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-02', 'ATM-02', 8, 300);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('ATM-03', 'CRZ-01', 18, 700);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-01', 'ATM-03', 18, 700);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('ATM-04', 'CRZ-03', 45, 3500);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-03', 'ATM-04', 45, 3500);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('ATM-05', 'CRZ-04', 22, 900);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-04', 'ATM-05', 22, 900);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('ATM-06', 'CRZ-02', 35, 1800);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-02', 'ATM-06', 35, 1800);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('ATM-07', 'CRZ-04', 15, 800);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-04', 'ATM-07', 15, 800);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-01', 'CRZ-02', 20, 1500);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-02', 'CRZ-01', 20, 1500);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-02', 'CRZ-03', 40, 2200);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-03', 'CRZ-02', 40, 2200);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-03', 'CRZ-04', 30, 1600);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-04', 'CRZ-03', 30, 1600);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-01', 'CRZ-04', 12, 1100);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-04', 'CRZ-01', 12, 1100);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CD-01', 'CRZ-02', 15, 800);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-02', 'CD-01', 15, 800);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CD-02', 'CRZ-03', 28, 1300);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CRZ-03', 'CD-02', 28, 1300);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('AG-01', 'CD-01', 40, 2500);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CD-01', 'AG-01', 40, 2500);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('AG-02', 'CD-02', 35, 2000);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('CD-02', 'AG-02', 35, 2000);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('ATM-01', 'ATM-03', 50, 2800);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('ATM-03', 'ATM-01', 50, 2800);

INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('AG-01', 'AG-02', 60, 4000);
INSERT INTO edges (origin_id, dest_id, risk, distance) VALUES ('AG-02', 'AG-01', 60, 4000);
