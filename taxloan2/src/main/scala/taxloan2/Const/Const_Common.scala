package taxloan2.Const

import com.plj.scala.tools.loadString

object Const_Common {

  val global_set_01: Array[String] =loadString.getHiveStringList("/sql/Const_Common/global_set_01.sql",this)

  val insert_bak_cjlog_tmp: String =loadString.getString("/sql/Const_Common/insert_bak_cjlog_tmp.sql",this)
  val insert_bak_saleinvoice_tmp: String =loadString.getString("/sql/Const_Common/insert_bak_saleinvoice_tmp.sql",this)
  val insert_bak_latest_month_tmp: String =loadString.getString("/sql/Const_Common/insert_bak_latest_month_tmp.sql",this)
  val insert_bak_cross_month_tmp: String =loadString.getString("/sql/Const_Common/insert_bak_cross_month_tmp.sql",this)


  val truncate_cjlog_tmp: String =loadString.getString("/sql/Const_Common/truncate_cjlog_tmp.sql",this)

  val truncate_saleinvoice_tmp1: String =loadString.getString("/sql/Const_Common/truncate_saleinvoice_tmp1.sql",this)

  val truncate_saleinvoice_tmp2: String =loadString.getString("/sql/Const_Common/truncate_saleinvoice_tmp2.sql",this)

  val truncate_saleinvoice_tmp: String =loadString.getString("/sql/Const_Common/truncate_saleinvoice_tmp.sql",this)

  val truncate_mcht_tax: String =loadString.getString("/sql/Const_Common/truncate_mcht_tax.sql",this)

  val truncate_counterparty_classify: String =loadString.getString("/sql/Const_Common/truncate_counterparty_classify.sql",this)

  val truncate_cross_month_tmp: String =loadString.getString("/sql/Const_Common/truncate_cross_month_tmp.sql",this)

  val truncate_p2: String =loadString.getString("/sql/Const_Common/truncate_p2.sql",this)

  val truncate_statistics_month: String =loadString.getString("/sql/Const_Common/truncate_statistics_month.sql",this)

  val truncate_c_p2: String =loadString.getString("/sql/Const_Common/truncate_c_p2.sql",this)

  val truncate_statistics_crossmonth: String =loadString.getString("/sql/Const_Common/truncate_statistics_crossmonth.sql",this)

  val truncate_latest_month_tmp: String =loadString.getString("/sql/Const_Common/truncate_latest_month_tmp.sql",this)

  val truncate_counterparty: String =loadString.getString("/sql/Const_Common/truncate_counterparty.sql",this)


  /* *********************************** 插入时间日志 ******************************************* */

  val insert_control_table_mcht_tax: String =loadString.getString("/sql/Const_Common/insert_control_table_mcht_tax.sql",this)
  val insert_control_table_counterparty: String =loadString.getString("/sql/Const_Common/insert_control_table_counterparty.sql",this)
  val insert_control_table_counterparty_classify: String =loadString.getString("/sql/Const_Common/insert_control_table_counterparty_classify.sql",this)
  val insert_control_table_statistics_month: String =loadString.getString("/sql/Const_Common/insert_control_table_statistics_month.sql",this)
  val insert_control_table_statistics_crossmonth: String =loadString.getString("/sql/Const_Common/insert_control_table_statistics_crossmonth.sql",this)

}
