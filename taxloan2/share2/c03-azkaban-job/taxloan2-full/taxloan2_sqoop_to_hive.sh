#!/bin/bash

echo "*************************************全量导入生产库中的表到hive dm_taxloan库中*******************************"

IP="172.31.100.202"
PORT="3306"
MYSQL_DB="invoice"
URL="jdbc:mysql://${IP}:${PORT}/${MYSQL_DB}"
USER="root"
PASS_F="/user/hive/warehouse/mysql_pwd_202"
HIVE_DB="dm_taxloan2"


source ./0000_set_vars.sh

DATE_S=$(date -d "0 day" "+%Y-%m-%d %H:%M:%S")

rm -rf time.txt
# echo $(date -d "0 day" "+%Y-%m-%d %H:%M:%S") > time.txt
echo "${DATE_S}" > time.txt
DATE_S="$(cat time.txt 2>/dev/null)"
echo "开始时间  ${DATE_S}"

# 日志表
THE_CMD="hive -e \"insert into ${HIVE_DB}.control_table  select 'cjlog_last' as table_name, '${DATE_S}' as export_date\""
echo -e "\n\n========== 开始处理命令 ==========\n"
echo "# su admin -c \"${THE_CMD}\""
# su admin -c "${THE_CMD}"


# cjlog表
THE_CMD="sqoop import --connect ${URL} --username ${USER} --password-file='${PASS_F}' --table cjlog --hive-delims-replacement ' ' --hive-import --hive-database ${HIVE_DB} --hive-table cjlog --hive-overwrite"
echo -e "\n\n========== 开始处理命令 ==========\n"
echo "su admin -c \"${THE_CMD}\""
su admin -c "${THE_CMD}"


# saleinvoice表
THE_CMD="sqoop import --connect ${URL} --username ${USER} --password-file='${PASS_F}' --table saleinvoice --hive-delims-replacement ' ' --hive-import --hive-database ${HIVE_DB} --hive-table saleinvoice --hive-overwrite"
echo -e "\n\n========== 开始处理命令 ==========\n"
echo "su admin -c \"${THE_CMD}\""
su admin -c "${THE_CMD}"


# saleinvoicedetail表
THE_CMD="sqoop import --connect ${URL} --username ${USER} --password-file='${PASS_F}' --table saleinvoicedetail --hive-delims-replacement ' ' --hive-import --hive-database ${HIVE_DB} --hive-table saleinvoicedetail --hive-overwrite"
echo -e "\n\n========== 开始处理命令 ==========\n"
echo "su admin -c \"${THE_CMD}\""
su admin -c "${THE_CMD}"


