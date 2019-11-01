CREATE TABLE IF NOT EXISTS dm_taxloan.bak_latest_montn_tmp(
  `mcht_cd` string,
  `month` string,
  `buyer_tax_cd` string,
  `buyername` string)
partitioned by (create_time string)