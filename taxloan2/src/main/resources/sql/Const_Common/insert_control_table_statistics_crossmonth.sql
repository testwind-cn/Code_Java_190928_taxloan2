



insert into ${hivevar:DATABASE_DEST}.control_table
select 'statistics_crossmonth' as table_name, from_unixtime(unix_timestamp()) as export_date