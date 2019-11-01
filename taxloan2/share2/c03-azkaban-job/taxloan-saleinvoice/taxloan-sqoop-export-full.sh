#!/bin/bash
### AUTHOR: zoupp
### EMAIL: zoupp@allinpay.com
### DATE: 2019/04/23
### DESC: taxloan-sqoop-export
### REV: 3.0
source /etc/profile
source /var/lib/hadoop-hdfs/.bash_profile

CURR_DATE=`date +%Y-%m-%d`
HIVE_DB="dm_taxloan"

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


sed  's/${MYSQL_DB}'"/${MYSQL_DB}/g" sql_before.sql | cat
echo -e "\n=========== MYSQL BEGIN ===========\n"
sed  's/${MYSQL_DB}'"/${MYSQL_DB}/g" sql_before.sql | mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}"
echo "=========== MYSQL OK ==========="



### e0506 saleinvoice
# mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}" -e "TRUNCATE TABLE ${MYSQL_DB}.saleinvoice;COMMIT;"
sqoop export \
--hcatalog-database ${HIVE_DB} \
--hcatalog-table saleinvoice \
--connect ${URL} \
--username ${USER} \
--password "${PASS_S}" \
--m 24 \
--table saleinvoice_hive \
--columns="cid,agentcode,authstate,authtime,authusername,batchid,billid,billno,buyeraddtel,buyerbankno,buyername,buyerno,buyertaxno,cancelflag,carriertaxno,cd,checkcode,checkresult,checkstate,checktime,ciphertext,cjhm,comments,confirmstate,createtime,detaillistflag,email,encryptionver,fdjhm,fhrsbh,forwordstate,forwordtime,gatheringperson,hgzh,imgstate,incomingstate,incomingtime,incomingusername,inputperson,invoicecode,invoicecontent,invoicedate,invoiceid,invoiceno,invoicetype,jztype,kpzdbs,logno,machineno,makeinvoicedeviceno,makeinvoiceperson,mobileno,pdfurl,printflag,pushstate,pushtime,qyd,recheckperson,repairflag,responecode,responeexplain,salebillno,selleraddtel,sellerbankno,sellername,sellertaxno,senterrtimes,sentoperator,senttime,senttobank,shrsbh,sktype,source,spsm,srctype,taxofficecode,taxrate,totalamount,totaltax,writebackstate,writebacktime,xcrs,xfdh,xfdz,yshwxx,yyzzh,zh,zyspmc"

	
sed  's/${MYSQL_DB}'"/${MYSQL_DB}/g" sql_after.sql | cat
echo -e "\n=========== MYSQL BEGIN ===========\n"
sed  's/${MYSQL_DB}'"/${MYSQL_DB}/g" sql_after.sql | mysql -u"${USER}" -p"${PASS_S}" -h"${IP}" -P"${PORT}"
echo "=========== MYSQL OK ==========="


