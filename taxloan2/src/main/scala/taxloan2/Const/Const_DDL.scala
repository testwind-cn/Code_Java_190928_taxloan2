package taxloan2.Const

import com.plj.scala.tools.loadString

object Const_DDL {

  val create_control_table: String =loadString.getString("/sql/Const_DDL_Hive/create_control_table.sql",this)

  val create_cjlog_tmp: String =loadString.getString("/sql/Const_DDL_Hive/create_cjlog_tmp.sql",this)
  val create_saleinvoice_tmp1: String =loadString.getString("/sql/Const_DDL_Hive/create_saleinvoice_tmp1.sql",this)

  val create_saleinvoice_tmp2: String =loadString.getString("/sql/Const_DDL_Hive/create_saleinvoice_tmp2.sql",this)
  val create_saleinvoice_tmp: String =loadString.getString("/sql/Const_DDL_Hive/create_saleinvoice_tmp.sql",this)
  val create_mcht_tax: String =loadString.getString("/sql/Const_DDL_Hive/create_mcht_tax.sql",this)
  val create_latest_month_tmp: String =loadString.getString("/sql/Const_DDL_Hive/create_latest_month_tmp.sql",this)
  val create_counterparty: String =loadString.getString("/sql/Const_DDL_Hive/create_counterparty.sql",this)
  val create_counterparty_classify: String =loadString.getString("/sql/Const_DDL_Hive/create_counterparty_classify.sql",this)
  val create_cross_month_tmp: String =loadString.getString("/sql/Const_DDL_Hive/create_cross_month_tmp.sql",this)

  val create_p2: String =loadString.getString("/sql/Const_DDL_Hive/create_p2.sql",this)
  val create_statistics_month: String =loadString.getString("/sql/Const_DDL_Hive/create_statistics_month.sql",this)
  val create_c_p2: String =loadString.getString("/sql/Const_DDL_Hive/create_c_p2.sql",this)
  val create_statistics_crossmonth: String =loadString.getString("/sql/Const_DDL_Hive/create_statistics_crossmonth.sql",this)

  val create_counterparty_classify_incre: String =loadString.getString("/sql/Const_DDL_Hive/create_counterparty_classify_incre.sql",this)
  val create_counterparty_incre: String =loadString.getString("/sql/Const_DDL_Hive/create_counterparty_incre.sql",this)
  val create_mcht_tax_incre: String =loadString.getString("/sql/Const_DDL_Hive/create_mcht_tax_incre.sql",this)
  val create_statistics_crossmonth_incre: String =loadString.getString("/sql/Const_DDL_Hive/create_statistics_crossmonth_incre.sql",this)
  val create_statistics_month_incre: String =loadString.getString("/sql/Const_DDL_Hive/create_statistics_month_incre.sql",this)

  val create_bak_cjlog_tmp: String =loadString.getString("/sql/Const_DDL_Hive/create_bak_cjlog_tmp.sql",this)
  val create_bak_cross_month_tmp: String =loadString.getString("/sql/Const_DDL_Hive/create_bak_cross_month_tmp.sql",this)
  val create_bak_latest_month_tmp: String =loadString.getString("/sql/Const_DDL_Hive/create_bak_latest_month_tmp.sql",this)
  val create_bak_saleinvoice_tmp: String =loadString.getString("/sql/Const_DDL_Hive/create_bak_saleinvoice_tmp.sql",this)

}
