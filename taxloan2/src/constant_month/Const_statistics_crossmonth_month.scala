package constant_month

import constant_common.Const_Common

object Const_statistics_crossmonth_month {

  val statistics_crossmonth_tmp =
    s"""
      |select
      |m.mcht_cd_m1 as mcht_cd,
      |(case when month(current_date())=1 then concat(year(current_date())-1,12)
      |		when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
      |		else concat(year(current_date()),"0",month(current_date())-1) end) as month_pre1
      |from
      |(
      |select
      |m1.mcht_cd as mcht_cd_m1,
      |m2.mcht_cd as mcht_cd_m2
      |from ${Const_Common.DATABASE}.mcht_tax m1
      |left join
      |(
      |select
      |	a.mcht_cd
      |from (
      |select
      |	mcht_cd,
      |	(case when month(current_date())=1 then concat(year(current_date())-1,12)
      |		when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
      |		else concat(year(current_date()),"0",month(current_date())-1) end) as data_month_pre1,
      |	data_month,
      |	row_number() over(partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM'))) desc) as rank --取当前表中最近一个月的数据
      |from ${Const_Common.DATABASE}.statistics_crossmonth
      |)a where a.data_month=a.data_month_pre1 and a.rank=1
      |)m2 on m1.mcht_cd=m2.mcht_cd
      |)m where m.mcht_cd_m2 is NULL
    """.stripMargin

//  val c_t3 =
//    """
//      |
//    """.stripMargin

  val r1_1 =
    s"""
      |select
      |	mcht_cd,
      |	data_month,
      |	--近3月增值税开票统计
      |	sum(case when invoice_sc_cnt is null then 0 else invoice_sc_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_sc_cnt_l3m,
      |	sum(case when invoice_sc_cancel_cnt is null then 0 else invoice_sc_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_sc_cancel_cnt_l3m,
      |	sum(case when invoice_sc_red_cnt is null then 0 else invoice_sc_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_sc_red_cnt_l3m,
      |	sum(case when invoice_sc_total_sum is null then 0 else invoice_sc_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_sc_total_sum_l3m,
      |	sum(case when invoice_cnt is null then 0 else invoice_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_cnt_l3m,
      |	sum(case when invoice_cr_cnt is null then 0 else invoice_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_cr_cnt_l3m,
      |	sum(case when invoice_cancel_cnt is null then 0 else invoice_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_c_all_cnt_l3m,
      |	sum(case when invoice_red_cnt is null then 0 else invoice_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_r_all_cnt_l3m,
      |	sum(case when invoice_total_sum is null then 0 else invoice_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_total_sum_l3m,
      |	sum(case when invoice_total_r_sum is null then 0 else invoice_total_r_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_total_r_sum_l3m,
      |	sum(case when invoice_total_c_sum is null then 0 else invoice_total_c_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_total_c_sum_l3m,
      |
      |	--近3月增值税专用发票
      |	sum(case when invoice_s_cnt is null then 0 else invoice_s_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_s_cnt_l3m,
      |	sum(case when invoice_s_cancel_cnt is null then 0 else invoice_s_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_s_cancel_cnt_l3m,
      |	sum(case when invoice_s_red_cnt is null then 0 else invoice_s_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_s_red_cnt_l3m,
      |	sum(case when invoice_s_total_sum is null then 0 else invoice_s_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_s_total_sum_l3m,
      |	sum(case when invoice_c_cnt is null then 0 else invoice_c_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_c_cnt_l3m,
      |	sum(case when invoice_c_cancel_cnt is null then 0 else invoice_c_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_c_cancel_cnt_l3m,
      |	sum(case when invoice_c_red_cnt is null then 0 else invoice_c_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_c_red_cnt_l3m,
      |	sum(case when invoice_c_total_sum is null then 0 else invoice_c_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_c_total_sum_l3m,
      |	sum(case when invoice_t_cnt is null then 0 else invoice_t_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_t_cnt_l3m,
      |	sum(case when invoice_t_cancel_cnt is null then 0 else invoice_t_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_t_cancel_cnt_l3m,
      |	sum(case when invoice_t_red_cnt is null then 0 else invoice_t_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_t_red_cnt_l3m,
      |	sum(case when invoice_t_total_sum is null then 0 else invoice_t_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_t_total_sum_l3m,
      |	sum(case when invoice_p_cnt is null then 0 else invoice_p_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_p_cnt_l3m,
      |	sum(case when invoice_p_cancel_cnt is null then 0 else invoice_p_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_p_cancel_cnt_l3m,
      |	sum(case when invoice_p_red_cnt is null then 0 else invoice_p_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_p_red_cnt_l3m,
      |	sum(case when invoice_p_total_sum is null then 0 else invoice_p_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_p_total_sum_l3m,
      |	sum(case when invoice_e_cnt is null then 0 else invoice_e_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_e_cnt_l3m,
      |	sum(case when invoice_e_cancel_cnt is null then 0 else invoice_e_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_e_cancel_cnt_l3m,
      |	sum(case when invoice_e_red_cnt is null then 0 else invoice_e_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_e_red_cnt_l3m,
      |	sum(case when invoice_e_total_sum is null then 0 else invoice_e_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_e_total_sum_l3m,
      |	--近3月卷式发票
      |	sum(case when invoice_r_cnt is null then 0 else invoice_r_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_r_cnt_l3m,
      |	sum(case when invoice_r_cancel_cnt is null then 0 else invoice_r_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_r_cancel_cnt_l3m,
      |	sum(case when invoice_r_red_cnt is null then 0 else invoice_r_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_r_red_cnt_l3m,
      |	sum(case when invoice_r_total_sum is null then 0 else invoice_r_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_r_total_sum_l3m,
      |	--近3月金额小于100发票
      |	sum(case when invoice_lt100_cnt is null then 0 else invoice_lt100_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt100_cnt_l3m,
      |	sum(case when invoice_lt100_cr_cnt is null then 0 else invoice_lt100_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt100_cr_cnt_l3m,
      |	sum(case when invoice_lt100_total_sum is null then 0 else invoice_lt100_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt100_total_sum_l3m,
      |	--近3月金额小于1000（且大于等于100）发票
      |	sum(case when invoice_lt1000_cnt is null then 0 else invoice_lt1000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt1000_cnt_l3m,
      |	sum(case when invoice_lt1000_cr_cnt is null then 0 else invoice_lt1000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt1000_cr_cnt_l3m,
      |	sum(case when invoice_lt1000_total_sum is null then 0 else invoice_lt1000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt1000_total_sum_l3m,
      |	--近3月金额小于2500（且大于等于1000）发票
      |	sum(case when invoice_lt2500_cnt is null then 0 else invoice_lt2500_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt2500_cnt_l3m,
      |	sum(case when invoice_lt2500_cr_cnt is null then 0 else invoice_lt2500_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt2500_cr_cnt_l3m,
      |	sum(case when invoice_lt2500_total_sum is null then 0 else invoice_lt2500_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt2500_total_sum_l3m,
      |	--近3月金额小于5000（且大于等于2500）发票
      |	sum(case when invoice_lt5000_cnt is null then 0 else invoice_lt5000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt5000_cnt_l3m,
      |	sum(case when invoice_lt5000_cr_cnt is null then 0 else invoice_lt5000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt5000_cr_cnt_l3m,
      |	sum(case when invoice_lt5000_total_sum is null then 0 else invoice_lt5000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt5000_total_sum_l3m,
      |	--近3月金额小于10000（且大于等于5000）发票
      |	sum(case when invoice_lt10000_cnt is null then 0 else invoice_lt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt10000_cnt_l3m,
      |	sum(case when invoice_lt10000_cr_cnt is null then 0 else invoice_lt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt10000_cr_cnt_l3m,
      |	sum(case when invoice_lt10000_total_sum is null then 0 else invoice_lt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_lt10000_total_sum_l3m,
      |	--近3月金额大于10000发票
      |	sum(case when invoice_gt10000_cnt is null then 0 else invoice_gt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_gt10000_cnt_l3m,
      |	sum(case when invoice_gt10000_cr_cnt is null then 0 else invoice_gt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_gt10000_cr_cnt_l3m,
      |	sum(case when invoice_gt10000_total_sum is null then 0 else invoice_gt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_gt10000_total_sum_l3m,
      |	--近3月附有清单的发票
      |	sum(case when invoice_detail_cnt is null then 0 else invoice_detail_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_detail_cnt_l3m,
      |	sum(case when invoice_detail_cr_cnt is null then 0 else invoice_detail_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_detail_cr_cnt_l3m,
      |	sum(case when invoice_detail_total_sum is null then 0 else invoice_detail_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as invoice_detail_total_sum_l3m,
      |
      |	--近6月增值税开票统计
      |	sum(case when invoice_sc_cnt is null then 0 else invoice_sc_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_sc_cnt_l6m,
      |	sum(case when invoice_sc_cancel_cnt is null then 0 else invoice_sc_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_sc_cancel_cnt_l6m,
      |	sum(case when invoice_sc_red_cnt is null then 0 else invoice_sc_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_sc_red_cnt_l6m,
      |	sum(case when invoice_sc_total_sum is null then 0 else invoice_sc_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_sc_total_sum_l6m,
      |	--近6月所有发票
      |	sum(case when invoice_cnt is null then 0 else invoice_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_cnt_l6m,
      |	sum(case when invoice_cr_cnt is null then 0 else invoice_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_cr_cnt_l6m,
      |	sum(case when invoice_cancel_cnt is null then 0 else invoice_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_c_all_cnt_l6m,
      |	sum(case when invoice_red_cnt is null then 0 else invoice_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_r_all_cnt_l6m,
      |	sum(case when invoice_total_sum is null then 0 else invoice_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_total_sum_l6m,
      |	sum(case when invoice_total_r_sum is null then 0 else invoice_total_r_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_total_r_sum_l6m,
      |	sum(case when invoice_total_c_sum is null then 0 else invoice_total_c_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_total_c_sum_l6m,
      |	--近6月增值税专用发票
      |	sum(case when invoice_s_cnt is null then 0 else invoice_s_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_s_cnt_l6m,
      |	sum(case when invoice_s_cancel_cnt is null then 0 else invoice_s_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_s_cancel_cnt_l6m,
      |	sum(case when invoice_s_red_cnt is null then 0 else invoice_s_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_s_red_cnt_l6m,
      |	sum(case when invoice_s_total_sum is null then 0 else invoice_s_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_s_total_sum_l6m,
      |	--近6月增值税普通发票
      |	sum(case when invoice_c_cnt is null then 0 else invoice_c_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_c_cnt_l6m,
      |	sum(case when invoice_c_cancel_cnt is null then 0 else invoice_c_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_c_cancel_cnt_l6m,
      |	sum(case when invoice_c_red_cnt is null then 0 else invoice_c_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_c_red_cnt_l6m,
      |	sum(case when invoice_c_total_sum is null then 0 else invoice_c_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_c_total_sum_l6m,
      |	--近6月运输发票
      |	sum(case when invoice_t_cnt is null then 0 else invoice_t_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_t_cnt_l6m,
      |	sum(case when invoice_t_cancel_cnt is null then 0 else invoice_t_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_t_cancel_cnt_l6m,
      |	sum(case when invoice_t_red_cnt is null then 0 else invoice_t_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_t_red_cnt_l6m,
      |	sum(case when invoice_t_total_sum is null then 0 else invoice_t_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_t_total_sum_l6m,
      |	--近6月纸质发票
      |	sum(case when invoice_p_cnt is null then 0 else invoice_p_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_p_cnt_l6m,
      |	sum(case when invoice_p_cancel_cnt is null then 0 else invoice_p_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_p_cancel_cnt_l6m,
      |	sum(case when invoice_p_red_cnt is null then 0 else invoice_p_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_p_red_cnt_l6m,
      |	sum(case when invoice_p_total_sum is null then 0 else invoice_p_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_p_total_sum_l6m,
      |	--近6月电子发票
      |	sum(case when invoice_e_cnt is null then 0 else invoice_e_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_e_cnt_l6m,
      |	sum(case when invoice_e_cancel_cnt is null then 0 else invoice_e_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_e_cancel_cnt_l6m,
      |	sum(case when invoice_e_red_cnt is null then 0 else invoice_e_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_e_red_cnt_l6m,
      |	sum(case when invoice_e_total_sum is null then 0 else invoice_e_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_e_total_sum_l6m,
      |	--近6月卷式发票
      |	sum(case when invoice_r_cnt is null then 0 else invoice_r_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_r_cnt_l6m,
      |	sum(case when invoice_r_cancel_cnt is null then 0 else invoice_r_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_r_cancel_cnt_l6m,
      |	sum(case when invoice_r_red_cnt is null then 0 else invoice_r_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_r_red_cnt_l6m,
      |	sum(case when invoice_r_total_sum is null then 0 else invoice_r_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_r_total_sum_l6m,
      |	--近6月金额小于100发票
      |	sum(case when invoice_lt100_cnt is null then 0 else invoice_lt100_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt100_cnt_l6m,
      |	sum(case when invoice_lt100_cr_cnt is null then 0 else invoice_lt100_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt100_cr_cnt_l6m,
      |	sum(case when invoice_lt100_total_sum is null then 0 else invoice_lt100_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt100_total_sum_l6m,
      |	--近6月金额小于1000（且大于等于100）发票
      |	sum(case when invoice_lt1000_cnt is null then 0 else invoice_lt1000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt1000_cnt_l6m,
      |	sum(case when invoice_lt1000_cr_cnt is null then 0 else invoice_lt1000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt1000_cr_cnt_l6m,
      |	sum(case when invoice_lt1000_total_sum is null then 0 else invoice_lt1000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt1000_total_sum_l6m,
      |	--近6月金额小于2500（且大于等于1000）发票
      |	sum(case when invoice_lt2500_cnt is null then 0 else invoice_lt2500_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt2500_cnt_l6m,
      |	sum(case when invoice_lt2500_cr_cnt is null then 0 else invoice_lt2500_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt2500_cr_cnt_l6m,
      |	sum(case when invoice_lt2500_total_sum is null then 0 else invoice_lt2500_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt2500_total_sum_l6m,
      |	--近6月金额小于5000（且大于等于2500）发票
      |	sum(case when invoice_lt5000_cnt is null then 0 else invoice_lt5000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt5000_cnt_l6m,
      |	sum(case when invoice_lt5000_cr_cnt is null then 0 else invoice_lt5000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt5000_cr_cnt_l6m,
      |	sum(case when invoice_lt5000_total_sum is null then 0 else invoice_lt5000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt5000_total_sum_l6m,
      |	--近6月金额小于10000（且大于等于5000）发票
      |	sum(case when invoice_lt10000_cnt is null then 0 else invoice_lt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt10000_cnt_l6m,
      |	sum(case when invoice_lt10000_cr_cnt is null then 0 else invoice_lt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt10000_cr_cnt_l6m,
      |	sum(case when invoice_lt10000_total_sum is null then 0 else invoice_lt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_lt10000_total_sum_l6m,
      |	--近6月金额大于10000发票
      |	sum(case when invoice_gt10000_cnt is null then 0 else invoice_gt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_gt10000_cnt_l6m,
      |	sum(case when invoice_gt10000_cr_cnt is null then 0 else invoice_gt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_gt10000_cr_cnt_l6m,
      |	sum(case when invoice_gt10000_total_sum is null then 0 else invoice_gt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_gt10000_total_sum_l6m,
      |	--近6月附有清单的发票
      |	sum(case when invoice_detail_cnt is null then 0 else invoice_detail_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_detail_cnt_l6m,
      |	sum(case when invoice_detail_cr_cnt is null then 0 else invoice_detail_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_detail_cr_cnt_l6m,
      |	sum(case when invoice_detail_total_sum is null then 0 else invoice_detail_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as invoice_detail_total_sum_l6m
      |
      |from ${Const_Common.DATABASE}.statistics_month t3
    """.stripMargin

  val r1_2 =
    s"""
      |select
      |	mcht_cd,
      |	data_month,
      |	--近12月统计
      |	sum(case when invoice_sc_cnt is null then 0 else invoice_sc_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_sc_cnt_l12m,
      |	sum(case when invoice_sc_cancel_cnt is null then 0 else invoice_sc_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_sc_cancel_cnt_l12m,
      |	sum(case when invoice_sc_red_cnt is null then 0 else invoice_sc_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_sc_red_cnt_l12m,
      |	sum(case when invoice_sc_total_sum is null then 0 else invoice_sc_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_sc_total_sum_l12m,
      |	--近12月所有发票
      |	sum(case when invoice_cnt is null then 0 else invoice_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_cnt_l12m,
      |	sum(case when invoice_cr_cnt is null then 0 else invoice_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_cr_cnt_l12m,
      |	sum(case when invoice_cancel_cnt is null then 0 else invoice_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_c_all_cnt_l12m,
      |	sum(case when invoice_red_cnt is null then 0 else invoice_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_r_all_cnt_l12m,
      |	sum(case when invoice_total_sum is null then 0 else invoice_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_total_sum_l12m,
      |	sum(case when invoice_total_r_sum is null then 0 else invoice_total_r_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_total_r_sum_l12m,
      |	sum(case when invoice_total_c_sum is null then 0 else invoice_total_c_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_total_c_sum_l12m,
      |	--近12月增值税专用发票
      |	sum(case when invoice_s_cnt is null then 0 else invoice_s_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_s_cnt_l12m,
      |	sum(case when invoice_s_cancel_cnt is null then 0 else invoice_s_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_s_cancel_cnt_l12m,
      |	sum(case when invoice_s_red_cnt is null then 0 else invoice_s_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_s_red_cnt_l12m,
      |	sum(case when invoice_s_total_sum is null then 0 else invoice_s_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_s_total_sum_l12m,
      |	--近12月增值税普通发票
      |	sum(case when invoice_c_cnt is null then 0 else invoice_c_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_c_cnt_l12m,
      |	sum(case when invoice_c_cancel_cnt is null then 0 else invoice_c_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_c_cancel_cnt_l12m,
      |	sum(case when invoice_c_red_cnt is null then 0 else invoice_c_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_c_red_cnt_l12m,
      |	sum(case when invoice_c_total_sum is null then 0 else invoice_c_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_c_total_sum_l12m,
      |	--近12月运输发票
      |	sum(case when invoice_t_cnt is null then 0 else invoice_t_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_t_cnt_l12m,
      |	sum(case when invoice_t_cancel_cnt is null then 0 else invoice_t_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_t_cancel_cnt_l12m,
      |	sum(case when invoice_t_red_cnt is null then 0 else invoice_t_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_t_red_cnt_l12m,
      |	sum(case when invoice_t_total_sum is null then 0 else invoice_t_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_t_total_sum_l12m,
      |	--近12月纸质发票
      |	sum(case when invoice_p_cnt is null then 0 else invoice_p_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_p_cnt_l12m,
      |	sum(case when invoice_p_cancel_cnt is null then 0 else invoice_p_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_p_cancel_cnt_l12m,
      |	sum(case when invoice_p_red_cnt is null then 0 else invoice_p_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_p_red_cnt_l12m,
      |	sum(case when invoice_p_total_sum is null then 0 else invoice_p_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_p_total_sum_l12m,
      |	--近12月电子发票
      |	sum(case when invoice_e_cnt is null then 0 else invoice_e_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_e_cnt_l12m,
      |	sum(case when invoice_e_cancel_cnt is null then 0 else invoice_e_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_e_cancel_cnt_l12m,
      |	sum(case when invoice_e_red_cnt is null then 0 else invoice_e_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_e_red_cnt_l12m,
      |	sum(case when invoice_e_total_sum is null then 0 else invoice_e_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_e_total_sum_l12m,
      |	--近12月卷式发票
      |	sum(case when invoice_r_cnt is null then 0 else invoice_r_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_r_cnt_l12m,
      |	sum(case when invoice_r_cancel_cnt is null then 0 else invoice_r_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_r_cancel_cnt_l12m,
      |	sum(case when invoice_r_red_cnt is null then 0 else invoice_r_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_r_red_cnt_l12m,
      |	sum(case when invoice_r_total_sum is null then 0 else invoice_r_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_r_total_sum_l12m,
      |	--近12月金额小于100发票
      |	sum(case when invoice_lt100_cnt is null then 0 else invoice_lt100_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt100_cnt_l12m,
      |	sum(case when invoice_lt100_cr_cnt is null then 0 else invoice_lt100_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt100_cr_cnt_l12m,
      |	sum(case when invoice_lt100_total_sum is null then 0 else invoice_lt100_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt100_total_sum_l12m,
      |	--近12月金额小于1000（且大于等于100）发票
      |	sum(case when invoice_lt1000_cnt is null then 0 else invoice_lt1000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt1000_cnt_l12m,
      |	sum(case when invoice_lt1000_cr_cnt is null then 0 else invoice_lt1000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt1000_cr_cnt_l12m,
      |	sum(case when invoice_lt1000_total_sum is null then 0 else invoice_lt1000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt1000_total_sum_l12m,
      |	--近12月金额小于2500（且大于等于1000）发票
      |	sum(case when invoice_lt2500_cnt is null then 0 else invoice_lt2500_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt2500_cnt_l12m,
      |	sum(case when invoice_lt2500_cr_cnt is null then 0 else invoice_lt2500_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt2500_cr_cnt_l12m,
      |	sum(case when invoice_lt2500_total_sum is null then 0 else invoice_lt2500_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt2500_total_sum_l12m,
      |	--近12月金额小于5000（且大于等于2500）发票
      |	sum(case when invoice_lt5000_cnt is null then 0 else invoice_lt5000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt5000_cnt_l12m,
      |	sum(case when invoice_lt5000_cr_cnt is null then 0 else invoice_lt5000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt5000_cr_cnt_l12m,
      |	sum(case when invoice_lt5000_total_sum is null then 0 else invoice_lt5000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt5000_total_sum_l12m,
      |	--近12月金额小于10000（且大于等于5000）发票
      |	sum(case when invoice_lt10000_cnt is null then 0 else invoice_lt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt10000_cnt_l12m,
      |	sum(case when invoice_lt10000_cr_cnt is null then 0 else invoice_lt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt10000_cr_cnt_l12m,
      |	sum(case when invoice_lt10000_total_sum is null then 0 else invoice_lt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_lt10000_total_sum_l12m,
      |	--近12月金额大于10000发票
      |	sum(case when invoice_gt10000_cnt is null then 0 else invoice_gt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_gt10000_cnt_l12m,
      |	sum(case when invoice_gt10000_cr_cnt is null then 0 else invoice_gt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_gt10000_cr_cnt_l12m,
      |	sum(case when invoice_gt10000_total_sum is null then 0 else invoice_gt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_gt10000_total_sum_l12m,
      |	--近12月附有清单的发票
      |	sum(case when invoice_detail_cnt is null then 0 else invoice_detail_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_detail_cnt_l12m,
      |	sum(case when invoice_detail_cr_cnt is null then 0 else invoice_detail_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_detail_cr_cnt_l12m,
      |	sum(case when invoice_detail_total_sum is null then 0 else invoice_detail_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as invoice_detail_total_sum_l12m,
      |	--近24月增值税开票统计
      |	sum(case when invoice_sc_cnt is null then 0 else invoice_sc_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_sc_cnt_l24m,
      |	sum(case when invoice_sc_cancel_cnt is null then 0 else invoice_sc_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_sc_cancel_cnt_l24m,
      |	sum(case when invoice_sc_red_cnt is null then 0 else invoice_sc_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_sc_red_cnt_l24m,
      |	sum(case when invoice_sc_total_sum is null then 0 else invoice_sc_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_sc_total_sum_l24m,
      |	--近24月所有发票
      |	sum(case when invoice_cnt is null then 0 else invoice_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_cnt_l24m,
      |	sum(case when invoice_cr_cnt is null then 0 else invoice_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_cr_cnt_l24m,
      |	sum(case when invoice_cancel_cnt is null then 0 else invoice_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_c_all_cnt_l24m,
      |	sum(case when invoice_red_cnt is null then 0 else invoice_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_r_all_cnt_l24m,
      |	sum(case when invoice_total_sum is null then 0 else invoice_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_total_sum_l24m,
      |	sum(case when invoice_total_r_sum is null then 0 else invoice_total_r_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_total_r_sum_l24m,
      |	sum(case when invoice_total_c_sum is null then 0 else invoice_total_c_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_total_c_sum_l24m,
      |	--近24月增值税专用发票
      |	sum(case when invoice_s_cnt is null then 0 else invoice_s_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_s_cnt_l24m,
      |	sum(case when invoice_s_cancel_cnt is null then 0 else invoice_s_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_s_cancel_cnt_l24m,
      |	sum(case when invoice_s_red_cnt is null then 0 else invoice_s_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_s_red_cnt_l24m,
      |	sum(case when invoice_s_total_sum is null then 0 else invoice_s_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_s_total_sum_l24m,
      |	--近24月增值税普通发票
      |	sum(case when invoice_c_cnt is null then 0 else invoice_c_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_c_cnt_l24m,
      |	sum(case when invoice_c_cancel_cnt is null then 0 else invoice_c_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_c_cancel_cnt_l24m,
      |	sum(case when invoice_c_red_cnt is null then 0 else invoice_c_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_c_red_cnt_l24m,
      |	sum(case when invoice_c_total_sum is null then 0 else invoice_c_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_c_total_sum_l24m,
      |	--近24月运输发票
      |	sum(case when invoice_t_cnt is null then 0 else invoice_t_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_t_cnt_l24m,
      |	sum(case when invoice_t_cancel_cnt is null then 0 else invoice_t_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_t_cancel_cnt_l24m,
      |	sum(case when invoice_t_red_cnt is null then 0 else invoice_t_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_t_red_cnt_l24m,
      |	sum(case when invoice_t_total_sum is null then 0 else invoice_t_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_t_total_sum_l24m,
      |	--近24月纸质发票
      |	sum(case when invoice_p_cnt is null then 0 else invoice_p_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_p_cnt_l24m,
      |	sum(case when invoice_p_cancel_cnt is null then 0 else invoice_p_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_p_cancel_cnt_l24m,
      |	sum(case when invoice_p_red_cnt is null then 0 else invoice_p_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_p_red_cnt_l24m,
      |	sum(case when invoice_p_total_sum is null then 0 else invoice_p_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_p_total_sum_l24m,
      |	--近24月电子发票
      |	sum(case when invoice_e_cnt is null then 0 else invoice_e_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_e_cnt_l24m,
      |	sum(case when invoice_e_cancel_cnt is null then 0 else invoice_e_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_e_cancel_cnt_l24m,
      |	sum(case when invoice_e_red_cnt is null then 0 else invoice_e_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_e_red_cnt_l24m,
      |	sum(case when invoice_e_total_sum is null then 0 else invoice_e_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_e_total_sum_l24m,
      |	--近24月卷式发票
      |	sum(case when invoice_r_cnt is null then 0 else invoice_r_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_r_cnt_l24m,
      |	sum(case when invoice_r_cancel_cnt is null then 0 else invoice_r_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_r_cancel_cnt_l24m,
      |	sum(case when invoice_r_red_cnt is null then 0 else invoice_r_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_r_red_cnt_l24m,
      |	sum(case when invoice_r_total_sum is null then 0 else invoice_r_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_r_total_sum_l24m,
      |	--近24月金额小于100发票
      |	sum(case when invoice_lt100_cnt is null then 0 else invoice_lt100_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt100_cnt_l24m,
      |	sum(case when invoice_lt100_cr_cnt is null then 0 else invoice_lt100_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt100_cr_cnt_l24m,
      |	sum(case when invoice_lt100_total_sum is null then 0 else invoice_lt100_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt100_total_sum_l24m,
      |	--近24月金额小于1000（且大于等于100）发票
      |	sum(case when invoice_lt1000_cnt is null then 0 else invoice_lt1000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt1000_cnt_l24m,
      |	sum(case when invoice_lt1000_cr_cnt is null then 0 else invoice_lt1000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt1000_cr_cnt_l24m,
      |	sum(case when invoice_lt1000_total_sum is null then 0 else invoice_lt1000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt1000_total_sum_l24m,
      |	--近24月金额小于2500（且大于等于1000）发票
      |	sum(case when invoice_lt2500_cnt is null then 0 else invoice_lt2500_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt2500_cnt_l24m,
      |	sum(case when invoice_lt2500_cr_cnt is null then 0 else invoice_lt2500_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt2500_cr_cnt_l24m,
      |	sum(case when invoice_lt2500_total_sum is null then 0 else invoice_lt2500_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt2500_total_sum_l24m,
      |	--近24月金额小于5000（且大于等于2500）发票
      |	sum(case when invoice_lt5000_cnt is null then 0 else invoice_lt5000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt5000_cnt_l24m,
      |	sum(case when invoice_lt5000_cr_cnt is null then 0 else invoice_lt5000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt5000_cr_cnt_l24m,
      |	sum(case when invoice_lt5000_total_sum is null then 0 else invoice_lt5000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt5000_total_sum_l24m,
      |	--近24月金额小于10000（且大于等于5000）发票
      |	sum(case when invoice_lt10000_cnt is null then 0 else invoice_lt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt10000_cnt_l24m,
      |	sum(case when invoice_lt10000_cr_cnt is null then 0 else invoice_lt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt10000_cr_cnt_l24m,
      |	sum(case when invoice_lt10000_total_sum is null then 0 else invoice_lt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_lt10000_total_sum_l24m,
      |	--近24月金额大于10000发票
      |	sum(case when invoice_gt10000_cnt is null then 0 else invoice_gt10000_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_gt10000_cnt_l24m,
      |	sum(case when invoice_gt10000_cr_cnt is null then 0 else invoice_gt10000_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_gt10000_cr_cnt_l24m,
      |	sum(case when invoice_gt10000_total_sum is null then 0 else invoice_gt10000_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_gt10000_total_sum_l24m,
      |	--近24月附有清单的发票
      |	sum(case when invoice_detail_cnt is null then 0 else invoice_detail_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_detail_cnt_l24m,
      |	sum(case when invoice_detail_cr_cnt is null then 0 else invoice_detail_cr_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_detail_cr_cnt_l24m,
      |	sum(case when invoice_detail_total_sum is null then 0 else invoice_detail_total_sum end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as invoice_detail_total_sum_l24m,
      |	--近3/6/12/24个月的交易对手数----以下指标另外单独计算
      |--	sum(case when buyer_cnt is null then 0 else buyer_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |--	rows between 2 preceding and current row) as buyer_cnt_l3m,
      |--	sum(case when buyer_cnt is null then 0 else buyer_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |--	rows between 5 preceding and current row) as buyer_cnt_l6m,
      |--	sum(case when buyer_cnt is null then 0 else buyer_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |--	rows between 11 preceding and current row) as buyer_cnt_l12m,
      |--	sum(case when buyer_cnt is null then 0 else buyer_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |--	rows between 23 preceding and current row) as buyer_cnt_l24m,
      |--	sum(case when buyer_cnt is null then 0 else buyer_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |--	rows between 11 preceding and 7 preceding) as buyer_cnt_l712m,
      |	sum(case when invoice_red_cnt is null then 0 else invoice_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and 7 preceding) as invoice_r_all_cnt_l712m,
      |	sum(case when invoice_cancel_cnt is null then 0 else invoice_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and 7 preceding) as invoice_c_all_cnt_l712m,
      |--	sum(case when buyer_cnt is null then 0 else buyer_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |--	rows between 23 preceding and 13 preceding) as buyer_cnt_l1324m,
      |	sum(case when invoice_red_cnt is null then 0 else invoice_red_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and 13 preceding) as invoice_r_all_cnt_l1324m,
      |	sum(case when invoice_cancel_cnt is null then 0 else invoice_cancel_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and 13 preceding) as invoice_c_all_cnt_l1324m,
      |
      |--新增两个指标，
      |	sum(case when invoice_cnt is null then 0 else invoice_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 11 preceding and 7 preceding) as invoice_cnt_l712m, --7-12月所有发票开票张数
      |	sum(case when invoice_cnt is null then 0 else invoice_cnt end) over (partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))
      |	rows between 23 preceding and 13 preceding) as invoice_cnt_l1324m  --13-24月所有发票开票张数
      |
      |from ${Const_Common.DATABASE}.statistics_month t3
    """.stripMargin

  /***************************************************************************/

  val r1_4_common =
    s"""
      |select
      |a.sellertaxno,
      |a.data_month as month_a,
      |b.data_month as month_b,
      |b.buyertaxno,
      |b.buyername
      |from
      |
      |(select distinct sellertaxno,data_month from ${Const_Common.DATABASE}.saleinvoice_tmp_all
      |)a
      |left join (
      |select distinct sellertaxno,data_month,buyername,buyertaxno from ${Const_Common.DATABASE}.saleinvoice_tmp_all
      |)b on a.sellertaxno=b.sellertaxno
    """.stripMargin

  val r1_4_3m =
    """
      |select
      |a.sellertaxno,
      |a.month_a,
      |count(distinct a.buyertaxno) as buyer_cnt_l3m
      |from
      |(
      |select
      |*
      | from r1_4_common
      |where datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))<92
      |and datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))>0
      |)a group by a.sellertaxno,a.month_a
    """.stripMargin
  val r1_4_6m =
    """
      |select
      |a.sellertaxno,
      |a.month_a,
      |count(distinct a.buyertaxno) as buyer_cnt_l6m
      |from
      |(
      |select
      |*
      | from r1_4_common
      |where datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))<182
      |and datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))>0
      |)a group by a.sellertaxno,a.month_a
    """.stripMargin

  val r1_4_12m =
    """
      |select
      |a.sellertaxno,
      |a.month_a,
      |count(distinct a.buyertaxno) as buyer_cnt_l12m
      |from
      |(
      |select
      |*
      | from r1_4_common
      |where datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))<366
      |and datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))>0
      |)a group by a.sellertaxno,a.month_a
    """.stripMargin

  val r1_4_24m =
    """
      |select
      |a.sellertaxno,
      |a.month_a,
      |count(distinct a.buyertaxno) as buyer_cnt_l24m
      |from
      |(
      |select
      |*
      | from r1_4_common
      |where datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))<732
      |and datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))>0
      |)a group by a.sellertaxno,a.month_a
    """.stripMargin

  val r1_4_712m =
    """
      |select
      |a.sellertaxno,
      |a.month_a,
      |count(distinct a.buyertaxno) as buyer_cnt_l712m
      |from
      |(
      |select
      |*
      | from r1_4_common
      |where datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))<366
      |and datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))>212
      |)a group by a.sellertaxno,a.month_a
    """.stripMargin

  val r1_4_1324m =
    """
      |select
      |a.sellertaxno,
      |a.month_a,
      |count(distinct a.buyertaxno) as buyer_cnt_l1324m
      |from
      |(
      |select
      |*
      | from r1_4_common
      |where datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))<732
      |and datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))>366
      |)a group by a.sellertaxno,a.month_a
    """.stripMargin

  val r1_4 =
    s"""
      |select
      |m3.sellertaxno,
      |m3.month_a,
      |m3.buyer_cnt_l3m,
      |m6.buyer_cnt_l6m,
      |m12.buyer_cnt_l12m,
      |m24.buyer_cnt_l24m,
      |m712.buyer_cnt_l712m,
      |m1324.buyer_cnt_l1324m
      |
      | from
      |(
      |select distinct sellertaxno from ${Const_Common.DATABASE}.saleinvoice_tmp_all
      |)a
      |left join
      | r1_4_3m m3
      |on a.sellertaxno=m3.sellertaxno
      |left join r1_4_6m m6
      |on 	m3.sellertaxno=m6.sellertaxno and m3.month_a=m6.month_a
      |left join r1_4_12m m12
      |on 	m12.sellertaxno=m6.sellertaxno and m12.month_a=m6.month_a
      |left join r1_4_24m m24
      |on 	m12.sellertaxno=m24.sellertaxno and m12.month_a=m24.month_a
      |left join r1_4_712m m712
      |on 	m712.sellertaxno=m24.sellertaxno and m712.month_a=m24.month_a
      |left join r1_4_1324m m1324
      |on 	m712.sellertaxno=m1324.sellertaxno and m712.month_a=m1324.month_a
    """.stripMargin
  /***************************************************************************/


  val r1_3 =
    """
      |select
      |r1_1.mcht_cd,
      |r1_1.data_month,
      |
      |r1_1.invoice_sc_cnt_l3m,
      |r1_1.invoice_sc_cancel_cnt_l3m,
      |r1_1.invoice_sc_red_cnt_l3m,
      |r1_1.invoice_sc_total_sum_l3m,
      |r1_1.invoice_cnt_l3m,
      |r1_1.invoice_cr_cnt_l3m,
      |r1_1.invoice_c_all_cnt_l3m,
      |r1_1.invoice_r_all_cnt_l3m,
      |r1_1.invoice_total_sum_l3m,
      |r1_1.invoice_total_r_sum_l3m,
      |r1_1.invoice_total_c_sum_l3m,
      |r1_1.invoice_s_cnt_l3m,
      |r1_1.invoice_s_cancel_cnt_l3m,
      |r1_1.invoice_s_red_cnt_l3m,
      |r1_1.invoice_s_total_sum_l3m,
      |r1_1.invoice_c_cnt_l3m,
      |r1_1.invoice_c_cancel_cnt_l3m,
      |r1_1.invoice_c_red_cnt_l3m,
      |r1_1.invoice_c_total_sum_l3m,
      |r1_1.invoice_t_cnt_l3m,
      |r1_1.invoice_t_cancel_cnt_l3m,
      |r1_1.invoice_t_red_cnt_l3m,
      |r1_1.invoice_t_total_sum_l3m,
      |r1_1.invoice_p_cnt_l3m,
      |r1_1.invoice_p_cancel_cnt_l3m,
      |r1_1.invoice_p_red_cnt_l3m,
      |r1_1.invoice_p_total_sum_l3m,
      |r1_1.invoice_e_cnt_l3m,
      |r1_1.invoice_e_cancel_cnt_l3m,
      |r1_1.invoice_e_red_cnt_l3m,
      |r1_1.invoice_e_total_sum_l3m,
      |r1_1.invoice_r_cnt_l3m,
      |r1_1.invoice_r_cancel_cnt_l3m,
      |r1_1.invoice_r_red_cnt_l3m,
      |r1_1.invoice_r_total_sum_l3m,
      |r1_1.invoice_lt100_cnt_l3m,
      |r1_1.invoice_lt100_cr_cnt_l3m,
      |r1_1.invoice_lt100_total_sum_l3m,
      |r1_1.invoice_lt1000_cnt_l3m,
      |r1_1.invoice_lt1000_cr_cnt_l3m,
      |r1_1.invoice_lt1000_total_sum_l3m,
      |r1_1.invoice_lt2500_cnt_l3m,
      |r1_1.invoice_lt2500_cr_cnt_l3m,
      |r1_1.invoice_lt2500_total_sum_l3m,
      |r1_1.invoice_lt5000_cnt_l3m,
      |r1_1.invoice_lt5000_cr_cnt_l3m,
      |r1_1.invoice_lt5000_total_sum_l3m,
      |r1_1.invoice_lt10000_cnt_l3m,
      |r1_1.invoice_lt10000_cr_cnt_l3m,
      |r1_1.invoice_lt10000_total_sum_l3m,
      |r1_1.invoice_gt10000_cnt_l3m,
      |r1_1.invoice_gt10000_cr_cnt_l3m,
      |r1_1.invoice_gt10000_total_sum_l3m,
      |r1_1.invoice_detail_cnt_l3m,
      |r1_1.invoice_detail_cr_cnt_l3m,
      |r1_1.invoice_detail_total_sum_l3m,
      |r1_1.invoice_sc_cnt_l6m,
      |r1_1.invoice_sc_cancel_cnt_l6m,
      |r1_1.invoice_sc_red_cnt_l6m,
      |r1_1.invoice_sc_total_sum_l6m,
      |r1_1.invoice_cnt_l6m,
      |r1_1.invoice_cr_cnt_l6m,
      |r1_1.invoice_c_all_cnt_l6m,
      |r1_1.invoice_r_all_cnt_l6m,
      |r1_1.invoice_total_sum_l6m,
      |r1_1.invoice_total_r_sum_l6m,
      |r1_1.invoice_total_c_sum_l6m,
      |r1_1.invoice_s_cnt_l6m,
      |r1_1.invoice_s_cancel_cnt_l6m,
      |r1_1.invoice_s_red_cnt_l6m,
      |r1_1.invoice_s_total_sum_l6m,
      |r1_1.invoice_c_cnt_l6m,
      |r1_1.invoice_c_cancel_cnt_l6m,
      |r1_1.invoice_c_red_cnt_l6m,
      |r1_1.invoice_c_total_sum_l6m,
      |r1_1.invoice_t_cnt_l6m,
      |r1_1.invoice_t_cancel_cnt_l6m,
      |r1_1.invoice_t_red_cnt_l6m,
      |r1_1.invoice_t_total_sum_l6m,
      |r1_1.invoice_p_cnt_l6m,
      |r1_1.invoice_p_cancel_cnt_l6m,
      |r1_1.invoice_p_red_cnt_l6m,
      |r1_1.invoice_p_total_sum_l6m,
      |r1_1.invoice_e_cnt_l6m,
      |r1_1.invoice_e_cancel_cnt_l6m,
      |r1_1.invoice_e_red_cnt_l6m,
      |r1_1.invoice_e_total_sum_l6m,
      |r1_1.invoice_r_cnt_l6m,
      |r1_1.invoice_r_cancel_cnt_l6m,
      |r1_1.invoice_r_red_cnt_l6m,
      |r1_1.invoice_r_total_sum_l6m,
      |r1_1.invoice_lt100_cnt_l6m,
      |r1_1.invoice_lt100_cr_cnt_l6m,
      |r1_1.invoice_lt100_total_sum_l6m,
      |r1_1.invoice_lt1000_cnt_l6m,
      |r1_1.invoice_lt1000_cr_cnt_l6m,
      |r1_1.invoice_lt1000_total_sum_l6m,
      |r1_1.invoice_lt2500_cnt_l6m,
      |r1_1.invoice_lt2500_cr_cnt_l6m,
      |r1_1.invoice_lt2500_total_sum_l6m,
      |r1_1.invoice_lt5000_cnt_l6m,
      |r1_1.invoice_lt5000_cr_cnt_l6m,
      |r1_1.invoice_lt5000_total_sum_l6m,
      |r1_1.invoice_lt10000_cnt_l6m,
      |r1_1.invoice_lt10000_cr_cnt_l6m,
      |r1_1.invoice_lt10000_total_sum_l6m,
      |r1_1.invoice_gt10000_cnt_l6m,
      |r1_1.invoice_gt10000_cr_cnt_l6m,
      |r1_1.invoice_gt10000_total_sum_l6m,
      |r1_1.invoice_detail_cnt_l6m,
      |r1_1.invoice_detail_cr_cnt_l6m,
      |r1_1.invoice_detail_total_sum_l6m,
      |
      |r1_2.invoice_sc_cnt_l12m,
      |r1_2.invoice_sc_cancel_cnt_l12m,
      |r1_2.invoice_sc_red_cnt_l12m,
      |r1_2.invoice_sc_total_sum_l12m,
      |r1_2.invoice_cnt_l12m,
      |r1_2.invoice_cr_cnt_l12m,
      |r1_2.invoice_c_all_cnt_l12m,
      |r1_2.invoice_r_all_cnt_l12m,
      |r1_2.invoice_total_sum_l12m,
      |r1_2.invoice_total_r_sum_l12m,
      |r1_2.invoice_total_c_sum_l12m,
      |r1_2.invoice_s_cnt_l12m,
      |r1_2.invoice_s_cancel_cnt_l12m,
      |r1_2.invoice_s_red_cnt_l12m,
      |r1_2.invoice_s_total_sum_l12m,
      |r1_2.invoice_c_cnt_l12m,
      |r1_2.invoice_c_cancel_cnt_l12m,
      |r1_2.invoice_c_red_cnt_l12m,
      |r1_2.invoice_c_total_sum_l12m,
      |r1_2.invoice_t_cnt_l12m,
      |r1_2.invoice_t_cancel_cnt_l12m,
      |r1_2.invoice_t_red_cnt_l12m,
      |r1_2.invoice_t_total_sum_l12m,
      |r1_2.invoice_p_cnt_l12m,
      |r1_2.invoice_p_cancel_cnt_l12m,
      |r1_2.invoice_p_red_cnt_l12m,
      |r1_2.invoice_p_total_sum_l12m,
      |r1_2.invoice_e_cnt_l12m,
      |r1_2.invoice_e_cancel_cnt_l12m,
      |r1_2.invoice_e_red_cnt_l12m,
      |r1_2.invoice_e_total_sum_l12m,
      |r1_2.invoice_r_cnt_l12m,
      |r1_2.invoice_r_cancel_cnt_l12m,
      |r1_2.invoice_r_red_cnt_l12m,
      |r1_2.invoice_r_total_sum_l12m,
      |r1_2.invoice_lt100_cnt_l12m,
      |r1_2.invoice_lt100_cr_cnt_l12m,
      |r1_2.invoice_lt100_total_sum_l12m,
      |r1_2.invoice_lt1000_cnt_l12m,
      |r1_2.invoice_lt1000_cr_cnt_l12m,
      |r1_2.invoice_lt1000_total_sum_l12m,
      |r1_2.invoice_lt2500_cnt_l12m,
      |r1_2.invoice_lt2500_cr_cnt_l12m,
      |r1_2.invoice_lt2500_total_sum_l12m,
      |r1_2.invoice_lt5000_cnt_l12m,
      |r1_2.invoice_lt5000_cr_cnt_l12m,
      |r1_2.invoice_lt5000_total_sum_l12m,
      |r1_2.invoice_lt10000_cnt_l12m,
      |r1_2.invoice_lt10000_cr_cnt_l12m,
      |r1_2.invoice_lt10000_total_sum_l12m,
      |r1_2.invoice_gt10000_cnt_l12m,
      |r1_2.invoice_gt10000_cr_cnt_l12m,
      |r1_2.invoice_gt10000_total_sum_l12m,
      |r1_2.invoice_detail_cnt_l12m,
      |r1_2.invoice_detail_cr_cnt_l12m,
      |r1_2.invoice_detail_total_sum_l12m,
      |r1_2.invoice_sc_cnt_l24m,
      |r1_2.invoice_sc_cancel_cnt_l24m,
      |r1_2.invoice_sc_red_cnt_l24m,
      |r1_2.invoice_sc_total_sum_l24m,
      |r1_2.invoice_cnt_l24m,
      |r1_2.invoice_cr_cnt_l24m,
      |r1_2.invoice_c_all_cnt_l24m,
      |r1_2.invoice_r_all_cnt_l24m,
      |r1_2.invoice_total_sum_l24m,
      |r1_2.invoice_total_r_sum_l24m,
      |r1_2.invoice_total_c_sum_l24m,
      |r1_2.invoice_s_cnt_l24m,
      |r1_2.invoice_s_cancel_cnt_l24m,
      |r1_2.invoice_s_red_cnt_l24m,
      |r1_2.invoice_s_total_sum_l24m,
      |r1_2.invoice_c_cnt_l24m,
      |r1_2.invoice_c_cancel_cnt_l24m,
      |r1_2.invoice_c_red_cnt_l24m,
      |r1_2.invoice_c_total_sum_l24m,
      |r1_2.invoice_t_cnt_l24m,
      |r1_2.invoice_t_cancel_cnt_l24m,
      |r1_2.invoice_t_red_cnt_l24m,
      |r1_2.invoice_t_total_sum_l24m,
      |r1_2.invoice_p_cnt_l24m,
      |r1_2.invoice_p_cancel_cnt_l24m,
      |r1_2.invoice_p_red_cnt_l24m,
      |r1_2.invoice_p_total_sum_l24m,
      |r1_2.invoice_e_cnt_l24m,
      |r1_2.invoice_e_cancel_cnt_l24m,
      |r1_2.invoice_e_red_cnt_l24m,
      |r1_2.invoice_e_total_sum_l24m,
      |r1_2.invoice_r_cnt_l24m,
      |r1_2.invoice_r_cancel_cnt_l24m,
      |r1_2.invoice_r_red_cnt_l24m,
      |r1_2.invoice_r_total_sum_l24m,
      |r1_2.invoice_lt100_cnt_l24m,
      |r1_2.invoice_lt100_cr_cnt_l24m,
      |r1_2.invoice_lt100_total_sum_l24m,
      |r1_2.invoice_lt1000_cnt_l24m,
      |r1_2.invoice_lt1000_cr_cnt_l24m,
      |r1_2.invoice_lt1000_total_sum_l24m,
      |r1_2.invoice_lt2500_cnt_l24m,
      |r1_2.invoice_lt2500_cr_cnt_l24m,
      |r1_2.invoice_lt2500_total_sum_l24m,
      |r1_2.invoice_lt5000_cnt_l24m,
      |r1_2.invoice_lt5000_cr_cnt_l24m,
      |r1_2.invoice_lt5000_total_sum_l24m,
      |r1_2.invoice_lt10000_cnt_l24m,
      |r1_2.invoice_lt10000_cr_cnt_l24m,
      |r1_2.invoice_lt10000_total_sum_l24m,
      |r1_2.invoice_gt10000_cnt_l24m,
      |r1_2.invoice_gt10000_cr_cnt_l24m,
      |r1_2.invoice_gt10000_total_sum_l24m,
      |r1_2.invoice_detail_cnt_l24m,
      |r1_2.invoice_detail_cr_cnt_l24m,
      |r1_2.invoice_detail_total_sum_l24m,
      |
      |--计算累计交易对手数，按照交易对手号去重
      |r1_4.buyer_cnt_l3m,
      |r1_4.buyer_cnt_l6m,
      |r1_4.buyer_cnt_l12m,
      |r1_4.buyer_cnt_l24m,
      |r1_4.buyer_cnt_l712m,
      |r1_4.buyer_cnt_l1324m,
      |
      |r1_2.invoice_r_all_cnt_l712m,
      |r1_2.invoice_c_all_cnt_l712m,
      |
      |
      |r1_2.invoice_r_all_cnt_l1324m,
      |r1_2.invoice_c_all_cnt_l1324m,
      |r1_2.invoice_cnt_l712m,
      |r1_2.invoice_cnt_l1324m
      |
      |from r1_1
      |left join r1_2
      |on r1_1.mcht_cd=r1_2.mcht_cd and r1_1.data_month=r1_2.data_month
      |left join r1_4
      |on r1_4.sellertaxno=r1_2.mcht_cd and r1_4.month_a=r1_2.data_month
    """.stripMargin

  val r1 =
    s"""
      |select
      |*
      |from ${Const_Common.DATABASE}.month_pre1 m
      |left join r1_3
      |on m.month_pre1=r1_3.data_month
    """.stripMargin

  val r4_1 =
    s"""
      |select
      |	t1.sellertaxno,
      |	t1.data_month,
      |	----在统计月归为A/B/C/F类的交易对手近6月开票张数
      |	sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_a_invoice_cnt_l6m,
      |	sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_b_invoice_cnt_l6m,
      |	sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_c_invoice_cnt_l6m,
      |	sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_f_invoice_cnt_l6m,
      |	--在统计月归为A/B/C/F类的交易对手近6月合计金额总和
      |	sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_a_amt_sum_l6m,
      |	sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_b_amt_sum_l6m,
      |	sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_c_amt_sum_l6m,
      |	sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_f_amt_sum_l6m,
      |
      |	----在统计月归为A/B/C/F类的交易对手近12月开票张数
      |	sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_a_invoice_cnt_l12m,
      |	sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_b_invoice_cnt_l12m,
      |	sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_c_invoice_cnt_l12m,
      |	sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_f_invoice_cnt_l12m,
      |	--在统计月归为A/B/C/F类的交易对手近12月合计金额总和
      |	sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_a_amt_sum_l12m,
      |	sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_b_amt_sum_l12m,
      |	sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_c_amt_sum_l12m,
      |	sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_f_amt_sum_l12m,
      |
      |	----在统计月归为A/B/C/F类的交易对手近24月开票张数
      |	sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_a_invoice_cnt_l24m,
      |	sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_b_invoice_cnt_l24m,
      |	sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_c_invoice_cnt_l24m,
      |	sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_f_invoice_cnt_l24m,
      |	--在统计月归为A/B/C/F类的交易对手近24月合计金额总和
      |	sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_a_amt_sum_l24m,
      |	sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_b_amt_sum_l24m,
      |	sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_c_amt_sum_l24m,
      |	sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_f_amt_sum_l24m
      |
      |from
      |(
      |select
      |	mcht_cd as sellertaxno,
      |	data_month,
      |	buyer_type,
      |	buyer_invoice_cnt_l6m,
      |	buyer_invoice_total_sum_l6m,
      |	buyer_invoice_cnt_l12m,
      |	buyer_invoice_total_sum_l12m,
      |	buyer_invoice_cnt_l24m,
      |	buyer_invoice_total_sum_l24m
      |
      |from ${Const_Common.DATABASE}.counterparty_classify
      |)t1 group by t1.sellertaxno,t1.data_month
    """.stripMargin

  val r4 =
    s"""
      |select *
      |from ${Const_Common.DATABASE}.month_pre1 m
      |left join r4_1
      |on m.month_pre1=r4_1.data_month
    """.stripMargin

  val r5_t4 =
    s"""
      |select
      |	t3.mcht_cd,
      |	t3.data_month,
      |	t3.buyer_a_cnt,
      |	t3.buyer_b_cnt,
      |	t3.buyer_c_cnt,
      |	t3.buyer_f_cnt,
      |	lag(t3.buyer_a_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_a_cnt_pre7,
      |	lag(t3.buyer_b_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_b_cnt_pre7,
      |	lag(t3.buyer_c_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_c_cnt_pre7,
      |	lag(t3.buyer_f_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_f_cnt_pre7,
      |
      |	lag(t3.buyer_a_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_a_cnt_pre13,
      |	lag(t3.buyer_b_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_b_cnt_pre13,
      |	lag(t3.buyer_c_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_c_cnt_pre13,
      |	lag(t3.buyer_f_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_f_cnt_pre13
      |
      |from ${Const_Common.DATABASE}.statistics_month t3
    """.stripMargin

  val r5 =
    s"""
      |select
      |	t4.mcht_cd,
      |	t4.data_month,
      |		--'A类交易对手数目6个月变化率'
      |		--'B类交易对手数目6个月变化率'
      |		--'C类交易对手数目6个月变化率'
      |		--'F类交易对手数目6个月变化率'
      |	(case when t4.buyer_a_cnt is null or t4.buyer_a_cnt_pre7 is null then -9998
      |		  when t4.buyer_a_cnt=0 and t4.buyer_a_cnt_pre7=0 then -9997
      |		  when t4.buyer_a_cnt!=0 and t4.buyer_a_cnt_pre7=0 then -9996
      |		  when t4.buyer_a_cnt!=0 and t4.buyer_a_cnt_pre7!=0 then (t4.buyer_a_cnt / t4.buyer_a_cnt_pre7 -1)
      |		  else 0 end) as buyer_a_cnt_chg_rate_6m,
      |	(case when t4.buyer_b_cnt is null or t4.buyer_b_cnt_pre7 is null then -9998
      |		  when t4.buyer_b_cnt=0 and t4.buyer_b_cnt_pre7=0 then -9997
      |		  when t4.buyer_b_cnt!=0 and t4.buyer_b_cnt_pre7=0 then -9996
      |		  when t4.buyer_b_cnt!=0 and t4.buyer_b_cnt_pre7!=0 then (t4.buyer_b_cnt / t4.buyer_b_cnt_pre7 -1)
      |		  else 0 end) as buyer_b_cnt_chg_rate_6m,
      |	(case when t4.buyer_c_cnt is null or t4.buyer_c_cnt_pre7 is null then -9998
      |		  when t4.buyer_c_cnt=0 and t4.buyer_c_cnt_pre7=0 then -9997
      |		  when t4.buyer_c_cnt!=0 and t4.buyer_c_cnt_pre7=0 then -9996
      |		  when t4.buyer_c_cnt!=0 and t4.buyer_c_cnt_pre7!=0 then (t4.buyer_c_cnt / t4.buyer_c_cnt_pre7 -1)
      |		  else 0 end) as buyer_c_cnt_chg_rate_6m,
      |	(case when t4.buyer_f_cnt is null or t4.buyer_f_cnt_pre7 is null then -9998
      |		  when t4.buyer_f_cnt=0 and t4.buyer_f_cnt_pre7=0 then -9997
      |		  when t4.buyer_f_cnt!=0 and t4.buyer_f_cnt_pre7=0 then -9996
      |		  when t4.buyer_f_cnt!=0 and t4.buyer_f_cnt_pre7!=0 then (t4.buyer_f_cnt / t4.buyer_f_cnt_pre7 -1)
      |		  else 0 end) as buyer_f_cnt_chg_rate_6m,
      |
      |		--'A类交易对手数目12个月变化率'
      |		--'B类交易对手数目12个月变化率'
      |		--'C类交易对手数目12个月变化率'
      |		--'F类交易对手数目12个月变化率'
      |	(case when t4.buyer_a_cnt is null or t4.buyer_a_cnt_pre13 is null then -9998
      |		  when t4.buyer_a_cnt=0 and t4.buyer_a_cnt_pre13=0 then -9997
      |		  when t4.buyer_a_cnt!=0 and t4.buyer_a_cnt_pre13=0 then -9996
      |		  when t4.buyer_a_cnt!=0 and t4.buyer_a_cnt_pre13!=0 then (t4.buyer_a_cnt / t4.buyer_a_cnt_pre13 -1)
      |		  else 0 end) as buyer_a_cnt_chg_rate_12m,
      |	(case when t4.buyer_b_cnt is null or t4.buyer_b_cnt_pre13 is null then -9998
      |		  when t4.buyer_b_cnt=0 and t4.buyer_b_cnt_pre13=0 then -9997
      |		  when t4.buyer_b_cnt!=0 and t4.buyer_b_cnt_pre13=0 then -9996
      |		  when t4.buyer_b_cnt!=0 and t4.buyer_b_cnt_pre13!=0 then (t4.buyer_b_cnt / t4.buyer_b_cnt_pre13 -1)
      |		  else 0 end) as buyer_b_cnt_chg_rate_12m,
      |	(case when t4.buyer_c_cnt is null or t4.buyer_c_cnt_pre13 is null then -9998
      |		  when t4.buyer_c_cnt=0 and t4.buyer_c_cnt_pre13=0 then -9997
      |		  when t4.buyer_c_cnt!=0 and t4.buyer_c_cnt_pre13=0 then -9996
      |		  when t4.buyer_c_cnt!=0 and t4.buyer_c_cnt_pre13!=0 then (t4.buyer_c_cnt / t4.buyer_c_cnt_pre13 -1)
      |		  else 0 end) as buyer_c_cnt_chg_rate_12m,
      |	(case when t4.buyer_f_cnt is null or t4.buyer_f_cnt_pre13 is null then -9998
      |		  when t4.buyer_f_cnt=0 and t4.buyer_f_cnt_pre13=0 then -9997
      |		  when t4.buyer_f_cnt!=0 and t4.buyer_f_cnt_pre13=0 then -9996
      |		  when t4.buyer_f_cnt!=0 and t4.buyer_f_cnt_pre13!=0 then (t4.buyer_f_cnt / t4.buyer_f_cnt_pre13 -1)
      |		  else 0 end) as buyer_f_cnt_chg_rate_12m
      |from ${Const_Common.DATABASE}.month_pre1 m
      |left join r5_t4 t4
      |on m.month_pre1=t4.data_month
    """.stripMargin

  val r6_t1 =
    s"""
      |select
      |	distinct t2.mcht_cd,
      |	t2.data_month,
      |	t2.buyer_tax_cd,
      |	t2.buyer_type,
      |	t2.buyer_invoice_cnt_l12m,
      |	t2.buyer_invoice_cnt_l712m,
      |	t2.buyer_invoice_cnt_l1324m,
      |	t2.buyer_invoice_total_sum_l12m,
      |	t2.buyer_invoice_total_sum_l712m,
      |	t2.buyer_invoice_total_sum_l1324m
      |from ${Const_Common.DATABASE}.counterparty_classify t2
    """.stripMargin

  val r6_t2 =
    """
      |select * from(
      |select
      |*,
      |row_number() over(partition by aaa.mcht_cd,aaa.data_month,aaa.buyer_type order by aaa.data_month )as rank
      |from (
      |select
      |	t3.mcht_cd,
      |	t3.data_month,
      |	--t3.buyer_tax_cd,
      |	t3.buyer_type,
      |
      |	--按照商户号，月份，交易对手类型分组求和
      |	sum(t3.buyer_invoice_cnt_l12m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
      |		order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_cnt_l12m_all,
      |	sum(t3.buyer_invoice_total_sum_l12m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
      |		order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l12m_all,
      |
      |	sum(t3.buyer_invoice_cnt_l712m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
      |		order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_cnt_l712m_all,
      |	sum(t3.buyer_invoice_total_sum_l712m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
      |		order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l712m_all,
      |	sum(t3.buyer_invoice_cnt_l1324m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
      |		order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_cnt_l1324m_all,
      |	sum(t3.buyer_invoice_total_sum_l1324m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
      |		order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l1324m_all
      |
      |from r6_t1 t3
      |)aaa
      |)bbb where bbb.rank=1
    """.stripMargin

  val r6_t3 =
    """
      |select
      |	pp.mcht_cd,
      |	pp.data_month,
      |	pp.buyer_type,
      |	pp.buyer_invoice_cnt_l12m_all,
      |	pp.buyer_invoice_total_sum_l12m_all,
      |
      |	lag(pp.buyer_invoice_cnt_l712m_all,7,0) over(partition by pp.mcht_cd,pp.data_month,pp.buyer_type,
      |		pp.buyer_invoice_cnt_l12m_all,buyer_invoice_total_sum_l12m_all
      |			order by to_date(from_unixtime(unix_timestamp(pp.data_month,'yyyyMM')))) as buyer_invoice_cnt_l712m_pre7,
      |	lag(pp.buyer_invoice_total_sum_l712m_all,7,0) over(partition by pp.mcht_cd,pp.data_month,pp.buyer_type,
      |		pp.buyer_invoice_cnt_l12m_all,buyer_invoice_total_sum_l12m_all
      |			order by to_date(from_unixtime(unix_timestamp(pp.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l712m_pre7,
      |
      |	lag(pp.buyer_invoice_cnt_l1324m_all,13,0) over(partition by pp.mcht_cd,pp.data_month,pp.buyer_type,
      |		pp.buyer_invoice_cnt_l12m_all,buyer_invoice_total_sum_l12m_all
      |			order by to_date(from_unixtime(unix_timestamp(pp.data_month,'yyyyMM')))) as buyer_invoice_cnt_l1324m_pre13,
      |	lag(pp.buyer_invoice_total_sum_l1324m_all,13,0) over(partition by pp.mcht_cd,pp.data_month,pp.buyer_type,
      |		pp.buyer_invoice_cnt_l12m_all,buyer_invoice_total_sum_l12m_all
      |			order by to_date(from_unixtime(unix_timestamp(pp.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l1324m_pre13
      |
      |from r6_t2 pp
    """.stripMargin

  val r6_t4 =
    """
      |select
      |a1.mcht_cd,
      |a1.data_month,
      |a1.buyer_a_invoice_cnt_l12m_chg_rate_6m,
      |a1.buyer_a_amt_sum_l12m_chg_rate_6m,
      |a1.buyer_a_invoice_cnt_l12m_chg_rate_12m,
      |a1.buyer_a_amt_sum_l12m_chg_rate_12m,
      |
      |b1.buyer_b_invoice_cnt_l12m_chg_rate_6m,
      |b1.buyer_b_amt_sum_l12m_chg_rate_6m,
      |b1.buyer_b_invoice_cnt_l12m_chg_rate_12m,
      |b1.buyer_b_amt_sum_l12m_chg_rate_12m,
      |
      |c1.buyer_c_invoice_cnt_l12m_chg_rate_6m,
      |c1.buyer_c_amt_sum_l12m_chg_rate_6m,
      |c1.buyer_c_invoice_cnt_l12m_chg_rate_12m,
      |c1.buyer_c_amt_sum_l12m_chg_rate_12m,
      |
      |f1.buyer_f_invoice_cnt_l12m_chg_rate_6m,
      |f1.buyer_f_amt_sum_l12m_chg_rate_6m,
      |f1.buyer_f_invoice_cnt_l12m_chg_rate_12m,
      |f1.buyer_f_amt_sum_l12m_chg_rate_12m
      |
      | from
      |
      |(
      |select
      |p1.mcht_cd,
      |p1.data_month,
      |(CASE WHEN p1.buyer_type='A' and (p1.buyer_invoice_cnt_l12m_all is null or p1.buyer_invoice_cnt_l712m_pre7 is null) THEN -9998
      |		  WHEN p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all=0 AND p1.buyer_invoice_cnt_l712m_pre7=0 THEN -9997
      |		  WHEN p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all!=0 AND p1.buyer_invoice_cnt_l712m_pre7=0 THEN -9996
      |		  WHEN  p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all!=0 AND p1.buyer_invoice_cnt_l712m_pre7!=0
      |				THEN(p1.buyer_invoice_cnt_l12m_all / p1.buyer_invoice_cnt_l712m_pre7 -1)
      |		  ELSE 0 END)AS buyer_a_invoice_cnt_l12m_chg_rate_6m,
      |(CASE WHEN p1.buyer_type='A' and (p1.buyer_invoice_total_sum_l12m_all is null or p1.buyer_invoice_total_sum_l712m_pre7 is null) THEN -9998
      |		  WHEN p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all=0 AND p1.buyer_invoice_total_sum_l712m_pre7=0 THEN -9997
      |		  WHEN p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all!=0 AND p1.buyer_invoice_total_sum_l712m_pre7=0 THEN -9996
      |		  WHEN  p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all!=0 AND p1.buyer_invoice_total_sum_l712m_pre7!=0
      |				THEN (p1.buyer_invoice_total_sum_l12m_all / p1.buyer_invoice_total_sum_l712m_pre7 -1)
      |		  ELSE 0 END) AS buyer_a_amt_sum_l12m_chg_rate_6m,
      |
      |(CASE WHEN p1.buyer_type='A' and (p1.buyer_invoice_cnt_l12m_all is null or p1.buyer_invoice_cnt_l1324m_pre13 is null) THEN -9998
      |		  WHEN p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all=0 AND p1.buyer_invoice_cnt_l1324m_pre13=0 THEN -9997
      |		  WHEN p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all!=0 AND p1.buyer_invoice_cnt_l1324m_pre13=0 THEN -9996
      |		  WHEN  p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all!=0 AND p1.buyer_invoice_cnt_l1324m_pre13!=0
      |				THEN(p1.buyer_invoice_cnt_l12m_all / p1.buyer_invoice_cnt_l1324m_pre13 -1)
      |		  ELSE 0 END)AS buyer_a_invoice_cnt_l12m_chg_rate_12m,
      |
      |(CASE WHEN p1.buyer_type='A' and (p1.buyer_invoice_total_sum_l12m_all is null or p1.buyer_invoice_total_sum_l1324m_pre13 is null) THEN -9998
      |		  WHEN p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all=0 AND p1.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9997
      |		  WHEN p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all!=0 AND p1.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9996
      |		  WHEN  p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all!=0 AND p1.buyer_invoice_total_sum_l1324m_pre13!=0
      |				THEN (p1.buyer_invoice_total_sum_l12m_all / p1.buyer_invoice_total_sum_l1324m_pre13 -1)
      |		  ELSE 0 END) AS buyer_a_amt_sum_l12m_chg_rate_12m
      |
      |from r6_t3 p1
      |where p1.buyer_type='A'
      |)a1
      |left join
      |(
      |----B----
      |select
      |p2.mcht_cd,
      |p2.data_month,
      |(CASE WHEN p2.buyer_type='B' and (p2.buyer_invoice_cnt_l12m_all is null or p2.buyer_invoice_cnt_l712m_pre7 is null) THEN -9998
      |		  WHEN p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all=0 AND p2.buyer_invoice_cnt_l712m_pre7=0 THEN -9997
      |		  WHEN p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all!=0 AND p2.buyer_invoice_cnt_l712m_pre7=0 THEN -9996
      |		  WHEN  p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all!=0 AND p2.buyer_invoice_cnt_l712m_pre7!=0
      |				THEN(p2.buyer_invoice_cnt_l12m_all / p2.buyer_invoice_cnt_l712m_pre7 -1)
      |		  ELSE 0 END)AS buyer_b_invoice_cnt_l12m_chg_rate_6m,
      |(CASE WHEN p2.buyer_type='B' and (p2.buyer_invoice_total_sum_l12m_all is null or p2.buyer_invoice_total_sum_l712m_pre7 is null) THEN -9998
      |		  WHEN p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all=0 AND p2.buyer_invoice_total_sum_l712m_pre7=0 THEN -9997
      |		  WHEN p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all!=0 AND p2.buyer_invoice_total_sum_l712m_pre7=0 THEN -9996
      |		  WHEN  p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all!=0 AND p2.buyer_invoice_total_sum_l712m_pre7!=0
      |				THEN (p2.buyer_invoice_total_sum_l12m_all / p2.buyer_invoice_total_sum_l712m_pre7 -1)
      |		  ELSE 0 END) AS buyer_b_amt_sum_l12m_chg_rate_6m,
      |
      |(CASE WHEN p2.buyer_type='B' and (p2.buyer_invoice_cnt_l12m_all is null or p2.buyer_invoice_cnt_l1324m_pre13 is null) THEN -9998
      |		  WHEN p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all=0 AND p2.buyer_invoice_cnt_l1324m_pre13=0 THEN -9997
      |		  WHEN p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all!=0 AND p2.buyer_invoice_cnt_l1324m_pre13=0 THEN -9996
      |		  WHEN  p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all!=0 AND p2.buyer_invoice_cnt_l1324m_pre13!=0
      |				THEN(p2.buyer_invoice_cnt_l12m_all / p2.buyer_invoice_cnt_l1324m_pre13 -1)
      |		  ELSE 0 END)AS buyer_b_invoice_cnt_l12m_chg_rate_12m,
      |
      |(CASE WHEN p2.buyer_type='B' and (p2.buyer_invoice_total_sum_l12m_all is null or p2.buyer_invoice_total_sum_l1324m_pre13 is null) THEN -9998
      |		  WHEN p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all=0 AND p2.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9997
      |		  WHEN p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all!=0 AND p2.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9996
      |		  WHEN  p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all!=0 AND p2.buyer_invoice_total_sum_l1324m_pre13!=0
      |				THEN (p2.buyer_invoice_total_sum_l12m_all / p2.buyer_invoice_total_sum_l1324m_pre13 -1)
      |		  ELSE 0 END) AS buyer_b_amt_sum_l12m_chg_rate_12m
      |
      |from r6_t3 p2
      |where p2.buyer_type='B'
      |)b1
      |on a1.mcht_cd=b1.mcht_cd and a1.data_month=b1.data_month
      |left join
      |(
      |----C--
      |select
      |p3.mcht_cd,
      |p3.data_month,
      |(CASE WHEN p3.buyer_type='C' and (p3.buyer_invoice_cnt_l12m_all is null or p3.buyer_invoice_cnt_l712m_pre7 is null) THEN -9998
      |		  WHEN p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all=0 AND p3.buyer_invoice_cnt_l712m_pre7=0 THEN -9997
      |		  WHEN p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all!=0 AND p3.buyer_invoice_cnt_l712m_pre7=0 THEN -9996
      |		  WHEN  p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all!=0 AND p3.buyer_invoice_cnt_l712m_pre7!=0
      |				THEN(p3.buyer_invoice_cnt_l12m_all / p3.buyer_invoice_cnt_l712m_pre7 -1)
      |		  ELSE 0 END)AS buyer_c_invoice_cnt_l12m_chg_rate_6m,
      |(CASE WHEN p3.buyer_type='C' and (p3.buyer_invoice_total_sum_l12m_all is null or p3.buyer_invoice_total_sum_l712m_pre7 is null) THEN -9998
      |		  WHEN p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all=0 AND p3.buyer_invoice_total_sum_l712m_pre7=0 THEN -9997
      |		  WHEN p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all!=0 AND p3.buyer_invoice_total_sum_l712m_pre7=0 THEN -9996
      |		  WHEN  p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all!=0 AND p3.buyer_invoice_total_sum_l712m_pre7!=0
      |				THEN (p3.buyer_invoice_total_sum_l12m_all / p3.buyer_invoice_total_sum_l712m_pre7 -1)
      |		  ELSE 0 END) AS buyer_c_amt_sum_l12m_chg_rate_6m,
      |
      |(CASE WHEN p3.buyer_type='C' and (p3.buyer_invoice_cnt_l12m_all is null or p3.buyer_invoice_cnt_l1324m_pre13 is null) THEN -9998
      |		  WHEN p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all=0 AND p3.buyer_invoice_cnt_l1324m_pre13=0 THEN -9997
      |		  WHEN p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all!=0 AND p3.buyer_invoice_cnt_l1324m_pre13=0 THEN -9996
      |		  WHEN  p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all!=0 AND p3.buyer_invoice_cnt_l1324m_pre13!=0
      |				THEN(p3.buyer_invoice_cnt_l12m_all / p3.buyer_invoice_cnt_l1324m_pre13 -1)
      |		  ELSE 0 END)AS buyer_c_invoice_cnt_l12m_chg_rate_12m,
      |
      |(CASE WHEN p3.buyer_type='C' and (p3.buyer_invoice_total_sum_l12m_all is null or p3.buyer_invoice_total_sum_l1324m_pre13 is null) THEN -9998
      |		  WHEN p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all=0 AND p3.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9997
      |		  WHEN p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all!=0 AND p3.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9996
      |		  WHEN  p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all!=0 AND p3.buyer_invoice_total_sum_l1324m_pre13!=0
      |				THEN (p3.buyer_invoice_total_sum_l12m_all / p3.buyer_invoice_total_sum_l1324m_pre13 -1)
      |		  ELSE 0 END) AS buyer_c_amt_sum_l12m_chg_rate_12m
      |
      |from r6_t3 p3
      |where p3.buyer_type='C'
      |)c1
      |on b1.mcht_cd=c1.mcht_cd and b1.data_month=c1.data_month
      |left join
      |(
      |---f---
      |
      |select
      |p4.mcht_cd,
      |p4.data_month,
      |(CASE WHEN p4.buyer_type='F' and (p4.buyer_invoice_cnt_l12m_all is null or p4.buyer_invoice_cnt_l712m_pre7 is null) THEN -9998
      |		  WHEN p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all=0 AND p4.buyer_invoice_cnt_l712m_pre7=0 THEN -9997
      |		  WHEN p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all!=0 AND p4.buyer_invoice_cnt_l712m_pre7=0 THEN -9996
      |		  WHEN  p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all!=0 AND p4.buyer_invoice_cnt_l712m_pre7!=0
      |				THEN(p4.buyer_invoice_cnt_l12m_all / p4.buyer_invoice_cnt_l712m_pre7 -1)
      |		  ELSE 0 END)AS buyer_f_invoice_cnt_l12m_chg_rate_6m,
      |(CASE WHEN p4.buyer_type='F' and (p4.buyer_invoice_total_sum_l12m_all is null or p4.buyer_invoice_total_sum_l712m_pre7 is null) THEN -9998
      |		  WHEN p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all=0 AND p4.buyer_invoice_total_sum_l712m_pre7=0 THEN -9997
      |		  WHEN p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all!=0 AND p4.buyer_invoice_total_sum_l712m_pre7=0 THEN -9996
      |		  WHEN  p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all!=0 AND p4.buyer_invoice_total_sum_l712m_pre7!=0
      |				THEN (p4.buyer_invoice_total_sum_l12m_all / p4.buyer_invoice_total_sum_l712m_pre7 -1)
      |		  ELSE 0 END) AS buyer_f_amt_sum_l12m_chg_rate_6m,
      |
      |(CASE WHEN p4.buyer_type='F' and (p4.buyer_invoice_cnt_l12m_all is null or p4.buyer_invoice_cnt_l1324m_pre13 is null) THEN -9998
      |		  WHEN p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all=0 AND p4.buyer_invoice_cnt_l1324m_pre13=0 THEN -9997
      |		  WHEN p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all!=0 AND p4.buyer_invoice_cnt_l1324m_pre13=0 THEN -9996
      |		  WHEN  p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all!=0 AND p4.buyer_invoice_cnt_l1324m_pre13!=0
      |				THEN(p4.buyer_invoice_cnt_l12m_all / p4.buyer_invoice_cnt_l1324m_pre13 -1)
      |		  ELSE 0 END)AS buyer_f_invoice_cnt_l12m_chg_rate_12m,
      |
      |(CASE WHEN p4.buyer_type='F' and (p4.buyer_invoice_total_sum_l12m_all is null or p4.buyer_invoice_total_sum_l1324m_pre13 is null) THEN -9998
      |		  WHEN p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all=0 AND p4.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9997
      |		  WHEN p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all!=0 AND p4.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9996
      |		  WHEN  p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all!=0 AND p4.buyer_invoice_total_sum_l1324m_pre13!=0
      |				THEN (p4.buyer_invoice_total_sum_l12m_all / p4.buyer_invoice_total_sum_l1324m_pre13 -1)
      |		  ELSE 0 END) AS buyer_f_amt_sum_l12m_chg_rate_12m
      |
      |from r6_t3 p4
      |where p4.buyer_type='F'
      |)f1
      |on c1.mcht_cd=f1.mcht_cd and c1.data_month=f1.data_month
    """.stripMargin

  val r6 =
    s"""
      |select * from ${Const_Common.DATABASE}.month_pre1 m
      |left join r6_t4
      |on m.month_pre1=r6_t4.data_month
    """.stripMargin

  val c_p2 =
    """
      |select
      |	distinct r1.mcht_cd,
      |	r1.data_month,
      |--以下为新补充的字段
      |	r1.invoice_r_all_cnt_l3m,
      |	r1.invoice_c_all_cnt_l3m,
      |	r1.invoice_total_r_sum_l3m,
      |	r1.invoice_total_c_sum_l3m,
      |	r1.invoice_r_all_cnt_l6m,
      |	r1.invoice_c_all_cnt_l6m,
      |	r1.invoice_total_r_sum_l6m,
      |	r1.invoice_total_c_sum_l6m,
      |	r1.invoice_r_all_cnt_l12m,
      |	r1.invoice_c_all_cnt_l12m,
      |	r1.invoice_total_r_sum_l12m,
      |	r1.invoice_total_c_sum_l12m,
      |	r1.invoice_r_all_cnt_l24m,
      |	r1.invoice_c_all_cnt_l24m,
      |	r1.invoice_total_r_sum_l24m,
      |	r1.invoice_total_c_sum_l24m,
      |	r1.invoice_r_all_cnt_l712m,
      |	r1.invoice_c_all_cnt_l712m,
      |	r1.invoice_cnt_l712m,
      |	r1.invoice_cnt_l1324m,
      |	r1.invoice_r_all_cnt_l1324m,
      |	r1.invoice_c_all_cnt_l1324m,
      |	r1.buyer_cnt_l712m,
      |	r1.buyer_cnt_l1324m,
      |
      |	r1.invoice_sc_cnt_l3m, --近3月增值税开票张数
      |	r1.invoice_sc_cancel_cnt_l3m, --近3月增值税发票作废张数
      |	r1.invoice_sc_red_cnt_l3m, --近3月增值税发票红冲张数
      |	r1.invoice_sc_total_sum_l3m, --近3月增值税发票合计金额税额总和
      |	r1.invoice_cnt_l3m, --近3月所有发票开票张数
      |	r1.invoice_cr_cnt_l3m, --近3月所有发票作废红冲张数
      |	r1.invoice_total_sum_l3m, --近3月所有发票合计金额税额总和
      |	r1.invoice_s_cnt_l3m, --近3月增值税专用发票开票张数
      |	r1.invoice_s_cancel_cnt_l3m, --近3月增值税专用发票作废张数
      |	r1.invoice_s_red_cnt_l3m, --近3月增值税专用发票红冲张数
      |	r1.invoice_s_total_sum_l3m, --近3月增值税专用发票合计金额税额总和
      |	r1.invoice_c_cnt_l3m, --近3月增值税普通发票开票张数
      |	r1.invoice_c_cancel_cnt_l3m, --近3月增值税普通发票作废张数
      |	r1.invoice_c_red_cnt_l3m, --近3月增值税普通发票红冲张数
      |	r1.invoice_c_total_sum_l3m, --近3月增值税普通发票合计金额税额总和
      |	r1.invoice_t_cnt_l3m, --近3月运输发票开票张数
      |	r1.invoice_t_cancel_cnt_l3m, --近3月运输发票作废张数
      |	r1.invoice_t_red_cnt_l3m, --近3月运输发票红冲张数
      |	r1.invoice_t_total_sum_l3m, --近3月运输发票合计金额税额总和
      |	r1.invoice_p_cnt_l3m, --近3月纸质发票开票张数
      |	r1.invoice_p_cancel_cnt_l3m, --近3月纸质发票作废张数
      |	r1.invoice_p_red_cnt_l3m, --近3月纸质发票红冲张数
      |	r1.invoice_p_total_sum_l3m, --近3月纸质发票合计金额税额总和
      |	r1.invoice_e_cnt_l3m, --近3月电子发票开票张数
      |	r1.invoice_e_cancel_cnt_l3m, --近3月电子发票作废张数
      |	r1.invoice_e_red_cnt_l3m, --近3月电子发票红冲张数
      |	r1.invoice_e_total_sum_l3m, --近3月电子发票合计金额税额总和
      |	r1.invoice_r_cnt_l3m, --近3月卷式发票开票张数
      |	r1.invoice_r_cancel_cnt_l3m, --近3月卷式发票作废张数
      |	r1.invoice_r_red_cnt_l3m, --近3月卷式发票红冲张数
      |	r1.invoice_r_total_sum_l3m, --近3月卷式发票合计金额税额总和
      |	r1.invoice_lt100_cnt_l3m, --近3月金额小于100发票张数
      |	r1.invoice_lt100_cr_cnt_l3m, --近3月金额小于100发票作废红冲张数
      |	r1.invoice_lt100_total_sum_l3m, --近3月金额小于100发票合计金额税额总和
      |	r1.invoice_lt1000_cnt_l3m, --近3月金额小于1000（且大于等于100）发票张数
      |	r1.invoice_lt1000_cr_cnt_l3m, --近3月金额小于1000（且大于等于100）发票作废红冲张数
      |	r1.invoice_lt1000_total_sum_l3m, --近3月金额小于1000（且大于等于100）发票合计金额税额总和
      |	r1.invoice_lt2500_cnt_l3m, --近3月金额小于2500且（且大于等于1000）发票张数
      |	r1.invoice_lt2500_cr_cnt_l3m, --近3月金额小于2500（且大于等于1000）发票作废红冲张数
      |	r1.invoice_lt2500_total_sum_l3m, --近3月金额小于2500（且大于等于1000）发票合计金额税额总和
      |	r1.invoice_lt5000_cnt_l3m, --近3月金额小于5000（且大于等于2500）发票张数
      |	r1.invoice_lt5000_cr_cnt_l3m, --近3月金额小于5000（且大于等于2500）发票作废红冲张数
      |	r1.invoice_lt5000_total_sum_l3m, --近3月金额小于5000（且大于等于2500）发票合计金额税额总和
      |	r1.invoice_lt10000_cnt_l3m, --近3月金额小于10000（且大于等于5000）发票张数
      |	r1.invoice_lt10000_cr_cnt_l3m, --近3月金额小于10000（且大于等于5000）发票作废红冲张数
      |	r1.invoice_lt10000_total_sum_l3m, --近3月金额小于10000（且大于等于5000）发票合计金额税额总和
      |	r1.invoice_gt10000_cnt_l3m, --近3月金额大于10000发票张数
      |	r1.invoice_gt10000_cr_cnt_l3m, --近3月金额大于10000发票作废红冲张数
      |	r1.invoice_gt10000_total_sum_l3m, --近3月金额大于10000发票合计金额税额总和
      |
      |	r1.invoice_detail_cnt_l3m, --近3月附有清单的发票张数
      |	r1.invoice_detail_cr_cnt_l3m, --近3月附有清单的发票作废红冲张数
      |	r1.invoice_detail_total_sum_l3m, --近3月附有清单的发票合计金额税额总和
      |	r1.buyer_cnt_l3m, --近3月交易对手数目
      |
      |	r1.invoice_sc_cnt_l6m, --近6月增值税开票张数
      |	r1.invoice_sc_cancel_cnt_l6m, --近6月增值税发票作废张数
      |	r1.invoice_sc_red_cnt_l6m, --近6月增值税发票红冲张数
      |	r1.invoice_sc_total_sum_l6m, --近6月增值税发票合计金额税额总和
      |	r1.invoice_cnt_l6m, --近6月所有发票开票张数
      |	r1.invoice_cr_cnt_l6m, --近6月所有发票作废红冲张数
      |	r1.invoice_total_sum_l6m, --近6月所有发票合计金额税额总和
      |	r1.invoice_s_cnt_l6m, --近6月增值税专用发票开票张数
      |	r1.invoice_s_cancel_cnt_l6m, --近6月增值税专用发票作废张数
      |	r1.invoice_s_red_cnt_l6m, --近6月增值税专用发票红冲张数
      |	r1.invoice_s_total_sum_l6m, --近6月增值税专用发票合计金额税额总和
      |	r1.invoice_c_cnt_l6m, --近6月增值税普通发票开票张数
      |	r1.invoice_c_cancel_cnt_l6m, --近6月增值税普通发票作废张数
      |	r1.invoice_c_red_cnt_l6m, --近6月增值税普通发票红冲张数
      |	r1.invoice_c_total_sum_l6m, --近6月增值税普通发票合计金额税额总和
      |	r1.invoice_t_cnt_l6m, --近6月运输发票开票张数
      |	r1.invoice_t_cancel_cnt_l6m, --近6月运输发票作废张数
      |	r1.invoice_t_red_cnt_l6m, --近6月运输发票红冲张数
      |	r1.invoice_t_total_sum_l6m, --近6月运输发票合计金额税额总和
      |	r1.invoice_p_cnt_l6m, --近6月纸质发票开票张数
      |	r1.invoice_p_cancel_cnt_l6m, --近6月纸质发票作废张数
      |	r1.invoice_p_red_cnt_l6m, --近6月纸质发票红冲张数
      |	r1.invoice_p_total_sum_l6m, --近6月纸质发票合计金额税额总和
      |	r1.invoice_e_cnt_l6m, --近6月电子发票开票张数
      |	r1.invoice_e_cancel_cnt_l6m, --近6月电子发票作废张数
      |	r1.invoice_e_red_cnt_l6m, --近6月电子发票红冲张数
      |	r1.invoice_e_total_sum_l6m, --近6月电子发票合计金额税额总和
      |	r1.invoice_r_cnt_l6m, --近6月卷式发票开票张数
      |	r1.invoice_r_cancel_cnt_l6m, --近6月卷式发票作废张数
      |	r1.invoice_r_red_cnt_l6m, --近6月卷式发票红冲张数
      |	r1.invoice_r_total_sum_l6m, --近6月卷式发票合计金额税额总和
      |	r1.invoice_lt100_cnt_l6m, --近6月金额小于100发票张数
      |	r1.invoice_lt100_cr_cnt_l6m, --近6月金额小于100发票作废红冲张数
      |	r1.invoice_lt100_total_sum_l6m, --近6月金额小于100发票合计金额税额总和
      |	r1.invoice_lt1000_cnt_l6m, --近6月金额小于1000（且大于等于100）发票张数
      |	r1.invoice_lt1000_cr_cnt_l6m, --近6月金额小于1000（且大于等于100）发票作废红冲张数
      |	r1.invoice_lt1000_total_sum_l6m, --近6月金额小于1000（且大于等于100）发票合计金额税额总和
      |	r1.invoice_lt2500_cnt_l6m, --近6月金额小于2500且（且大于等于1000）发票张数
      |	r1.invoice_lt2500_cr_cnt_l6m, --近6月金额小于2500（且大于等于1000）发票作废红冲张数
      |	r1.invoice_lt2500_total_sum_l6m, --近6月金额小于2500（且大于等于1000）发票合计金额税额总和
      |	r1.invoice_lt5000_cnt_l6m, --近6月金额小于5000（且大于等于2500）发票张数
      |	r1.invoice_lt5000_cr_cnt_l6m, --近6月金额小于5000（且大于等于2500）发票作废红冲张数
      |	r1.invoice_lt5000_total_sum_l6m, --近6月金额小于5000（且大于等于2500）发票合计金额税额总和
      |	r1.invoice_lt10000_cnt_l6m, --近6月金额小于10000（且大于等于5000）发票张数
      |	r1.invoice_lt10000_cr_cnt_l6m, --近6月金额小于10000（且大于等于5000）发票作废红冲张数
      |	r1.invoice_lt10000_total_sum_l6m, --近6月金额小于10000（且大于等于5000）发票合计金额税额总和
      |	r1.invoice_gt10000_cnt_l6m, --近6月金额大于10000发票张数
      |	r1.invoice_gt10000_cr_cnt_l6m, --近6月金额大于10000发票作废红冲张数
      |	r1.invoice_gt10000_total_sum_l6m, --近6月金额大于10000发票合计金额税额总和
      |	r1.invoice_detail_cnt_l6m, --近6月附有清单的发票张数
      |	r1.invoice_detail_cr_cnt_l6m, --近6月附有清单的发票作废红冲张数
      |	r1.invoice_detail_total_sum_l6m, --近6月附有清单的发票合计金额税额总和
      |	r1.buyer_cnt_l6m, --近6月交易对手数目
      |
      |	r4.buyer_a_invoice_cnt_l6m, --在统计月归为A类的交易对手近6月开票张数
      |	r4.buyer_b_invoice_cnt_l6m, --在统计月归为B类的交易对手近6月开票张数
      |	r4.buyer_c_invoice_cnt_l6m, --在统计月归为C类的交易对手近6月开票张数
      |	r4.buyer_f_invoice_cnt_l6m, --在统计月归为F类的交易对手近6月开票张数
      |	r4.buyer_a_amt_sum_l6m, --在统计月归为A类的交易对手近6月合计金额总和
      |	r4.buyer_b_amt_sum_l6m, --在统计月归为B类的交易对手近6月合计金额总和
      |	r4.buyer_c_amt_sum_l6m, --在统计月归为C类的交易对手近6月合计金额总和
      |	r4.buyer_f_amt_sum_l6m, --在统计月归为F类的交易对手近6月合计金额总和
      |
      |	r1.invoice_sc_cnt_l12m, --近12月增值税开票张数
      |	r1.invoice_sc_cancel_cnt_l12m, --近12月增值税发票作废张数
      |	r1.invoice_sc_red_cnt_l12m, --近12月增值税发票红冲张数
      |	r1.invoice_sc_total_sum_l12m, --近12月增值税发票合计金额税额总和
      |	r1.invoice_cnt_l12m, --近12月所有发票开票张数
      |	r1.invoice_cr_cnt_l12m, --近12月所有发票作废红冲张数
      |	r1.invoice_total_sum_l12m, --近12月所有发票合计金额税额总和
      |	r1.invoice_s_cnt_l12m, --近12月增值税专用发票开票张数
      |	r1.invoice_s_cancel_cnt_l12m, --近12月增值税专用发票作废张数
      |	r1.invoice_s_red_cnt_l12m, --近12月增值税专用发票红冲张数
      |	r1.invoice_s_total_sum_l12m, --近12月增值税专用发票合计金额税额总和
      |	r1.invoice_c_cnt_l12m, --近12月增值税普通发票开票张数
      |	r1.invoice_c_cancel_cnt_l12m, --近12月增值税普通发票作废张数
      |	r1.invoice_c_red_cnt_l12m, --近12月增值税普通发票红冲张数
      |	r1.invoice_c_total_sum_l12m, --近12月增值税普通发票合计金额税额总和
      |	r1.invoice_t_cnt_l12m, --近12月运输发票开票张数
      |	r1.invoice_t_cancel_cnt_l12m, --近12月运输发票作废张数
      |	r1.invoice_t_red_cnt_l12m, --近12月运输发票红冲张数
      |	r1.invoice_t_total_sum_l12m, --近12月运输发票合计金额税额总和
      |	r1.invoice_p_cnt_l12m, --近12月纸质发票开票张数
      |	r1.invoice_p_cancel_cnt_l12m, --近12月纸质发票作废张数
      |	r1.invoice_p_red_cnt_l12m, --近12月纸质发票红冲张数
      |	r1.invoice_p_total_sum_l12m, --近12月纸质发票合计金额税额总和
      |	r1.invoice_e_cnt_l12m, --近12月电子发票开票张数
      |	r1.invoice_e_cancel_cnt_l12m, --近12月电子发票作废张数
      |	r1.invoice_e_red_cnt_l12m, --近12月电子发票红冲张数
      |	r1.invoice_e_total_sum_l12m, --近12月电子发票合计金额税额总和
      |	r1.invoice_r_cnt_l12m, --近12月卷式发票开票张数
      |	r1.invoice_r_cancel_cnt_l12m, --近12月卷式发票作废张数
      |	r1.invoice_r_red_cnt_l12m, --近12月卷式发票红冲张数
      |	r1.invoice_r_total_sum_l12m, --近12月卷式发票合计金额税额总和
      |
      |	r1.invoice_lt100_cnt_l12m, --近12月金额小于100发票张数
      |	r1.invoice_lt100_cr_cnt_l12m, --近12月金额小于100发票作废红冲张数
      |	r1.invoice_lt100_total_sum_l12m, --近12月金额小于100发票合计金额税额总和
      |	r1.invoice_lt1000_cnt_l12m, --近12月金额小于1000（且大于等于100）发票张数
      |	r1.invoice_lt1000_cr_cnt_l12m, --近12月金额小于1000（且大于等于100）发票作废红冲张数
      |	r1.invoice_lt1000_total_sum_l12m, --近12月金额小于1000（且大于等于100）发票合计金额税额总和
      |	r1.invoice_lt2500_cnt_l12m, --近12月金额小于2500且（且大于等于1000）发票张数
      |	r1.invoice_lt2500_cr_cnt_l12m, --近12月金额小于2500（且大于等于1000）发票作废红冲张数
      |	r1.invoice_lt2500_total_sum_l12m, --近12月金额小于2500（且大于等于1000）发票合计金额税额总和
      |	r1.invoice_lt5000_cnt_l12m, --近12月金额小于5000（且大于等于2500）发票张数
      |	r1.invoice_lt5000_cr_cnt_l12m, --近12月金额小于5000（且大于等于2500）发票作废红冲张数
      |	r1.invoice_lt5000_total_sum_l12m, --近12月金额小于5000（且大于等于2500）发票合计金额税额总和
      |	r1.invoice_lt10000_cnt_l12m, --近12月金额小于10000（且大于等于5000）发票张数
      |	r1.invoice_lt10000_cr_cnt_l12m, --近12月金额小于10000（且大于等于5000）发票作废红冲张数
      |	r1.invoice_lt10000_total_sum_l12m, --近12月金额小于10000（且大于等于5000）发票合计金额税额总和
      |	r1.invoice_gt10000_cnt_l12m, --近12月金额大于10000发票张数
      |	r1.invoice_gt10000_cr_cnt_l12m, --近12月金额大于10000发票作废红冲张数
      |	r1.invoice_gt10000_total_sum_l12m, --近12月金额大于10000发票合计金额税额总和
      |	r1.invoice_detail_cnt_l12m, --近12月附有清单的发票张数
      |	r1.invoice_detail_cr_cnt_l12m, --近12月附有清单的发票作废红冲张数
      |	r1.invoice_detail_total_sum_l12m, --近12月附有清单的发票合计金额税额总和
      |	r1.buyer_cnt_l12m, --近12月交易对手数目
      |	r4.buyer_a_invoice_cnt_l12m, --在统计月归为A类的交易对手近12月开票张数
      |	r4.buyer_b_invoice_cnt_l12m, --在统计月归为B类的交易对手近12月开票张数
      |	r4.buyer_c_invoice_cnt_l12m, --在统计月归为C类的交易对手近12月开票张数
      |	r4.buyer_f_invoice_cnt_l12m, --在统计月归为F类的交易对手近12月开票张数
      |	r4.buyer_a_amt_sum_l12m, --在统计月归为A类的交易对手近12月合计金额总和
      |	r4.buyer_b_amt_sum_l12m, --在统计月归为B类的交易对手近12月合计金额总和
      |	r4.buyer_c_amt_sum_l12m, --在统计月归为C类的交易对手近12月合计金额总和
      |	r4.buyer_f_amt_sum_l12m, --在统计月归为F类的交易对手近12月合计金额总和
      |
      |	r1.invoice_sc_cnt_l24m, --近24月增值税开票张数
      |	r1.invoice_sc_cancel_cnt_l24m, --近24月增值税发票作废张数
      |	r1.invoice_sc_red_cnt_l24m, --近24月增值税发票红冲张数
      |	r1.invoice_sc_total_sum_l24m, --近24月增值税发票合计金额税额总和
      |	r1.invoice_cnt_l24m, --近24月所有发票开票张数
      |	r1.invoice_cr_cnt_l24m, --近24月所有发票作废红冲张数
      |	r1.invoice_total_sum_l24m, --近24月所有发票合计金额税额总和
      |	r1.invoice_s_cnt_l24m, --近24月增值税专用发票开票张数
      |	r1.invoice_s_cancel_cnt_l24m, --近24月增值税专用发票作废张数
      |	r1.invoice_s_red_cnt_l24m, --近24月增值税专用发票红冲张数
      |	r1.invoice_s_total_sum_l24m, --近24月增值税专用发票合计金额税额总和
      |	r1.invoice_c_cnt_l24m, --近24月增值税普通发票开票张数
      |	r1.invoice_c_cancel_cnt_l24m, --近24月增值税普通发票作废张数
      |	r1.invoice_c_red_cnt_l24m, --近24月增值税普通发票红冲张数
      |	r1.invoice_c_total_sum_l24m, --近24月增值税普通发票合计金额税额总和
      |	r1.invoice_t_cnt_l24m, --近24月运输发票开票张数
      |	r1.invoice_t_cancel_cnt_l24m, --近24月运输发票作废张数
      |	r1.invoice_t_red_cnt_l24m, --近24月运输发票红冲张数
      |	r1.invoice_t_total_sum_l24m, --近24月运输发票合计金额税额总和
      |	r1.invoice_p_cnt_l24m, --近24月纸质发票开票张数
      |	r1.invoice_p_cancel_cnt_l24m, --近24月纸质发票作废张数
      |	r1.invoice_p_red_cnt_l24m, --近24月纸质发票红冲张数
      |	r1.invoice_p_total_sum_l24m, --近24月纸质发票合计金额税额总和
      |	r1.invoice_e_cnt_l24m, --近24月电子发票开票张数
      |	r1.invoice_e_cancel_cnt_l24m, --近24月电子发票作废张数
      |	r1.invoice_e_red_cnt_l24m, --近24月电子发票红冲张数
      |	r1.invoice_e_total_sum_l24m, --近24月电子发票合计金额税额总和
      |	r1.invoice_r_cnt_l24m, --近24月卷式发票开票张数
      |	r1.invoice_r_cancel_cnt_l24m, --近24月卷式发票作废张数
      |	r1.invoice_r_red_cnt_l24m, --近24月卷式发票红冲张数
      |	r1.invoice_r_total_sum_l24m, --近24月卷式发票合计金额税额总和
      |
      |	r1.invoice_lt100_cnt_l24m, --近24月金额小于100发票张数
      |	r1.invoice_lt100_cr_cnt_l24m, --近24月金额小于100发票作废红冲张数
      |	r1.invoice_lt100_total_sum_l24m, --近24月金额小于100发票合计金额税额总和
      |	r1.invoice_lt1000_cnt_l24m, --近24月金额小于1000（且大于等于100）发票张数
      |	r1.invoice_lt1000_cr_cnt_l24m, --近24月金额小于1000（且大于等于100）发票作废红冲张数
      |	r1.invoice_lt1000_total_sum_l24m, --近24月金额小于1000（且大于等于100）发票合计金额税额总和
      |	r1.invoice_lt2500_cnt_l24m, --近24月金额小于2500且（且大于等于1000）发票张数
      |	r1.invoice_lt2500_cr_cnt_l24m, --近24月金额小于2500（且大于等于1000）发票作废红冲张数
      |	r1.invoice_lt2500_total_sum_l24m, --近24月金额小于2500（且大于等于1000）发票合计金额税额总和
      |	r1.invoice_lt5000_cnt_l24m, --近24月金额小于5000（且大于等于2500）发票张数
      |	r1.invoice_lt5000_cr_cnt_l24m, --近24月金额小于5000（且大于等于2500）发票作废红冲张数
      |	r1.invoice_lt5000_total_sum_l24m, --近24月金额小于5000（且大于等于2500）发票合计金额税额总和
      |	r1.invoice_lt10000_cnt_l24m, --近24月金额小于10000（且大于等于5000）发票张数
      |	r1.invoice_lt10000_cr_cnt_l24m, --近24月金额小于10000（且大于等于5000）发票作废红冲张数
      |	r1.invoice_lt10000_total_sum_l24m, --近24月金额小于10000（且大于等于5000）发票合计金额税额总和
      |	r1.invoice_gt10000_cnt_l24m, --近24月金额大于10000发票张数
      |	r1.invoice_gt10000_cr_cnt_l24m, --近24月金额大于10000发票作废红冲张数
      |	r1.invoice_gt10000_total_sum_l24m, --近24月金额大于10000发票合计金额税额总和
      |	r1.invoice_detail_cnt_l24m, --近24月附有清单的发票张数
      |	r1.invoice_detail_cr_cnt_l24m, --近24月附有清单的发票作废红冲张数
      |	r1.invoice_detail_total_sum_l24m, --近24月附有清单的发票合计金额税额总和
      |	r1.buyer_cnt_l24m, --近24月交易对手数目
      |
      |	r4.buyer_a_invoice_cnt_l24m, --在统计月归为A类的交易对手近24月开票张数
      |	r4.buyer_b_invoice_cnt_l24m, --在统计月归为B类的交易对手近24月开票张数
      |	r4.buyer_c_invoice_cnt_l24m, --在统计月归为C类的交易对手近24月开票张数
      |	r4.buyer_f_invoice_cnt_l24m, --在统计月归为F类的交易对手近24月开票张数
      |	r4.buyer_a_amt_sum_l24m, --在统计月归为A类的交易对手近24月合计金额总和
      |	r4.buyer_b_amt_sum_l24m, --在统计月归为B类的交易对手近24月合计金额总和
      |	r4.buyer_c_amt_sum_l24m, --在统计月归为C类的交易对手近24月合计金额总和
      |	r4.buyer_f_amt_sum_l24m, --在统计月归为F类的交易对手近24月合计金额总和
      |
      |	r5.buyer_a_cnt_chg_rate_6m, --A类交易对手数目6个月变化率
      |	r5.buyer_b_cnt_chg_rate_6m, --B类交易对手数目6个月变化率
      |	r5.buyer_c_cnt_chg_rate_6m, --C类交易对手数目6个月变化率
      |	r5.buyer_f_cnt_chg_rate_6m, --F类交易对手数目6个月变化率
      |
      |	r6.buyer_a_invoice_cnt_l12m_chg_rate_6m, --近12月A类交易对手开票张数6个月变化率
      |	r6.buyer_b_invoice_cnt_l12m_chg_rate_6m, --近12月B类交易对手开票张数6个月变化率
      |	r6.buyer_c_invoice_cnt_l12m_chg_rate_6m, --近12月C类交易对手开票张数6个月变化率
      |	r6.buyer_f_invoice_cnt_l12m_chg_rate_6m, --近12月F类交易对手开票张数6个月变化率
      |	r6.buyer_a_amt_sum_l12m_chg_rate_6m, --近12月A类交易对手合计金额总和6个月变化率
      |	r6.buyer_b_amt_sum_l12m_chg_rate_6m, --近12月B类交易对手合计金额总和6个月变化率
      |	r6.buyer_c_amt_sum_l12m_chg_rate_6m, --近12月C类交易对手合计金额总和6个月变化率
      |	r6.buyer_f_amt_sum_l12m_chg_rate_6m, --近12月F类交易对手合计金额总和6个月变化率
      |
      |	r5.buyer_a_cnt_chg_rate_12m, --A类交易对手数目12个月环比变化率
      |	r5.buyer_b_cnt_chg_rate_12m, --B类交易对手数目12个月环比变化率
      |	r5.buyer_c_cnt_chg_rate_12m, --C类交易对手数目12个月环比变化率
      |	r5.buyer_f_cnt_chg_rate_12m, --F类交易对手数目12个月变化率
      |
      |	r6.buyer_a_invoice_cnt_l12m_chg_rate_12m, --近12月A类交易对手开票张数12个月变化率
      |	r6.buyer_b_invoice_cnt_l12m_chg_rate_12m, --近12月B类交易对手开票张数12个月变化率
      |	r6.buyer_c_invoice_cnt_l12m_chg_rate_12m, --近12月C类交易对手开票张数12个月变化率
      |	r6.buyer_f_invoice_cnt_l12m_chg_rate_12m, --近12月F类交易对手开票张数12个月变化率
      |	r6.buyer_a_amt_sum_l12m_chg_rate_12m, --近12月A类交易对手合计金额总和12个月变化率
      |	r6.buyer_b_amt_sum_l12m_chg_rate_12m, --近12月B类交易对手合计金额总和12个月变化率
      |	r6.buyer_c_amt_sum_l12m_chg_rate_12m, --近12月C类交易对手合计金额总和12个月变化率
      |	r6.buyer_f_amt_sum_l12m_chg_rate_12m --近12月F类交易对手合计金额总和12个月变化率
      |from r4
      |left join r1
      |on r1.mcht_cd=r4.sellertaxno and r1.data_month=r4.data_month
      |left join r5
      |on r1.mcht_cd=r5.mcht_cd and r1.data_month=r5.data_month
      |left join r6
      |on r5.mcht_cd=r6.mcht_cd and r5.data_month=r6.data_month
    """.stripMargin


  //--------插入语句拆分为cross_1与cross_2

  val cross_1 =
    s"""
      |select
      |	p1.mcht_cd,
      |	p1.month_pre1,
      |
      |	(case when p2.invoice_sc_cnt_l3m is null then 0 else p2.invoice_sc_cnt_l3m end) as invoice_sc_cnt_l3m,
      |	(case when p2.invoice_sc_cancel_cnt_l3m is null then 0 else p2.invoice_sc_cancel_cnt_l3m end) as invoice_sc_cancel_cnt_l3m,
      |	(case when p2.invoice_sc_red_cnt_l3m is null then 0 else p2.invoice_sc_red_cnt_l3m end) as invoice_sc_red_cnt_l3m,
      |	(case when p2.invoice_sc_total_sum_l3m is null then 0 else ROUND(p2.invoice_sc_total_sum_l3m,2) end) as invoice_sc_total_sum_l3m,
      |	(case when p2.invoice_cnt_l3m is null then 0 else p2.invoice_cnt_l3m end) as invoice_cnt_l3m,
      |	(case when p2.invoice_cr_cnt_l3m is null then 0 else p2.invoice_cr_cnt_l3m end) as invoice_cr_cnt_l3m,
      |	(case when p2.invoice_total_sum_l3m is null then 0 else ROUND(p2.invoice_total_sum_l3m,2) end) as invoice_total_sum_l3m,
      |	(case when p2.invoice_s_cnt_l3m is null then 0 else p2.invoice_s_cnt_l3m end) as invoice_s_cnt_l3m,
      |	(case when p2.invoice_s_cancel_cnt_l3m is null then 0 else p2.invoice_s_cancel_cnt_l3m end) as invoice_s_cancel_cnt_l3m,
      |	(case when p2.invoice_s_red_cnt_l3m is null then 0 else p2.invoice_s_red_cnt_l3m end) as invoice_s_red_cnt_l3m,
      |	(case when p2.invoice_s_total_sum_l3m is null then 0 else ROUND(p2.invoice_s_total_sum_l3m,2) end) as invoice_s_total_sum_l3m,
      |	(case when p2.invoice_c_cnt_l3m is null then 0 else p2.invoice_c_cnt_l3m end) as invoice_c_cnt_l3m,
      |	(case when p2.invoice_c_cancel_cnt_l3m is null then 0 else p2.invoice_c_cancel_cnt_l3m end) as invoice_c_cancel_cnt_l3m,
      |	(case when p2.invoice_c_red_cnt_l3m is null then 0 else p2.invoice_c_red_cnt_l3m end) as invoice_c_red_cnt_l3m,
      |	(case when p2.invoice_c_total_sum_l3m is null then 0 else ROUND(p2.invoice_c_total_sum_l3m,2) end) as invoice_c_total_sum_l3m,
      |	(case when p2.invoice_t_cnt_l3m is null then 0 else p2.invoice_t_cnt_l3m end) as invoice_t_cnt_l3m,
      |	(case when p2.invoice_t_cancel_cnt_l3m is null then 0 else p2.invoice_t_cancel_cnt_l3m end) as invoice_t_cancel_cnt_l3m,
      |	(case when p2.invoice_t_red_cnt_l3m is null then 0 else p2.invoice_t_red_cnt_l3m end) as invoice_t_red_cnt_l3m,
      |	(case when p2.invoice_t_total_sum_l3m is null then 0 else ROUND(p2.invoice_t_total_sum_l3m,2) end) as invoice_t_total_sum_l3m,
      |	(case when p2.invoice_p_cnt_l3m is null then 0 else p2.invoice_p_cnt_l3m end) as invoice_p_cnt_l3m,
      |	(case when p2.invoice_p_cancel_cnt_l3m is null then 0 else p2.invoice_p_cancel_cnt_l3m end) as invoice_p_cancel_cnt_l3m,
      |	(case when p2.invoice_p_red_cnt_l3m is null then 0 else p2.invoice_p_red_cnt_l3m end) as invoice_p_red_cnt_l3m,
      |	(case when p2.invoice_p_total_sum_l3m is null then 0 else ROUND(p2.invoice_p_total_sum_l3m,2) end) as invoice_p_total_sum_l3m,
      |	(case when p2.invoice_e_cnt_l3m is null then 0 else p2.invoice_e_cnt_l3m end) as invoice_e_cnt_l3m,
      |	(case when p2.invoice_e_cancel_cnt_l3m is null then 0 else p2.invoice_e_cancel_cnt_l3m end) as invoice_e_cancel_cnt_l3m,
      |	(case when p2.invoice_e_red_cnt_l3m is null then 0 else p2.invoice_e_red_cnt_l3m end) as invoice_e_red_cnt_l3m,
      |	(case when p2.invoice_e_total_sum_l3m is null then 0 else ROUND(p2.invoice_e_total_sum_l3m,2) end) as invoice_e_total_sum_l3m,
      |	(case when p2.invoice_r_cnt_l3m is null then 0 else p2.invoice_r_cnt_l3m end) as invoice_r_cnt_l3m,
      |	(case when p2.invoice_r_cancel_cnt_l3m is null then 0 else p2.invoice_r_cancel_cnt_l3m end) as invoice_r_cancel_cnt_l3m,
      |	(case when p2.invoice_r_red_cnt_l3m is null then 0 else p2.invoice_r_red_cnt_l3m end) as invoice_r_red_cnt_l3m,
      |	(case when p2.invoice_r_total_sum_l3m is null then 0 else ROUND(p2.invoice_r_total_sum_l3m,2) end) as invoice_r_total_sum_l3m,
      |
      |	(case when p2.invoice_lt100_cnt_l3m is null then 0 else p2.invoice_lt100_cnt_l3m end) as invoice_lt100_cnt_l3m,
      |	(case when p2.invoice_lt100_cr_cnt_l3m is null then 0 else p2.invoice_lt100_cr_cnt_l3m end) as invoice_lt100_cr_cnt_l3m,
      |	(case when p2.invoice_lt100_total_sum_l3m is null then 0 else ROUND(p2.invoice_lt100_total_sum_l3m,2) end) as invoice_lt100_total_sum_l3m,
      |	(case when p2.invoice_lt1000_cnt_l3m is null then 0 else p2.invoice_lt1000_cnt_l3m end) as invoice_lt1000_cnt_l3m,
      |	(case when p2.invoice_lt1000_cr_cnt_l3m is null then 0 else p2.invoice_lt1000_cr_cnt_l3m end) as invoice_lt1000_cr_cnt_l3m,
      |	(case when p2.invoice_lt1000_total_sum_l3m is null then 0 else ROUND(p2.invoice_lt1000_total_sum_l3m,2) end) as invoice_lt1000_total_sum_l3m,
      |	(case when p2.invoice_lt2500_cnt_l3m is null then 0 else p2.invoice_lt2500_cnt_l3m end) as invoice_lt2500_cnt_l3m,
      |	(case when p2.invoice_lt2500_cr_cnt_l3m is null then 0 else p2.invoice_lt2500_cr_cnt_l3m end) as invoice_lt2500_cr_cnt_l3m,
      |	(case when p2.invoice_lt2500_total_sum_l3m is null then 0 else ROUND(p2.invoice_lt2500_total_sum_l3m,2) end) as invoice_lt2500_total_sum_l3m,
      |	(case when p2.invoice_lt5000_cnt_l3m is null then 0 else p2.invoice_lt5000_cnt_l3m end) as invoice_lt5000_cnt_l3m,
      |	(case when p2.invoice_lt5000_cr_cnt_l3m is null then 0 else p2.invoice_lt5000_cr_cnt_l3m end) as invoice_lt5000_cr_cnt_l3m,
      |	(case when p2.invoice_lt5000_total_sum_l3m is null then 0 else ROUND(p2.invoice_lt5000_total_sum_l3m,2) end) as invoice_lt5000_total_sum_l3m,
      |	(case when p2.invoice_lt10000_cnt_l3m is null then 0 else p2.invoice_lt10000_cnt_l3m end) as invoice_lt10000_cnt_l3m,
      |	(case when p2.invoice_lt10000_cr_cnt_l3m is null then 0 else p2.invoice_lt10000_cr_cnt_l3m end) as invoice_lt10000_cr_cnt_l3m,
      |	(case when p2.invoice_lt10000_total_sum_l3m is null then 0 else ROUND(p2.invoice_lt10000_total_sum_l3m,2) end) as invoice_lt10000_total_sum_l3m,
      |	(case when p2.invoice_gt10000_cnt_l3m is null then 0 else p2.invoice_gt10000_cnt_l3m end) as invoice_gt10000_cnt_l3m,
      |	(case when p2.invoice_gt10000_cr_cnt_l3m is null then 0 else p2.invoice_gt10000_cr_cnt_l3m end) as invoice_gt10000_cr_cnt_l3m,
      |	(case when p2.invoice_gt10000_total_sum_l3m is null then 0 else ROUND(p2.invoice_gt10000_total_sum_l3m,2) end) as invoice_gt10000_total_sum_l3m,
      |	(case when p2.invoice_detail_cnt_l3m is null then 0 else p2.invoice_detail_cnt_l3m end) as invoice_detail_cnt_l3m,
      |	(case when p2.invoice_detail_cr_cnt_l3m is null then 0 else p2.invoice_detail_cr_cnt_l3m end) as invoice_detail_cr_cnt_l3m,
      |	(case when p2.invoice_detail_total_sum_l3m is null then 0 else ROUND(p2.invoice_detail_total_sum_l3m,2) end) as invoice_detail_total_sum_l3m,
      |	(case when p2.buyer_cnt_l3m is null then 0 else p2.buyer_cnt_l3m end) as buyer_cnt_l3m,
      |	(case when p2.invoice_sc_cnt_l6m is null then 0 else p2.invoice_sc_cnt_l6m end) as invoice_sc_cnt_l6m,
      |	(case when p2.invoice_sc_cancel_cnt_l6m is null then 0 else p2.invoice_sc_cancel_cnt_l6m end) as invoice_sc_cancel_cnt_l6m,
      |	(case when p2.invoice_sc_red_cnt_l6m is null then 0 else p2.invoice_sc_red_cnt_l6m end) as invoice_sc_red_cnt_l6m,
      |	(case when p2.invoice_sc_total_sum_l6m is null then 0 else ROUND(p2.invoice_sc_total_sum_l6m,2) end) as invoice_sc_total_sum_l6m,
      |	(case when p2.invoice_cnt_l6m is null then 0 else p2.invoice_cnt_l6m end) as invoice_cnt_l6m,
      |	(case when p2.invoice_cr_cnt_l6m is null then 0 else p2.invoice_cr_cnt_l6m end) as invoice_cr_cnt_l6m,
      |	(case when p2.invoice_total_sum_l6m is null then 0 else ROUND(p2.invoice_total_sum_l6m,2) end) as invoice_total_sum_l6m,
      |	(case when p2.invoice_s_cnt_l6m is null then 0 else p2.invoice_s_cnt_l6m end) as invoice_s_cnt_l6m,
      |	(case when p2.invoice_s_cancel_cnt_l6m is null then 0 else p2.invoice_s_cancel_cnt_l6m end) as invoice_s_cancel_cnt_l6m,
      |	(case when p2.invoice_s_red_cnt_l6m is null then 0 else p2.invoice_s_red_cnt_l6m end) as invoice_s_red_cnt_l6m,
      |	(case when p2.invoice_s_total_sum_l6m is null then 0 else ROUND(p2.invoice_s_total_sum_l6m,2) end) as invoice_s_total_sum_l6m,
      |	(case when p2.invoice_c_cnt_l6m is null then 0 else p2.invoice_c_cnt_l6m end) as invoice_c_cnt_l6m,
      |	(case when p2.invoice_c_cancel_cnt_l6m is null then 0 else p2.invoice_c_cancel_cnt_l6m end) as invoice_c_cancel_cnt_l6m,
      |	(case when p2.invoice_c_red_cnt_l6m is null then 0 else p2.invoice_c_red_cnt_l6m end) as invoice_c_red_cnt_l6m,
      |	(case when p2.invoice_c_total_sum_l6m is null then 0 else ROUND(p2.invoice_c_total_sum_l6m,2) end) as invoice_c_total_sum_l6m,
      |	(case when p2.invoice_t_cnt_l6m is null then 0 else p2.invoice_t_cnt_l6m end) as invoice_t_cnt_l6m,
      |	(case when p2.invoice_t_cancel_cnt_l6m is null then 0 else p2.invoice_t_cancel_cnt_l6m end) as invoice_t_cancel_cnt_l6m,
      |	(case when p2.invoice_t_red_cnt_l6m is null then 0 else p2.invoice_t_red_cnt_l6m end) as invoice_t_red_cnt_l6m,
      |	(case when p2.invoice_t_total_sum_l6m is null then 0 else ROUND(p2.invoice_t_total_sum_l6m,2) end) as invoice_t_total_sum_l6m,
      |	(case when p2.invoice_p_cnt_l6m is null then 0 else p2.invoice_p_cnt_l6m end) as invoice_p_cnt_l6m,
      |	(case when p2.invoice_p_cancel_cnt_l6m is null then 0 else p2.invoice_p_cancel_cnt_l6m end) as invoice_p_cancel_cnt_l6m,
      |	(case when p2.invoice_p_red_cnt_l6m is null then 0 else p2.invoice_p_red_cnt_l6m end) as invoice_p_red_cnt_l6m,
      |	(case when p2.invoice_p_total_sum_l6m is null then 0 else ROUND(p2.invoice_p_total_sum_l6m,2) end) as invoice_p_total_sum_l6m,
      |	(case when p2.invoice_e_cnt_l6m is null then 0 else p2.invoice_e_cnt_l6m end) as invoice_e_cnt_l6m,
      |	(case when p2.invoice_e_cancel_cnt_l6m is null then 0 else p2.invoice_e_cancel_cnt_l6m end) as invoice_e_cancel_cnt_l6m,
      |	(case when p2.invoice_e_red_cnt_l6m is null then 0 else p2.invoice_e_red_cnt_l6m end) as invoice_e_red_cnt_l6m,
      |	(case when p2.invoice_e_total_sum_l6m is null then 0 else ROUND(p2.invoice_e_total_sum_l6m,2) end) as invoice_e_total_sum_l6m,
      |	(case when p2.invoice_r_cnt_l6m is null then 0 else p2.invoice_r_cnt_l6m end) as invoice_r_cnt_l6m,
      |	(case when p2.invoice_r_cancel_cnt_l6m is null then 0 else p2.invoice_r_cancel_cnt_l6m end) as invoice_r_cancel_cnt_l6m,
      |	(case when p2.invoice_r_red_cnt_l6m is null then 0 else p2.invoice_r_red_cnt_l6m end) as invoice_r_red_cnt_l6m,
      |	(case when p2.invoice_r_total_sum_l6m is null then 0 else ROUND(p2.invoice_r_total_sum_l6m,2) end) as invoice_r_total_sum_l6m,
      |
      |	(case when p2.invoice_lt100_cnt_l6m is null then 0 else p2.invoice_lt100_cnt_l6m end) as invoice_lt100_cnt_l6m,
      |	(case when p2.invoice_lt100_cr_cnt_l6m is null then 0 else p2.invoice_lt100_cr_cnt_l6m end) as invoice_lt100_cr_cnt_l6m,
      |	(case when p2.invoice_lt100_total_sum_l6m is null then 0 else ROUND(p2.invoice_lt100_total_sum_l6m,2) end) as invoice_lt100_total_sum_l6m,
      |	(case when p2.invoice_lt1000_cnt_l6m is null then 0 else p2.invoice_lt1000_cnt_l6m end) as invoice_lt1000_cnt_l6m,
      |	(case when p2.invoice_lt1000_cr_cnt_l6m is null then 0 else p2.invoice_lt1000_cr_cnt_l6m end) as invoice_lt1000_cr_cnt_l6m,
      |	(case when p2.invoice_lt1000_total_sum_l6m is null then 0 else ROUND(p2.invoice_lt1000_total_sum_l6m,2) end) as invoice_lt1000_total_sum_l6m,
      |	(case when p2.invoice_lt2500_cnt_l6m is null then 0 else p2.invoice_lt2500_cnt_l6m end) as invoice_lt2500_cnt_l6m,
      |	(case when p2.invoice_lt2500_cr_cnt_l6m is null then 0 else p2.invoice_lt2500_cr_cnt_l6m end) as invoice_lt2500_cr_cnt_l6m,
      |	(case when p2.invoice_lt2500_total_sum_l6m is null then 0 else ROUND(p2.invoice_lt2500_total_sum_l6m,2) end) as invoice_lt2500_total_sum_l6m,
      |	(case when p2.invoice_lt5000_cnt_l6m is null then 0 else p2.invoice_lt5000_cnt_l6m end) as invoice_lt5000_cnt_l6m,
      |	(case when p2.invoice_lt5000_cr_cnt_l6m is null then 0 else p2.invoice_lt5000_cr_cnt_l6m end) as invoice_lt5000_cr_cnt_l6m,
      |	(case when p2.invoice_lt5000_total_sum_l6m is null then 0 else ROUND(p2.invoice_lt5000_total_sum_l6m,2) end) as invoice_lt5000_total_sum_l6m,
      |	(case when p2.invoice_lt10000_cnt_l6m is null then 0 else p2.invoice_lt10000_cnt_l6m end) as invoice_lt10000_cnt_l6m,
      |	(case when p2.invoice_lt10000_cr_cnt_l6m is null then 0 else p2.invoice_lt10000_cr_cnt_l6m end) as invoice_lt10000_cr_cnt_l6m,
      |	(case when p2.invoice_lt10000_total_sum_l6m is null then 0 else ROUND(p2.invoice_lt10000_total_sum_l6m,2) end) as invoice_lt10000_total_sum_l6m,
      |	(case when p2.invoice_gt10000_cnt_l6m is null then 0 else p2.invoice_gt10000_cnt_l6m end) as invoice_gt10000_cnt_l6m,
      |	(case when p2.invoice_gt10000_cr_cnt_l6m is null then 0 else p2.invoice_gt10000_cr_cnt_l6m end) as invoice_gt10000_cr_cnt_l6m,
      |	(case when p2.invoice_gt10000_total_sum_l6m is null then 0 else ROUND(p2.invoice_gt10000_total_sum_l6m,2) end) as invoice_gt10000_total_sum_l6m,
      |	(case when p2.invoice_detail_cnt_l6m is null then 0 else p2.invoice_detail_cnt_l6m end) as invoice_detail_cnt_l6m,
      |	(case when p2.invoice_detail_cr_cnt_l6m is null then 0 else p2.invoice_detail_cr_cnt_l6m end) as invoice_detail_cr_cnt_l6m,
      |	(case when p2.invoice_detail_total_sum_l6m is null then 0 else ROUND(p2.invoice_detail_total_sum_l6m,2) end) as invoice_detail_total_sum_l6m,
      |	(case when p2.buyer_cnt_l6m is null then 0 else p2.buyer_cnt_l6m end) as buyer_cnt_l6m,
      |	(case when p2.buyer_a_invoice_cnt_l6m is null then 0 else p2.buyer_a_invoice_cnt_l6m end) as buyer_a_invoice_cnt_l6m,
      |	(case when p2.buyer_b_invoice_cnt_l6m is null then 0 else p2.buyer_b_invoice_cnt_l6m end) as buyer_b_invoice_cnt_l6m,
      |	(case when p2.buyer_c_invoice_cnt_l6m is null then 0 else p2.buyer_c_invoice_cnt_l6m end) as buyer_c_invoice_cnt_l6m,
      |	(case when p2.buyer_f_invoice_cnt_l6m is null then 0 else p2.buyer_f_invoice_cnt_l6m end) as buyer_f_invoice_cnt_l6m,
      |	(case when p2.buyer_a_amt_sum_l6m is null then 0 else ROUND(p2.buyer_a_amt_sum_l6m,2) end) as buyer_a_amt_sum_l6m,
      |	(case when p2.buyer_b_amt_sum_l6m is null then 0 else ROUND(p2.buyer_b_amt_sum_l6m,2) end) as buyer_b_amt_sum_l6m,
      |	(case when p2.buyer_c_amt_sum_l6m is null then 0 else ROUND(p2.buyer_c_amt_sum_l6m,2) end) as buyer_c_amt_sum_l6m,
      |	(case when p2.buyer_f_amt_sum_l6m is null then 0 else ROUND(p2.buyer_f_amt_sum_l6m,2) end) as buyer_f_amt_sum_l6m,
      |	(case when p2.invoice_sc_cnt_l12m is null then 0 else p2.invoice_sc_cnt_l12m end) as invoice_sc_cnt_l12m,
      |	(case when p2.invoice_sc_cancel_cnt_l12m is null then 0 else p2.invoice_sc_cancel_cnt_l12m end) as invoice_sc_cancel_cnt_l12m,
      |	(case when p2.invoice_sc_red_cnt_l12m is null then 0 else p2.invoice_sc_red_cnt_l12m end) as invoice_sc_red_cnt_l12m,
      |	(case when p2.invoice_sc_total_sum_l12m is null then 0 else ROUND(p2.invoice_sc_total_sum_l12m,2) end) as invoice_sc_total_sum_l12m,
      |	(case when p2.invoice_cnt_l12m is null then 0 else p2.invoice_cnt_l12m end) as invoice_cnt_l12m,
      |	(case when p2.invoice_cr_cnt_l12m is null then 0 else p2.invoice_cr_cnt_l12m end) as invoice_cr_cnt_l12m,
      |	(case when p2.invoice_total_sum_l12m is null then 0 else ROUND(p2.invoice_total_sum_l12m,2) end) as invoice_total_sum_l12m,
      |	(case when p2.invoice_s_cnt_l12m is null then 0 else p2.invoice_s_cnt_l12m end) as invoice_s_cnt_l12m,
      |	(case when p2.invoice_s_cancel_cnt_l12m is null then 0 else p2.invoice_s_cancel_cnt_l12m end) as invoice_s_cancel_cnt_l12m,
      |	(case when p2.invoice_s_red_cnt_l12m is null then 0 else p2.invoice_s_red_cnt_l12m end) as invoice_s_red_cnt_l12m,
      |	(case when p2.invoice_s_total_sum_l12m is null then 0 else ROUND(p2.invoice_s_total_sum_l12m,2) end) as invoice_s_total_sum_l12m,
      |	(case when p2.invoice_c_cnt_l12m is null then 0 else p2.invoice_c_cnt_l12m end) as invoice_c_cnt_l12m,
      |	(case when p2.invoice_c_cancel_cnt_l12m is null then 0 else p2.invoice_c_cancel_cnt_l12m end) as invoice_c_cancel_cnt_l12m,
      |	(case when p2.invoice_c_red_cnt_l12m is null then 0 else p2.invoice_c_red_cnt_l12m end) as invoice_c_red_cnt_l12m,
      |	(case when p2.invoice_c_total_sum_l12m is null then 0 else ROUND(p2.invoice_c_total_sum_l12m,2) end) as invoice_c_total_sum_l12m,
      |	(case when p2.invoice_t_cnt_l12m is null then 0 else p2.invoice_t_cnt_l12m end) as invoice_t_cnt_l12m,
      |	(case when p2.invoice_t_cancel_cnt_l12m is null then 0 else p2.invoice_t_cancel_cnt_l12m end) as invoice_t_cancel_cnt_l12m,
      |	(case when p2.invoice_t_red_cnt_l12m is null then 0 else p2.invoice_t_red_cnt_l12m end) as invoice_t_red_cnt_l12m,
      |	(case when p2.invoice_t_total_sum_l12m is null then 0 else ROUND(p2.invoice_t_total_sum_l12m,2) end) as invoice_t_total_sum_l12m,
      |	(case when p2.invoice_p_cnt_l12m is null then 0 else p2.invoice_p_cnt_l12m end) as invoice_p_cnt_l12m,
      |	(case when p2.invoice_p_cancel_cnt_l12m is null then 0 else p2.invoice_p_cancel_cnt_l12m end) as invoice_p_cancel_cnt_l12m,
      |	(case when p2.invoice_p_red_cnt_l12m is null then 0 else p2.invoice_p_red_cnt_l12m end) as invoice_p_red_cnt_l12m,
      |	(case when p2.invoice_p_total_sum_l12m is null then 0 else ROUND(p2.invoice_p_total_sum_l12m,2) end) as invoice_p_total_sum_l12m,
      |	(case when p2.invoice_e_cnt_l12m is null then 0 else p2.invoice_e_cnt_l12m end) as invoice_e_cnt_l12m,
      |	(case when p2.invoice_e_cancel_cnt_l12m is null then 0 else p2.invoice_e_cancel_cnt_l12m end) as invoice_e_cancel_cnt_l12m,
      |	(case when p2.invoice_e_red_cnt_l12m is null then 0 else p2.invoice_e_red_cnt_l12m end) as invoice_e_red_cnt_l12m,
      |	(case when p2.invoice_e_total_sum_l12m is null then 0 else ROUND(p2.invoice_e_total_sum_l12m,2) end) as invoice_e_total_sum_l12m,
      |	(case when p2.invoice_r_cnt_l12m is null then 0 else p2.invoice_r_cnt_l12m end) as invoice_r_cnt_l12m,
      |	(case when p2.invoice_r_cancel_cnt_l12m is null then 0 else p2.invoice_r_cancel_cnt_l12m end) as invoice_r_cancel_cnt_l12m,
      |	(case when p2.invoice_r_red_cnt_l12m is null then 0 else p2.invoice_r_red_cnt_l12m end) as invoice_r_red_cnt_l12m,
      |	(case when p2.invoice_r_total_sum_l12m is null then 0 else ROUND(p2.invoice_r_total_sum_l12m,2) end) as invoice_r_total_sum_l12m,
      |
      |	(case when p2.invoice_lt100_cnt_l12m is null then 0 else p2.invoice_lt100_cnt_l12m end) as invoice_lt100_cnt_l12m,
      |	(case when p2.invoice_lt100_cr_cnt_l12m is null then 0 else p2.invoice_lt100_cr_cnt_l12m end) as invoice_lt100_cr_cnt_l12m,
      |	(case when p2.invoice_lt100_total_sum_l12m is null then 0 else ROUND(p2.invoice_lt100_total_sum_l12m,2) end) as invoice_lt100_total_sum_l12m,
      |	(case when p2.invoice_lt1000_cnt_l12m is null then 0 else p2.invoice_lt1000_cnt_l12m end) as invoice_lt1000_cnt_l12m,
      |	(case when p2.invoice_lt1000_cr_cnt_l12m is null then 0 else p2.invoice_lt1000_cr_cnt_l12m end) as invoice_lt1000_cr_cnt_l12m,
      |	(case when p2.invoice_lt1000_total_sum_l12m is null then 0 else ROUND(p2.invoice_lt1000_total_sum_l12m,2) end) as invoice_lt1000_total_sum_l12m,
      |	(case when p2.invoice_lt2500_cnt_l12m is null then 0 else p2.invoice_lt2500_cnt_l12m end) as invoice_lt2500_cnt_l12m,
      |	(case when p2.invoice_lt2500_cr_cnt_l12m is null then 0 else p2.invoice_lt2500_cr_cnt_l12m end) as invoice_lt2500_cr_cnt_l12m,
      |	(case when p2.invoice_lt2500_total_sum_l12m is null then 0 else ROUND(p2.invoice_lt2500_total_sum_l12m,2) end) as invoice_lt2500_total_sum_l12m,
      |	(case when p2.invoice_lt5000_cnt_l12m is null then 0 else p2.invoice_lt5000_cnt_l12m end) as invoice_lt5000_cnt_l12m,
      |	(case when p2.invoice_lt5000_cr_cnt_l12m is null then 0 else p2.invoice_lt5000_cr_cnt_l12m end) as invoice_lt5000_cr_cnt_l12m,
      |	(case when p2.invoice_lt5000_total_sum_l12m is null then 0 else ROUND(p2.invoice_lt5000_total_sum_l12m,2) end) as invoice_lt5000_total_sum_l12m,
      |	(case when p2.invoice_lt10000_cnt_l12m is null then 0 else p2.invoice_lt10000_cnt_l12m end) as invoice_lt10000_cnt_l12m,
      |	(case when p2.invoice_lt10000_cr_cnt_l12m is null then 0 else p2.invoice_lt10000_cr_cnt_l12m end) as invoice_lt10000_cr_cnt_l12m,
      |	(case when p2.invoice_lt10000_total_sum_l12m is null then 0 else ROUND(p2.invoice_lt10000_total_sum_l12m,2) end) as invoice_lt10000_total_sum_l12m,
      |	(case when p2.invoice_gt10000_cnt_l12m is null then 0 else p2.invoice_gt10000_cnt_l12m end) as invoice_gt10000_cnt_l12m,
      |	(case when p2.invoice_gt10000_cr_cnt_l12m is null then 0 else p2.invoice_gt10000_cr_cnt_l12m end) as invoice_gt10000_cr_cnt_l12m,
      |	(case when p2.invoice_gt10000_total_sum_l12m is null then 0 else ROUND(p2.invoice_gt10000_total_sum_l12m,2) end) as invoice_gt10000_total_sum_l12m,
      |	(case when p2.invoice_detail_cnt_l12m is null then 0 else p2.invoice_detail_cnt_l12m end) as invoice_detail_cnt_l12m,
      |	(case when p2.invoice_detail_cr_cnt_l12m is null then 0 else p2.invoice_detail_cr_cnt_l12m end) as invoice_detail_cr_cnt_l12m,
      |	(case when p2.invoice_detail_total_sum_l12m is null then 0 else ROUND(p2.invoice_detail_total_sum_l12m,2) end) as invoice_detail_total_sum_l12m,
      |	(case when p2.buyer_cnt_l12m is null then 0 else p2.buyer_cnt_l12m end) as buyer_cnt_l12m,
      |	(case when p2.buyer_a_invoice_cnt_l12m is null then 0 else p2.buyer_a_invoice_cnt_l12m end) as buyer_a_invoice_cnt_l12m,
      |	(case when p2.buyer_b_invoice_cnt_l12m is null then 0 else p2.buyer_b_invoice_cnt_l12m end) as buyer_b_invoice_cnt_l12m,
      |	(case when p2.buyer_c_invoice_cnt_l12m is null then 0 else p2.buyer_c_invoice_cnt_l12m end) as buyer_c_invoice_cnt_l12m,
      |	(case when p2.buyer_f_invoice_cnt_l12m is null then 0 else p2.buyer_f_invoice_cnt_l12m end) as buyer_f_invoice_cnt_l12m,
      |	(case when p2.buyer_a_amt_sum_l12m is null then 0 else ROUND(p2.buyer_a_amt_sum_l12m,2) end) as buyer_a_amt_sum_l12m,
      |	(case when p2.buyer_b_amt_sum_l12m is null then 0 else ROUND(p2.buyer_b_amt_sum_l12m,2) end) as buyer_b_amt_sum_l12m,
      |	(case when p2.buyer_c_amt_sum_l12m is null then 0 else ROUND(p2.buyer_c_amt_sum_l12m,2) end) as buyer_c_amt_sum_l12m,
      |	(case when p2.buyer_f_amt_sum_l12m is null then 0 else ROUND(p2.buyer_f_amt_sum_l12m,2) end) as buyer_f_amt_sum_l12m,
      |	(case when p2.invoice_sc_cnt_l24m is null then 0 else p2.invoice_sc_cnt_l24m end) as invoice_sc_cnt_l24m,
      |	(case when p2.invoice_sc_cancel_cnt_l24m is null then 0 else p2.invoice_sc_cancel_cnt_l24m end) as invoice_sc_cancel_cnt_l24m,
      |	(case when p2.invoice_sc_red_cnt_l24m is null then 0 else p2.invoice_sc_red_cnt_l24m end) as invoice_sc_red_cnt_l24m,
      |	(case when p2.invoice_sc_total_sum_l24m is null then 0 else ROUND(p2.invoice_sc_total_sum_l24m,2) end) as invoice_sc_total_sum_l24m,
      |	(case when p2.invoice_cnt_l24m is null then 0 else p2.invoice_cnt_l24m end) as invoice_cnt_l24m,
      |	(case when p2.invoice_cr_cnt_l24m is null then 0 else p2.invoice_cr_cnt_l24m end) as invoice_cr_cnt_l24m,
      |	(case when p2.invoice_total_sum_l24m is null then 0 else ROUND(p2.invoice_total_sum_l24m,2) end) as invoice_total_sum_l24m,
      |	(case when p2.invoice_s_cnt_l24m is null then 0 else p2.invoice_s_cnt_l24m end) as invoice_s_cnt_l24m,
      |	(case when p2.invoice_s_cancel_cnt_l24m is null then 0 else p2.invoice_s_cancel_cnt_l24m end) as invoice_s_cancel_cnt_l24m,
      |	(case when p2.invoice_s_red_cnt_l24m is null then 0 else p2.invoice_s_red_cnt_l24m end) as invoice_s_red_cnt_l24m,
      |	(case when p2.invoice_s_total_sum_l24m is null then 0 else ROUND(p2.invoice_s_total_sum_l24m,2) end) as invoice_s_total_sum_l24m,
      |	(case when p2.invoice_c_cnt_l24m is null then 0 else p2.invoice_c_cnt_l24m end) as invoice_c_cnt_l24m,
      |	(case when p2.invoice_c_cancel_cnt_l24m is null then 0 else p2.invoice_c_cancel_cnt_l24m end) as invoice_c_cancel_cnt_l24m,
      |	(case when p2.invoice_c_red_cnt_l24m is null then 0 else p2.invoice_c_red_cnt_l24m end) as invoice_c_red_cnt_l24m,
      |	(case when p2.invoice_c_total_sum_l24m is null then 0 else ROUND(p2.invoice_c_total_sum_l24m,2) end) as invoice_c_total_sum_l24m,
      |	(case when p2.invoice_t_cnt_l24m is null then 0 else p2.invoice_t_cnt_l24m end) as invoice_t_cnt_l24m,
      |	(case when p2.invoice_t_cancel_cnt_l24m is null then 0 else p2.invoice_t_cancel_cnt_l24m end) as invoice_t_cancel_cnt_l24m,
      |	(case when p2.invoice_t_red_cnt_l24m is null then 0 else p2.invoice_t_red_cnt_l24m end) as invoice_t_red_cnt_l24m,
      |	(case when p2.invoice_t_total_sum_l24m is null then 0 else ROUND(p2.invoice_t_total_sum_l24m,2) end) as invoice_t_total_sum_l24m,
      |	(case when p2.invoice_p_cnt_l24m is null then 0 else p2.invoice_p_cnt_l24m end) as invoice_p_cnt_l24m,
      |	(case when p2.invoice_p_cancel_cnt_l24m is null then 0 else p2.invoice_p_cancel_cnt_l24m end) as invoice_p_cancel_cnt_l24m,
      |	(case when p2.invoice_p_red_cnt_l24m is null then 0 else p2.invoice_p_red_cnt_l24m end) as invoice_p_red_cnt_l24m,
      |	(case when p2.invoice_p_total_sum_l24m is null then 0 else ROUND(p2.invoice_p_total_sum_l24m,2) end) as invoice_p_total_sum_l24m,
      |	(case when p2.invoice_e_cnt_l24m is null then 0 else p2.invoice_e_cnt_l24m end) as invoice_e_cnt_l24m,
      |	(case when p2.invoice_e_cancel_cnt_l24m is null then 0 else p2.invoice_e_cancel_cnt_l24m end) as invoice_e_cancel_cnt_l24m,
      |	(case when p2.invoice_e_red_cnt_l24m is null then 0 else p2.invoice_e_red_cnt_l24m end) as invoice_e_red_cnt_l24m,
      |	(case when p2.invoice_e_total_sum_l24m is null then 0 else ROUND(p2.invoice_e_total_sum_l24m,2) end) as invoice_e_total_sum_l24m,
      |	(case when p2.invoice_r_cnt_l24m is null then 0 else p2.invoice_r_cnt_l24m end) as invoice_r_cnt_l24m,
      |	(case when p2.invoice_r_cancel_cnt_l24m is null then 0 else p2.invoice_r_cancel_cnt_l24m end) as invoice_r_cancel_cnt_l24m,
      |	(case when p2.invoice_r_red_cnt_l24m is null then 0 else p2.invoice_r_red_cnt_l24m end) as invoice_r_red_cnt_l24m,
      |	(case when p2.invoice_r_total_sum_l24m is null then 0 else ROUND(p2.invoice_r_total_sum_l24m,2) end) as invoice_r_total_sum_l24m,
      |
      |	(case when p2.invoice_lt100_cnt_l24m is null then 0 else p2.invoice_lt100_cnt_l24m end) as invoice_lt100_cnt_l24m,
      |	(case when p2.invoice_lt100_cr_cnt_l24m is null then 0 else p2.invoice_lt100_cr_cnt_l24m end) as invoice_lt100_cr_cnt_l24m,
      |	(case when p2.invoice_lt100_total_sum_l24m is null then 0 else ROUND(p2.invoice_lt100_total_sum_l24m,2) end) as invoice_lt100_total_sum_l24m,
      |	(case when p2.invoice_lt1000_cnt_l24m is null then 0 else p2.invoice_lt1000_cnt_l24m end) as invoice_lt1000_cnt_l24m,
      |	(case when p2.invoice_lt1000_cr_cnt_l24m is null then 0 else p2.invoice_lt1000_cr_cnt_l24m end) as invoice_lt1000_cr_cnt_l24m,
      |	(case when p2.invoice_lt1000_total_sum_l24m is null then 0 else ROUND(p2.invoice_lt1000_total_sum_l24m,2) end) as invoice_lt1000_total_sum_l24m,
      |	(case when p2.invoice_lt2500_cnt_l24m is null then 0 else p2.invoice_lt2500_cnt_l24m end) as invoice_lt2500_cnt_l24m,
      |	(case when p2.invoice_lt2500_cr_cnt_l24m is null then 0 else p2.invoice_lt2500_cr_cnt_l24m end) as invoice_lt2500_cr_cnt_l24m,
      |	(case when p2.invoice_lt2500_total_sum_l24m is null then 0 else ROUND(p2.invoice_lt2500_total_sum_l24m,2) end) as invoice_lt2500_total_sum_l24m,
      |	(case when p2.invoice_lt5000_cnt_l24m is null then 0 else p2.invoice_lt5000_cnt_l24m end) as invoice_lt5000_cnt_l24m,
      |	(case when p2.invoice_lt5000_cr_cnt_l24m is null then 0 else p2.invoice_lt5000_cr_cnt_l24m end) as invoice_lt5000_cr_cnt_l24m,
      |	(case when p2.invoice_lt5000_total_sum_l24m is null then 0 else ROUND(p2.invoice_lt5000_total_sum_l24m,2) end) as invoice_lt5000_total_sum_l24m,
      |	(case when p2.invoice_lt10000_cnt_l24m is null then 0 else p2.invoice_lt10000_cnt_l24m end) as invoice_lt10000_cnt_l24m,
      |	(case when p2.invoice_lt10000_cr_cnt_l24m is null then 0 else p2.invoice_lt10000_cr_cnt_l24m end) as invoice_lt10000_cr_cnt_l24m,
      |	(case when p2.invoice_lt10000_total_sum_l24m is null then 0 else ROUND(p2.invoice_lt10000_total_sum_l24m,2) end) as invoice_lt10000_total_sum_l24m,
      |	(case when p2.invoice_gt10000_cnt_l24m is null then 0 else p2.invoice_gt10000_cnt_l24m end) as invoice_gt10000_cnt_l24m,
      |	(case when p2.invoice_gt10000_cr_cnt_l24m is null then 0 else p2.invoice_gt10000_cr_cnt_l24m end) as invoice_gt10000_cr_cnt_l24m,
      |	(case when p2.invoice_gt10000_total_sum_l24m is null then 0 else ROUND(p2.invoice_gt10000_total_sum_l24m,2) end) as invoice_gt10000_total_sum_l24m,
      |	(case when p2.invoice_detail_cnt_l24m is null then 0 else p2.invoice_detail_cnt_l24m end) as invoice_detail_cnt_l24m,
      |	(case when p2.invoice_detail_cr_cnt_l24m is null then 0 else p2.invoice_detail_cr_cnt_l24m end) as invoice_detail_cr_cnt_l24m,
      |	(case when p2.invoice_detail_total_sum_l24m is null then 0 else ROUND(p2.invoice_detail_total_sum_l24m,2) end) as invoice_detail_total_sum_l24m,
      |	(case when p2.buyer_cnt_l24m is null then 0 else p2.buyer_cnt_l24m end) as buyer_cnt_l24m,
      |	(case when p2.buyer_a_invoice_cnt_l24m is null then 0 else p2.buyer_a_invoice_cnt_l24m end) as buyer_a_invoice_cnt_l24m,
      |	(case when p2.buyer_b_invoice_cnt_l24m is null then 0 else p2.buyer_b_invoice_cnt_l24m end) as buyer_b_invoice_cnt_l24m,
      |	(case when p2.buyer_c_invoice_cnt_l24m is null then 0 else p2.buyer_c_invoice_cnt_l24m end) as buyer_c_invoice_cnt_l24m,
      |	(case when p2.buyer_f_invoice_cnt_l24m is null then 0 else p2.buyer_f_invoice_cnt_l24m end) as buyer_f_invoice_cnt_l24m,
      |	(case when p2.buyer_a_amt_sum_l24m is null then 0 else ROUND(p2.buyer_a_amt_sum_l24m,2) end) as buyer_a_amt_sum_l24m,
      |	(case when p2.buyer_b_amt_sum_l24m is null then 0 else ROUND(p2.buyer_b_amt_sum_l24m,2) end) as buyer_b_amt_sum_l24m,
      |	(case when p2.buyer_c_amt_sum_l24m is null then 0 else ROUND(p2.buyer_c_amt_sum_l24m,2) end) as buyer_c_amt_sum_l24m,
      |	(case when p2.buyer_f_amt_sum_l24m is null then 0 else ROUND(p2.buyer_f_amt_sum_l24m,2) end) as buyer_f_amt_sum_l24m,
      |
      |	(case when p2.buyer_a_cnt_chg_rate_6m is null then 0 else ROUND(p2.buyer_a_cnt_chg_rate_6m,3) end) as buyer_a_cnt_chg_rate_6m,
      |	(case when p2.buyer_b_cnt_chg_rate_6m is null then 0 else ROUND(p2.buyer_b_cnt_chg_rate_6m,3) end) as buyer_b_cnt_chg_rate_6m,
      |	(case when p2.buyer_c_cnt_chg_rate_6m is null then 0 else ROUND(p2.buyer_c_cnt_chg_rate_6m,3) end) as buyer_c_cnt_chg_rate_6m,
      |	(case when p2.buyer_f_cnt_chg_rate_6m is null then 0 else ROUND(p2.buyer_f_cnt_chg_rate_6m,3) end) as buyer_f_cnt_chg_rate_6m,
      |
      |	(case when p2.buyer_a_invoice_cnt_l12m_chg_rate_6m is null then 0 else ROUND(p2.buyer_a_invoice_cnt_l12m_chg_rate_6m,3) end) as buyer_a_invoice_cnt_l12m_chg_rate_6m,
      |	(case when p2.buyer_b_invoice_cnt_l12m_chg_rate_6m is null then 0 else ROUND(p2.buyer_b_invoice_cnt_l12m_chg_rate_6m,3) end) as buyer_b_invoice_cnt_l12m_chg_rate_6m,
      |	(case when p2.buyer_c_invoice_cnt_l12m_chg_rate_6m is null then 0 else ROUND(p2.buyer_c_invoice_cnt_l12m_chg_rate_6m,3) end) as buyer_c_invoice_cnt_l12m_chg_rate_6m,
      |	(case when p2.buyer_f_invoice_cnt_l12m_chg_rate_6m is null then 0 else ROUND(p2.buyer_f_invoice_cnt_l12m_chg_rate_6m,3) end) as buyer_f_invoice_cnt_l12m_chg_rate_6m,
      |	(case when p2.buyer_a_amt_sum_l12m_chg_rate_6m is null then 0 else ROUND(p2.buyer_a_amt_sum_l12m_chg_rate_6m,3) end) as buyer_a_amt_sum_l12m_chg_rate_6m,
      |	(case when p2.buyer_b_amt_sum_l12m_chg_rate_6m is null then 0 else ROUND(p2.buyer_b_amt_sum_l12m_chg_rate_6m,3) end) as buyer_b_amt_sum_l12m_chg_rate_6m,
      |	(case when p2.buyer_c_amt_sum_l12m_chg_rate_6m is null then 0 else ROUND(p2.buyer_c_amt_sum_l12m_chg_rate_6m,3) end) as buyer_c_amt_sum_l12m_chg_rate_6m,
      |	(case when p2.buyer_f_amt_sum_l12m_chg_rate_6m is null then 0 else ROUND(p2.buyer_f_amt_sum_l12m_chg_rate_6m,3) end) as buyer_f_amt_sum_l12m_chg_rate_6m,
      |
      |	(case when p2.buyer_a_cnt_chg_rate_12m is null then 0 else ROUND(p2.buyer_a_cnt_chg_rate_12m,3) end) as buyer_a_cnt_chg_rate_12m,
      |	(case when p2.buyer_b_cnt_chg_rate_12m is null then 0 else ROUND(p2.buyer_b_cnt_chg_rate_12m,3) end) as buyer_b_cnt_chg_rate_12m,
      |	(case when p2.buyer_c_cnt_chg_rate_12m is null then 0 else ROUND(p2.buyer_c_cnt_chg_rate_12m,3) end) as buyer_c_cnt_chg_rate_12m,
      |	(case when p2.buyer_f_cnt_chg_rate_12m is null then 0 else ROUND(p2.buyer_f_cnt_chg_rate_12m,3) end) as buyer_f_cnt_chg_rate_12m,
      |	(case when p2.buyer_a_invoice_cnt_l12m_chg_rate_12m is null then 0 else ROUND(p2.buyer_a_invoice_cnt_l12m_chg_rate_12m,3) end) as buyer_a_invoice_cnt_l12m_chg_rate_12m,
      |	(case when p2.buyer_b_invoice_cnt_l12m_chg_rate_12m is null then 0 else ROUND(p2.buyer_b_invoice_cnt_l12m_chg_rate_12m,3) end) as buyer_b_invoice_cnt_l12m_chg_rate_12m,
      |	(case when p2.buyer_c_invoice_cnt_l12m_chg_rate_12m is null then 0 else ROUND(p2.buyer_c_invoice_cnt_l12m_chg_rate_12m,3) end) as buyer_c_invoice_cnt_l12m_chg_rate_12m,
      |	(case when p2.buyer_f_invoice_cnt_l12m_chg_rate_12m is null then 0 else ROUND(p2.buyer_f_invoice_cnt_l12m_chg_rate_12m,3) end) as buyer_f_invoice_cnt_l12m_chg_rate_12m,
      |	(case when p2.buyer_a_amt_sum_l12m_chg_rate_12m is null then 0 else ROUND(p2.buyer_a_amt_sum_l12m_chg_rate_12m,3) end) as buyer_a_amt_sum_l12m_chg_rate_12m,
      |	(case when p2.buyer_b_amt_sum_l12m_chg_rate_12m is null then 0 else ROUND(p2.buyer_b_amt_sum_l12m_chg_rate_12m,3) end) as buyer_b_amt_sum_l12m_chg_rate_12m,
      |	(case when p2.buyer_c_amt_sum_l12m_chg_rate_12m is null then 0 else ROUND(p2.buyer_c_amt_sum_l12m_chg_rate_12m,3) end) as buyer_c_amt_sum_l12m_chg_rate_12m,
      |	(case when p2.buyer_f_amt_sum_l12m_chg_rate_12m is null then 0 else ROUND(p2.buyer_f_amt_sum_l12m_chg_rate_12m,3) end) as buyer_f_amt_sum_l12m_chg_rate_12m
      |
      |from
      |statistics_crossmonth_tmp p1
      |left join ${Const_Common.DATABASE}.c_p2 p2  --从hive中读出p2表使用
      |on p1.mcht_cd=p2.mcht_cd
    """.stripMargin

  val cross_2 =
    s"""
      |select
      |	p1.mcht_cd,
      |	p1.month_pre1,
      |
      |	(CASE WHEN p2.invoice_r_all_cnt_l3m is null or p2.invoice_cnt_l3m is null THEN -9998
      |		  WHEN  p2.invoice_r_all_cnt_l3m=0 AND p2.invoice_cnt_l3m=0 THEN -9997
      |		  WHEN p2.invoice_r_all_cnt_l3m!=0 AND p2.invoice_cnt_l3m=0 THEN -9996
      |		  ELSE p2.invoice_r_all_cnt_l3m / p2.invoice_cnt_l3m END) AS invoice_red_cnt_rate_l3m, --近3月红冲发票张数占比
      |	(CASE WHEN p2.invoice_c_all_cnt_l3m is null or p2.invoice_cnt_l3m is null THEN -9998
      |		  WHEN  p2.invoice_c_all_cnt_l3m=0 AND p2.invoice_cnt_l3m=0 THEN -9997
      |		  WHEN p2.invoice_c_all_cnt_l3m!=0 AND p2.invoice_cnt_l3m=0 THEN -9996
      |		  ELSE p2.invoice_c_all_cnt_l3m / p2.invoice_cnt_l3m END) AS invoice_cancel_cnt_rate_l3m, --近3月作废发票张数占比
      |	(CASE WHEN p2.invoice_total_r_sum_l3m is null or p2.invoice_total_sum_l3m is null THEN -9998
      |		  WHEN  p2.invoice_total_r_sum_l3m=0 AND p2.invoice_total_sum_l3m=0 THEN -9997
      |		  WHEN p2.invoice_total_r_sum_l3m!=0 AND p2.invoice_total_sum_l3m=0 THEN -9996
      |		  ELSE p2.invoice_total_r_sum_l3m / p2.invoice_total_sum_l3m END) AS invoice_red_total_sum_rate_l3m,	 --近3月红冲发票合计金额税额总和比率
      |	(CASE WHEN p2.invoice_total_c_sum_l3m is null or p2.invoice_total_sum_l3m is null THEN -9998
      |		  WHEN  p2.invoice_total_c_sum_l3m=0 AND p2.invoice_total_sum_l3m=0 THEN -9997
      |		  WHEN p2.invoice_total_c_sum_l3m!=0 AND p2.invoice_total_sum_l3m=0 THEN -9996
      |		  ELSE p2.invoice_total_c_sum_l3m / p2.invoice_total_sum_l3m END) AS invoice_cancel_total_sum_rate_l3m, --近3月作废发票合计金额税金总和比率
      |
      |	(CASE WHEN p2.invoice_r_all_cnt_l6m is null or p2.invoice_cnt_l6m is null THEN -9998
      |		  WHEN  p2.invoice_r_all_cnt_l6m=0 AND p2.invoice_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_r_all_cnt_l6m!=0 AND p2.invoice_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_r_all_cnt_l6m / p2.invoice_cnt_l6m END) AS invoice_red_cnt_rate_l6m, --近6月红冲发票张数占比
      |	(CASE WHEN p2.invoice_c_all_cnt_l6m is null or p2.invoice_cnt_l6m is null THEN -9998
      |		  WHEN  p2.invoice_c_all_cnt_l6m=0 AND p2.invoice_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_c_all_cnt_l6m!=0 AND p2.invoice_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_c_all_cnt_l6m / p2.invoice_cnt_l6m END) AS invoice_cancel_cnt_rate_l6m, --近6月作废发票张数占比
      |	(CASE WHEN p2.invoice_total_r_sum_l6m is null or p2.invoice_total_sum_l6m is null THEN -9998
      |		  WHEN  p2.invoice_total_r_sum_l6m=0 AND p2.invoice_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_total_r_sum_l6m!=0 AND p2.invoice_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m END) AS invoice_red_total_sum_rate_l6m,	 --近6月红冲发票合计金额税额总和比率
      |	(CASE WHEN p2.invoice_total_c_sum_l6m is null or p2.invoice_total_sum_l6m is null THEN -9998
      |		  WHEN  p2.invoice_total_c_sum_l6m=0 AND p2.invoice_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_total_c_sum_l6m!=0 AND p2.invoice_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m END) AS invoice_cancel_total_sum_rate_l6m, --近6月作废发票合计金额税金总和比率
      |
      |	(CASE WHEN p2.invoice_r_all_cnt_l12m is null or p2.invoice_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_r_all_cnt_l12m=0 AND p2.invoice_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_r_all_cnt_l12m!=0 AND p2.invoice_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_r_all_cnt_l12m / p2.invoice_cnt_l12m END) AS invoice_red_cnt_rate_l12m, --近12月红冲发票张数占比
      |	(CASE WHEN p2.invoice_c_all_cnt_l12m is null or p2.invoice_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_c_all_cnt_l12m=0 AND p2.invoice_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_c_all_cnt_l12m!=0 AND p2.invoice_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_c_all_cnt_l12m / p2.invoice_cnt_l12m END) AS invoice_cancel_cnt_rate_l12m, --近12月作废发票张数占比
      |	(CASE WHEN p2.invoice_total_r_sum_l12m is null or p2.invoice_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_total_r_sum_l12m=0 AND p2.invoice_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_total_r_sum_l12m!=0 AND p2.invoice_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m END) AS invoice_red_total_sum_rate_l12m,	 --近12月红冲发票合计金额税额总和比率
      |	(CASE WHEN p2.invoice_total_c_sum_l12m is null or p2.invoice_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_total_c_sum_l12m=0 AND p2.invoice_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_total_c_sum_l12m!=0 AND p2.invoice_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m END) AS invoice_cancel_total_sum_rate_l12m, --近12月作废发票合计金额税金总和比率
      |
      |	(CASE WHEN p2.invoice_r_all_cnt_l24m is null or p2.invoice_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_r_all_cnt_l24m=0 AND p2.invoice_cnt_l24m=0 THEN -9997
      |		  WHEN p2.invoice_r_all_cnt_l24m!=0 AND p2.invoice_cnt_l24m=0 THEN -9996
      |		  ELSE p2.invoice_r_all_cnt_l24m / p2.invoice_cnt_l24m END) AS invoice_red_cnt_rate_l24m, --近24月红冲发票张数占比
      |	(CASE WHEN p2.invoice_c_all_cnt_l24m is null or p2.invoice_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_c_all_cnt_l24m=0 AND p2.invoice_cnt_l24m=0 THEN -9997
      |		  WHEN p2.invoice_c_all_cnt_l24m!=0 AND p2.invoice_cnt_l24m=0 THEN -9996
      |		  ELSE p2.invoice_c_all_cnt_l24m / p2.invoice_cnt_l24m END) AS invoice_cancel_cnt_rate_l24m, --近24月作废发票张数占比
      |	(CASE WHEN p2.invoice_total_r_sum_l24m is null or p2.invoice_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_total_r_sum_l24m=0 AND p2.invoice_total_sum_l24m=0 THEN -9997
      |		  WHEN p2.invoice_total_r_sum_l24m!=0 AND p2.invoice_total_sum_l24m=0 THEN -9996
      |		  ELSE p2.invoice_total_r_sum_l24m / p2.invoice_total_sum_l24m END) AS invoice_red_total_sum_rate_l24m,	 --近24月红冲发票合计金额税额总和比率
      |	(CASE WHEN p2.invoice_total_c_sum_l24m is null or p2.invoice_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_total_c_sum_l24m=0 AND p2.invoice_total_sum_l24m=0 THEN -9997
      |		  WHEN p2.invoice_total_c_sum_l24m!=0 AND p2.invoice_total_sum_l24m=0 THEN -9996
      |		  ELSE p2.invoice_total_c_sum_l24m / p2.invoice_total_sum_l24m END) AS invoice_cancel_total_sum_rate_l24m, --近24月作废发票合计金额税金总和比率
      |
      |	(CASE WHEN p2.invoice_sc_cnt_l6m is null or p2.invoice_sc_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_sc_cnt_l6m=0 AND p2.invoice_sc_cnt_l12m-p2.invoice_sc_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_sc_cnt_l6m!=0 AND p2.invoice_sc_cnt_l12m-p2.invoice_sc_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_sc_cnt_l6m / (p2.invoice_sc_cnt_l12m-p2.invoice_sc_cnt_l6m) END) AS invoice_sc_cnt_ratio_6m, --增值税开票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_sc_cancel_cnt_l6m is null or p2.invoice_sc_cancel_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_sc_cancel_cnt_l6m=0 AND p2.invoice_sc_cancel_cnt_l12m-p2.invoice_sc_cancel_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_sc_cancel_cnt_l6m!=0 AND p2.invoice_sc_cnt_l12m-p2.invoice_sc_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_sc_cancel_cnt_l6m / (p2.invoice_sc_cancel_cnt_l12m-p2.invoice_sc_cancel_cnt_l6m) END) AS invoice_sc_cancel_cnt_ratio_6m, --增值税发票作废张数6个月环比
      |
      |	(CASE WHEN p2.invoice_sc_red_cnt_l6m is null or p2.invoice_sc_red_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_sc_red_cnt_l6m=0 AND p2.invoice_sc_red_cnt_l12m-p2.invoice_sc_red_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_sc_red_cnt_l6m!=0 AND p2.invoice_sc_red_cnt_l12m-p2.invoice_sc_red_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_sc_red_cnt_l6m / (p2.invoice_sc_red_cnt_l12m-p2.invoice_sc_red_cnt_l6m) END) AS invoice_sc_red_cnt_ratio_6m, --增值税发票红冲张数6个月环比
      |
      |	(CASE WHEN p2.invoice_sc_total_sum_l6m is null or p2.invoice_sc_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_sc_total_sum_l6m=0 AND p2.invoice_sc_total_sum_l12m-p2.invoice_sc_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_sc_total_sum_l6m!=0 AND p2.invoice_sc_total_sum_l12m-p2.invoice_sc_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_sc_total_sum_l6m / (p2.invoice_sc_total_sum_l12m-p2.invoice_sc_total_sum_l6m) END) AS invoice_sc_total_sum_ratio_6m, --增值税发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_cnt_l6m is null or p2.invoice_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_cnt_l6m=0 AND p2.invoice_cnt_l12m - p2.invoice_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_cnt_l6m!=0 AND p2.invoice_cnt_l12m - p2.invoice_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_cnt_l6m / (p2.invoice_cnt_l12m - p2.invoice_cnt_l6m) END) AS invoice_cnt_ratio_6m, --所有发票开票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_total_sum_l6m is null or p2.invoice_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_total_sum_l6m=0 AND p2.invoice_total_sum_l12m - p2.invoice_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_total_sum_l6m!=0 AND p2.invoice_total_sum_l12m - p2.invoice_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_total_sum_l6m / (p2.invoice_total_sum_l12m - p2.invoice_total_sum_l6m) END) AS invoice_total_sum_ratio_6m, --所有发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_s_cnt_l6m is null or p2.invoice_s_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_s_cnt_l6m=0 AND p2.invoice_s_cnt_l12m - p2.invoice_s_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_s_cnt_l6m!=0 AND p2.invoice_s_cnt_l12m - p2.invoice_s_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_s_cnt_l6m / (p2.invoice_s_cnt_l12m - p2.invoice_s_cnt_l6m) END) AS invoice_s_cnt_ratio_6m, --增值税专用发票开票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_s_total_sum_l6m is null or p2.invoice_s_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_s_total_sum_l6m=0 AND p2.invoice_s_total_sum_l12m - p2.invoice_s_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_s_total_sum_l6m!=0 AND p2.invoice_s_total_sum_l12m - p2.invoice_s_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_s_total_sum_l6m / (p2.invoice_s_total_sum_l12m - p2.invoice_s_total_sum_l6m) END) AS invoice_s_total_sum_ratio_6m, --增值税专用发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_c_cnt_l6m is null or p2.invoice_c_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_c_cnt_l6m=0 AND p2.invoice_c_cnt_l12m - p2.invoice_c_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_c_cnt_l6m!=0 AND p2.invoice_c_cnt_l12m - p2.invoice_c_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_c_cnt_l6m / (p2.invoice_c_cnt_l12m - p2.invoice_c_cnt_l6m) END) AS invoice_c_cnt_ratio_6m, --增值税普通发票开票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_c_total_sum_l6m is null or p2.invoice_c_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_c_total_sum_l6m=0 AND p2.invoice_c_total_sum_l12m - p2.invoice_c_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_c_total_sum_l6m!=0 AND p2.invoice_c_total_sum_l12m - p2.invoice_c_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_c_total_sum_l6m / (p2.invoice_c_total_sum_l12m - p2.invoice_c_total_sum_l6m) END) AS invoice_c_total_sum_ratio_6m, --增值税普通发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_t_cnt_l6m is null or p2.invoice_t_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_t_cnt_l6m=0 AND p2.invoice_t_cnt_l12m - p2.invoice_t_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_t_cnt_l6m!=0 AND p2.invoice_t_cnt_l12m - p2.invoice_t_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_t_cnt_l6m / (p2.invoice_t_cnt_l12m - p2.invoice_t_cnt_l6m) END) AS invoice_t_cnt_ratio_6m, --运输发票开票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_t_total_sum_l6m is null or p2.invoice_t_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_t_total_sum_l6m=0 AND p2.invoice_t_total_sum_l12m - p2.invoice_t_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_t_total_sum_l6m!=0 AND p2.invoice_t_total_sum_l12m - p2.invoice_t_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_t_total_sum_l6m / (p2.invoice_t_total_sum_l12m - p2.invoice_t_total_sum_l6m) END) AS invoice_t_total_sum_ratio_6m, --运输发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_p_cnt_l6m is null or p2.invoice_p_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_p_cnt_l6m=0 AND p2.invoice_p_cnt_l12m - p2.invoice_p_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_p_cnt_l6m!=0 AND p2.invoice_p_cnt_l12m - p2.invoice_p_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_p_cnt_l6m / (p2.invoice_p_cnt_l12m - p2.invoice_p_cnt_l6m) END) AS invoice_p_cnt_ratio_6m, --纸质发票开票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_p_total_sum_l6m is null or p2.invoice_p_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_p_total_sum_l6m=0 AND p2.invoice_p_total_sum_l12m - p2.invoice_p_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_p_total_sum_l6m!=0 AND p2.invoice_p_total_sum_l12m - p2.invoice_p_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_p_total_sum_l6m / (p2.invoice_p_total_sum_l12m - p2.invoice_p_total_sum_l6m) END) AS invoice_p_total_sum_ratio_6m, --纸质发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_e_cnt_l6m is null or p2.invoice_e_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_e_cnt_l6m=0 AND p2.invoice_e_cnt_l12m - p2.invoice_e_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_e_cnt_l6m!=0 AND p2.invoice_e_cnt_l12m - p2.invoice_e_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_e_cnt_l6m / (p2.invoice_e_cnt_l12m - p2.invoice_e_cnt_l6m) END) AS invoice_e_cnt_ratio_6m, --电子发票开票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_e_total_sum_l6m is null or p2.invoice_e_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_e_total_sum_l6m=0 AND p2.invoice_e_total_sum_l12m - p2.invoice_e_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_e_total_sum_l6m!=0 AND p2.invoice_e_total_sum_l12m - p2.invoice_e_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_e_total_sum_l6m / (p2.invoice_e_total_sum_l12m - p2.invoice_e_total_sum_l6m) END) AS invoice_e_total_sum_ratio_6m, --电子发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_r_cnt_l6m is null or p2.invoice_r_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_r_cnt_l6m=0 AND p2.invoice_r_cnt_l12m - p2.invoice_r_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_r_cnt_l6m!=0 AND p2.invoice_r_cnt_l12m - p2.invoice_r_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_r_cnt_l6m / (p2.invoice_r_cnt_l12m - p2.invoice_r_cnt_l6m) END) AS invoice_r_cnt_ratio_6m, --卷式发票开票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_r_total_sum_l6m is null or p2.invoice_r_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_r_total_sum_l6m=0 AND p2.invoice_r_total_sum_l12m - p2.invoice_r_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_r_total_sum_l6m!=0 AND p2.invoice_r_total_sum_l12m - p2.invoice_r_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_r_total_sum_l6m / (p2.invoice_r_total_sum_l12m - p2.invoice_r_total_sum_l6m) END) AS invoice_r_total_sum_ratio_6m, --卷式发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_r_all_cnt_l6m is null or p2.invoice_cnt_l6m is null or p2.invoice_r_all_cnt_l712m is null or p2.invoice_cnt_l712m is null THEN -9998
      |		  WHEN  p2.invoice_r_all_cnt_l6m=0 AND p2.invoice_r_all_cnt_l712m =0 THEN -9997
      |		  WHEN p2.invoice_r_all_cnt_l6m!=0 AND p2.invoice_r_all_cnt_l712m=0 THEN -9996
      |		  ELSE (p2.invoice_r_all_cnt_l6m / p2.invoice_cnt_l6m ) / (p2.invoice_r_all_cnt_l712m / p2.invoice_cnt_l712m) END) AS invoice_red_cnt_rate_ratio_6m, --红冲发票张数占比6个月环比
      |
      |	(CASE WHEN p2.invoice_c_all_cnt_l6m is null or p2.invoice_cnt_l6m is null or p2.invoice_c_all_cnt_l712m is null or p2.invoice_cnt_l712m is null THEN -9998
      |		  WHEN  p2.invoice_c_all_cnt_l6m=0 AND p2.invoice_c_all_cnt_l712m =0 THEN -9997
      |		  WHEN p2.invoice_c_all_cnt_l6m!=0 AND p2.invoice_c_all_cnt_l712m=0 THEN -9996
      |		  ELSE (p2.invoice_c_all_cnt_l6m / p2.invoice_cnt_l6m ) / (p2.invoice_c_all_cnt_l712m / p2.invoice_cnt_l712m) END) AS invoice_cancel_cnt_rate_ratio_6m, --作废发票张数占比6个月环比
      |	(CASE WHEN p2.invoice_total_r_sum_l6m is null or p2.invoice_total_sum_l6m is null or p2.invoice_total_r_sum_l12m is null or p2.invoice_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_total_r_sum_l6m=0 AND (p2.invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m=0) THEN -9997
      |		  WHEN p2.invoice_total_r_sum_l6m!=0 AND (p2.invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m=0) THEN -9996
      |		  ELSE (invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m ) / (invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m ) END) AS invoice_red_total_sum_rate_ratio_6m, --红冲发票合计金额税额总和比率6个月环比
      |
      |	(CASE WHEN p2.invoice_total_c_sum_l6m is null or p2.invoice_total_sum_l6m is null or p2.invoice_total_c_sum_l12m is null or p2.invoice_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_total_c_sum_l6m=0 AND (p2.invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m=0) THEN -9997
      |		  WHEN p2.invoice_total_c_sum_l6m!=0 AND (p2.invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m=0) THEN -9996
      |		  ELSE (invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m ) / (invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m ) END) AS invoice_cancel_total_sum_rate_ratio_6m, --作废发票合计金额税额总和比率6个月环比
      |
      |	(CASE WHEN p2.invoice_lt100_cnt_l6m is null or p2.invoice_lt100_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_lt100_cnt_l6m=0 AND p2.invoice_lt100_cnt_l12m - p2.invoice_lt100_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_lt100_cnt_l6m!=0 AND p2.invoice_lt100_cnt_l12m - p2.invoice_lt100_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_lt100_cnt_l6m / (p2.invoice_lt100_cnt_l12m - p2.invoice_lt100_cnt_l6m) END) AS invoice_lt100_cnt_ratio_6m, --金额小于100发票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_lt100_total_sum_l6m is null or p2.invoice_lt100_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_lt100_total_sum_l6m=0 AND p2.invoice_lt100_total_sum_l12m - p2.invoice_lt100_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_lt100_total_sum_l6m!=0 AND p2.invoice_lt100_total_sum_l12m - p2.invoice_lt100_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_lt100_total_sum_l6m / (p2.invoice_lt100_total_sum_l12m - p2.invoice_lt100_total_sum_l6m) END) AS invoice_lt100_total_sum_ratio_6m, --金额小于100发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_lt1000_cnt_l6m is null or p2.invoice_lt1000_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_lt1000_cnt_l6m=0 AND p2.invoice_lt1000_cnt_l12m - p2.invoice_lt1000_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_lt1000_cnt_l6m!=0 AND p2.invoice_lt1000_cnt_l12m - p2.invoice_lt1000_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_lt1000_cnt_l6m / (p2.invoice_lt1000_cnt_l12m - p2.invoice_lt1000_cnt_l6m) END) AS invoice_lt1000_cnt_ratio_6m, --金额小于1000（且大于等于100）发票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_lt1000_total_sum_l6m is null or p2.invoice_lt1000_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_lt1000_total_sum_l6m=0 AND p2.invoice_lt1000_total_sum_l12m - p2.invoice_lt1000_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_lt1000_total_sum_l6m!=0 AND p2.invoice_lt1000_total_sum_l12m - p2.invoice_lt1000_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_lt1000_total_sum_l6m / (p2.invoice_lt1000_total_sum_l12m - p2.invoice_lt1000_total_sum_l6m) END) AS invoice_lt1000_total_sum_ratio_6m, --金额小于1000（且大于等100）发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_lt2500_cnt_l6m is null or p2.invoice_lt2500_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_lt2500_cnt_l6m=0 AND p2.invoice_lt2500_cnt_l12m - p2.invoice_lt2500_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_lt2500_cnt_l6m!=0 AND p2.invoice_lt2500_cnt_l12m - p2.invoice_lt2500_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_lt2500_cnt_l6m / (p2.invoice_lt2500_cnt_l12m - p2.invoice_lt2500_cnt_l6m) END) AS invoice_lt2500_cnt_ratio_6m, --金额小于2500（且大于等于1000）发票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_lt2500_total_sum_l6m is null or p2.invoice_lt2500_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_lt2500_total_sum_l6m=0 AND p2.invoice_lt2500_total_sum_l12m - p2.invoice_lt2500_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_lt2500_total_sum_l6m!=0 AND p2.invoice_lt2500_total_sum_l12m - p2.invoice_lt2500_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_lt2500_total_sum_l6m / (p2.invoice_lt2500_total_sum_l12m - p2.invoice_lt2500_total_sum_l6m) END) AS invoice_lt2500_total_sum_ratio_6m, --金额小于2500（且大于等于1000）发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_lt5000_cnt_l6m is null or p2.invoice_lt5000_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_lt5000_cnt_l6m=0 AND p2.invoice_lt5000_cnt_l12m - p2.invoice_lt5000_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_lt5000_cnt_l6m!=0 AND p2.invoice_lt5000_cnt_l12m - p2.invoice_lt5000_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_lt5000_cnt_l6m / (p2.invoice_lt5000_cnt_l12m - p2.invoice_lt5000_cnt_l6m) END) AS invoice_lt5000_cnt_ratio_6m, --金额小于5000（且大于等于2500）发票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_lt5000_total_sum_l6m is null or p2.invoice_lt5000_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_lt5000_total_sum_l6m=0 AND p2.invoice_lt5000_total_sum_l12m - p2.invoice_lt5000_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_lt5000_total_sum_l6m!=0 AND p2.invoice_lt5000_total_sum_l12m - p2.invoice_lt5000_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_lt5000_total_sum_l6m / (p2.invoice_lt5000_total_sum_l12m - p2.invoice_lt5000_total_sum_l6m) END) AS invoice_lt5000_total_sum_ratio_6m, --金额小于5000（且大于等于2500）发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_gt10000_cnt_l6m is null or p2.invoice_gt10000_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_gt10000_cnt_l6m=0 AND p2.invoice_gt10000_cnt_l12m - p2.invoice_gt10000_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_gt10000_cnt_l6m!=0 AND p2.invoice_gt10000_cnt_l12m - p2.invoice_gt10000_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_gt10000_cnt_l6m / (p2.invoice_gt10000_cnt_l12m - p2.invoice_gt10000_cnt_l6m) END) AS invoice_gt10000_cnt_ratio_6m, --金额大于10000发票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_gt10000_total_sum_l6m is null or p2.invoice_gt10000_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_gt10000_total_sum_l6m=0 AND p2.invoice_gt10000_total_sum_l12m - p2.invoice_gt10000_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_gt10000_total_sum_l6m!=0 AND p2.invoice_gt10000_total_sum_l12m - p2.invoice_gt10000_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_gt10000_total_sum_l6m / (p2.invoice_gt10000_total_sum_l12m - p2.invoice_gt10000_total_sum_l6m) END) AS invoice_gt10000_total_sum_ratio_6m, --金额大于10000发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.invoice_detail_cnt_l6m is null or p2.invoice_detail_cnt_l12m is null THEN -9998
      |		  WHEN  p2.invoice_detail_cnt_l6m=0 AND p2.invoice_detail_cnt_l12m - p2.invoice_detail_cnt_l6m=0 THEN -9997
      |		  WHEN p2.invoice_detail_cnt_l6m!=0 AND p2.invoice_detail_cnt_l12m - p2.invoice_detail_cnt_l6m=0 THEN -9996
      |		  ELSE p2.invoice_detail_cnt_l6m / (p2.invoice_detail_cnt_l12m - p2.invoice_detail_cnt_l6m) END) AS invoice_detail_cnt_ratio_6m, --附有清单的发票张数6个月环比
      |
      |	(CASE WHEN p2.invoice_detail_total_sum_l6m is null or p2.invoice_detail_total_sum_l12m is null THEN -9998
      |		  WHEN  p2.invoice_detail_total_sum_l6m=0 AND p2.invoice_detail_total_sum_l12m - p2.invoice_detail_total_sum_l6m=0 THEN -9997
      |		  WHEN p2.invoice_detail_total_sum_l6m!=0 AND p2.invoice_detail_total_sum_l12m - p2.invoice_detail_total_sum_l6m=0 THEN -9996
      |		  ELSE p2.invoice_detail_total_sum_l6m / (p2.invoice_detail_total_sum_l12m - p2.invoice_detail_total_sum_l6m) END) AS invoice_detail_total_sum_ratio_6m, --附有清单的发票合计金额税额总和6个月环比
      |
      |	(CASE WHEN p2.buyer_cnt_l6m is null or p2.buyer_cnt_l712m is null THEN -9998
      |		  WHEN  p2.buyer_cnt_l6m=0 AND p2.buyer_cnt_l712m=0 THEN -9997
      |		  WHEN p2.buyer_cnt_l6m!=0 AND p2.buyer_cnt_l712m=0 THEN -9996
      |		  ELSE (p2.buyer_cnt_l6m / p2.buyer_cnt_l712m -1) END) AS buyer_cnt_chg_rate_6m, --交易对手数目6个月环比变化率
      |
      |	(CASE WHEN p2.invoice_sc_cnt_l12m is null or p2.invoice_sc_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_sc_cnt_l12m=0 AND p2.invoice_sc_cnt_l24m-p2.invoice_sc_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_sc_cnt_l12m!=0 AND p2.invoice_sc_cnt_l24m-p2.invoice_sc_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_sc_cnt_l12m / (p2.invoice_sc_cnt_l24m-p2.invoice_sc_cnt_l12m) END) AS invoice_sc_cnt_ratio_12m, --增值税开票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_sc_cancel_cnt_l12m is null or p2.invoice_sc_cancel_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_sc_cancel_cnt_l12m=0 AND p2.invoice_sc_cancel_cnt_l24m-p2.invoice_sc_cancel_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_sc_cancel_cnt_l12m!=0 AND p2.invoice_sc_cnt_l24m-p2.invoice_sc_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_sc_cancel_cnt_l12m / (p2.invoice_sc_cancel_cnt_l24m-p2.invoice_sc_cancel_cnt_l12m) END) AS invoice_sc_cancel_cnt_ratio_12m, --增值税发票作废张数12个月环比
      |
      |	(CASE WHEN p2.invoice_sc_red_cnt_l12m is null or p2.invoice_sc_red_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_sc_red_cnt_l12m=0 AND p2.invoice_sc_red_cnt_l24m-p2.invoice_sc_red_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_sc_red_cnt_l12m!=0 AND p2.invoice_sc_red_cnt_l24m-p2.invoice_sc_red_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_sc_red_cnt_l12m / (p2.invoice_sc_red_cnt_l24m-p2.invoice_sc_red_cnt_l12m) END) AS invoice_sc_red_cnt_ratio_12m, --增值税发票红冲张数12个月环比
      |
      |	(CASE WHEN p2.invoice_sc_total_sum_l12m is null or p2.invoice_sc_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_sc_total_sum_l12m=0 AND p2.invoice_sc_total_sum_l24m-p2.invoice_sc_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_sc_total_sum_l12m!=0 AND p2.invoice_sc_total_sum_l24m-p2.invoice_sc_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_sc_total_sum_l12m / (p2.invoice_sc_total_sum_l24m-p2.invoice_sc_total_sum_l12m) END) AS invoice_sc_total_sum_ratio_12m, --增值税发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_cnt_l12m is null or p2.invoice_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_cnt_l12m=0 AND p2.invoice_cnt_l24m - p2.invoice_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_cnt_l12m!=0 AND p2.invoice_cnt_l24m - p2.invoice_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_cnt_l12m / (p2.invoice_cnt_l24m - p2.invoice_cnt_l12m) END) AS invoice_cnt_ratio_12m, --所有发票开票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_total_sum_l12m is null or p2.invoice_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_total_sum_l12m=0 AND p2.invoice_total_sum_l24m - p2.invoice_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_total_sum_l12m!=0 AND p2.invoice_total_sum_l24m - p2.invoice_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_total_sum_l12m / (p2.invoice_total_sum_l24m - p2.invoice_total_sum_l12m) END) AS invoice_total_sum_ratio_12m, --所有发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_s_cnt_l12m is null or p2.invoice_s_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_s_cnt_l12m=0 AND p2.invoice_s_cnt_l24m - p2.invoice_s_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_s_cnt_l12m!=0 AND p2.invoice_s_cnt_l24m - p2.invoice_s_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_s_cnt_l12m / (p2.invoice_s_cnt_l24m - p2.invoice_s_cnt_l12m) END) AS invoice_s_cnt_ratio_12m, --增值税专用发票开票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_s_total_sum_l12m is null or p2.invoice_s_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_s_total_sum_l12m=0 AND p2.invoice_s_total_sum_l24m - p2.invoice_s_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_s_total_sum_l12m!=0 AND p2.invoice_s_total_sum_l24m - p2.invoice_s_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_s_total_sum_l12m / (p2.invoice_s_total_sum_l24m - p2.invoice_s_total_sum_l12m) END) AS invoice_s_total_sum_ratio_12m, --增值税专用发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_c_cnt_l12m is null or p2.invoice_c_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_c_cnt_l12m=0 AND p2.invoice_c_cnt_l24m - p2.invoice_c_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_c_cnt_l12m!=0 AND p2.invoice_c_cnt_l24m - p2.invoice_c_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_c_cnt_l12m / (p2.invoice_c_cnt_l24m - p2.invoice_c_cnt_l12m) END) AS invoice_c_cnt_ratio_12m, --增值税普通发票开票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_c_total_sum_l12m is null or p2.invoice_c_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_c_total_sum_l12m=0 AND p2.invoice_c_total_sum_l24m - p2.invoice_c_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_c_total_sum_l12m!=0 AND p2.invoice_c_total_sum_l24m - p2.invoice_c_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_c_total_sum_l12m / (p2.invoice_c_total_sum_l24m - p2.invoice_c_total_sum_l12m) END) AS invoice_c_total_sum_ratio_12m, --增值税普通发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_t_cnt_l12m is null or p2.invoice_t_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_t_cnt_l12m=0 AND p2.invoice_t_cnt_l24m - p2.invoice_t_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_t_cnt_l12m!=0 AND p2.invoice_t_cnt_l24m - p2.invoice_t_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_t_cnt_l12m / (p2.invoice_t_cnt_l24m - p2.invoice_t_cnt_l12m) END) AS invoice_t_cnt_ratio_12m, --运输发票开票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_t_total_sum_l12m is null or p2.invoice_t_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_t_total_sum_l12m=0 AND p2.invoice_t_total_sum_l24m - p2.invoice_t_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_t_total_sum_l12m!=0 AND p2.invoice_t_total_sum_l24m - p2.invoice_t_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_t_total_sum_l12m / (p2.invoice_t_total_sum_l24m - p2.invoice_t_total_sum_l12m) END) AS invoice_t_total_sum_ratio_12m, --运输发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_p_cnt_l12m is null or p2.invoice_p_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_p_cnt_l12m=0 AND p2.invoice_p_cnt_l24m - p2.invoice_p_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_p_cnt_l12m!=0 AND p2.invoice_p_cnt_l24m - p2.invoice_p_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_p_cnt_l12m / (p2.invoice_p_cnt_l24m - p2.invoice_p_cnt_l12m) END) AS invoice_p_cnt_ratio_12m, --纸质发票开票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_p_total_sum_l12m is null or p2.invoice_p_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_p_total_sum_l12m=0 AND p2.invoice_p_total_sum_l24m - p2.invoice_p_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_p_total_sum_l12m!=0 AND p2.invoice_p_total_sum_l24m - p2.invoice_p_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_p_total_sum_l12m / (p2.invoice_p_total_sum_l24m - p2.invoice_p_total_sum_l12m) END) AS invoice_p_total_sum_ratio_12m, --纸质发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_e_cnt_l12m is null or p2.invoice_e_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_e_cnt_l12m=0 AND p2.invoice_e_cnt_l24m - p2.invoice_e_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_e_cnt_l12m!=0 AND p2.invoice_e_cnt_l24m - p2.invoice_e_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_e_cnt_l12m / (p2.invoice_e_cnt_l24m - p2.invoice_e_cnt_l12m) END) AS invoice_e_cnt_ratio_12m, --电子发票开票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_e_total_sum_l12m is null or p2.invoice_e_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_e_total_sum_l12m=0 AND p2.invoice_e_total_sum_l24m - p2.invoice_e_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_e_total_sum_l12m!=0 AND p2.invoice_e_total_sum_l24m - p2.invoice_e_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_e_total_sum_l12m / (p2.invoice_e_total_sum_l24m - p2.invoice_e_total_sum_l12m) END) AS invoice_e_total_sum_ratio_12m, --电子发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_r_cnt_l12m is null or p2.invoice_r_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_r_cnt_l12m=0 AND p2.invoice_r_cnt_l24m - p2.invoice_r_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_r_cnt_l12m!=0 AND p2.invoice_r_cnt_l24m - p2.invoice_r_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_r_cnt_l12m / (p2.invoice_r_cnt_l24m - p2.invoice_r_cnt_l12m) END) AS invoice_r_cnt_ratio_12m, --卷式发票开票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_r_total_sum_l12m is null or p2.invoice_r_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_r_total_sum_l12m=0 AND p2.invoice_r_total_sum_l24m - p2.invoice_r_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_r_total_sum_l12m!=0 AND p2.invoice_r_total_sum_l24m - p2.invoice_r_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_r_total_sum_l12m / (p2.invoice_r_total_sum_l24m - p2.invoice_r_total_sum_l12m) END) AS invoice_r_total_sum_ratio_12m, --卷式发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_r_all_cnt_l12m is null or p2.invoice_cnt_l12m is null or p2.invoice_r_all_cnt_l1324m is null or p2.invoice_cnt_l1324m is null THEN -9998
      |		  WHEN  p2.invoice_r_all_cnt_l12m=0 AND p2.invoice_r_all_cnt_l1324m =0 THEN -9997
      |		  WHEN p2.invoice_r_all_cnt_l12m!=0 AND p2.invoice_r_all_cnt_l1324m=0 THEN -9996
      |		  ELSE (p2.invoice_r_all_cnt_l12m / p2.invoice_cnt_l12m ) / (p2.invoice_r_all_cnt_l1324m / p2.invoice_cnt_l1324m) END) AS invoice_red_cnt_rate_ratio_12m, --红冲发票张数占比12个月环比
      |
      |	(CASE WHEN p2.invoice_c_all_cnt_l12m is null or p2.invoice_cnt_l12m is null or p2.invoice_c_all_cnt_l1324m is null or p2.invoice_cnt_l1324m is null THEN -9998
      |		  WHEN  p2.invoice_c_all_cnt_l12m=0 AND p2.invoice_c_all_cnt_l1324m =0 THEN -9997
      |		  WHEN p2.invoice_c_all_cnt_l12m!=0 AND p2.invoice_c_all_cnt_l1324m=0 THEN -9996
      |		  ELSE (p2.invoice_c_all_cnt_l12m / p2.invoice_cnt_l12m ) / (p2.invoice_c_all_cnt_l1324m / p2.invoice_cnt_l1324m) END) AS invoice_cancel_cnt_rate_ratio_12m, --作废发票张数占比12个月环比
      |
      |	(CASE WHEN p2.invoice_total_r_sum_l12m is null or p2.invoice_total_sum_l12m is null or p2.invoice_total_r_sum_l24m is null or p2.invoice_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_total_r_sum_l12m=0 AND (p2.invoice_total_r_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m=0) THEN -9997
      |		  WHEN p2.invoice_total_r_sum_l12m!=0 AND (p2.invoice_total_r_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m=0) THEN -9996
      |		  ELSE (invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m ) / (invoice_total_r_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m ) END) AS invoice_red_total_sum_rate_ratio_12m, --红冲发票合计金额税额总和比率12个月环比
      |
      |	(CASE WHEN p2.invoice_total_c_sum_l12m is null or p2.invoice_total_sum_l12m is null or p2.invoice_total_c_sum_l24m is null or p2.invoice_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_total_c_sum_l12m=0 AND (p2.invoice_total_c_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m=0) THEN -9997
      |		  WHEN p2.invoice_total_c_sum_l12m!=0 AND (p2.invoice_total_c_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m=0) THEN -9996
      |		  ELSE (invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m ) / (invoice_total_c_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m ) END) AS invoice_cancel_total_sum_rate_ratio_12m, --作废发票合计金额税额总和比率12个月环比
      |
      |	(CASE WHEN p2.invoice_lt100_cnt_l12m is null or p2.invoice_lt100_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_lt100_cnt_l12m=0 AND p2.invoice_lt100_cnt_l24m - p2.invoice_lt100_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_lt100_cnt_l12m!=0 AND p2.invoice_lt100_cnt_l24m - p2.invoice_lt100_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_lt100_cnt_l12m / (p2.invoice_lt100_cnt_l24m - p2.invoice_lt100_cnt_l12m) END) AS invoice_lt100_cnt_ratio_12m, --金额小于100发票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_lt100_total_sum_l12m is null or p2.invoice_lt100_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_lt100_total_sum_l12m=0 AND p2.invoice_lt100_total_sum_l24m - p2.invoice_lt100_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_lt100_total_sum_l12m!=0 AND p2.invoice_lt100_total_sum_l24m - p2.invoice_lt100_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_lt100_total_sum_l12m / (p2.invoice_lt100_total_sum_l24m - p2.invoice_lt100_total_sum_l12m) END) AS invoice_lt100_total_sum_ratio_12m, --金额小于100发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_lt1000_cnt_l12m is null or p2.invoice_lt1000_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_lt1000_cnt_l12m=0 AND p2.invoice_lt1000_cnt_l24m - p2.invoice_lt1000_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_lt1000_cnt_l12m!=0 AND p2.invoice_lt1000_cnt_l24m - p2.invoice_lt1000_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_lt1000_cnt_l12m / (p2.invoice_lt1000_cnt_l24m - p2.invoice_lt1000_cnt_l12m) END) AS invoice_lt1000_cnt_ratio_12m, --金额小于1000（且大于等于100）发票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_lt1000_total_sum_l12m is null or p2.invoice_lt1000_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_lt1000_total_sum_l12m=0 AND p2.invoice_lt1000_total_sum_l24m - p2.invoice_lt1000_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_lt1000_total_sum_l12m!=0 AND p2.invoice_lt1000_total_sum_l24m - p2.invoice_lt1000_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_lt1000_total_sum_l12m / (p2.invoice_lt1000_total_sum_l24m - p2.invoice_lt1000_total_sum_l12m) END) AS invoice_lt1000_total_sum_ratio_12m, --金额小于1000（且大于等100）发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_lt2500_cnt_l12m is null or p2.invoice_lt2500_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_lt2500_cnt_l12m=0 AND p2.invoice_lt2500_cnt_l24m - p2.invoice_lt2500_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_lt2500_cnt_l12m!=0 AND p2.invoice_lt2500_cnt_l24m - p2.invoice_lt2500_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_lt2500_cnt_l12m / (p2.invoice_lt2500_cnt_l24m - p2.invoice_lt2500_cnt_l12m) END) AS invoice_lt2500_cnt_ratio_12m, --金额小于2500（且大于等于1000）发票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_lt2500_total_sum_l12m is null or p2.invoice_lt2500_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_lt2500_total_sum_l12m=0 AND p2.invoice_lt2500_total_sum_l24m - p2.invoice_lt2500_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_lt2500_total_sum_l12m!=0 AND p2.invoice_lt2500_total_sum_l24m - p2.invoice_lt2500_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_lt2500_total_sum_l12m / (p2.invoice_lt2500_total_sum_l24m - p2.invoice_lt2500_total_sum_l12m) END) AS invoice_lt2500_total_sum_ratio_12m, --金额小于2500（且大于等于1000）发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_lt5000_cnt_l12m is null or p2.invoice_lt5000_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_lt5000_cnt_l12m=0 AND p2.invoice_lt5000_cnt_l24m - p2.invoice_lt5000_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_lt5000_cnt_l12m!=0 AND p2.invoice_lt5000_cnt_l24m - p2.invoice_lt5000_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_lt5000_cnt_l12m / (p2.invoice_lt5000_cnt_l24m - p2.invoice_lt5000_cnt_l12m) END) AS invoice_lt5000_cnt_ratio_12m, --金额小于5000（且大于等于2500）发票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_lt5000_total_sum_l12m is null or p2.invoice_lt5000_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_lt5000_total_sum_l12m=0 AND p2.invoice_lt5000_total_sum_l24m - p2.invoice_lt5000_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_lt5000_total_sum_l12m!=0 AND p2.invoice_lt5000_total_sum_l24m - p2.invoice_lt5000_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_lt5000_total_sum_l12m / (p2.invoice_lt5000_total_sum_l24m - p2.invoice_lt5000_total_sum_l12m) END) AS invoice_lt5000_total_sum_ratio_12m, --金额小于5000（且大于等于2500）发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_gt10000_cnt_l12m is null or p2.invoice_gt10000_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_gt10000_cnt_l12m=0 AND p2.invoice_gt10000_cnt_l24m - p2.invoice_gt10000_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_gt10000_cnt_l12m!=0 AND p2.invoice_gt10000_cnt_l24m - p2.invoice_gt10000_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_gt10000_cnt_l12m / (p2.invoice_gt10000_cnt_l24m - p2.invoice_gt10000_cnt_l12m) END) AS invoice_gt10000_cnt_ratio_12m, --金额大于10000发票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_gt10000_total_sum_l12m is null or p2.invoice_gt10000_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_gt10000_total_sum_l12m=0 AND p2.invoice_gt10000_total_sum_l24m - p2.invoice_gt10000_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_gt10000_total_sum_l12m!=0 AND p2.invoice_gt10000_total_sum_l24m - p2.invoice_gt10000_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_gt10000_total_sum_l12m / (p2.invoice_gt10000_total_sum_l24m - p2.invoice_gt10000_total_sum_l12m) END) AS invoice_gt10000_total_sum_ratio_12m, --金额大于10000发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.invoice_detail_cnt_l12m is null or p2.invoice_detail_cnt_l24m is null THEN -9998
      |		  WHEN  p2.invoice_detail_cnt_l12m=0 AND p2.invoice_detail_cnt_l24m - p2.invoice_detail_cnt_l12m=0 THEN -9997
      |		  WHEN p2.invoice_detail_cnt_l12m!=0 AND p2.invoice_detail_cnt_l24m - p2.invoice_detail_cnt_l12m=0 THEN -9996
      |		  ELSE p2.invoice_detail_cnt_l12m / (p2.invoice_detail_cnt_l24m - p2.invoice_detail_cnt_l12m) END) AS invoice_detail_cnt_ratio_12m, --附有清单的发票张数12个月环比
      |
      |	(CASE WHEN p2.invoice_detail_total_sum_l12m is null or p2.invoice_detail_total_sum_l24m is null THEN -9998
      |		  WHEN  p2.invoice_detail_total_sum_l12m=0 AND p2.invoice_detail_total_sum_l24m - p2.invoice_detail_total_sum_l12m=0 THEN -9997
      |		  WHEN p2.invoice_detail_total_sum_l12m!=0 AND p2.invoice_detail_total_sum_l24m - p2.invoice_detail_total_sum_l12m=0 THEN -9996
      |		  ELSE p2.invoice_detail_total_sum_l12m / (p2.invoice_detail_total_sum_l24m - p2.invoice_detail_total_sum_l12m) END) AS invoice_detail_total_sum_ratio_12m, --附有清单的发票合计金额税额总和12个月环比
      |
      |	(CASE WHEN p2.buyer_cnt_l12m is null or p2.buyer_cnt_l1324m is null THEN -9998
      |		  WHEN  p2.buyer_cnt_l12m=0 AND p2.buyer_cnt_l1324m=0 THEN -9997
      |		  WHEN p2.buyer_cnt_l12m!=0 AND p2.buyer_cnt_l1324m=0 THEN -9996
      |		  ELSE (p2.buyer_cnt_l12m / p2.buyer_cnt_l1324m -1) END) AS buyer_cnt_chg_rate_12m --交易对手数目12个月环比变化率
      |
      |from
      |statistics_crossmonth_tmp p1
      |left join ${Const_Common.DATABASE}.c_p2 p2  --从hive中读出p2表使用
      |on p1.mcht_cd=p2.mcht_cd
    """.stripMargin

  val insert_statistics_crossmonth =
    s"""
      |insert into ${Const_Common.DATABASE}.statistics_crossmonth
      |partition(create_time)
      |select
      |	c1.mcht_cd,
      |	c1.month_pre1 as data_month,
      |	c1.invoice_sc_cnt_l3m,
      |	c1.invoice_sc_cancel_cnt_l3m,
      |	c1.invoice_sc_red_cnt_l3m,
      |	c1.invoice_sc_total_sum_l3m,
      |	c1.invoice_cnt_l3m,
      |	c1.invoice_cr_cnt_l3m,
      |	c1.invoice_total_sum_l3m,
      |	c1.invoice_s_cnt_l3m,
      |	c1.invoice_s_cancel_cnt_l3m,
      |	c1.invoice_s_red_cnt_l3m,
      |	c1.invoice_s_total_sum_l3m,
      |	c1.invoice_c_cnt_l3m,
      |	c1.invoice_c_cancel_cnt_l3m,
      |	c1.invoice_c_red_cnt_l3m,
      |	c1.invoice_c_total_sum_l3m,
      |	c1.invoice_t_cnt_l3m,
      |	c1.invoice_t_cancel_cnt_l3m,
      |	c1.invoice_t_red_cnt_l3m,
      |	c1.invoice_t_total_sum_l3m,
      |	c1.invoice_p_cnt_l3m,
      |	c1.invoice_p_cancel_cnt_l3m,
      |	c1.invoice_p_red_cnt_l3m,
      |	c1.invoice_p_total_sum_l3m,
      |	c1.invoice_e_cnt_l3m,
      |	c1.invoice_e_cancel_cnt_l3m,
      |	c1.invoice_e_red_cnt_l3m,
      |	c1.invoice_e_total_sum_l3m,
      |	c1.invoice_r_cnt_l3m,
      |	c1.invoice_r_cancel_cnt_l3m,
      |	c1.invoice_r_red_cnt_l3m,
      |	c1.invoice_r_total_sum_l3m,
      |	c2.invoice_red_cnt_rate_l3m,
      |	c2.invoice_cancel_cnt_rate_l3m,
      |	c2.invoice_red_total_sum_rate_l3m,
      |	c2.invoice_cancel_total_sum_rate_l3m,
      |	c1.invoice_lt100_cnt_l3m,
      |	c1.invoice_lt100_cr_cnt_l3m,
      |	c1.invoice_lt100_total_sum_l3m,
      |	c1.invoice_lt1000_cnt_l3m,
      |	c1.invoice_lt1000_cr_cnt_l3m,
      |	c1.invoice_lt1000_total_sum_l3m,
      |	c1.invoice_lt2500_cnt_l3m,
      |	c1.invoice_lt2500_cr_cnt_l3m,
      |	c1.invoice_lt2500_total_sum_l3m,
      |	c1.invoice_lt5000_cnt_l3m,
      |	c1.invoice_lt5000_cr_cnt_l3m,
      |	c1.invoice_lt5000_total_sum_l3m,
      |	c1.invoice_lt10000_cnt_l3m,
      |	c1.invoice_lt10000_cr_cnt_l3m,
      |	c1.invoice_lt10000_total_sum_l3m,
      |	c1.invoice_gt10000_cnt_l3m,
      |	c1.invoice_gt10000_cr_cnt_l3m,
      |	c1.invoice_gt10000_total_sum_l3m,
      |	c1.invoice_detail_cnt_l3m,
      |	c1.invoice_detail_cr_cnt_l3m,
      |	c1.invoice_detail_total_sum_l3m,
      |	c1.buyer_cnt_l3m,
      |	c1.invoice_sc_cnt_l6m,
      |	c1.invoice_sc_cancel_cnt_l6m,
      |	c1.invoice_sc_red_cnt_l6m,
      |	c1.invoice_sc_total_sum_l6m,
      |	c1.invoice_cnt_l6m,
      |	c1.invoice_cr_cnt_l6m,
      |	c1.invoice_total_sum_l6m,
      |	c1.invoice_s_cnt_l6m,
      |	c1.invoice_s_cancel_cnt_l6m,
      |	c1.invoice_s_red_cnt_l6m,
      |	c1.invoice_s_total_sum_l6m,
      |	c1.invoice_c_cnt_l6m,
      |	c1.invoice_c_cancel_cnt_l6m,
      |	c1.invoice_c_red_cnt_l6m,
      |	c1.invoice_c_total_sum_l6m,
      |	c1.invoice_t_cnt_l6m,
      |	c1.invoice_t_cancel_cnt_l6m,
      |	c1.invoice_t_red_cnt_l6m,
      |	c1.invoice_t_total_sum_l6m,
      |	c1.invoice_p_cnt_l6m,
      |	c1.invoice_p_cancel_cnt_l6m,
      |	c1.invoice_p_red_cnt_l6m,
      |	c1.invoice_p_total_sum_l6m,
      |	c1.invoice_e_cnt_l6m,
      |	c1.invoice_e_cancel_cnt_l6m,
      |	c1.invoice_e_red_cnt_l6m,
      |	c1.invoice_e_total_sum_l6m,
      |	c1.invoice_r_cnt_l6m,
      |	c1.invoice_r_cancel_cnt_l6m,
      |	c1.invoice_r_red_cnt_l6m,
      |	c1.invoice_r_total_sum_l6m,
      |	c2.invoice_red_cnt_rate_l6m,
      |	c2.invoice_cancel_cnt_rate_l6m,
      |	c2.invoice_red_total_sum_rate_l6m,
      |	c2.invoice_cancel_total_sum_rate_l6m,
      |	c1.invoice_lt100_cnt_l6m,
      |	c1.invoice_lt100_cr_cnt_l6m,
      |	c1.invoice_lt100_total_sum_l6m,
      |	c1.invoice_lt1000_cnt_l6m,
      |	c1.invoice_lt1000_cr_cnt_l6m,
      |	c1.invoice_lt1000_total_sum_l6m,
      |	c1.invoice_lt2500_cnt_l6m,
      |	c1.invoice_lt2500_cr_cnt_l6m,
      |	c1.invoice_lt2500_total_sum_l6m,
      |	c1.invoice_lt5000_cnt_l6m,
      |	c1.invoice_lt5000_cr_cnt_l6m,
      |	c1.invoice_lt5000_total_sum_l6m,
      |	c1.invoice_lt10000_cnt_l6m,
      |	c1.invoice_lt10000_cr_cnt_l6m,
      |	c1.invoice_lt10000_total_sum_l6m,
      |	c1.invoice_gt10000_cnt_l6m,
      |	c1.invoice_gt10000_cr_cnt_l6m,
      |	c1.invoice_gt10000_total_sum_l6m,
      |	c1.invoice_detail_cnt_l6m,
      |	c1.invoice_detail_cr_cnt_l6m,
      |	c1.invoice_detail_total_sum_l6m,
      |	c1.buyer_cnt_l6m,
      |	c1.buyer_a_invoice_cnt_l6m,
      |	c1.buyer_b_invoice_cnt_l6m,
      |	c1.buyer_c_invoice_cnt_l6m,
      |	c1.buyer_f_invoice_cnt_l6m,
      |	c1.buyer_a_amt_sum_l6m,
      |	c1.buyer_b_amt_sum_l6m,
      |	c1.buyer_c_amt_sum_l6m,
      |	c1.buyer_f_amt_sum_l6m,
      |	c1.invoice_sc_cnt_l12m,
      |	c1.invoice_sc_cancel_cnt_l12m,
      |	c1.invoice_sc_red_cnt_l12m,
      |	c1.invoice_sc_total_sum_l12m,
      |	c1.invoice_cnt_l12m,
      |	c1.invoice_cr_cnt_l12m,
      |	c1.invoice_total_sum_l12m,
      |	c1.invoice_s_cnt_l12m,
      |	c1.invoice_s_cancel_cnt_l12m,
      |	c1.invoice_s_red_cnt_l12m,
      |	c1.invoice_s_total_sum_l12m,
      |	c1.invoice_c_cnt_l12m,
      |	c1.invoice_c_cancel_cnt_l12m,
      |	c1.invoice_c_red_cnt_l12m,
      |	c1.invoice_c_total_sum_l12m,
      |	c1.invoice_t_cnt_l12m,
      |	c1.invoice_t_cancel_cnt_l12m,
      |	c1.invoice_t_red_cnt_l12m,
      |	c1.invoice_t_total_sum_l12m,
      |	c1.invoice_p_cnt_l12m,
      |	c1.invoice_p_cancel_cnt_l12m,
      |	c1.invoice_p_red_cnt_l12m,
      |	c1.invoice_p_total_sum_l12m,
      |	c1.invoice_e_cnt_l12m,
      |	c1.invoice_e_cancel_cnt_l12m,
      |	c1.invoice_e_red_cnt_l12m,
      |	c1.invoice_e_total_sum_l12m,
      |	c1.invoice_r_cnt_l12m,
      |	c1.invoice_r_cancel_cnt_l12m,
      |	c1.invoice_r_red_cnt_l12m,
      |	c1.invoice_r_total_sum_l12m,
      |	c2.invoice_red_cnt_rate_l12m,
      |	c2.invoice_cancel_cnt_rate_l12m,
      |	c2.invoice_red_total_sum_rate_l12m,
      |	c2.invoice_cancel_total_sum_rate_l12m,
      |	c1.invoice_lt100_cnt_l12m,
      |	c1.invoice_lt100_cr_cnt_l12m,
      |	c1.invoice_lt100_total_sum_l12m,
      |	c1.invoice_lt1000_cnt_l12m,
      |	c1.invoice_lt1000_cr_cnt_l12m,
      |	c1.invoice_lt1000_total_sum_l12m,
      |	c1.invoice_lt2500_cnt_l12m,
      |	c1.invoice_lt2500_cr_cnt_l12m,
      |	c1.invoice_lt2500_total_sum_l12m,
      |	c1.invoice_lt5000_cnt_l12m,
      |	c1.invoice_lt5000_cr_cnt_l12m,
      |	c1.invoice_lt5000_total_sum_l12m,
      |	c1.invoice_lt10000_cnt_l12m,
      |	c1.invoice_lt10000_cr_cnt_l12m,
      |	c1.invoice_lt10000_total_sum_l12m,
      |	c1.invoice_gt10000_cnt_l12m,
      |	c1.invoice_gt10000_cr_cnt_l12m,
      |	c1.invoice_gt10000_total_sum_l12m,
      |	c1.invoice_detail_cnt_l12m,
      |	c1.invoice_detail_cr_cnt_l12m,
      |	c1.invoice_detail_total_sum_l12m,
      |	c1.buyer_cnt_l12m,
      |	c1.buyer_a_invoice_cnt_l12m,
      |	c1.buyer_b_invoice_cnt_l12m,
      |	c1.buyer_c_invoice_cnt_l12m,
      |	c1.buyer_f_invoice_cnt_l12m,
      |	c1.buyer_a_amt_sum_l12m,
      |	c1.buyer_b_amt_sum_l12m,
      |	c1.buyer_c_amt_sum_l12m,
      |	c1.buyer_f_amt_sum_l12m,
      |	c1.invoice_sc_cnt_l24m,
      |	c1.invoice_sc_cancel_cnt_l24m,
      |	c1.invoice_sc_red_cnt_l24m,
      |	c1.invoice_sc_total_sum_l24m,
      |	c1.invoice_cnt_l24m,
      |	c1.invoice_cr_cnt_l24m,
      |	c1.invoice_total_sum_l24m,
      |	c1.invoice_s_cnt_l24m,
      |	c1.invoice_s_cancel_cnt_l24m,
      |	c1.invoice_s_red_cnt_l24m,
      |	c1.invoice_s_total_sum_l24m,
      |	c1.invoice_c_cnt_l24m,
      |	c1.invoice_c_cancel_cnt_l24m,
      |	c1.invoice_c_red_cnt_l24m,
      |	c1.invoice_c_total_sum_l24m,
      |	c1.invoice_t_cnt_l24m,
      |	c1.invoice_t_cancel_cnt_l24m,
      |	c1.invoice_t_red_cnt_l24m,
      |	c1.invoice_t_total_sum_l24m,
      |	c1.invoice_p_cnt_l24m,
      |	c1.invoice_p_cancel_cnt_l24m,
      |	c1.invoice_p_red_cnt_l24m,
      |	c1.invoice_p_total_sum_l24m,
      |	c1.invoice_e_cnt_l24m,
      |	c1.invoice_e_cancel_cnt_l24m,
      |	c1.invoice_e_red_cnt_l24m,
      |	c1.invoice_e_total_sum_l24m,
      |	c1.invoice_r_cnt_l24m,
      |	c1.invoice_r_cancel_cnt_l24m,
      |	c1.invoice_r_red_cnt_l24m,
      |	c1.invoice_r_total_sum_l24m,
      |	c2.invoice_red_cnt_rate_l24m,
      |	c2.invoice_cancel_cnt_rate_l24m,
      |	c2.invoice_red_total_sum_rate_l24m,
      |	c2.invoice_cancel_total_sum_rate_l24m,
      |	c1.invoice_lt100_cnt_l24m,
      |	c1.invoice_lt100_cr_cnt_l24m,
      |	c1.invoice_lt100_total_sum_l24m,
      |	c1.invoice_lt1000_cnt_l24m,
      |	c1.invoice_lt1000_cr_cnt_l24m,
      |	c1.invoice_lt1000_total_sum_l24m,
      |	c1.invoice_lt2500_cnt_l24m,
      |	c1.invoice_lt2500_cr_cnt_l24m,
      |	c1.invoice_lt2500_total_sum_l24m,
      |	c1.invoice_lt5000_cnt_l24m,
      |	c1.invoice_lt5000_cr_cnt_l24m,
      |	c1.invoice_lt5000_total_sum_l24m,
      |	c1.invoice_lt10000_cnt_l24m,
      |	c1.invoice_lt10000_cr_cnt_l24m,
      |	c1.invoice_lt10000_total_sum_l24m,
      |	c1.invoice_gt10000_cnt_l24m,
      |	c1.invoice_gt10000_cr_cnt_l24m,
      |	c1.invoice_gt10000_total_sum_l24m,
      |	c1.invoice_detail_cnt_l24m,
      |	c1.invoice_detail_cr_cnt_l24m,
      |	c1.invoice_detail_total_sum_l24m,
      |	c1.buyer_cnt_l24m,
      |	c1.buyer_a_invoice_cnt_l24m,
      |	c1.buyer_b_invoice_cnt_l24m,
      |	c1.buyer_c_invoice_cnt_l24m,
      |	c1.buyer_f_invoice_cnt_l24m,
      |	c1.buyer_a_amt_sum_l24m,
      |	c1.buyer_b_amt_sum_l24m,
      |	c1.buyer_c_amt_sum_l24m,
      |	c1.buyer_f_amt_sum_l24m,
      |
      |	c2.invoice_sc_cnt_ratio_6m,
      |	c2.invoice_sc_cancel_cnt_ratio_6m,
      |	c2.invoice_sc_red_cnt_ratio_6m,
      |	c2.invoice_sc_total_sum_ratio_6m,
      |	c2.invoice_cnt_ratio_6m,
      |	c2.invoice_total_sum_ratio_6m,
      |	c2.invoice_s_cnt_ratio_6m,
      |	c2.invoice_s_total_sum_ratio_6m,
      |	c2.invoice_c_cnt_ratio_6m,
      |	c2.invoice_c_total_sum_ratio_6m,
      |	c2.invoice_t_cnt_ratio_6m,
      |	c2.invoice_t_total_sum_ratio_6m,
      |	c2.invoice_p_cnt_ratio_6m,
      |	c2.invoice_p_total_sum_ratio_6m,
      |	c2.invoice_e_cnt_ratio_6m,
      |	c2.invoice_e_total_sum_ratio_6m,
      |	c2.invoice_r_cnt_ratio_6m,
      |	c2.invoice_r_total_sum_ratio_6m,
      |	c2.invoice_red_cnt_rate_ratio_6m,
      |	c2.invoice_cancel_cnt_rate_ratio_6m,
      |	c2.invoice_red_total_sum_rate_ratio_6m,
      |	c2.invoice_cancel_total_sum_rate_ratio_6m,
      |	c2.invoice_lt100_cnt_ratio_6m,
      |	c2.invoice_lt100_total_sum_ratio_6m,
      |	c2.invoice_lt1000_cnt_ratio_6m,
      |	c2.invoice_lt1000_total_sum_ratio_6m,
      |	c2.invoice_lt2500_cnt_ratio_6m,
      |	c2.invoice_lt2500_total_sum_ratio_6m,
      |	c2.invoice_lt5000_cnt_ratio_6m,
      |	c2.invoice_lt5000_total_sum_ratio_6m,
      |	c2.invoice_gt10000_cnt_ratio_6m,
      |	c2.invoice_gt10000_total_sum_ratio_6m,
      |	c2.invoice_detail_cnt_ratio_6m,
      |	c2.invoice_detail_total_sum_ratio_6m,
      |	c2.buyer_cnt_chg_rate_6m,
      |
      |	c1.buyer_a_cnt_chg_rate_6m,
      |	c1.buyer_b_cnt_chg_rate_6m,
      |	c1.buyer_c_cnt_chg_rate_6m,
      |	c1.buyer_f_cnt_chg_rate_6m,
      |	c1.buyer_a_invoice_cnt_l12m_chg_rate_6m,
      |	c1.buyer_b_invoice_cnt_l12m_chg_rate_6m,
      |	c1.buyer_c_invoice_cnt_l12m_chg_rate_6m,
      |	c1.buyer_f_invoice_cnt_l12m_chg_rate_6m,
      |	c1.buyer_a_amt_sum_l12m_chg_rate_6m,
      |	c1.buyer_b_amt_sum_l12m_chg_rate_6m,
      |	c1.buyer_c_amt_sum_l12m_chg_rate_6m,
      |	c1.buyer_f_amt_sum_l12m_chg_rate_6m,
      |
      |	c2.invoice_sc_cnt_ratio_12m,
      |	c2.invoice_sc_cancel_cnt_ratio_12m,
      |	c2.invoice_sc_red_cnt_ratio_12m,
      |	c2.invoice_sc_total_sum_ratio_12m,
      |	c2.invoice_cnt_ratio_12m,
      |	c2.invoice_total_sum_ratio_12m,
      |	c2.invoice_s_cnt_ratio_12m,
      |	c2.invoice_s_total_sum_ratio_12m,
      |	c2.invoice_c_cnt_ratio_12m,
      |	c2.invoice_c_total_sum_ratio_12m,
      |	c2.invoice_t_cnt_ratio_12m,
      |	c2.invoice_t_total_sum_ratio_12m,
      |	c2.invoice_p_cnt_ratio_12m,
      |	c2.invoice_p_total_sum_ratio_12m,
      |	c2.invoice_e_cnt_ratio_12m,
      |	c2.invoice_e_total_sum_ratio_12m,
      |	c2.invoice_r_cnt_ratio_12m,
      |	c2.invoice_r_total_sum_ratio_12m,
      |	c2.invoice_red_cnt_rate_ratio_12m,
      |	c2.invoice_cancel_cnt_rate_ratio_12m,
      |	c2.invoice_red_total_sum_rate_ratio_12m,
      |	c2.invoice_cancel_total_sum_rate_ratio_12m,
      |	c2.invoice_lt100_cnt_ratio_12m,
      |	c2.invoice_lt100_total_sum_ratio_12m,
      |	c2.invoice_lt1000_cnt_ratio_12m,
      |	c2.invoice_lt1000_total_sum_ratio_12m,
      |	c2.invoice_lt2500_cnt_ratio_12m,
      |	c2.invoice_lt2500_total_sum_ratio_12m,
      |	c2.invoice_lt5000_cnt_ratio_12m,
      |	c2.invoice_lt5000_total_sum_ratio_12m,
      |	c2.invoice_gt10000_cnt_ratio_12m,
      |	c2.invoice_gt10000_total_sum_ratio_12m,
      |	c2.invoice_detail_cnt_ratio_12m,
      |	c2.invoice_detail_total_sum_ratio_12m,
      |	c2.buyer_cnt_chg_rate_12m,
      |
      |	c1.buyer_a_cnt_chg_rate_12m,
      |	c1.buyer_b_cnt_chg_rate_12m,
      |	c1.buyer_c_cnt_chg_rate_12m,
      |	c1.buyer_f_cnt_chg_rate_12m,
      |	c1.buyer_a_invoice_cnt_l12m_chg_rate_12m,
      |	c1.buyer_b_invoice_cnt_l12m_chg_rate_12m,
      |	c1.buyer_c_invoice_cnt_l12m_chg_rate_12m,
      |	c1.buyer_f_invoice_cnt_l12m_chg_rate_12m,
      |	c1.buyer_a_amt_sum_l12m_chg_rate_12m,
      |	c1.buyer_b_amt_sum_l12m_chg_rate_12m,
      |	c1.buyer_c_amt_sum_l12m_chg_rate_12m,
      |	c1.buyer_f_amt_sum_l12m_chg_rate_12m,
      |	'0' AS is_delete,
      |	current_user() AS create_user,
      |	from_unixtime(unix_timestamp()) as modify_time,
      |	current_user() AS modify_user,
      |	current_date() AS create_time
      |
      |from cross_1 c1
      |left join cross_2 c2
      |on c1.mcht_cd=c2.mcht_cd and c1.month_pre1=c2.month_pre1
    """.stripMargin

}
