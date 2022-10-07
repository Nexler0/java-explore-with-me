DROP TABLE IF EXISTS statistics;

CREATE TABLE IF NOT EXISTS statistics
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    app       VARCHAR(255),
    uri       VARCHAR(2000),
    ip        VARCHAR(255),
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_statistics PRIMARY KEY (id)
);