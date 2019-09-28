


CREATE TABLE  IF NOT EXISTS  ${hivevar:DATABASE_DEST}.mcht_tax_incre (
  `mcht_cd` string,
  `mcht_name` string,
  `mcht_tax_cd` string,
  `mcht_addtel` string,
  `mcht_bankno` string,
  `first_invoicedate` string,
  `initial_get_flag` string,
  `is_delete` string,
  `create_time` date,
  `create_user` string,
  `modify_time` timestamp,
  `modify_user` string
)
