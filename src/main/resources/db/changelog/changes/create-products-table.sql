--liquibase formatted sql
--changeset <artmar>:<create-orders-table>
CREATE TABLE IF NOT EXISTS public.products
(
    id SERIAL,
    name character varying(255),
    price numeric(38,2),
    CONSTRAINT products_pk PRIMARY KEY (id)
);

--rollback DROP TABLE products;
