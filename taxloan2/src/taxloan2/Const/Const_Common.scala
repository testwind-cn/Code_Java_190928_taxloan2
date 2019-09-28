package taxloan2.Const

import com.plj.scala.tools.loadString

object Const_Common {

  val global_set_01: Array[String] =loadString.getStringList("/sql/Const_Common/global_set_01.sql")

  val truncate_cjlog_tmp: String =loadString.getString("/sql/Const_Common/truncate_cjlog_tmp.sql")

  val truncate_saleinvoice_tmp1: String =loadString.getString("/sql/Const_Common/truncate_saleinvoice_tmp1.sql")

  val truncate_saleinvoice_tmp2: String =loadString.getString("/sql/Const_Common/truncate_saleinvoice_tmp2.sql")

  val truncate_saleinvoice_tmp: String =loadString.getString("/sql/Const_Common/truncate_saleinvoice_tmp.sql")

  val truncate_mcht_tax: String =loadString.getString("/sql/Const_Common/truncate_mcht_tax.sql")

  val truncate_counterparty_classify: String =loadString.getString("/sql/Const_Common/truncate_counterparty_classify.sql")

  val truncate_cross_month_tmp: String =loadString.getString("/sql/Const_Common/truncate_cross_month_tmp.sql")

  val truncate_p2: String =loadString.getString("/sql/Const_Common/truncate_p2.sql")

  val truncate_statistics_month: String =loadString.getString("/sql/Const_Common/truncate_statistics_month.sql")

  val truncate_c_p2: String =loadString.getString("/sql/Const_Common/truncate_c_p2.sql")

  val truncate_statistics_crossmonth: String =loadString.getString("/sql/Const_Common/truncate_statistics_crossmonth.sql")

  val truncate_latest_month_tmp: String =loadString.getString("/sql/Const_Common/truncate_latest_month_tmp.sql")

  val truncate_counterparty: String =loadString.getString("/sql/Const_Common/truncate_counterparty.sql")


  /* *********************************** 插入时间日志 ******************************************* */

  val insert_control_table_mcht_tax: String =loadString.getString("/sql/Const_Common/insert_control_table_mcht_tax.sql")
  val insert_control_table_counterparty: String =loadString.getString("/sql/Const_Common/insert_control_table_counterparty.sql")
  val insert_control_table_counterparty_classify: String =loadString.getString("/sql/Const_Common/insert_control_table_counterparty_classify.sql")
  val insert_control_table_statistics_month: String =loadString.getString("/sql/Const_Common/insert_control_table_statistics_month.sql")
  val insert_control_table_statistics_crossmonth: String =loadString.getString("/sql/Const_Common/insert_control_table_statistics_crossmonth.sql")

}
