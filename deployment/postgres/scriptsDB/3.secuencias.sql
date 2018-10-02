DO $$
DECLARE 
i TEXT;
BEGIN
    FOR i IN (select table_name from information_schema.tables where table_catalog='postgres' and table_schema='public') LOOP
    EXECUTE 'CREATE SEQUENCE SEQ_'||i|| ' minvalue 1 start with 1 increment by 1;';
    END LOOP;
END$$;