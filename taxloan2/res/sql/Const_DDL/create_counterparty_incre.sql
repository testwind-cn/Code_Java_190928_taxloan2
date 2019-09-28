


CREATE TABLE  IF NOT EXISTS ${hivevar:DATABASE_DEST}.counterparty_incre (
  `mcht_cd` string,
  `data_month` string,
  `buyer_name` string,
  `buyer_tax_cd` string,
  `buyer_invoice_cnt` int,
  `buyer_invoice_cr_cnt` int,
  `buyer_invoice_amt_sum` decimal(20,2),
  `buyer_invoice_tax_sum` decimal(20,2),
  `buyer_invoice_total_sum` decimal(20,2),
  `is_delete` string,
  `create_time` date,
  `create_user` string,
  `modify_time` date,
  `modify_user` string)
