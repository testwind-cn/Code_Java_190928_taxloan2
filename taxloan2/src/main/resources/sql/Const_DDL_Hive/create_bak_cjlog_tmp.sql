CREATE TABLE IF NOT EXISTS ${hivevar:DATABASE_DEST}.bak_cjlog_tmp(
`taxno` string,
`oldtaxno` string
)
partitioned by (create_time string)
