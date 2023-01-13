--liquibase formatted sql
--changeset <artmar>:<create-cars-table>
CREATE TABLE IF NOT EXISTS public.cars
(
    id SERIAL,
    license_plate bigint,
    manufacturer character varying(255),
    model character varying(255),
    year integer NOT NULL,
    owner_id bigint NOT NULL,
    CONSTRAINT car_pk PRIMARY KEY (id)
);

--rollback DROP TABLE cars;