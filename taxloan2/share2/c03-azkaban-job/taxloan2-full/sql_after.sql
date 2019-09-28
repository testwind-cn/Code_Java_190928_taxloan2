use ${MYSQL_DB};


DROP TABLE IF EXISTS ${MYSQL_DB}.mcht_tax_last;
rename table ${MYSQL_DB}.mcht_tax to ${MYSQL_DB}.mcht_tax_last, ${MYSQL_DB}.mcht_tax_hive to ${MYSQL_DB}.mcht_tax;


DROP TABLE IF EXISTS ${MYSQL_DB}.saleinvoice_last;
rename table ${MYSQL_DB}.saleinvoice to ${MYSQL_DB}.saleinvoice_last, ${MYSQL_DB}.saleinvoice_hive to ${MYSQL_DB}.saleinvoice;


DROP TABLE IF EXISTS ${MYSQL_DB}.counterparty_last;
rename table ${MYSQL_DB}.counterparty to ${MYSQL_DB}.counterparty_last , ${MYSQL_DB}.counterparty_hive to ${MYSQL_DB}.counterparty;


DROP TABLE IF EXISTS ${MYSQL_DB}.counterparty_classify_last;
rename table ${MYSQL_DB}.counterparty_classify  to ${MYSQL_DB}.counterparty_classify_last, ${MYSQL_DB}.counterparty_classify_hive to ${MYSQL_DB}.counterparty_classify;


DROP TABLE IF EXISTS ${MYSQL_DB}.statistics_month_last;
rename table ${MYSQL_DB}.statistics_month  to ${MYSQL_DB}.statistics_month_last , ${MYSQL_DB}.statistics_month_hive to ${MYSQL_DB}.statistics_month;


DROP TABLE IF EXISTS ${MYSQL_DB}.statistics_crossmonth_last;
rename table ${MYSQL_DB}.statistics_crossmonth to ${MYSQL_DB}.statistics_crossmonth_last, ${MYSQL_DB}.statistics_crossmonth_hive to ${MYSQL_DB}.statistics_crossmonth;
