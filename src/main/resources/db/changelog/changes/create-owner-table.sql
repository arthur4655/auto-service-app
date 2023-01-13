--liquibase formatted sql
--changeset <artmar>:<create-owners-table>
CREATE TABLE IF NOT EXISTS public.owners
(
    id SERIAL,
    full_name character varying(255),
    CONSTRAINT owner_pk PRIMARY KEY (id)
);

--rollback DROP TABLE owners;
