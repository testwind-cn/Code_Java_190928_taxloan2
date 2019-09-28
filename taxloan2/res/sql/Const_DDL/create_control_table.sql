

CREATE TABLE IF NOT EXISTS ${hivevar:DATABASE_DEST}.control_table(
	  `table_name` string,
	  `export_date` timestamp)