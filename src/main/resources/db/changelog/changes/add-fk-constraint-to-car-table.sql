--liquibase formatted sql
--changeset <artmar>:<add-fk-constraint-to-car-table.sql>
ALTER TABLE cars
ADD CONSTRAINT owner_id_fk FOREIGN KEY (owner_id)
REFERENCES public.owners (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION

--rollback DROP TABLE cars;