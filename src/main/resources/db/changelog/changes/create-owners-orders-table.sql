--liquibase formatted sql
--changeset <artmar>:<create-owners-orders-table>
CREATE TABLE IF NOT EXISTS public.owners_orders
(
    owner_id bigint NOT NULL,
    orders_id bigint NOT NULL,
    CONSTRAINT orders_id_un UNIQUE (orders_id),
    CONSTRAINT owner_id_fk FOREIGN KEY (owner_id)
        REFERENCES public.owners (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT orders_id_fk FOREIGN KEY (orders_id)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE owners_orders;