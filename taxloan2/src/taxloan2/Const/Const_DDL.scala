package taxloan2.Const

import com.plj.scala.tools.loadString

object Const_DDL {

  val create_control_table: String =loadString.getString("/sql/Const_DDL/create_control_table.sql")

  val create_cjlog_tmp: String =loadString.getString("/sql/Const_DDL/create_cjlog_tmp.sql")
  val create_saleinvoice_tmp1: String =loadString.getString("/sql/Const_DDL/create_saleinvoice_tmp1.sql")

  val create_saleinvoice_tmp2: String =loadString.getString("/sql/Const_DDL/create_saleinvoice_tmp2.sql")
  val create_saleinvoice_tmp: String =loadString.getString("/sql/Const_DDL/create_saleinvoice_tmp.sql")
  val create_mcht_tax: String =loadString.getString("/sql/Const_DDL/create_mcht_tax.sql")
  val create_latest_month_tmp: String =loadString.getString("/sql/Const_DDL/create_latest_month_tmp.sql")
  val create_counterparty: String =loadString.getString("/sql/Const_DDL/create_counterparty.sql")
  val create_counterparty_classify: String =loadString.getString("/sql/Const_DDL/create_counterparty_classify.sql")
  val create_cross_month_tmp: String =loadString.getString("/sql/Const_DDL/create_cross_month_tmp.sql")

  val create_p2: String =loadString.getString("/sql/Const_DDL/create_p2.sql")
  val create_statistics_month: String =loadString.getString("/sql/Const_DDL/create_statistics_month.sql")
  val create_c_p2: String =loadString.getString("/sql/Const_DDL/create_c_p2.sql")
  val create_statistics_crossmonth: String =loadString.getString("/sql/Const_DDL/create_statistics_crossmonth.sql")

  val create_counterparty_classify_incre: String =loadString.getString("/sql/Const_DDL/create_counterparty_classify_incre.sql")
  val create_counterparty_incre: String =loadString.getString("/sql/Const_DDL/create_counterparty_incre.sql")
  val create_mcht_tax_incre: String =loadString.getString("/sql/Const_DDL/create_mcht_tax_incre.sql")
  val create_statistics_crossmonth_incre: String =loadString.getString("/sql/Const_DDL/create_statistics_crossmonth_incre.sql")
  val create_statistics_month_incre: String =loadString.getString("/sql/Const_DDL/create_statistics_month_incre.sql")


}
