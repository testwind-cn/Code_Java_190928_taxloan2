




su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/cjlog hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2.tmp/cjlog"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoice hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2.tmp/saleinvoice"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoicedetail hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2.tmp/saleinvoicedetail"



truncate table dm_taxloan2.cjlog;
truncate table dm_taxloan2.saleinvoice;
truncate table dm_taxloan2.saleinvoicedetail;



LOAD DATA INPATH '/user/hive/warehouse/dm_taxloan2.tmp/cjlog/*' OVERWRITE  INTO TABLE dm_taxloan2.cjlog;

LOAD DATA INPATH '/user/hive/warehouse/dm_taxloan2.tmp/saleinvoice/*' OVERWRITE  INTO TABLE dm_taxloan2.saleinvoice;

LOAD DATA INPATH '/user/hive/warehouse/dm_taxloan2.tmp/saleinvoicedetail/*' OVERWRITE  INTO TABLE dm_taxloan2.saleinvoicedetail;



su admin -c "hdfs dfs -mkdir /user/hive/warehouse/dm_taxloan2_201909241240.tmp"

su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/cjlog hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241240.tmp/cjlog"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoice hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241240.tmp/saleinvoice"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoicedetail hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241240.tmp/saleinvoicedetail"



su admin -c "hdfs dfs -mkdir /user/hive/warehouse/dm_taxloan2_201909241500.tmp"

su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/cjlog hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241500.tmp/cjlog"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoice hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241500.tmp/saleinvoice"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoicedetail hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241500.tmp/saleinvoicedetail"


su admin -c "hdfs dfs -mkdir /user/hive/warehouse/dm_taxloan2_201909241540.tmp"

su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/cjlog hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241540.tmp/cjlog"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoice hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241540.tmp/saleinvoice"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoicedetail hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241540.tmp/saleinvoicedetail"


su admin -c "hdfs dfs -mkdir /user/hive/warehouse/dm_taxloan2_201909241600.tmp"

su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/cjlog hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241600.tmp/cjlog"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoice hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241600.tmp/saleinvoice"
su admin -c "hadoop distcp hdfs://10.91.1.100:8020/user/hive/warehouse/dm_taxloan.db/saleinvoicedetail hdfs://10.91.1.21:8020/user/hive/warehouse/dm_taxloan2_201909241600.tmp/saleinvoicedetail"







sql/Const_mcht_incre/insert_saleinvoice_tmp1.sql 多了一个 distinct
sql/Const_mcht_incre/insert_saleinvoice_tmp2多了一个 distinct



91310114090096316J
c6348b72459a4d22bb6372bdd7fb53e9	90827	2019-09-25 01:23:44	首次采集	2019-09-25 04:07:56	1     84459
52b7284fe59b421fb62b05607e7ca068	24423	2019-09-25 09:53:23	日常采集	2019-09-25 10:49:15	1
