

CREATE TABLE  IF NOT EXISTS ${hivevar:DATABASE_DEST}.counterparty_classify_incre(
  `mcht_cd` string,
  `data_month` string,
  `buyer_name` string,
  `buyer_tax_cd` string,
  `buyer_hist_month` int,
  `buyer_invoice_cnt_l24m` int,
  `buyer_invoice_total_sum_l24m` decimal(20,2),
  `buyer_invoice_cnt_l12m` int,
  `buyer_invoice_total_sum_l12m` decimal(20,2),
  `buyer_invoice_cnt_l6m` int,
  `buyer_invoice_total_sum_l6m` decimal(20,2),
  `buyer_invoice_cnt_l3m` int,
  `buyer_invoice_total_sum_l3m` decimal(20,2),
  `buyer_invoice_cnt_l712m` int,
  `buyer_invoice_total_sum_l712m` decimal(20,2),
  `buyer_invoice_cnt_l1324m` int,
  `buyer_invoice_total_sum_l1324m` decimal(20,2),
  `buyer_invoice_cnt_l1m` int,
  `buyer_invoice_total_sum_l1m` decimal(20,2),
  `buyer_type` string,
  `is_delete` string,
  `create_time` date,
  `create_user` string,
  `modify_time` date,
  `modify_user` string)
