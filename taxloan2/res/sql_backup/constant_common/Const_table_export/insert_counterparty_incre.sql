

/* **********************交易对手表增量数据导出到增量辅助表完成*********************** */


insert overwrite table dm_taxloan.counterparty_incre
select
       mcht_cd,data_month,buyer_name,buyer_tax_cd,buyer_invoice_cnt,buyer_invoice_cr_cnt,buyer_invoice_amt_sum,buyer_invoice_tax_sum,buyer_invoice_total_sum,is_delete,create_time,create_user,modify_time,modify_user
from
    dm_taxloan.counterparty e,
    (
        select table_name, max(export_date) as last_export_date
        from dm_taxloan.control_table
        group by table_name
    ) c
where
    c.table_name = 'counterparty' and e.modify_time >c.last_export_date
