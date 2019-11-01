CREATE TABLE IF NOT EXISTS ${hivevar:DATABASE_DEST}.bak_cross_month_tmp (
  `mcht_cd` string,
  `month` string)
partitioned by (create_time string)