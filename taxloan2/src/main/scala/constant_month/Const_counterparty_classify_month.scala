package constant_month

import constant_common.Const_Common

object Const_counterparty_classify_month {

  val counterparty_classify_tmp1 =
    s"""
       |select
       |distinct b.mcht_cd,
       |b.buyer_name,
       |b.buyer_tax_cd
       |from
       |(
       |select
       |distinct a.mcht_cd,
       |a.buyername as buyer_name,
       |a.buyertaxno as buyer_tax_cd
       |from
       |(
       |select
       |m1.mcht_cd,
       |s.buyertaxno,
       |s.buyername
       |from ${Const_Common.DATABASE}.mcht_tax m1
       |left join ${Const_Common.DATABASE}.saleinvoice_tmp s
       |on m1.mcht_cd=s.sellertaxno
       |)a where a.buyertaxno is not NULL
       |
      |UNION ALL
       |
      |select
       |distinct mcht_cd,
       |buyer_name,
       |buyer_tax_cd
       |from ${Const_Common.DATABASE}.counterparty_classify
       |where datediff(to_date(from_unixtime(unix_timestamp( substr(current_date(),1,7),'yyyy-MM'))),
       |		to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))) >50
       |	and datediff(to_date(from_unixtime(unix_timestamp( substr(current_date(),1,7),'yyyy-MM'))),
       |		to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))) <70
       |)b
    """.stripMargin

  val counterparty_classify_tmp2 =
    s"""
      |select
      |c2.mcht_cd,
      |c2.buyer_name,
      |c2.buyer_tax_cd
      |from
      |(
      |select
      |	mcht_cd,
      |	data_month,
      |	buyer_name,
      |	buyer_tax_cd,
      |	(case when month(current_date())=1 then concat(year(current_date())-1,12)
      |		when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
      |		else concat(year(current_date()),"0",month(current_date())-1) end) as month_pre1,
      |	row_number() over(partition by mcht_cd,buyer_name,buyer_tax_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM'))) desc) as rank
      |from--取当前表中最近一个月的数据
      |${Const_Common.DATABASE}.counterparty_classify
      |)c2 where c2.rank=1 and c2.data_month=c2.month_pre1
    """.stripMargin

  val counterparty_classify_tmp3 =
    """
      |select
      |c.mcht_cd_c1 as mcht_cd,
      |c.buyer_name_c1 as buyer_name,
      |c.buyer_tax_cd_c1 as buyer_tax_cd
      |
      |from
      |(
      |
      |select
      |c1.mcht_cd as mcht_cd_c1,
      |c1.buyer_name as buyer_name_c1,
      |c1.buyer_tax_cd as buyer_tax_cd_c1,
      |c2.mcht_cd as mcht_cd_c2
      |from counterparty_classify_tmp1 c1
      |left join
      |counterparty_classify_tmp2 c2
      |on c1.mcht_cd=c2.mcht_cd and c1.buyer_name=c2.buyer_name and c1.buyer_tax_cd=c2.buyer_tax_cd
      |)c where c.mcht_cd_c2 is NULL
    """.stripMargin

  val s4 =
    s"""
      |select
      |*
      |from
      |(
      |select
      |	s3.mcht_cd,
      |	s2.sellertaxno,
      |	s3.data_month,
      |	s3.buyer_tax_cd as buyertaxno,
      |	s2.buyername,
      |	((year(to_date(from_unixtime(unix_timestamp(s3.data_month,'yyyyMM'))))-year(to_date(from_unixtime(unix_timestamp(s2.data_month,'yyyyMM')))))*12+month(to_date(from_unixtime(unix_timestamp(s3.data_month,'yyyyMM'))))-month(to_date(from_unixtime(unix_timestamp(s2.data_month,'yyyyMM'))))) AS buyer_hist_month,
      |	(case when month(current_date())=1 then concat(year(current_date())-1,12)
      |		when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
      |		else concat(year(current_date()),"0",month(current_date())-1) end) as month_pre1,
      |	s3.buyer_invoice_cnt_l24m ,
      |	s3.buyer_invoice_total_sum_l24m ,
      |	s3.buyer_invoice_cnt_l12m ,
      |	s3.buyer_invoice_total_sum_l12m ,
      |	s3.buyer_invoice_cnt_l6m,
      |	s3.buyer_invoice_total_sum_l6m,
      |	s3.buyer_invoice_cnt_l3m ,
      |	s3.buyer_invoice_total_sum_l3m ,
      |
      |	s3.buyer_invoice_cnt_l712m ,
      |	s3.buyer_invoice_total_sum_l712m ,
      |	s3.buyer_invoice_cnt_l1324m ,
      |	s3.buyer_invoice_total_sum_l1324m ,
      |
      |	s3.buyer_invoice_cnt_l1m,
      |	s3.buyer_invoice_total_sum_l1m
      | from
      |
      | (
      | select
      |	t3.mcht_cd,
      |	t3.data_month,
      |	t3.buyer_tax_cd,
      |	t3.buyer_name,
      |	--交易对手近24月统计
      |	sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as buyer_invoice_cnt_l24m,   --交易对手近24月开票张数，含作废红冲发票
      |	sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 23 preceding and current row) as buyer_invoice_total_sum_l24m,   --交易对手近24月合计金额税额总和，不含作废红冲发票
      |	--交易对手近12月统计
      |	sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as buyer_invoice_cnt_l12m,   --交易对手近12月开票张数，含作废红冲发票
      |	sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 11 preceding and current row) as buyer_invoice_total_sum_l12m,   --交易对手近12月合计金额税额总和，不含作废红冲发票
      |	--交易对手近6月统计
      |	sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as buyer_invoice_cnt_l6m,   --交易对手近6月开票张数，含作废红冲发票
      |	sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 5 preceding and current row) as buyer_invoice_total_sum_l6m,   --交易对手近6月合计金额税额总和，不含作废红冲发票
      |	--交易对手近3月统计
      |	sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as buyer_invoice_cnt_l3m,   --交易对手近3月开票张数，含作废红冲发票
      |	sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 2 preceding and current row) as buyer_invoice_total_sum_l3m,   --交易对手近3月合计金额税额总和，不含作废红冲发票
      |
      |	--交易对手近7-12月统计
      |	sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 12 preceding and 7 preceding) as buyer_invoice_cnt_l712m,   --交易对手近7-12月开票张数，含作废红冲发票
      |	sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 12 preceding and 7 preceding) as buyer_invoice_total_sum_l712m,   --交易对手近7-12月合计金额税额总和，不含作废红冲发票
      |
      |	--交易对手近13-24月统计
      |	sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 24 preceding and 13 preceding) as buyer_invoice_cnt_l1324m,   --交易对手近13-24月开票张数，含作废红冲发票
      |	sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
      |	rows between 24 preceding and 13 preceding) as buyer_invoice_total_sum_l1324m,   --交易对手近13-24月合计金额税额总和，不含作废红冲发票
      |
      |	--交易对手近1月统计
      |	t3.buyer_invoice_cnt as buyer_invoice_cnt_l1m,
      |	t3.buyer_invoice_total_sum as buyer_invoice_total_sum_l1m
      |
      |from ${Const_Common.DATABASE}.counterparty t3
      |
      | )s3
      | left join
      | (
      | select
      |		sellertaxno,
      |		data_month,
      |		buyertaxno,
      |		buyername,
      |		invoicedate,
      |		row_number() over(partition by sellertaxno, buyertaxno,buyername order by invoicedate) as rank  --取该交易对手最早的交易时间
      |	from ${Const_Common.DATABASE}.saleinvoice_tmp_all
      | )s2 on s3.mcht_cd=s2.sellertaxno and s3.buyer_tax_cd=s2.buyertaxno and s3.buyer_name=s2.buyername  where s2.rank=1
      | )s5 where s5.sellertaxno is not null and s5.data_month=s5.month_pre1
    """.stripMargin

  val INSERT_COUNTERPARTY_CLASSIFY =
    s"""
      |insert into ${Const_Common.DATABASE}.counterparty_classify
      |partition(create_time)
      |select
      |	c1.mcht_cd,
      |	(case when month(current_date())=1 then concat(year(current_date())-1,12)
      |		when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
      |		else concat(year(current_date()),"0",month(current_date())-1) end) as data_month,
      |	c1.buyer_name,
      |	c1.buyer_tax_cd,
      |
      |	(CASE WHEN s4.buyer_hist_month IS NULL THEN 0 ELSE s4.buyer_hist_month END) AS buyer_hist_month,
      |
      |	(CASE WHEN s4.buyer_invoice_cnt_l24m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l24m END) AS buyer_invoice_cnt_l24m,
      |	(CASE WHEN s4.buyer_invoice_total_sum_l24m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l24m END) AS buyer_invoice_total_sum_l24m,
      |
      |	(CASE WHEN s4.buyer_invoice_cnt_l12m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l12m END) AS buyer_invoice_cnt_l12m,
      |	(CASE WHEN s4.buyer_invoice_total_sum_l12m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l12m END) AS buyer_invoice_total_sum_l12m,
      |
      |	(CASE WHEN s4.buyer_invoice_cnt_l6m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l6m END) AS buyer_invoice_cnt_l6m,
      |	(CASE WHEN s4.buyer_invoice_total_sum_l6m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l6m END) AS buyer_invoice_total_sum_l6m,
      |
      |	(CASE WHEN s4.buyer_invoice_cnt_l3m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l3m END) AS buyer_invoice_cnt_l3m,
      |	(CASE WHEN s4.buyer_invoice_total_sum_l3m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l3m END) AS buyer_invoice_total_sum_l3m,
      |
      |	(CASE WHEN s4.buyer_invoice_cnt_l712m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l712m END) AS buyer_invoice_cnt_l712m,
      |	(CASE WHEN s4.buyer_invoice_total_sum_l712m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l712m END) AS buyer_invoice_total_sum_l712m,
      |
      |	(CASE WHEN s4.buyer_invoice_cnt_l1324m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l1324m END) AS buyer_invoice_cnt_l1324m,
      |	(CASE WHEN s4.buyer_invoice_total_sum_l1324m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l1324m END) AS buyer_invoice_total_sum_l1324m,
      |
      |	(CASE WHEN s4.buyer_invoice_cnt_l1m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l1m END) AS buyer_invoice_cnt_l1m,
      |	(CASE WHEN s4.buyer_invoice_total_sum_l1m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l1m END) AS buyer_invoice_total_sum_l1m,
      |
      |
      |	(CASE WHEN s4.buyer_hist_month>18 AND s4.buyer_invoice_cnt_l12m>=3 AND s4.buyer_invoice_cnt_l6m>0 THEN 'A'
      |	  WHEN s4.buyer_hist_month<=18 AND s4.buyer_invoice_cnt_l12m>=3 AND s4.buyer_invoice_cnt_l6m>0 THEN 'B'
      |	  ELSE 'C' END
      |	)AS buyer_type,  --判断交易对手类型
      |
      |	'0' AS is_delete,
      |	current_user() AS create_user,
      |	from_unixtime(unix_timestamp()) as modify_time,
      |	current_user() AS modify_user,
      |	current_date() AS create_time
      |
      |from counterparty_classify_tmp3 c1
      |left join s4
      |on c1.mcht_cd=s4.mcht_cd and c1.buyer_name=s4.buyername and c1.buyer_tax_cd=s4.buyertaxno
    """.stripMargin



}
