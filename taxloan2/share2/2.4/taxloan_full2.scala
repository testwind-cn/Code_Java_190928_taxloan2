package taxloan2

import com.plj.scala.tools.spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import taxloan2.Const._

object taxloan_full2 {


  def main(args:Array[String]):Unit = {


    println("=========  测试命令内容  =========")
    println(Const_Common.insert_control_table_mcht_tax)
    println("=========  测试命令内容  =========")


    val hc = SparkSession
      .builder()
      .appName("taxloan2_full")
      .master("yarn")
      .config("spark.submit.deployMode", "client")
      .config("hadoop.home.dir", "/user/hive/warehouse")
      .config("spark.sql.warehouse.dir", "/user/hive/warehouse")
      .config("spark.yarn.jars", "local:/opt/cloudera/parcels/SPARK2-2.4.0.cloudera2-1.cdh5.13.3.p0.1041012/lib/spark2/jars/*")
      .enableHiveSupport()
      .getOrCreate();



    spark.runSettings2(hc, Const_Common.global_set_01)


    println(Const_DDL.create_control_table)
    hc.sql(Const_DDL.create_control_table).show(10)

    hc.sql(Const_DDL.create_cjlog_tmp).show(10)
    hc.sql(Const_DDL.create_saleinvoice_tmp1).show(10)
    hc.sql(Const_DDL.create_saleinvoice_tmp2).show(10)
    hc.sql(Const_DDL.create_saleinvoice_tmp).show(10)
    hc.sql(Const_DDL.create_mcht_tax).show(10)
    hc.sql(Const_DDL.create_latest_month_tmp).show(10)
    hc.sql(Const_DDL.create_counterparty).show(10)
    hc.sql(Const_DDL.create_counterparty_classify).show(10)
    hc.sql(Const_DDL.create_cross_month_tmp).show(10)
    hc.sql(Const_DDL.create_p2).show(10)
    hc.sql(Const_DDL.create_statistics_month).show(10)
    hc.sql(Const_DDL.create_c_p2).show(10)
    hc.sql(Const_DDL.create_statistics_crossmonth).show(10)



    //    Const_Common.create_cjlog_tmp.sql
    println(Const_Common.truncate_cjlog_tmp)
    hc.sql(Const_Common.truncate_cjlog_tmp).show(10)

    println(Const_mcht_full.insert_cjlog_tmp)
    hc.sql(Const_mcht_full.insert_cjlog_tmp).show(10)



    //    Const_Common.create_saleinvoice_tmp1.sql
    println(Const_Common.truncate_saleinvoice_tmp1)
    hc.sql(Const_Common.truncate_saleinvoice_tmp1).show(10)

    println(Const_mcht_full.insert_saleinvoice_tmp1)
    hc.sql(Const_mcht_full.insert_saleinvoice_tmp1).show(10)


    //    Const_Common.create_saleinvoice_tmp2.sql
    println(Const_Common.truncate_saleinvoice_tmp2)
    hc.sql(Const_Common.truncate_saleinvoice_tmp2).show(10)

    println(Const_mcht_full.insert_saleinvoice_tmp2)
    hc.sql(Const_mcht_full.insert_saleinvoice_tmp2).show(10)



    //    Const_Common.create_saleinvoice_tmp.sql
    println(Const_Common.truncate_saleinvoice_tmp)
    hc.sql(Const_Common.truncate_saleinvoice_tmp).show(10)

    println(Const_mcht_full.insert_saleinvoice_tmp)
    hc.sql(Const_mcht_full.insert_saleinvoice_tmp).show(10)


    println("=========  插入saleinvoice_tmp成功  =========")


    /*****************************************商户表********************************************/

    println(Const_Common.truncate_mcht_tax)
    hc.sql(Const_Common.truncate_mcht_tax).show(10)

    println(Const_mcht_full.insert_mcht_tax)
    hc.sql(Const_mcht_full.insert_mcht_tax).show(10)

    /*****************************************交易对手表********************************************/

    println(Const_Common.truncate_latest_month_tmp)
    hc.sql(Const_Common.truncate_latest_month_tmp).show(10)

    println(Const_counterparty.insert_latest_month_tmp)
    hc.sql(Const_counterparty.insert_latest_month_tmp).show(10)

    println(Const_Common.truncate_counterparty)
    hc.sql(Const_Common.truncate_counterparty).show(10)

    println(Const_counterparty.insert_counterparty)
    hc.sql(Const_counterparty.insert_counterparty).show(10)

    println("=========  交易对手表插入成功  =========")

    /*****************************************交易对手分类表****************************************/

    println(Const_Common.truncate_counterparty_classify)
    hc.sql(Const_Common.truncate_counterparty_classify).show(10)


    println(Const_counterparty_classify.insert_counterparty_classify)
    hc.sql(Const_counterparty_classify.insert_counterparty_classify).show(10)



    /*****************************************月统计表********************************************/

    //    Const_Common.create_cross_month_tmp
    println(Const_Common.truncate_cross_month_tmp)
    hc.sql(Const_Common.truncate_cross_month_tmp).show(10)

    println(Const_mcht_full.insert_cross_month_tmp)
    hc.sql(Const_mcht_full.insert_cross_month_tmp).show(10)



    //  Create
    println(Const_Common.truncate_p2)
    hc.sql(Const_Common.truncate_p2).show(10)

    println(Const_statistics_month.insert_p2)
    hc.sql(Const_statistics_month.insert_p2).show(10)


    println(Const_Common.truncate_statistics_month)
    hc.sql(Const_Common.truncate_statistics_month).show(10)


    println(Const_statistics_month.insert_statistics_month)
    hc.sql(Const_statistics_month.insert_statistics_month).show(10)


    /*****************************************跨月统计表********************************************/

    println(Const_Common.truncate_c_p2)
    hc.sql(Const_Common.truncate_c_p2).show(10)

    println(Const_statistics_crossmonth.insert_c_p2)
    hc.sql(Const_statistics_crossmonth.insert_c_p2).show(10)

    println(Const_Common.truncate_statistics_crossmonth)
    hc.sql(Const_Common.truncate_statistics_crossmonth).show(10)

    println(Const_statistics_crossmonth.insert_statistics_crossmonth)
    hc.sql(Const_statistics_crossmonth.insert_statistics_crossmonth).show(10)


    /**************************************更新增量导出控制表时间挫*************************************/
    println(Const_Common.insert_control_table_mcht_tax)
    hc.sql(Const_Common.insert_control_table_mcht_tax).show(10)

    println(Const_Common.insert_control_table_counterparty)
    hc.sql(Const_Common.insert_control_table_counterparty).show(10)

    println(Const_Common.insert_control_table_counterparty_classify)
    hc.sql(Const_Common.insert_control_table_counterparty_classify).show(10)

    println(Const_Common.insert_control_table_statistics_month)
    hc.sql(Const_Common.insert_control_table_statistics_month).show(10)

    println(Const_Common.insert_control_table_statistics_crossmonth)
    hc.sql(Const_Common.insert_control_table_statistics_crossmonth).show(10)




    println("=========  任务成功结束 OK  =========")
    hc.stop()
    println("=========  任务成功结束 END  =========")

  }


}
