--liquibase formatted sql
--changeset <artmar>:<create-service-table>
CREATE TABLE IF NOT EXISTS public.services
(
    id SERIAL,
    type character varying(255),
    mechanic_id bigint NOT NULL,
    price numeric(38,2),
    status character varying(255),
    order_id bigint NOT NULL,
    CONSTRAINT service_pk PRIMARY KEY (id),
    CONSTRAINT mechanic_id_fk FOREIGN KEY (mechanic_id)
    REFERENCES public.mechanics (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT order_id_fk  FOREIGN KEY (order_id)
    REFERENCES public.orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

--rollback DROP TABLE services;
