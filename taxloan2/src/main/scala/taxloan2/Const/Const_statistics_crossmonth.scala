package taxloan2.Const

import com.plj.tools.scala.loadString

object Const_statistics_crossmonth {

  val insert_c_p2: String =loadString.getString("/sql/Const_statistics_crossmonth/insert_c_p2.sql",this)

  val insert_statistics_crossmonth: String =loadString.getString("/sql/Const_statistics_crossmonth/insert_statistics_crossmonth.sql",this)

}
