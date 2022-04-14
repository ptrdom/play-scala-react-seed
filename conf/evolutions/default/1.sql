-- Users schema

-- !Ups

CREATE TABLE test_table(
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    deleted BOOLEAN NOT NULL,
    modification_number BIGINT NOT NULL,
    PRIMARY KEY (id)
)

-- !Downs

DROP TABLE test_table;