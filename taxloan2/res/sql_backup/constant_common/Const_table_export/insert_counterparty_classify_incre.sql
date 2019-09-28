

/* **********************交易对手分类表增量数据导出到增量辅助表完成*********************** */



insert overwrite table dm_taxloan.counterparty_classify_incre
select
   mcht_cd,data_month,buyer_name,buyer_tax_cd,buyer_hist_month,buyer_invoice_cnt_l24m,buyer_invoice_total_sum_l24m,buyer_invoice_cnt_l12m,buyer_invoice_total_sum_l12m,buyer_invoice_cnt_l6m,buyer_invoice_total_sum_l6m,buyer_invoice_cnt_l3m,buyer_invoice_total_sum_l3m,buyer_invoice_cnt_l712m,buyer_invoice_total_sum_l712m,buyer_invoice_cnt_l1324m,buyer_invoice_total_sum_l1324m,buyer_invoice_cnt_l1m, buyer_invoice_total_sum_l1m,buyer_type,is_delete,create_time,create_user,modify_time,modify_user
from dm_taxloan.counterparty_classify e,
    (
        select table_name, max(export_date) as last_export_date from dm_taxloan.control_table group by table_name
    ) c
where
    c.table_name = 'counterparty_classify' and e.modify_time >c.last_export_date
