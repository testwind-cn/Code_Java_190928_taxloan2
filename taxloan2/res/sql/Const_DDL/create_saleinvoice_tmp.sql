

CREATE TABLE IF NOT EXISTS `${hivevar:DATABASE_DEST}.saleinvoice_tmp`(
    `sellertaxno` string,
    `sellername` string,
    `selleraddtel` string,
    `invoicedate` string,
    `data_month` string,
    `sellerbankno` string,
    `oldtaxno` string,
    `invoiceid` string,
    `buyername` string,
    `buyertaxno` string,
    `totalamount` double,
    `totaltax` double,
    `cancelflag` boolean,
    `invoicetype` string,
    `jztype` string)
