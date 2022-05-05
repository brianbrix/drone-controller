-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

CREATE OR REPLACE FUNCTION pg_temp.create_myuser(theUsername text, thePassword text)
RETURNS void AS
$BODY$
DECLARE
duplicate_object_message text;
BEGIN
BEGIN
EXECUTE format(
        'CREATE USER %I WITH PASSWORD %L',
        theUsername,
        thePassword
    );
EXCEPTION WHEN duplicate_object THEN
    GET STACKED DIAGNOSTICS duplicate_object_message = MESSAGE_TEXT;
    RAISE NOTICE '%, skipping', duplicate_object_message;
END;
END;
$BODY$
LANGUAGE 'plpgsql';

SELECT pg_temp.create_myuser('posgres_user', 'postgress_pass');
CREATE DATABASE musala_drone;
GRANT ALL PRIVILEGES ON DATABASE musala_drone TO postgres_user;
