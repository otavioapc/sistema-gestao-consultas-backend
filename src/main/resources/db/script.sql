CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) UNIQUE NOT NULL,
    data_criacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    ultimo_login TIMESTAMP WITHOUT TIME ZONE,
    perfil VARCHAR(50) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE pacientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_criacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    telefone VARCHAR(20)
);

CREATE TABLE dentistas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    cro VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP WITHOUT TIME ZONE DEFAUT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE especialidades (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE dentista_especialidade (
    id SERIAL PRIMARY KEY,
    id_dentista INT NOT NULL,
    id_especialidade INT NOT NULL,
    CONSTRAINT fk_dentista FOREIGN KEY (id_dentista) REFERENCES dentistas(id) ON DELETE CASCADE,
    CONSTRAINT fk_especialidade FOREIGN KEY (id_especialidade) REFERENCES especialidades(id) ON DELETE CASCADE
);

CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_dentista INT NOT NULL,
    id_usuario INT NOT NULL,
    descricao TEXT NOT NULL,
    motivo_cancelamento TEXT,
    data_inicio TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_fim TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_registro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes(id),
    CONSTRAINT fk_consulta_dentista FOREIGN KEY (id_dentista) REFERENCES dentistas(id),
    CONSTRAINT fk_consulta_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);