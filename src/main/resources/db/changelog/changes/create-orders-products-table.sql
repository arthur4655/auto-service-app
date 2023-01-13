--liquibase formatted sql
--changeset <artmar>:<create-orders-products-table>
CREATE TABLE IF NOT EXISTS public.orders_products
(
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT product_id_un UNIQUE (product_id),
    CONSTRAINT order_id_fk FOREIGN KEY (order_id)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT products_id_fk FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE orders_products;