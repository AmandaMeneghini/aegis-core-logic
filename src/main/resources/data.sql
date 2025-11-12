-- 1. Cria a tabela dos nossos locais (Vértices)
CREATE TABLE vertices (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- 2. Cria a tabela das nossas rotas (Arestas)
CREATE TABLE edges (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Boa prática ter um ID
    origin_id VARCHAR(10) NOT NULL,
    dest_id VARCHAR(10) NOT NULL,
    risk INT NOT NULL,
    FOREIGN KEY (origin_id) REFERENCES vertices(id),
    FOREIGN KEY (dest_id) REFERENCES vertices(id)
);

-- 3. Insere os dados dos Vértices
INSERT INTO vertices (id, name) VALUES
('AG-01', 'Agencia Central'),
('ATM-01', 'Shopping Plaza ATM'),
('ATM-02', 'Posto Shell ATM'),
('CRZ-01', 'Cruzamento A'),
('CRZ-02', 'Cruzamento B'),
('CD-01', 'Centro de Distribuicao');

-- 4. Insere os dados das Arestas (mão dupla)
-- (AG-01 <-> CRZ-01, risk 10)
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('AG-01', 'CRZ-01', 10);
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('CRZ-01', 'AG-01', 10);

-- (ATM-01 <-> CRZ-01, risk 5)
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('ATM-01', 'CRZ-01', 5);
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('CRZ-01', 'ATM-01', 5);

-- (CRZ-01 <-> CRZ-02, risk 20)
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('CRZ-01', 'CRZ-02', 20);
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('CRZ-02', 'CRZ-01', 20);

-- (CRZ-02 <-> ATM-02, risk 8)
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('CRZ-02', 'ATM-02', 8);
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('ATM-02', 'CRZ-02', 8);

-- (CRZ-02 <-> CD-01, risk 15)
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('CRZ-02', 'CD-01', 15);
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('CD-01', 'CRZ-02', 15);

-- (AG-01 <-> CD-01, risk 40)
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('AG-01', 'CD-01', 40);
INSERT INTO edges (origin_id, dest_id, risk) VALUES ('CD-01', 'AG-01', 40);