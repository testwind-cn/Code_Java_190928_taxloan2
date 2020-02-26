
-- 交易对手表
-- DROP TABLE IF EXISTS dm_taxloan.counterparty;
CREATE TABLE IF NOT EXISTS ${hivevar:DATABASE_DEST}.counterparty(
                                        mcht_cd String COMMENT '销方商户编号',
                                        data_month String COMMENT '统计月份',
                                        buyer_name String COMMENT '交易对手（购方）名称',
                                        buyer_tax_cd String COMMENT '交易对手税号',
                                        buyer_invoice_cnt int COMMENT '交易对手发票张数',
                                        buyer_invoice_cr_cnt int COMMENT '交易对手发票作废红冲张数',
                                        buyer_invoice_amt_sum decimal(20,2) COMMENT '交易对手发票合计金额',
                                        buyer_invoice_tax_sum decimal(20,2) COMMENT '交易对手发票合计税额',
                                        buyer_invoice_total_sum decimal(20,2) COMMENT '交易对手发票合计金额税额总和',
                                        is_delete String COMMENT '逻辑删除，1.是，0.否',
    --create_time date COMMENT '创建时间',
                                        create_user String COMMENT '创建人',
                                        modify_time timestamp COMMENT '修改时间',
                                        modify_user String COMMENT '修改人'
)COMMENT '【税金贷】交易对手表 ' partitioned by (create_time string)
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\0001' stored AS orc