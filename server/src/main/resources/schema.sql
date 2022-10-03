DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS compilations_events CASCADE;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  comments
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text      VARCHAR(255),
    item_id   BIGINT,
    author_id BIGINT,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN,
    title  VARCHAR(255),
    CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  compilations_events
(
    compilation_id BIGINT NOT NULL,
    events_id      BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS  events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000),
    category_id        BIGINT,
    confirmed_request  INTEGER,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR(2000),
    event_date         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    location_id        BIGINT,
    paid               BOOLEAN,
    participant_limit  INTEGER                                 NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state              VARCHAR(255),
    title              VARCHAR(500)                            NOT NULL,
    views              INTEGER                                 NOT NULL,
    CONSTRAINT pk_events PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  locations
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat DOUBLE PRECISION                        NOT NULL,
    lon DOUBLE PRECISION                        NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  users
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    users_name  VARCHAR(255),
    users_email VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE compilations_events
    ADD CONSTRAINT uc_compilations_events_events UNIQUE (events_id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_ITEM FOREIGN KEY (item_id) REFERENCES events (id);

ALTER TABLE events
    ADD CONSTRAINT FK_EVENTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE events
    ADD CONSTRAINT FK_EVENTS_ON_INITIATOR FOREIGN KEY (initiator_id) REFERENCES users (id);

ALTER TABLE events
    ADD CONSTRAINT FK_EVENTS_ON_LOCATION FOREIGN KEY (location_id) REFERENCES locations (id);

ALTER TABLE compilations_events
    ADD CONSTRAINT fk_comeve_on_compilation FOREIGN KEY (compilation_id) REFERENCES compilations (id);

ALTER TABLE compilations_events
    ADD CONSTRAINT fk_comeve_on_event FOREIGN KEY (events_id) REFERENCES events (id);