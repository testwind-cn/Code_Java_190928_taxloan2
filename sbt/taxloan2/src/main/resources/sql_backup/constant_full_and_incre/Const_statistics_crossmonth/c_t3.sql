



select
    t1.mcht_cd,
    t1.month,
    t2.invoice_sc_cnt,
    t2.invoice_sc_cancel_cnt,
    t2.invoice_sc_red_cnt,
    t2.invoice_sc_total_sum,
    t2.invoice_cnt,
    t2.invoice_cr_cnt,
    t2.invoice_total_sum,
    t2.invoice_cancel_cnt,
    t2.invoice_red_cnt,
    t2.invoice_total_c_sum ,
    t2.invoice_total_r_sum,

    t2.invoice_s_cnt,
    t2.invoice_s_cancel_cnt,
    t2.invoice_s_red_cnt,
    t2.invoice_s_total_sum,

    t2.invoice_c_cnt,
    t2.invoice_c_cancel_cnt,
    t2.invoice_c_red_cnt,
    t2.invoice_c_total_sum,

    t2.invoice_t_cnt,
    t2.invoice_t_cancel_cnt,
    t2.invoice_t_red_cnt,
    t2.invoice_t_total_sum,

    t2.invoice_p_cnt,
    t2.invoice_p_cancel_cnt,
    t2.invoice_p_red_cnt,
    t2.invoice_p_total_sum,

    t2.invoice_e_cnt,
    t2.invoice_e_cancel_cnt,
    t2.invoice_e_red_cnt,
    t2.invoice_e_total_sum,

    t2.invoice_r_cnt,
    t2.invoice_r_cancel_cnt,
    t2.invoice_r_red_cnt,
    t2.invoice_r_total_sum,

    t2.invoice_lt100_cnt,
    t2.invoice_lt100_cr_cnt,
    t2.invoice_lt100_total_sum,

    t2.invoice_lt1000_cnt,
    t2.invoice_lt1000_cr_cnt,
    t2.invoice_lt1000_total_sum,

    t2.invoice_lt2500_cnt,
    t2.invoice_lt2500_cr_cnt,
    t2.invoice_lt2500_total_sum,

    t2.invoice_lt5000_cnt,
    t2.invoice_lt5000_cr_cnt,
    t2.invoice_lt5000_total_sum,

    t2.invoice_lt10000_cnt,
    t2.invoice_lt10000_cr_cnt,
    t2.invoice_lt10000_total_sum,

    t2.invoice_gt10000_cnt,
    t2.invoice_gt10000_cr_cnt,
    t2.invoice_gt10000_total_sum,

    -- 附有清单的发票
    t2.invoice_detail_cnt ,
    t2.invoice_detail_cr_cnt,
    t2.invoice_detail_total_sum ,

    -- 统计月交易对手数目
    t2.buyer_cnt
from (
    select * from dm_taxloan.cross_month_tmp
) t1
left join (
    select * from dm_taxloan.statistics_month
) t2
on
    t1.mcht_cd=t2.mcht_cd and t1.month=t2.data_month

