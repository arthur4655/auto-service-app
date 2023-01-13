--liquibase formatted sql
--changeset <artmar>:<create-orders-table>
CREATE TABLE IF NOT EXISTS public.orders
(
    id SERIAL,
    description character varying(255),
    car_id bigint,
    owner_id bigint,
    total_cost numeric(38,2),
    status character varying(255),
    date_of_receiving date,
    date_of_finishing date,
    CONSTRAINT orders_pk PRIMARY KEY (id),
    CONSTRAINT owner_id_pk FOREIGN KEY (owner_id)
    REFERENCES public.owners (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

--rollback DROP TABLE orders;