


insert into ${hivevar:DATABASE_DEST}.bak_saleinvoice_tmp
partition(create_time=${hivevar:LAST_TIME_S})
select * from ${hivevar:DATABASE_DEST}.saleinvoice_tmp

