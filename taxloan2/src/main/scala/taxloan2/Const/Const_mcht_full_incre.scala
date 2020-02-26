package taxloan2.Const

import com.plj.tools.scala.loadString

object Const_mcht_full_incre {


  val insert_cjlog_tmp_full: String =loadString.getString("/sql/Const_mcht_full_incre/insert_cjlog_tmp_full.sql",this)
  val insert_cjlog_tmp_incre: String =loadString.getString("/sql/Const_mcht_full_incre/insert_cjlog_tmp_incre.sql",this)
  val insert_cjlog_tmp_zpp: String =loadString.getString("/sql/Const_mcht_full_incre/insert_cjlog_tmp_zpp.sql",this)

  val insert_saleinvoice_tmp1_full: String =loadString.getString("/sql/Const_mcht_full_incre/insert_saleinvoice_tmp1_full.sql",this)
  val insert_saleinvoice_tmp1_incre: String =loadString.getString("/sql/Const_mcht_full_incre/insert_saleinvoice_tmp1_incre.sql",this)


  val insert_saleinvoice_tmp2_full: String =loadString.getString("/sql/Const_mcht_full_incre/insert_saleinvoice_tmp2_full.sql",this)
  val insert_saleinvoice_tmp2_incre: String =loadString.getString("/sql/Const_mcht_full_incre/insert_saleinvoice_tmp2_incre.sql",this)


  val insert_saleinvoice_tmp: String =loadString.getString("/sql/Const_mcht_full_incre/insert_saleinvoice_tmp.sql",this)


  val insert_mcht_tax: String =loadString.getString("/sql/Const_mcht_full_incre/insert_mcht_tax.sql",this)

  val insert_cross_month_tmp_full: String =loadString.getString("/sql/Const_mcht_full_incre/insert_cross_month_tmp_full.sql",this)
  val insert_cross_month_tmp_incre: String =loadString.getString("/sql/Const_mcht_full_incre/insert_cross_month_tmp_incre.sql",this)


}
