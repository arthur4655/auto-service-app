--liquibase formatted sql
--changeset <artmar>:<add-fk-constraint-to-order-table.sql>
ALTER  TABLE public.orders
ADD CONSTRAINT car_id_fk FOREIGN KEY (car_id)
REFERENCES public.cars (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION

--rollback DROP TABLE orders;