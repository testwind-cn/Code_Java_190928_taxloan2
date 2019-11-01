

-- 交易对手分类表
-- DROP TABLE IF EXISTS dm_taxloan.counterparty_classify;
CREATE TABLE IF NOT EXISTS  ${hivevar:DATABASE_DEST}.counterparty_classify(
                                                 mcht_cd String COMMENT '销方商户编号',
                                                 data_month String COMMENT '统计月份',
                                                 buyer_name String COMMENT '交易对手（购方）名称',
                                                 buyer_tax_cd String COMMENT '交易对手税号',
                                                 buyer_hist_month int COMMENT '交易对手交易历史总月份数',
                                                 buyer_invoice_cnt_l24m int COMMENT '交易对手近24月开票张数',
                                                 buyer_invoice_total_sum_l24m decimal(20,2) COMMENT '交易对手近24月合计金额税额总和',
                                                 buyer_invoice_cnt_l12m int COMMENT '交易对手近12月开票张数',
                                                 buyer_invoice_total_sum_l12m decimal(20,2) COMMENT '交易对手近12月合计金额税额总和',
                                                 buyer_invoice_cnt_l6m int COMMENT '交易对手近6月开票张数',
                                                 buyer_invoice_total_sum_l6m decimal(20,2) COMMENT '交易对手近6月合计金额税额总和',
                                                 buyer_invoice_cnt_l3m int COMMENT '交易对手近3月开票张数',
                                                 buyer_invoice_total_sum_l3m decimal(20,2) COMMENT '交易对手近3月合计金额税额总和',

    --新增4个字段
                                                 buyer_invoice_cnt_l712m int COMMENT '交易对手近7-12月开票张数',
                                                 buyer_invoice_total_sum_l712m decimal(20,2) COMMENT '交易对手近7-12月合计金额税额总和',
                                                 buyer_invoice_cnt_l1324m int COMMENT '交易对手近13-24月开票张数',
                                                 buyer_invoice_total_sum_l1324m decimal(20,2) COMMENT '交易对手近13-24月合计金额税额总和',

                                                 buyer_invoice_cnt_l1m int COMMENT '交易对手近1月（统计当月）开票张数',
                                                 buyer_invoice_total_sum_l1m decimal(20,2) COMMENT '交易对手近1月（统计当月）合计金额税额总和',
                                                 buyer_type String COMMENT '交易对手类型',
                                                 is_delete String COMMENT '逻辑删除，1.是，0.否',
    --create_time date COMMENT '创建时间',
                                                 create_user String COMMENT '创建人',
                                                 modify_time timestamp COMMENT '修改时间',
                                                 modify_user String COMMENT '修改人'
)COMMENT '【税金贷】交易对手分类表 ' partitioned by (create_time string)
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\0001' stored AS orc