select
    mcht_cd,
    data_month,
    --近3月增值税开票统计
    sum(case when invoice_sc_cnt is null then 0 else invoice_sc_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_sc_cnt_l3m,
    sum(case when invoice_sc_cancel_cnt is null then 0 else invoice_sc_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_sc_cancel_cnt_l3m,
    sum(case when invoice_sc_red_cnt is null then 0 else invoice_sc_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_sc_red_cnt_l3m,
    sum(case when invoice_sc_total_sum is null then 0 else invoice_sc_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_sc_total_sum_l3m,
    sum(case when invoice_cnt is null then 0 else invoice_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_cnt_l3m,
    sum(case when invoice_cr_cnt is null then 0 else invoice_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_cr_cnt_l3m,
    sum(case when invoice_cancel_cnt is null then 0 else invoice_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_c_all_cnt_l3m,
    sum(case when invoice_red_cnt is null then 0 else invoice_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_r_all_cnt_l3m,
    sum(case when invoice_total_sum is null then 0 else invoice_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_total_sum_l3m,
    sum(case when invoice_total_r_sum is null then 0 else invoice_total_r_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_total_r_sum_l3m,
    sum(case when invoice_total_c_sum is null then 0 else invoice_total_c_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_total_c_sum_l3m,

    --近3月增值税专用发票
    sum(case when invoice_s_cnt is null then 0 else invoice_s_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_s_cnt_l3m,
    sum(case when invoice_s_cancel_cnt is null then 0 else invoice_s_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_s_cancel_cnt_l3m,
    sum(case when invoice_s_red_cnt is null then 0 else invoice_s_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_s_red_cnt_l3m,
    sum(case when invoice_s_total_sum is null then 0 else invoice_s_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_s_total_sum_l3m,
    sum(case when invoice_c_cnt is null then 0 else invoice_c_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_c_cnt_l3m,
    sum(case when invoice_c_cancel_cnt is null then 0 else invoice_c_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_c_cancel_cnt_l3m,
    sum(case when invoice_c_red_cnt is null then 0 else invoice_c_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_c_red_cnt_l3m,
    sum(case when invoice_c_total_sum is null then 0 else invoice_c_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_c_total_sum_l3m,
    sum(case when invoice_t_cnt is null then 0 else invoice_t_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_t_cnt_l3m,
    sum(case when invoice_t_cancel_cnt is null then 0 else invoice_t_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_t_cancel_cnt_l3m,
    sum(case when invoice_t_red_cnt is null then 0 else invoice_t_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_t_red_cnt_l3m,
    sum(case when invoice_t_total_sum is null then 0 else invoice_t_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_t_total_sum_l3m,
    sum(case when invoice_p_cnt is null then 0 else invoice_p_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_p_cnt_l3m,
    sum(case when invoice_p_cancel_cnt is null then 0 else invoice_p_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_p_cancel_cnt_l3m,
    sum(case when invoice_p_red_cnt is null then 0 else invoice_p_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_p_red_cnt_l3m,
    sum(case when invoice_p_total_sum is null then 0 else invoice_p_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_p_total_sum_l3m,
    sum(case when invoice_e_cnt is null then 0 else invoice_e_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_e_cnt_l3m,
    sum(case when invoice_e_cancel_cnt is null then 0 else invoice_e_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_e_cancel_cnt_l3m,
    sum(case when invoice_e_red_cnt is null then 0 else invoice_e_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_e_red_cnt_l3m,
    sum(case when invoice_e_total_sum is null then 0 else invoice_e_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_e_total_sum_l3m,
    --近3月卷式发票
    sum(case when invoice_r_cnt is null then 0 else invoice_r_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_r_cnt_l3m,
    sum(case when invoice_r_cancel_cnt is null then 0 else invoice_r_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_r_cancel_cnt_l3m,
    sum(case when invoice_r_red_cnt is null then 0 else invoice_r_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_r_red_cnt_l3m,
    sum(case when invoice_r_total_sum is null then 0 else invoice_r_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_r_total_sum_l3m,
    --近3月金额小于100发票
    sum(case when invoice_lt100_cnt is null then 0 else invoice_lt100_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt100_cnt_l3m,
    sum(case when invoice_lt100_cr_cnt is null then 0 else invoice_lt100_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt100_cr_cnt_l3m,
    sum(case when invoice_lt100_total_sum is null then 0 else invoice_lt100_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt100_total_sum_l3m,
    --近3月金额小于1000（且大于等于100）发票
    sum(case when invoice_lt1000_cnt is null then 0 else invoice_lt1000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt1000_cnt_l3m,
    sum(case when invoice_lt1000_cr_cnt is null then 0 else invoice_lt1000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt1000_cr_cnt_l3m,
    sum(case when invoice_lt1000_total_sum is null then 0 else invoice_lt1000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt1000_total_sum_l3m,
    --近3月金额小于2500（且大于等于1000）发票
    sum(case when invoice_lt2500_cnt is null then 0 else invoice_lt2500_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt2500_cnt_l3m,
    sum(case when invoice_lt2500_cr_cnt is null then 0 else invoice_lt2500_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt2500_cr_cnt_l3m,
    sum(case when invoice_lt2500_total_sum is null then 0 else invoice_lt2500_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt2500_total_sum_l3m,
    --近3月金额小于5000（且大于等于2500）发票
    sum(case when invoice_lt5000_cnt is null then 0 else invoice_lt5000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt5000_cnt_l3m,
    sum(case when invoice_lt5000_cr_cnt is null then 0 else invoice_lt5000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt5000_cr_cnt_l3m,
    sum(case when invoice_lt5000_total_sum is null then 0 else invoice_lt5000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt5000_total_sum_l3m,
    --近3月金额小于10000（且大于等于5000）发票
    sum(case when invoice_lt10000_cnt is null then 0 else invoice_lt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt10000_cnt_l3m,
    sum(case when invoice_lt10000_cr_cnt is null then 0 else invoice_lt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt10000_cr_cnt_l3m,
    sum(case when invoice_lt10000_total_sum is null then 0 else invoice_lt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_lt10000_total_sum_l3m,
    --近3月金额大于10000发票
    sum(case when invoice_gt10000_cnt is null then 0 else invoice_gt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_gt10000_cnt_l3m,
    sum(case when invoice_gt10000_cr_cnt is null then 0 else invoice_gt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_gt10000_cr_cnt_l3m,
    sum(case when invoice_gt10000_total_sum is null then 0 else invoice_gt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_gt10000_total_sum_l3m,
    --近3月附有清单的发票
    sum(case when invoice_detail_cnt is null then 0 else invoice_detail_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_detail_cnt_l3m,
    sum(case when invoice_detail_cr_cnt is null then 0 else invoice_detail_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_detail_cr_cnt_l3m,
    sum(case when invoice_detail_total_sum is null then 0 else invoice_detail_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 2 preceding and current row) as invoice_detail_total_sum_l3m,

    --近6月增值税开票统计
    sum(case when invoice_sc_cnt is null then 0 else invoice_sc_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_sc_cnt_l6m,
    sum(case when invoice_sc_cancel_cnt is null then 0 else invoice_sc_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_sc_cancel_cnt_l6m,
    sum(case when invoice_sc_red_cnt is null then 0 else invoice_sc_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_sc_red_cnt_l6m,
    sum(case when invoice_sc_total_sum is null then 0 else invoice_sc_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_sc_total_sum_l6m,
    --近6月所有发票
    sum(case when invoice_cnt is null then 0 else invoice_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_cnt_l6m,
    sum(case when invoice_cr_cnt is null then 0 else invoice_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_cr_cnt_l6m,
    sum(case when invoice_cancel_cnt is null then 0 else invoice_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_c_all_cnt_l6m,
    sum(case when invoice_red_cnt is null then 0 else invoice_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_r_all_cnt_l6m,
    sum(case when invoice_total_sum is null then 0 else invoice_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_total_sum_l6m,
    sum(case when invoice_total_r_sum is null then 0 else invoice_total_r_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_total_r_sum_l6m,
    sum(case when invoice_total_c_sum is null then 0 else invoice_total_c_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_total_c_sum_l6m,
    --近6月增值税专用发票
    sum(case when invoice_s_cnt is null then 0 else invoice_s_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_s_cnt_l6m,
    sum(case when invoice_s_cancel_cnt is null then 0 else invoice_s_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_s_cancel_cnt_l6m,
    sum(case when invoice_s_red_cnt is null then 0 else invoice_s_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_s_red_cnt_l6m,
    sum(case when invoice_s_total_sum is null then 0 else invoice_s_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_s_total_sum_l6m,
    --近6月增值税普通发票
    sum(case when invoice_c_cnt is null then 0 else invoice_c_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_c_cnt_l6m,
    sum(case when invoice_c_cancel_cnt is null then 0 else invoice_c_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_c_cancel_cnt_l6m,
    sum(case when invoice_c_red_cnt is null then 0 else invoice_c_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_c_red_cnt_l6m,
    sum(case when invoice_c_total_sum is null then 0 else invoice_c_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_c_total_sum_l6m,
    --近6月运输发票
    sum(case when invoice_t_cnt is null then 0 else invoice_t_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_t_cnt_l6m,
    sum(case when invoice_t_cancel_cnt is null then 0 else invoice_t_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_t_cancel_cnt_l6m,
    sum(case when invoice_t_red_cnt is null then 0 else invoice_t_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_t_red_cnt_l6m,
    sum(case when invoice_t_total_sum is null then 0 else invoice_t_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_t_total_sum_l6m,
    --近6月纸质发票
    sum(case when invoice_p_cnt is null then 0 else invoice_p_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_p_cnt_l6m,
    sum(case when invoice_p_cancel_cnt is null then 0 else invoice_p_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_p_cancel_cnt_l6m,
    sum(case when invoice_p_red_cnt is null then 0 else invoice_p_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_p_red_cnt_l6m,
    sum(case when invoice_p_total_sum is null then 0 else invoice_p_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_p_total_sum_l6m,
    --近6月电子发票
    sum(case when invoice_e_cnt is null then 0 else invoice_e_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_e_cnt_l6m,
    sum(case when invoice_e_cancel_cnt is null then 0 else invoice_e_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_e_cancel_cnt_l6m,
    sum(case when invoice_e_red_cnt is null then 0 else invoice_e_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_e_red_cnt_l6m,
    sum(case when invoice_e_total_sum is null then 0 else invoice_e_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_e_total_sum_l6m,
    --近6月卷式发票
    sum(case when invoice_r_cnt is null then 0 else invoice_r_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_r_cnt_l6m,
    sum(case when invoice_r_cancel_cnt is null then 0 else invoice_r_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_r_cancel_cnt_l6m,
    sum(case when invoice_r_red_cnt is null then 0 else invoice_r_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_r_red_cnt_l6m,
    sum(case when invoice_r_total_sum is null then 0 else invoice_r_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_r_total_sum_l6m,
    --近6月金额小于100发票
    sum(case when invoice_lt100_cnt is null then 0 else invoice_lt100_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt100_cnt_l6m,
    sum(case when invoice_lt100_cr_cnt is null then 0 else invoice_lt100_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt100_cr_cnt_l6m,
    sum(case when invoice_lt100_total_sum is null then 0 else invoice_lt100_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt100_total_sum_l6m,
    --近6月金额小于1000（且大于等于100）发票
    sum(case when invoice_lt1000_cnt is null then 0 else invoice_lt1000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt1000_cnt_l6m,
    sum(case when invoice_lt1000_cr_cnt is null then 0 else invoice_lt1000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt1000_cr_cnt_l6m,
    sum(case when invoice_lt1000_total_sum is null then 0 else invoice_lt1000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt1000_total_sum_l6m,
    --近6月金额小于2500（且大于等于1000）发票
    sum(case when invoice_lt2500_cnt is null then 0 else invoice_lt2500_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt2500_cnt_l6m,
    sum(case when invoice_lt2500_cr_cnt is null then 0 else invoice_lt2500_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt2500_cr_cnt_l6m,
    sum(case when invoice_lt2500_total_sum is null then 0 else invoice_lt2500_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt2500_total_sum_l6m,
    --近6月金额小于5000（且大于等于2500）发票
    sum(case when invoice_lt5000_cnt is null then 0 else invoice_lt5000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt5000_cnt_l6m,
    sum(case when invoice_lt5000_cr_cnt is null then 0 else invoice_lt5000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt5000_cr_cnt_l6m,
    sum(case when invoice_lt5000_total_sum is null then 0 else invoice_lt5000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt5000_total_sum_l6m,
    --近6月金额小于10000（且大于等于5000）发票
    sum(case when invoice_lt10000_cnt is null then 0 else invoice_lt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt10000_cnt_l6m,
    sum(case when invoice_lt10000_cr_cnt is null then 0 else invoice_lt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt10000_cr_cnt_l6m,
    sum(case when invoice_lt10000_total_sum is null then 0 else invoice_lt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_lt10000_total_sum_l6m,
    --近6月金额大于10000发票
    sum(case when invoice_gt10000_cnt is null then 0 else invoice_gt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_gt10000_cnt_l6m,
    sum(case when invoice_gt10000_cr_cnt is null then 0 else invoice_gt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_gt10000_cr_cnt_l6m,
    sum(case when invoice_gt10000_total_sum is null then 0 else invoice_gt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_gt10000_total_sum_l6m,
    --近6月附有清单的发票
    sum(case when invoice_detail_cnt is null then 0 else invoice_detail_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_detail_cnt_l6m,
    sum(case when invoice_detail_cr_cnt is null then 0 else invoice_detail_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_detail_cr_cnt_l6m,
    sum(case when invoice_detail_total_sum is null then 0 else invoice_detail_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
        rows between 5 preceding and current row) as invoice_detail_total_sum_l6m

from ${Const_Common.DATABASE}.statistics_month t3