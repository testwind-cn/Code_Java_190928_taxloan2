#!/bin/bash
### AUTHOR: zoupp
### EMAIL: zoupp@allinpay.com
### DATE: 2019/04/23
### DESC: taxloan-sqoop-export
### REV: 3.0
source /etc/profile
source /var/lib/hadoop-hdfs/.bash_profile

CURR_DATE=`date +%Y-%m-%d`
HIVE_DB="dm_taxloan2"

#IP="10.91.1.10"
#PORT="3306"
#MYSQL_DB="data_warehouse"
#USER="datauser"
#PASS="kpo9raFllycl@fc0"
#URL="jdbc:mysql://${IP}:${PORT}/${MYSQL_DB}"


IP="172.31.100.202"
PORT="3306"
MYSQL_DB="data_warehouse2"
USER="datauser"
PASS_S="kpo9raFllycl@fc0"
URL="jdbc:mysql://${IP}:${PORT}/${MYSQL_DB}"
# 以上没有作用

source ./0001_set_vars.sh

sed  's/${MYSQL_DB}'"/${MYSQL_DB}/g" sql_before.sql | cat
echo -e "\n=========== MYSQL BEGIN ===========\n"
sed  's/${MYSQL_DB}'"/${MYSQL_DB}/g" sql_before.sql | mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}"
echo "=========== MYSQL OK ==========="

### e0502 counterparty
# mysql -u"$USER" -p"${PASS_S}" -h"${IP}" -P"${PORT}" -e "TRUNCATE TABLE ${MYSQL_DB}.counterparty;COMMIT;"
sqoop export \
--hcatalog-database ${HIVE_DB} \
--hcatalog-table counterparty \
--hcatalog-partition-keys create_time \
--hcatalog-partition-values ${CURR_DATE} \
--fields-terminated-by "\0001" \
--connect ${URL} \
--username ${USER} \
--password "${PASS_S}" \
--m 10 \
--table counterparty_hive \
--columns="mcht_cd,data_month,buyer_name,buyer_tax_cd,buyer_invoice_cnt,buyer_invoice_cr_cnt,buyer_invoice_amt_sum,buyer_invoice_tax_sum,buyer_invoice_total_sum,is_delete,create_time,create_user,modify_time,modify_user"


### e0503 counterparty_classify
# mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}" -e "TRUNCATE TABLE ${MYSQL_DB}.counterparty_classify;COMMIT;"
sqoop export \
--hcatalog-database ${HIVE_DB} \
--hcatalog-table counterparty_classify \
--hcatalog-partition-keys create_time \
--hcatalog-partition-values ${CURR_DATE} \
--fields-terminated-by "\0001" \
--connect ${URL} \
--username ${USER} \
--password "${PASS_S}" \
--m 10 \
--table counterparty_classify_hive \
--columns="mcht_cd,data_month,buyer_name,buyer_tax_cd,buyer_hist_month,buyer_invoice_cnt_l24m,buyer_invoice_total_sum_l24m,buyer_invoice_cnt_l12m,buyer_invoice_total_sum_l12m,buyer_invoice_cnt_l6m,buyer_invoice_total_sum_l6m,buyer_invoice_cnt_l3m,buyer_invoice_total_sum_l3m,buyer_invoice_cnt_l712m,buyer_invoice_total_sum_l712m,buyer_invoice_cnt_l1324m,buyer_invoice_total_sum_l1324m,buyer_invoice_cnt_l1m, buyer_invoice_total_sum_l1m,buyer_type,is_delete,create_time,create_user,modify_time,modify_user"



### e0504 statistics_month
# mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}" -e "TRUNCATE TABLE ${MYSQL_DB}.statistics_month;COMMIT;"
sqoop export \
--hcatalog-database ${HIVE_DB} \
--hcatalog-table statistics_month \
--hcatalog-partition-keys create_time \
--hcatalog-partition-values ${CURR_DATE} \
--fields-terminated-by "\0001" \
--connect ${URL} \
--username ${USER} \
--password "${PASS_S}" \
--m 10 \
--table statistics_month_hive \
--columns="mcht_cd,data_month,invoice_sc_cnt,invoice_sc_cancel_cnt,invoice_sc_red_cnt,invoice_sc_total_sum,invoice_cnt,invoice_cr_cnt,invoice_amt_sum,invoice_tax_sum,invoice_total_sum,invoice_cancel_cnt,invoice_red_cnt,invoice_total_c_sum,invoice_total_r_sum,invoice_s_cnt,invoice_s_cancel_cnt,invoice_s_red_cnt,invoice_s_amt_sum,invoice_s_tax_sum,invoice_s_total_sum, invoice_c_cnt,invoice_c_cancel_cnt,invoice_c_red_cnt,invoice_c_amt_sum,invoice_c_tax_sum,invoice_c_total_sum,invoice_t_cnt,invoice_t_cancel_cnt,invoice_t_red_cnt,invoice_t_amt_sum,invoice_t_tax_sum,invoice_t_total_sum,invoice_p_cnt,invoice_p_cancel_cnt,invoice_p_red_cnt,invoice_p_amt_sum,invoice_p_tax_sum,invoice_p_total_sum,invoice_e_cnt,invoice_e_cancel_cnt,invoice_e_red_cnt,invoice_e_amt_sum,invoice_e_tax_sum,invoice_e_total_sum,invoice_r_cnt,invoice_r_cancel_cnt,invoice_r_red_cnt,invoice_r_amt_sum,invoice_r_tax_sum,invoice_r_total_sum,invoice_red_cnt_rate,invoice_cancel_cnt_rate,invoice_red_total_sum_rate,invoice_cancel_total_sum_rate,invoice_lt100_cnt,invoice_lt100_cr_cnt,invoice_lt100_total_sum,invoice_lt1000_cnt,invoice_lt1000_cr_cnt,invoice_lt1000_total_sum,invoice_lt2500_cnt,invoice_lt2500_cr_cnt,invoice_lt2500_total_sum,invoice_lt5000_cnt,invoice_lt5000_cr_cnt,invoice_lt5000_total_sum,invoice_lt10000_cnt,invoice_lt10000_cr_cnt,invoice_lt10000_total_sum,invoice_gt10000_cnt,invoice_gt10000_cr_cnt,invoice_gt10000_total_sum,invoice_detail_cnt,invoice_detail_cr_cnt,invoice_detail_total_sum,buyer_cnt,buyer_cnt_all,buyer_a_cnt,buyer_b_cnt,buyer_c_cnt,buyer_f_cnt,is_delete,create_time,create_user,modify_time,modify_user"  



### e0505 statistics_crossmonth
# mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}" -e "TRUNCATE TABLE ${MYSQL_DB}.statistics_crossmonth;COMMIT;"
sqoop export \
--hcatalog-database $HIVE_DB \
--hcatalog-table statistics_crossmonth \
--hcatalog-partition-keys create_time \
--hcatalog-partition-values ${CURR_DATE} \
--fields-terminated-by "\0001" \
--connect ${URL} \
--username ${USER} \
--password "${PASS_S}" \
--m 10 \
--table statistics_crossmonth_hive \
--columns="mcht_cd,data_month,invoice_sc_cnt_l3m,invoice_sc_cancel_cnt_l3m,invoice_sc_red_cnt_l3m,invoice_sc_total_sum_l3m,invoice_cnt_l3m,invoice_cr_cnt_l3m,invoice_total_sum_l3m,invoice_s_cnt_l3m,invoice_s_cancel_cnt_l3m,invoice_s_red_cnt_l3m,invoice_s_total_sum_l3m,invoice_c_cnt_l3m,invoice_c_cancel_cnt_l3m,invoice_c_red_cnt_l3m,invoice_c_total_sum_l3m,invoice_t_cnt_l3m,invoice_t_cancel_cnt_l3m,invoice_t_red_cnt_l3m,invoice_t_total_sum_l3m,invoice_p_cnt_l3m,invoice_p_cancel_cnt_l3m,invoice_p_red_cnt_l3m,invoice_p_total_sum_l3m,invoice_e_cnt_l3m,invoice_e_cancel_cnt_l3m,invoice_e_red_cnt_l3m,invoice_e_total_sum_l3m,invoice_r_cnt_l3m,invoice_r_cancel_cnt_l3m,invoice_r_red_cnt_l3m,invoice_r_total_sum_l3m,invoice_red_cnt_rate_l3m,invoice_cancel_cnt_rate_l3m,invoice_red_total_sum_rate_l3m,invoice_cancel_total_sum_rate_l3m,invoice_lt100_cnt_l3m,invoice_lt100_cr_cnt_l3m,invoice_lt100_total_sum_l3m,invoice_lt1000_cnt_l3m,invoice_lt1000_cr_cnt_l3m,invoice_lt1000_total_sum_l3m,invoice_lt2500_cnt_l3m,invoice_lt2500_cr_cnt_l3m,invoice_lt2500_total_sum_l3m,invoice_lt5000_cnt_l3m,invoice_lt5000_cr_cnt_l3m,invoice_lt5000_total_sum_l3m,invoice_lt10000_cnt_l3m,invoice_lt10000_cr_cnt_l3m,invoice_lt10000_total_sum_l3m,invoice_gt10000_cnt_l3m,invoice_gt10000_cr_cnt_l3m,invoice_gt10000_total_sum_l3m,invoice_detail_cnt_l3m,invoice_detail_cr_cnt_l3m,invoice_detail_total_sum_l3m,buyer_cnt_l3m,invoice_sc_cnt_l6m,invoice_sc_cancel_cnt_l6m,invoice_sc_red_cnt_l6m,invoice_sc_total_sum_l6m,invoice_cnt_l6m,invoice_cr_cnt_l6m,invoice_total_sum_l6m,invoice_s_cnt_l6m,invoice_s_cancel_cnt_l6m,invoice_s_red_cnt_l6m,invoice_s_total_sum_l6m,invoice_c_cnt_l6m,invoice_c_cancel_cnt_l6m,invoice_c_red_cnt_l6m,invoice_c_total_sum_l6m,invoice_t_cnt_l6m,invoice_t_cancel_cnt_l6m,invoice_t_red_cnt_l6m,invoice_t_total_sum_l6m,invoice_p_cnt_l6m,invoice_p_cancel_cnt_l6m,invoice_p_red_cnt_l6m,invoice_p_total_sum_l6m,invoice_e_cnt_l6m,invoice_e_cancel_cnt_l6m,invoice_e_red_cnt_l6m,invoice_e_total_sum_l6m,invoice_r_cnt_l6m,invoice_r_cancel_cnt_l6m,invoice_r_red_cnt_l6m,invoice_r_total_sum_l6m,invoice_red_cnt_rate_l6m,invoice_cancel_cnt_rate_l6m,invoice_red_total_sum_rate_l6m,invoice_cancel_total_sum_rate_l6m,invoice_lt100_cnt_l6m,invoice_lt100_cr_cnt_l6m,invoice_lt100_total_sum_l6m,invoice_lt1000_cnt_l6m,invoice_lt1000_cr_cnt_l6m,invoice_lt1000_total_sum_l6m,invoice_lt2500_cnt_l6m,invoice_lt2500_cr_cnt_l6m,invoice_lt2500_total_sum_l6m,invoice_lt5000_cnt_l6m,invoice_lt5000_cr_cnt_l6m,invoice_lt5000_total_sum_l6m,invoice_lt10000_cnt_l6m,invoice_lt10000_cr_cnt_l6m,invoice_lt10000_total_sum_l6m,invoice_gt10000_cnt_l6m,invoice_gt10000_cr_cnt_l6m,invoice_gt10000_total_sum_l6m,invoice_detail_cnt_l6m,invoice_detail_cr_cnt_l6m,invoice_detail_total_sum_l6m,buyer_cnt_l6m,buyer_a_invoice_cnt_l6m,buyer_b_invoice_cnt_l6m,buyer_c_invoice_cnt_l6m,buyer_f_invoice_cnt_l6m,buyer_a_amt_sum_l6m,buyer_b_amt_sum_l6m,buyer_c_amt_sum_l6m,buyer_f_amt_sum_l6m,invoice_sc_cnt_l12m,invoice_sc_cancel_cnt_l12m,invoice_sc_red_cnt_l12m,invoice_sc_total_sum_l12m,invoice_cnt_l12m,invoice_cr_cnt_l12m,invoice_total_sum_l12m,invoice_s_cnt_l12m,invoice_s_cancel_cnt_l12m,invoice_s_red_cnt_l12m,invoice_s_total_sum_l12m,invoice_c_cnt_l12m,invoice_c_cancel_cnt_l12m,invoice_c_red_cnt_l12m,invoice_c_total_sum_l12m,invoice_t_cnt_l12m,invoice_t_cancel_cnt_l12m,invoice_t_red_cnt_l12m,invoice_t_total_sum_l12m,invoice_p_cnt_l12m,invoice_p_cancel_cnt_l12m,invoice_p_red_cnt_l12m,invoice_p_total_sum_l12m,invoice_e_cnt_l12m,invoice_e_cancel_cnt_l12m,invoice_e_red_cnt_l12m,invoice_e_total_sum_l12m,invoice_r_cnt_l12m,invoice_r_cancel_cnt_l12m,invoice_r_red_cnt_l12m,invoice_r_total_sum_l12m,invoice_red_cnt_rate_l12m,invoice_cancel_cnt_rate_l12m,invoice_red_total_sum_rate_l12m,invoice_cancel_total_sum_rate_l12m,invoice_lt100_cnt_l12m,invoice_lt100_cr_cnt_l12m,invoice_lt100_total_sum_l12m,invoice_lt1000_cnt_l12m,invoice_lt1000_cr_cnt_l12m,invoice_lt1000_total_sum_l12m,invoice_lt2500_cnt_l12m,invoice_lt2500_cr_cnt_l12m,invoice_lt2500_total_sum_l12m,invoice_lt5000_cnt_l12m,invoice_lt5000_cr_cnt_l12m,invoice_lt5000_total_sum_l12m,invoice_lt10000_cnt_l12m,invoice_lt10000_cr_cnt_l12m,invoice_lt10000_total_sum_l12m,invoice_gt10000_cnt_l12m,invoice_gt10000_cr_cnt_l12m,invoice_gt10000_total_sum_l12m,invoice_detail_cnt_l12m,invoice_detail_cr_cnt_l12m,invoice_detail_total_sum_l12m,buyer_cnt_l12m,buyer_a_invoice_cnt_l12m,buyer_b_invoice_cnt_l12m,buyer_c_invoice_cnt_l12m,buyer_f_invoice_cnt_l12m,buyer_a_amt_sum_l12m,buyer_b_amt_sum_l12m,buyer_c_amt_sum_l12m,buyer_f_amt_sum_l12m,invoice_sc_cnt_l24m,invoice_sc_cancel_cnt_l24m,invoice_sc_red_cnt_l24m,invoice_sc_total_sum_l24m,invoice_cnt_l24m,invoice_cr_cnt_l24m,invoice_total_sum_l24m,invoice_s_cnt_l24m,invoice_s_cancel_cnt_l24m,invoice_s_red_cnt_l24m,invoice_s_total_sum_l24m,invoice_c_cnt_l24m,invoice_c_cancel_cnt_l24m,invoice_c_red_cnt_l24m,invoice_c_total_sum_l24m,invoice_t_cnt_l24m,invoice_t_cancel_cnt_l24m,invoice_t_red_cnt_l24m,invoice_t_total_sum_l24m,invoice_p_cnt_l24m,invoice_p_cancel_cnt_l24m,invoice_p_red_cnt_l24m,invoice_p_total_sum_l24m,invoice_e_cnt_l24m,invoice_e_cancel_cnt_l24m,invoice_e_red_cnt_l24m,invoice_e_total_sum_l24m,invoice_r_cnt_l24m,invoice_r_cancel_cnt_l24m,invoice_r_red_cnt_l24m,invoice_r_total_sum_l24m,invoice_red_cnt_rate_l24m,invoice_cancel_cnt_rate_l24m,invoice_red_total_sum_rate_l24m,invoice_cancel_total_sum_rate_l24m,invoice_lt100_cnt_l24m,invoice_lt100_cr_cnt_l24m,invoice_lt100_total_sum_l24m,invoice_lt1000_cnt_l24m,invoice_lt1000_cr_cnt_l24m,invoice_lt1000_total_sum_l24m,invoice_lt2500_cnt_l24m,invoice_lt2500_cr_cnt_l24m,invoice_lt2500_total_sum_l24m,invoice_lt5000_cnt_l24m,invoice_lt5000_cr_cnt_l24m,invoice_lt5000_total_sum_l24m,invoice_lt10000_cnt_l24m,invoice_lt10000_cr_cnt_l24m,invoice_lt10000_total_sum_l24m,invoice_gt10000_cnt_l24m,invoice_gt10000_cr_cnt_l24m,invoice_gt10000_total_sum_l24m,invoice_detail_cnt_l24m,invoice_detail_cr_cnt_l24m,invoice_detail_total_sum_l24m,buyer_cnt_l24m,buyer_a_invoice_cnt_l24m,buyer_b_invoice_cnt_l24m,buyer_c_invoice_cnt_l24m,buyer_f_invoice_cnt_l24m,buyer_a_amt_sum_l24m,buyer_b_amt_sum_l24m,buyer_c_amt_sum_l24m,buyer_f_amt_sum_l24m,invoice_sc_cnt_ratio_6m,invoice_sc_cancel_cnt_ratio_6m,invoice_sc_red_cnt_ratio_6m,invoice_sc_total_sum_ratio_6m,invoice_cnt_ratio_6m,invoice_total_sum_ratio_6m,invoice_s_cnt_ratio_6m,invoice_s_total_sum_ratio_6m,invoice_c_cnt_ratio_6m,invoice_c_total_sum_ratio_6m,invoice_t_cnt_ratio_6m,invoice_t_total_sum_ratio_6m,invoice_p_cnt_ratio_6m,invoice_p_total_sum_ratio_6m,invoice_e_cnt_ratio_6m,invoice_e_total_sum_ratio_6m,invoice_r_cnt_ratio_6m,invoice_r_total_sum_ratio_6m,invoice_red_cnt_rate_ratio_6m,invoice_cancel_cnt_rate_ratio_6m,invoice_red_total_sum_rate_ratio_6m,invoice_cancel_total_sum_rate_ratio_6m,invoice_lt100_cnt_ratio_6m,invoice_lt100_total_sum_ratio_6m,invoice_lt1000_cnt_ratio_6m,invoice_lt1000_total_sum_ratio_6m,invoice_lt2500_cnt_ratio_6m,invoice_lt2500_total_sum_ratio_6m,invoice_lt5000_cnt_ratio_6m,invoice_lt5000_total_sum_ratio_6m,invoice_gt10000_cnt_ratio_6m,invoice_gt10000_total_sum_ratio_6m,invoice_detail_cnt_ratio_6m,invoice_detail_total_sum_ratio_6m,buyer_cnt_chg_rate_6m,buyer_a_cnt_chg_rate_6m,buyer_b_cnt_chg_rate_6m,buyer_c_cnt_chg_rate_6m,buyer_f_cnt_chg_rate_6m,buyer_a_invoice_cnt_l12m_chg_rate_6m,buyer_b_invoice_cnt_l12m_chg_rate_6m,buyer_c_invoice_cnt_l12m_chg_rate_6m,buyer_f_invoice_cnt_l12m_chg_rate_6m,buyer_a_amt_sum_l12m_chg_rate_6m,buyer_b_amt_sum_l12m_chg_rate_6m,buyer_c_amt_sum_l12m_chg_rate_6m,buyer_f_amt_sum_l12m_chg_rate_6m,invoice_sc_cnt_ratio_12m,invoice_sc_cancel_cnt_ratio_12m,invoice_sc_red_cnt_ratio_12m,invoice_sc_total_sum_ratio_12m,invoice_cnt_ratio_12m,invoice_total_sum_ratio_12m,invoice_s_cnt_ratio_12m,invoice_s_total_sum_ratio_12m,invoice_c_cnt_ratio_12m,invoice_c_total_sum_ratio_12m,invoice_t_cnt_ratio_12m,invoice_t_total_sum_ratio_12m,invoice_p_cnt_ratio_12m,invoice_p_total_sum_ratio_12m,invoice_e_cnt_ratio_12m,invoice_e_total_sum_ratio_12m,invoice_r_cnt_ratio_12m,invoice_r_total_sum_ratio_12m,invoice_red_cnt_rate_ratio_12m,invoice_cancel_cnt_rate_ratio_12m,invoice_red_total_sum_rate_ratio_12m,invoice_cancel_total_sum_rate_ratio_12m,invoice_lt100_cnt_ratio_12m,invoice_lt100_total_sum_ratio_12m,invoice_lt1000_cnt_ratio_12m,invoice_lt1000_total_sum_ratio_12m,invoice_lt2500_cnt_ratio_12m,invoice_lt2500_total_sum_ratio_12m,invoice_lt5000_cnt_ratio_12m,invoice_lt5000_total_sum_ratio_12m,invoice_gt10000_cnt_ratio_12m,invoice_gt10000_total_sum_ratio_12m,invoice_detail_cnt_ratio_12m,invoice_detail_total_sum_ratio_12m,buyer_cnt_chg_rate_12m,buyer_a_cnt_chg_rate_12m,buyer_b_cnt_chg_rate_12m,buyer_c_cnt_chg_rate_12m,buyer_f_cnt_chg_rate_12m,buyer_a_invoice_cnt_l12m_chg_rate_12m,buyer_b_invoice_cnt_l12m_chg_rate_12m,buyer_c_invoice_cnt_l12m_chg_rate_12m,buyer_f_invoice_cnt_l12m_chg_rate_12m,buyer_a_amt_sum_l12m_chg_rate_12m,buyer_b_amt_sum_l12m_chg_rate_12m,buyer_c_amt_sum_l12m_chg_rate_12m,buyer_f_amt_sum_l12m_chg_rate_12m,is_delete,create_time,create_user,modify_time,modify_user"


### e0501 mcht_tax
# mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}" -e "TRUNCATE TABLE ${MYSQL_DB}.mcht_tax;COMMIT;"
sqoop export \
--hcatalog-database ${HIVE_DB} \
--hcatalog-table mcht_tax \
--hcatalog-partition-keys create_time \
--hcatalog-partition-values ${CURR_DATE} \
--fields-terminated-by "\0001" \
--connect ${URL} \
--username ${USER} \
--password "${PASS_S}" \
--m 10 \
--table mcht_tax_hive \
--columns="mcht_cd,mcht_name,mcht_tax_cd,mcht_addtel,mcht_bankno,first_invoicedate,initial_get_flag,is_delete,create_time,create_user,modify_time,modify_user"


### e0506 saleinvoice
# mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}" -e "TRUNCATE TABLE ${MYSQL_DB}.saleinvoice;COMMIT;"
# sqoop export \
# --hcatalog-database ${HIVE_DB} \
# --hcatalog-table saleinvoice \
# --connect ${URL} \
# --username ${USER} \
# --password "${PASS_S}" \
# --m 10 \
# --table saleinvoice_hive \
# --columns="cid,agentcode,authstate,authtime,authusername,batchid,billid,billno,buyeraddtel,buyerbankno,buyername,buyerno,buyertaxno,cancelflag,carriertaxno,cd,checkcode,checkresult,checkstate,checktime,ciphertext,cjhm,comments,confirmstate,createtime,detaillistflag,email,encryptionver,fdjhm,fhrsbh,forwordstate,forwordtime,gatheringperson,hgzh,imgstate,incomingstate,incomingtime,incomingusername,inputperson,invoicecode,invoicecontent,invoicedate,invoiceid,invoiceno,invoicetype,jztype,kpzdbs,logno,machineno,makeinvoicedeviceno,makeinvoiceperson,mobileno,pdfurl,printflag,pushstate,pushtime,qyd,recheckperson,repairflag,responecode,responeexplain,salebillno,selleraddtel,sellerbankno,sellername,sellertaxno,senterrtimes,sentoperator,senttime,senttobank,shrsbh,sktype,source,spsm,srctype,taxofficecode,taxrate,totalamount,totaltax,writebackstate,writebacktime,xcrs,xfdh,xfdz,yshwxx,yyzzh,zh,zyspmc"

	
sed  's/${MYSQL_DB}'"/${MYSQL_DB}/g" sql_after.sql | cat
echo -e "\n=========== MYSQL BEGIN ===========\n"
sed  's/${MYSQL_DB}'"/${MYSQL_DB}/g" sql_after.sql | mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}"
echo "=========== MYSQL OK ==========="


