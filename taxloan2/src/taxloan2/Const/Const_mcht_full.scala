package taxloan2.Const

import com.plj.scala.tools.loadString

object Const_mcht_full {


  val insert_cjlog_tmp: String =loadString.getString("/sql/Const_mcht_full/insert_cjlog_tmp.sql")

  val insert_saleinvoice_tmp1: String =loadString.getString("/sql/Const_mcht_full/insert_saleinvoice_tmp1.sql")
  val insert_saleinvoice_tmp2: String =loadString.getString("/sql/Const_mcht_full/insert_saleinvoice_tmp2.sql")
  val insert_saleinvoice_tmp: String =loadString.getString("/sql/Const_mcht_full/insert_saleinvoice_tmp.sql")

  val insert_mcht_tax: String =loadString.getString("/sql/Const_mcht_full/insert_mcht_tax.sql")

  val insert_cross_month_tmp: String =loadString.getString("/sql/Const_mcht_full/insert_cross_month_tmp.sql")



}
