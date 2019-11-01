package constant_month

import constant_common.Const_Common

object Const_tmp_table_month {

  val truncate_month_pre1 = s"truncate table ${Const_Common.DATABASE}.month_pre1"
  val insert_month_pre1 = s"insert into ${Const_Common.DATABASE}.month_pre1 select * from month_pre1"


  //m-1月份表处理
  val month_pre1 =
    """
      |select
      |(case when month(current_date())=1 then concat(year(current_date())-1,12)
      |    when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
      |    else concat(year(current_date()),"0",month(current_date())-1) end) as month_pre1
    """.stripMargin

  //cjlog_tmp表
  val cjlog_tmp1 =
    s"""
       |select
       |	distinct taxno,
       |	(CASE WHEN oldtaxno in('','null','Null') then '' else oldtaxno end) AS oldtaxno
       |from ${Const_Common.DATABASE}.cjlog where mflag=1
    """.stripMargin

  val cjlog_tmp =
    """
      |select a.taxno1 as taxno,a.oldtaxno1 as oldtaxno from (
      |select c1.taxno as taxno1,c1.oldtaxno as oldtaxno1,c2.taxno as taxno2,c2.oldtaxno as oldtaxno2 from cjlog_tmp1 c1
      |left join cjlog_tmp1 c2
      |on c1.oldtaxno=c2.taxno
      |)a where a.taxno2 is null
    """.stripMargin

  //saleinvoice_tmp表
  val saleinvoice_tmp1 =
    s"""
       |select
       |		c1.taxno AS sellertaxno,
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
       |	s3.taxno AS sellertaxno,
       |	s3.sellername,
       |	s3.selleraddtel,
       |	s3.invoicedate,
       |	(case when length (CAST(MONTH (s3.invoicedate) AS STRING)) =1 then concat(YEAR (s3.invoicedate),"0", MONTH (s3.invoicedate))
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
      |from ${Const_Common.DATABASE}.month_pre1 m
      |left join
      |(
      |select sellertaxno,sellername,selleraddtel,sellerbankno,invoicedate,data_month,
      |oldtaxno,invoiceid,buyername,buyertaxno,totalamount,totaltax,cancelflag,invoicetype,jztype,
      |row_number() over(partition by invoiceid order by invoicedate) as rank
      |from ${Const_Common.DATABASE}.saleinvoice_tmp3
      |)a
      |on m.month_pre1=a.data_month
      |where a.rank=1 and a.invoiceid!='NULL'
    """.stripMargin

  //saleinvoice_tmp_all 不过滤月份数据
  val saleinvoice_tmp_all =
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

}
