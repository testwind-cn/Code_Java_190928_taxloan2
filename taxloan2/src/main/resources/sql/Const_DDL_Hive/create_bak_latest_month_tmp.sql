CREATE TABLE IF NOT EXISTS ${hivevar:DATABASE_DEST}.bak_latest_month_tmp(
  `mcht_cd` string,
  `month` string,
  `buyer_tax_cd` string,
  `buyername` string)
partitioned by (create_time string)