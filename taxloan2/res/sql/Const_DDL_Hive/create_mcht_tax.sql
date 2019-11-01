

--商户表
-- DROP TABLE IF EXISTS dm_taxloan.mcht_tax;
CREATE TABLE IF NOT EXISTS ${hivevar:DATABASE_DEST}.mcht_tax(
                                    mcht_cd String COMMENT '销方商户编号',
                                    mcht_name String COMMENT '商户名称',
                                    mcht_tax_cd String COMMENT '商户税号（销方）',
                                    mcht_addtel String COMMENT '销方地址电话',
                                    mcht_bankno String COMMENT '销方银行帐号',
                                    first_invoicedate String COMMENT '首次开票时间',
                                    initial_get_flag  String COMMENT '初始采集完成标志位',
                                    is_delete String COMMENT '逻辑删除，1.是，0.否',
    --create_time date COMMENT '创建时间',
                                    create_user String COMMENT '创建人',
                                    modify_time timestamp COMMENT '修改时间',
                                    modify_user String COMMENT '修改人'

)COMMENT '【税金贷】商户表 ' partitioned by (create_time string)
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\0001' stored AS orc
