#!/bin/bash


LOCAL_IP=$(ifconfig eth0 | grep 'inet' | awk -F ' ' '{print $2}')
LOCAL_NAME=$(hostname)


if [[ ${LOCAL_IP} = '10.91.1.100' ]]
then        # 10.91.1.100
    echo "=============== 在生产服务器设置变量 =================="

    IP="172.31.100.202"
    PORT="3306"
    MYSQL_DB="invoice"
    URL="jdbc:mysql://${IP}:${PORT}/${MYSQL_DB}?useSSL=false"
    USER="root"
    PASS_F="/user/hive/warehouse/mysql_pwd_202"
    PASS_S="9G3z8~a0JXa#8G9E"
    HIVE_DB="dm_taxloan2"


else        # 10.91.1.21
    echo "=============== 在测试服务器设置变量 =================="


    IP="10.91.1.19"
    PORT="3306"
    MYSQL_DB="invoice"
    URL="jdbc:mysql://${IP}:${PORT}/${MYSQL_DB}?useSSL=false"
    USER="root"
    PASS_F="/user/hive/warehouse/mysql_pwd_202"
    PASS_S="Redhat@2016"
    HIVE_DB="dm_taxloan2"

fi



