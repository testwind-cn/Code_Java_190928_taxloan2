



insert into ${hivevar:DATABASE_DEST}.control_table
select 'mcht_tax' as table_name, from_unixtime(unix_timestamp()) as export_date