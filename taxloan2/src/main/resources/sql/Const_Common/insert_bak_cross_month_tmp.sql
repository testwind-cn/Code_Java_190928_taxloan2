


insert into ${hivevar:DATABASE_DEST}.bak_cross_month_tmp
partition(create_time=${hivevar:LAST_TIME_S})
select * from ${hivevar:DATABASE_DEST}.cross_month_tmp

