#!/bin/bash
echo "*************************************第一次导入生产库中的表到hive dm_taxloan库中*******************************"

#导入数据到hive中
#先要切换到hdfs账户    su hdfs


source ./0000_set_vars.sh

THE_CMD="hive -e 'create database if not exists ${HIVE_DB}'"
echo -e "\n\n========== 开始处理命令 ==========\n"
echo "su admin -c \"${THE_CMD}\""
su admin -c "${THE_CMD}"

# THE_CMD="sqoop import --connect ${URL} --username ${USER} --password='${PASS_S}' --table saleinvoice --num-mappers 1 --direct --delete-target-dir --target-dir=/user/hive/warehouse/${HIVE_DB}.tmp/saleinvoice --hive-import --hive-database ${HIVE_DB} --hive-table saleinvoice --create-hive-table"
THE_CMD="sqoop import --connect ${URL} --username ${USER} --password='${PASS_S}' --table saleinvoice --num-mappers 5 --delete-target-dir --target-dir=/user/hive/warehouse/${HIVE_DB}.tmp/saleinvoice --hive-import --hive-database ${HIVE_DB} --hive-table saleinvoice  --hive-delims-replacement ' ' --create-hive-table"
echo -e "\n\n========== 开始处理命令 ==========\n"
echo "su admin -c \"${THE_CMD}\""
su admin -c "${THE_CMD}"

THE_CMD="sqoop import --connect ${URL} --username ${USER} --password='${PASS_S}' --table saleinvoicedetail --num-mappers 5 --delete-target-dir --target-dir=/user/hive/warehouse/${HIVE_DB}.tmp/saleinvoicedetail --hive-import --hive-database ${HIVE_DB} --hive-table saleinvoicedetail  --hive-delims-replacement ' ' --create-hive-table"
echo -e "\n\n========== 开始处理命令 ==========\n"
echo "su admin -c \"${THE_CMD}\""
su admin -c "${THE_CMD}"

THE_CMD="sqoop import --connect ${URL} --username ${USER} --password='${PASS_S}' --table cjlog --num-mappers 5 --delete-target-dir --target-dir=/user/hive/warehouse/${HIVE_DB}.tmp/cjlog --hive-import --hive-database ${HIVE_DB} --hive-table cjlog  --hive-delims-replacement ' ' --create-hive-table"
echo -e "\n\n========== 开始处理命令 ==========\n"
echo "su admin -c \"${THE_CMD}\""
su admin -c "${THE_CMD}"

