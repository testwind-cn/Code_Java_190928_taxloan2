

/* **********************月统计表增量数据导出到增量辅助表完成*********************** */




insert overwrite table dm_taxloan.statistics_month_incre
select
    mcht_cd,data_month,invoice_sc_cnt,invoice_sc_cancel_cnt,invoice_sc_red_cnt,invoice_sc_total_sum,invoice_cnt,invoice_cr_cnt,invoice_amt_sum,invoice_tax_sum,invoice_total_sum,invoice_cancel_cnt,invoice_red_cnt,invoice_total_c_sum,invoice_total_r_sum,invoice_s_cnt,invoice_s_cancel_cnt,invoice_s_red_cnt,invoice_s_amt_sum,invoice_s_tax_sum,invoice_s_total_sum, invoice_c_cnt,invoice_c_cancel_cnt,invoice_c_red_cnt,invoice_c_amt_sum,invoice_c_tax_sum,invoice_c_total_sum,invoice_t_cnt,invoice_t_cancel_cnt,invoice_t_red_cnt,invoice_t_amt_sum,invoice_t_tax_sum,invoice_t_total_sum,invoice_p_cnt,invoice_p_cancel_cnt,invoice_p_red_cnt,invoice_p_amt_sum,invoice_p_tax_sum,invoice_p_total_sum,invoice_e_cnt,invoice_e_cancel_cnt,invoice_e_red_cnt,invoice_e_amt_sum,invoice_e_tax_sum,invoice_e_total_sum,invoice_r_cnt,invoice_r_cancel_cnt,invoice_r_red_cnt,invoice_r_amt_sum,invoice_r_tax_sum,invoice_r_total_sum,invoice_red_cnt_rate,invoice_cancel_cnt_rate,invoice_red_total_sum_rate,invoice_cancel_total_sum_rate,invoice_lt100_cnt,invoice_lt100_cr_cnt,invoice_lt100_total_sum,invoice_lt1000_cnt,invoice_lt1000_cr_cnt,invoice_lt1000_total_sum,invoice_lt2500_cnt,invoice_lt2500_cr_cnt,invoice_lt2500_total_sum,invoice_lt5000_cnt,invoice_lt5000_cr_cnt,invoice_lt5000_total_sum,invoice_lt10000_cnt,invoice_lt10000_cr_cnt,invoice_lt10000_total_sum,invoice_gt10000_cnt,invoice_gt10000_cr_cnt,invoice_gt10000_total_sum,invoice_detail_cnt,invoice_detail_cr_cnt,invoice_detail_total_sum,buyer_cnt,buyer_cnt_all,buyer_a_cnt,buyer_b_cnt,buyer_c_cnt,buyer_f_cnt,is_delete,create_time,create_user,modify_time,modify_user
from
    dm_taxloan.statistics_month e,
    (
        select table_name, max(export_date) as last_export_date from dm_taxloan.control_table group by table_name
    ) c
where
    c.table_name = 'statistics_month' and e.modify_time >c.last_export_date
