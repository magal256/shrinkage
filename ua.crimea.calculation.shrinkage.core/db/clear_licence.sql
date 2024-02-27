UPDATE "PUBLIC"."PROPERTIES" SET num_value = 0 WHERE NAME = 'count';
UPDATE "PUBLIC"."PROPERTIES" SET num_value = 500 WHERE NAME = 'max_count';
delete from "PUBLIC"."PROPERTIES" where name = 'start_date';