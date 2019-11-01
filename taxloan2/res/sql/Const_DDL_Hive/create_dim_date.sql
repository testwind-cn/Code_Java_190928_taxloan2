CREATE TABLE ${hivevar:DATABASE_DEST}.dim_date
(
  `month` string,
  `year` smallint)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
STORED AS TEXTFILE