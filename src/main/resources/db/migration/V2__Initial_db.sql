-- Структура БД для теми №12 «Періодичні видання»

CREATE TABLE readers (
                         id          bigserial NOT NULL,
                         first_name  character varying(100) NOT NULL,
                         last_name   character varying(100) NOT NULL,
                         email       character varying(255) NOT NULL
);

CREATE TABLE publications (
                              id              bigserial NOT NULL,
                              title           character varying(255) NOT NULL,
                              category        character varying(100),
                              publisher       character varying(255),
                              price_per_month numeric(10,2) NOT NULL
);

CREATE TABLE subscriptions (
                               id             bigserial NOT NULL,
                               reader_id      bigint NOT NULL,
                               publication_id bigint NOT NULL,
                               months         integer NOT NULL,
                               start_date     date NOT NULL,
                               total_amount   numeric(12,2)
);

CREATE TABLE payments (
                          id              bigserial NOT NULL,
                          subscription_id bigint NOT NULL,
                          amount          numeric(12,2) NOT NULL,
                          payment_date    timestamp,
                          status          character varying(20) NOT NULL
);

-- Первинні ключі
ALTER TABLE readers       ADD PRIMARY KEY (id);
ALTER TABLE publications  ADD PRIMARY KEY (id);
ALTER TABLE subscriptions ADD PRIMARY KEY (id);
ALTER TABLE payments      ADD PRIMARY KEY (id);

-- Унікальна пошта читача
ALTER TABLE readers
    ADD CONSTRAINT uq_reader_email UNIQUE (email);

-- Зв'язки: передплата -> читач, передплата -> видання
ALTER TABLE subscriptions
    ADD CONSTRAINT fk_reader_id
        FOREIGN KEY (reader_id) REFERENCES readers(id);

ALTER TABLE subscriptions
    ADD CONSTRAINT fk_publication_id
        FOREIGN KEY (publication_id) REFERENCES publications(id);

-- Зв'язок: платіж -> передплата
ALTER TABLE payments
    ADD CONSTRAINT fk_subscription_id
        FOREIGN KEY (subscription_id) REFERENCES subscriptions(id);

-- Перевірки даних
ALTER TABLE subscriptions
    ADD CONSTRAINT chk_months CHECK (months > 0);

ALTER TABLE payments
    ADD CONSTRAINT chk_payment_status CHECK (status IN ('PENDING', 'PAID', 'CANCELLED'));