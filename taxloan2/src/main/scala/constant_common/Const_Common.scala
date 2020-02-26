package constant_common

object Const_Common {

  val DATABASE = "dm_taxloan"

  //全量跑批插入数据之前需要先truncate数据
  val TRUNCATE_MCHT_TAX = s"TRUNCATE TABLE ${DATABASE}.mcht_tax"
  val TRUNCATE_COUNTERPARTY = s"TRUNCATE TABLE ${DATABASE}.counterparty"
  val TRUNCATE_COUNTERPARTY_CLASSIFY = s"TRUNCATE TABLE ${DATABASE}.counterparty_classify"
  val TRUNCATE_STATISTICS_MONTH = s"TRUNCATE TABLE ${DATABASE}.statistics_month"
  val TRUNCATE_STATISTICS_CROSSMONTH = s"TRUNCATE TABLE ${DATABASE}.statistics_crossmonth"

  val truncate_cjlog_tmp =
    s"""
       |truncate table ${Const_Common.DATABASE}.cjlog_tmp
    """.stripMargin

  val insert_cjlog_tmp =
    s"""
       |insert into ${Const_Common.DATABASE}.cjlog_tmp select * from cjlog_tmp
    """.stripMargin

  val truncate_saleinvoice_tmp =
    s"""
       |truncate table ${Const_Common.DATABASE}.saleinvoice_tmp
    """.stripMargin

  val insert_saleinvoice_tmp =
    s"""
       |insert into ${Const_Common.DATABASE}.saleinvoice_tmp select * from saleinvoice_tmp
    """.stripMargin

  val truncate_saleinvoice_tmp1 =
    s"""
       |truncate table ${Const_Common.DATABASE}.saleinvoice_tmp1
    """.stripMargin

  val insert_saleinvoice_tmp1 =
    s"""
       |insert into ${Const_Common.DATABASE}.saleinvoice_tmp1 select * from saleinvoice_tmp1
    """.stripMargin

  val truncate_saleinvoice_tmp2 =
    s"""
       |truncate table ${Const_Common.DATABASE}.saleinvoice_tmp2
    """.stripMargin

  val insert_saleinvoice_tmp2 =
    s"""
       |insert into ${Const_Common.DATABASE}.saleinvoice_tmp2 select * from saleinvoice_tmp2
    """.stripMargin

  val truncate_saleinvoice_tmp3 =
    s"""
       |truncate table ${Const_Common.DATABASE}.saleinvoice_tmp3
    """.stripMargin

  val insert_saleinvoice_tmp3 =
    s"""
       |insert into ${Const_Common.DATABASE}.saleinvoice_tmp3 select * from saleinvoice_tmp3
    """.stripMargin

  val truncate_saleinvoice_tmp_all =
    s"""
       |truncate table ${Const_Common.DATABASE}.saleinvoice_tmp_all
    """.stripMargin

  val insert_saleinvoice_tmp_all =
    s"""
       |insert into ${Const_Common.DATABASE}.saleinvoice_tmp_all select * from saleinvoice_tmp_all
    """.stripMargin

  val truncate_latest_month_tmp =
    s"""
       |truncate table ${Const_Common.DATABASE}.latest_month_tmp
    """.stripMargin

  val insert_latest_month_tmp =
    s"""
       |insert into ${Const_Common.DATABASE}.latest_month_tmp select * from latest_month_tmp
    """.stripMargin

  val truncate_cross_month_tmp =
    s"""
       |truncate table ${Const_Common.DATABASE}.cross_month_tmp
    """.stripMargin

  val insert_cross_month_tmp =
    s"""
       |insert into ${Const_Common.DATABASE}.cross_month_tmp select * from cross_month_tmp
    """.stripMargin

  val truncate_p2 =
    s"""
       |truncate table ${Const_Common.DATABASE}.p2
    """.stripMargin

  val insert_p2 =
    s"""
       |insert into ${Const_Common.DATABASE}.p2 select * from p2
    """.stripMargin

  val truncate_c_p2 =
    s"""
       |truncate table ${Const_Common.DATABASE}.c_p2
    """.stripMargin

  val insert_c_p2 =
    s"""
       |insert into ${Const_Common.DATABASE}.c_p2 select * from c_p2
    """.stripMargin

  val insert_bak_cjlog_tmp = s"insert into ${Const_Common.DATABASE}.bak_cjlog_tmp select * from ${Const_Common.DATABASE}.cjlog_tmp"

  val insert_bak_saleinvoice_tmp = s"insert into ${Const_Common.DATABASE}.bak_saleinvoice_tmp select * from ${Const_Common.DATABASE}.saleinvoice_tmp"

  val insert_bak_latest_month_tmp = s"insert into ${Const_Common.DATABASE}.bak_latest_month_tmp select * from ${Const_Common.DATABASE}.latest_month_tmp"

  val insert_bak_cross_month_tmp = s"insert into ${Const_Common.DATABASE}.bak_cross_month_tmp select * from ${Const_Common.DATABASE}.cross_month_tmp"









}
