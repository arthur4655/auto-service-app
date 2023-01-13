--liquibase formatted sql
--changeset <artmar>:<create-mechanics-table>
CREATE TABLE IF NOT EXISTS public.mechanics
(
    id SERIAL,
    full_name character varying(255),
    CONSTRAINT mechanic_pk PRIMARY KEY (id)
);

--rollback DROP TABLE mechanics;