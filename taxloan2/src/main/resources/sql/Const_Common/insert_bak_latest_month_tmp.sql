


insert into ${hivevar:DATABASE_DEST}.bak_latest_month_tmp
partition(create_time=${hivevar:LAST_TIME_S})
select * from ${hivevar:DATABASE_DEST}.latest_month_tmp

