package constant_month

import constant_common.Const_Common

object Const_counterparty_month {

  //counterparty_tmp 拿到需要生成新月份统计值的mcht_cd,buyer_name,buyer_tax_cd,若对应的统计月份记录已存在，则不生成插入数据
  val counterparty_tmp1 =
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
      |from ${Const_Common.DATABASE}.counterparty
      |where datediff(to_date(from_unixtime(unix_timestamp( substr(current_date(),1,7),'yyyy-MM'))),
      |		to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))) >50
      |	and datediff(to_date(from_unixtime(unix_timestamp( substr(current_date(),1,7),'yyyy-MM'))),
      |		to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))) <70
      |)b
    """.stripMargin

  val counterparty_tmp2 =
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
      |${Const_Common.DATABASE}.counterparty
      |)c2 where c2.rank=1 and c2.data_month=c2.month_pre1
    """.stripMargin

  val counterparty_tmp3 =
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
      |from counterparty_tmp1 c1
      |left join
      |counterparty_tmp2 c2
      |on c1.mcht_cd=c2.mcht_cd and c1.buyer_name=c2.buyer_name and c1.buyer_tax_cd=c2.buyer_tax_cd
      |)c where c.mcht_cd_c2 is NULL
    """.stripMargin


  val INSERT_COUNTERPARTY =
    s"""
      |insert into ${Const_Common.DATABASE}.counterparty
      |partition(create_time)
      |select
      |	c.mcht_cd,
      |	(case when month(current_date())=1 then concat(year(current_date())-1,12)
      |		when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
      |		else concat(year(current_date()),"0",month(current_date())-1) end) as data_month,
      |	c.buyer_name,
      |	c.buyer_tax_cd,
      |	(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) as buyer_invoice_cnt,
      |	(case when t3.buyer_invoice_cr_cnt is null then 0 else t3.buyer_invoice_cr_cnt end) as buyer_invoice_cr_cnt,
      |	(case when t3.buyer_invoice_amt_sum is null then 0.00 else t3.buyer_invoice_amt_sum end) as buyer_invoice_amt_sum,
      |	(case when t3.buyer_invoice_tax_sum is null then 0.00 else t3.buyer_invoice_tax_sum end) as buyer_invoice_tax_sum,
      |	(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) as buyer_invoice_total_sum,
      |	'0' AS is_delete,
      |	current_user() AS create_user,
      |	from_unixtime(unix_timestamp()) as modify_time,
      |	current_user() AS modify_user,
      |	current_date() AS create_time
      |
      |from counterparty_tmp3 c
      |left join
      |(
      |select
      |	t1.sellertaxno,  --商户编号
      |	t1.data_month,  --统计月份,取invoicedate的月份值
      |	t1.buyername,  --交易对手（购方）名称
      |	t1.buyertaxno,   --购方税号
      |	count( t1.buyertaxno) as buyer_invoice_cnt,   --交易对手发票张数
      |	SUM(CASE WHEN t1.totalamount < 0.0 OR t1.totaltax<0.0 OR t1.cancelflag='1' THEN 1	ELSE 0	END) AS buyer_invoice_cr_cnt,  --交易对手发票作废红冲张数
      |	SUM(CASE WHEN t1.totalamount > 0.0 AND t1.totaltax>=0.0 AND t1.cancelflag='0' THEN t1.totalamount ELSE 0	END) AS buyer_invoice_amt_sum,  --交易对手发票合计金额（不含作废及红冲发票）
      |	SUM(CASE WHEN t1.totalamount > 0.0 AND t1.totaltax>=0.0 AND t1.cancelflag='0' THEN t1.totaltax ELSE 0 END) AS buyer_invoice_tax_sum, --交易对手发票合计税额（不含作废及红冲发票）
      |	SUM(CASE WHEN t1.totalamount > 0.0 AND t1.totaltax>=0.0 AND t1.cancelflag='0' THEN t1.totalamount + t1.totaltax ELSE 0 END) AS buyer_invoice_total_sum --交易对手发票合计金额税额总和（不含作废及红冲发票）
      |from
      |${Const_Common.DATABASE}.saleinvoice_tmp t1
      |group by t1.sellertaxno,t1.data_month,t1.buyername,buyertaxno
      |)t3 on c.mcht_cd=t3.sellertaxno and c.buyer_name=t3.buyername and c.buyer_tax_cd=t3.buyertaxno
    """.stripMargin

}
