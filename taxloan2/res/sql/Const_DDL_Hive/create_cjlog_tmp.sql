

CREATE TABLE IF NOT EXISTS ${hivevar:DATABASE_DEST}.cjlog_tmp (
    `taxno` string COMMENT '新税号',
    `oldtaxno`  string COMMENT '老税号'
)
