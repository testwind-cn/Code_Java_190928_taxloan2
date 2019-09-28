



insert into ${hivevar:DATABASE_DEST}.control_table
select 'counterparty_classify' as table_name, from_unixtime(unix_timestamp()) as export_date