package constant_full_and_incre

import constant_common.Const_Common

object Const_mcht_incre {

  val cjlog_tmp1 =
    s"""
      |select
      |distinct p.taxno,
      |p.oldtaxno
      |from
      |(
      |select
      |	c.taxno,
      |	c.oldtaxno,
      |	m.mcht_cd
      |from
      |(
      |select
      |	taxno,
      |	oldtaxno,
      |	mflag,
      |	finish_time,
      |	row_number() over(partition by taxno,oldtaxno order by finish_time desc) AS rank
      |from ${Const_Common.DATABASE}.cjlog where finish_time!='null'
      |)c
      |left join ${Const_Common.DATABASE}.mcht_tax m  --判断增量数据税号是否已在mcht_tax表中存在
      |on c.taxno=m.mcht_cd
      |where c.rank=1 and c.mflag=1 --判断是否采集完成
      |--and cast(substr(c.finish_time,1,10) as date)=current_date() --判断采集日期是否为当天(增量跑时加上此条件)
      |)p
      |where p.mcht_cd is null
    """.stripMargin
/*
  val cjlog_tmp =
    """
      |select a.taxno1 as taxno,a.oldtaxno1 as oldtaxno from (
      |select c1.taxno as taxno1,c1.oldtaxno as oldtaxno1,c2.taxno as taxno2,c2.oldtaxno as oldtaxno2 from cjlog_tmp1 c1
      |left join cjlog_tmp1 c2
      |on c1.oldtaxno=c2.taxno
      |)a where a.taxno2 is null
    """.stripMargin
*/

  val cjlog_tmp =
    """
      |select a.taxno1 as taxno,a.oldtaxno1 as oldtaxno from (
      |select c1.taxno as taxno1,c1.oldtaxno as oldtaxno1,c2.taxno as taxno2,c2.oldtaxno as oldtaxno2 from cjlog_tmp1 c1
      |left join cjlog_tmp1 c2
      |on c1.taxno=c2.oldtaxno
      |)a where a.taxno2 is null
    """.stripMargin

  val saleinvoice_tmp1 =
    s"""
      |select
      |		distinct c1.taxno AS sellertaxno,
      |		s1.sellername,
      |		s1.selleraddtel,
      |		s1.invoicedate,
      |		(case when length (CAST(MONTH (s1.invoicedate) AS STRING)) =1 then concat(YEAR (s1.invoicedate),"0", MONTH (s1.invoicedate))
      |	      else concat(YEAR (s1.invoicedate),MONTH (s1.invoicedate)) end) AS data_month,
      |		s1.sellerbankno,
      |		c1.oldtaxno,
      |		s1.invoiceid,
      |		s1.buyername,
      |		s1.buyertaxno,
      |		s1.totalamount,
      |		s1.totaltax,
      |		s1.cancelflag,
      |		s1.invoicetype,
      |		s1.jztype
      |from ${Const_Common.DATABASE}.cjlog_tmp c1
      |left join ${Const_Common.DATABASE}.saleinvoice s1
      |on c1.taxno=s1.sellertaxno
      |where c1.oldtaxno='null' or c1.oldtaxno=''
    """.stripMargin

  val saleinvoice_tmp2 =
    s"""
      |select
      |distinct	s3.taxno AS sellertaxno,
      |	s3.sellername,
      |	s3.selleraddtel,
      |	s3.invoicedate,
      |	(case when  length (CAST(MONTH (s3.invoicedate) AS STRING)) =1 then concat(YEAR (s3.invoicedate),"0", MONTH (s3.invoicedate))
      |	      else concat(YEAR (s3.invoicedate),MONTH (s3.invoicedate)) end) AS data_month,
      |	s3.sellerbankno,
      |	s3.oldtaxno,
      |	s3.invoiceid,
      |	s3.buyername,
      |	s3.buyertaxno,
      |	s3.totalamount,
      |	s3.totaltax,
      |	s3.cancelflag,
      |	s3.invoicetype,
      |	s3.jztype
      |from
      |(
      |select
      |		c1.taxno,
      |		s1.sellertaxno,
      |		s1.sellername,
      |		s1.selleraddtel,
      |		s1.invoicedate,
      |		s1.sellerbankno,
      |		c1.oldtaxno,
      |		s1.invoiceid,
      |		s1.buyername,
      |		s1.buyertaxno,
      |		s1.totalamount,
      |		s1.totaltax,
      |		s1.cancelflag,
      |		s1.invoicetype,
      |		s1.jztype
      |
      |from ${Const_Common.DATABASE}.cjlog_tmp c1
      |left join ${Const_Common.DATABASE}.saleinvoice s1
      |on c1.taxno=s1.sellertaxno
      |where c1.oldtaxno!='null' and c1.oldtaxno!=''
      |
      |union all
      |
      |select
      |		c2.taxno,
      |		s2.sellertaxno,
      |		s2.sellername,
      |		s2.selleraddtel,
      |		s2.invoicedate,
      |		s2.sellerbankno,
      |		c2.oldtaxno,
      |		s2.invoiceid,
      |		s2.buyername,
      |		s2.buyertaxno,
      |		s2.totalamount,
      |		s2.totaltax,
      |		s2.cancelflag,
      |		s2.invoicetype,
      |		s2.jztype
      |
      |from ${Const_Common.DATABASE}.cjlog_tmp c2
      |left join ${Const_Common.DATABASE}.saleinvoice s2
      |on c2.oldtaxno=s2.sellertaxno
      |where c2.oldtaxno!='null' and c2.oldtaxno!=''
      |)s3
    """.stripMargin

  val saleinvoice_tmp3 =
    s"""
      |select * from ${Const_Common.DATABASE}.saleinvoice_tmp1
      |union all
      |select * from ${Const_Common.DATABASE}.saleinvoice_tmp2
    """.stripMargin

  val saleinvoice_tmp =
    s"""
      |select
      |sellertaxno,sellername,selleraddtel,invoicedate,data_month,sellerbankno,
      |oldtaxno,invoiceid,buyername,buyertaxno,totalamount,totaltax,cancelflag,invoicetype,jztype
      |
      |from (
      |select sellertaxno,sellername,selleraddtel,sellerbankno,invoicedate,data_month,
      |oldtaxno,invoiceid,buyername,buyertaxno,totalamount,totaltax,cancelflag,invoicetype,jztype,
      |row_number() over(partition by invoiceid order by invoicedate) as rank
      |from ${Const_Common.DATABASE}.saleinvoice_tmp3
      |)a where a.rank=1 and a.invoiceid!='NULL'
    """.stripMargin

  val insert_mcht_tax =
    s"""
      |insert into ${Const_Common.DATABASE}.mcht_tax
      |partition(create_time)
      |select
      |	distinct t3.sellertaxno AS mcht_cd,
      |	t3.sellername,
      |	t3.sellertaxno,
      |	t3.selleraddtel,
      |	t3.sellerbankno,
      |	t1.invoicedate AS first_invoicedate,
      |	'1' AS initial_get_flag,
      |	'0' AS is_delete,
      |	current_user() AS create_user,
      |	from_unixtime(unix_timestamp()) as modify_time,
      |	current_user() AS modify_user,
      |	current_date() AS create_time
      |
      |from
      |(
      |	select
      |		sellertaxno,
      |		sellername,
      |		selleraddtel,
      |		invoicedate,
      |		sellerbankno,
      |		row_number() over(partition by sellertaxno order by invoicedate) as rank
      |	from ${Const_Common.DATABASE}.saleinvoice_tmp where invoicedate is  not null
      | )t1
      | left join
      | (
      |	select
      |		sellertaxno,
      |		sellername,
      |		selleraddtel,
      |		invoicedate,
      |		sellerbankno,
      |		row_number() over(partition by sellertaxno order by invoicedate desc) as rank
      |	from ${Const_Common.DATABASE}.saleinvoice_tmp where length(coalesce(invoicedate,''))>0 and length(coalesce(sellername,''))>0 and length(coalesce(selleraddtel,''))>0 and length(coalesce(sellerbankno,''))>0
      | )t3
      | on t1.sellertaxno=t3.sellertaxno where t1.rank=1 and t3.rank=1
    """.stripMargin

  //生成辅助表，后面需要用到
  val cross_month_tmp =
    s"""
      |select
      |	t2.mcht_cd,
      |	t2.month
      |from
      |(
      |select
      |	t1.mcht_cd,
      |	d.month,
      |	(case when length(CAST(MONTH (t1.first_invoicedate) AS STRING))=1
      |		then concat(YEAR (t1.first_invoicedate),"0",MONTH (t1.	first_invoicedate))
      |		else concat(YEAR (t1.first_invoicedate),MONTH (t1.first_invoicedate)) end) AS data_month
      |from ${Const_Common.DATABASE}.dim_date d cross join
      |(
      |select
      |	distinct c.taxno as mcht_cd,
      |	m.first_invoicedate,
      |	m.mcht_cd_m
      | from ${Const_Common.DATABASE}.cjlog_tmp c
      |left join
      |(
      |select
      |	distinct mcht_cd as mcht_cd_m,
      |	first_invoicedate
      |from ${Const_Common.DATABASE}.mcht_tax
      |)m
      |on c.taxno=m.mcht_cd_m
      |) t1
      |)t2
      | where datediff(to_date(from_unixtime(unix_timestamp(t2.month,'yyyyMM'))),
      |				to_date(from_unixtime(unix_timestamp(t2.data_month,'yyyyMM'))))>=0
      |		and datediff(to_date(from_unixtime(unix_timestamp(t2.month,'yyyyMM'))),
      |		to_date(from_unixtime(unix_timestamp( substr(current_date(),1,7),'yyyy-MM'))))<-1
    """.stripMargin

}
