package test

import com.plj.tools.scala.{loadString, spark, TimeTools}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive.HiveContext
import taxloan2.Const.{Const_Common, Const_counterparty, Const_counterparty_classify, Const_mcht_full_incre, Const_statistics_crossmonth, Const_statistics_month, Const_table_export}

object test {


  def main(args: Array[String]): Unit = {

    println(this.getClass.getProtectionDomain.getCodeSource.getLocation.getPath)


    var add_taxno=loadString.getStringList("add_taxno.txt",this)
    add_taxno.foreach(println)
    println("==========================")
    if ( add_taxno.size > 0 ) {
      var ss = "(" + add_taxno.map(x => "'" + x + "'").mkString(",") + ")"
      println(ss)
    }

    loadString.clearFile("add_taxno.txt")//,this)



    // ${hivevar:ADD_TAXNO}


    loadString.getInputSteamByFile("test.txt")



    println(TimeTools.getDate("2019-01-12 12:33:44"))
    println(TimeTools.getDateTime("2019-01-12 12:33:44"))

    println(TimeTools.getDateTimeStr())
    println(TimeTools.getDateTimeStr(null,"yyyy-MM-dd_HH:mm:ss"))
    println(TimeTools.getDateStr(null,"yyyyMMdd_HH:mm:ss"))
    println(TimeTools.getDateStr())

    // runFullIncre(false)




    args.foreach(println)
    var the_opt:Option[String]=null
    the_opt=args.find( x => x.toLowerCase.trim.equals("--fulls"))

    var aaa = loadString
    var sss: String = aaa.getString("/sql_backup/constant_full_and_incre/Const_mcht_incre/cjlog_tmp.sql")

    println(sss)


  }


  def runFullIncre(isIncre:Boolean): Unit ={

    println("=========  测试命令内容  =========")
    println(Const_Common.insert_control_table_mcht_tax)
    println("=========  测试命令内容  =========")

    val appName:String=if ( isIncre ) "taxloan2_incre" else "taxloan2_full"

    val conf:SparkConf = null // = new SparkConf().setAppName(appName)
    val sc:SparkContext = null // = new SparkContext(conf)
    val hc:HiveContext= null  //= new HiveContext(sc)

   spark.runSettings(hc, Const_Common.global_set_01)


    //    Const_Common.create_cjlog_tmp.sql
    println(Const_Common.truncate_cjlog_tmp)
//    hc.sql(Const_Common.truncate_cjlog_tmp).show(10)

    if ( isIncre ) {
      // set hivevar:DATABASE_DEST=dm_taxloan
      // var sss="insert into ${hivevar:DATABASE_DEST}.control_table  select 'cjlog_new' as table_name, from_unixtime(unix_timestamp()) as export_date union all select 'cjlog_last' as table_name, from_unixtime(unix_timestamp()) as export_date"
      // var sss="insert into ${hivevar:DATABASE_DEST}.control_table  select 'cjlog_new' as table_name, '2019-09-25 22:30:00' as export_date union all select 'cjlog_last' as table_name, '2019-09-25 22:30:00' as export_date"
      // sqlContext.sql(sss)
      // 上面两句，可以插入两行数据


      var getLastTime=hc.sql("select max(export_date),table_name from ${hivevar:DATABASE_DEST}.control_table where table_name='cjlog_new' or table_name='cjlog_last' group by table_name order by table_name")
      var sArray=getLastTime.take(2)
      sArray.foreach(println)
      var sLastTime=sArray(0).get(0).toString.substring(0,19)
      var sNewTime=sArray(1).get(0).toString.substring(0,19)

      var cmd="insert into ${hivevar:DATABASE_DEST}.control_table  select 'cjlog_last' as table_name, '" +  sNewTime + "' as export_date"

      println(cmd)
      hc.sql(cmd).show()

      cmd="set hivevar:LAST_TIME='"+sLastTime+"'"

      println(cmd)

      hc.sql(cmd).show()

      println(Const_mcht_full_incre.insert_cjlog_tmp_incre) // 增量是自己的
      hc.sql(Const_mcht_full_incre.insert_cjlog_tmp_incre).show(10)


    } else {

      println(Const_mcht_full_incre.insert_cjlog_tmp_full)
      hc.sql(Const_mcht_full_incre.insert_cjlog_tmp_full).show(10)

    }

    //备份临时表 增量多做这个
    // hc.sql(Const_Common.insert_bak_cjlog_tmp)



    //    Const_Common.create_saleinvoice_tmp1.sql
    println(Const_Common.truncate_saleinvoice_tmp1)
    hc.sql(Const_Common.truncate_saleinvoice_tmp1).show(10)

    if ( isIncre ) {
      println(Const_mcht_full_incre.insert_saleinvoice_tmp1_incre) // 增量是自己的
      hc.sql(Const_mcht_full_incre.insert_saleinvoice_tmp1_incre).show(10)
    } else {
      println(Const_mcht_full_incre.insert_saleinvoice_tmp1_full) // 增量是自己的
      hc.sql(Const_mcht_full_incre.insert_saleinvoice_tmp1_full).show(10)
    }


    //    Const_Common.create_saleinvoice_tmp2.sql
    println(Const_Common.truncate_saleinvoice_tmp2)
    hc.sql(Const_Common.truncate_saleinvoice_tmp2).show(10)

    if ( isIncre ) {
      println(Const_mcht_full_incre.insert_saleinvoice_tmp2_incre) // 增量是自己的
      hc.sql(Const_mcht_full_incre.insert_saleinvoice_tmp2_incre).show(10)
    } else {
      println(Const_mcht_full_incre.insert_saleinvoice_tmp2_full)
      hc.sql(Const_mcht_full_incre.insert_saleinvoice_tmp2_full).show(10)
    }


    //    Const_Common.create_saleinvoice_tmp.sql
    println(Const_Common.truncate_saleinvoice_tmp)
    hc.sql(Const_Common.truncate_saleinvoice_tmp).show(10)

    println(Const_mcht_full_incre.insert_saleinvoice_tmp) // 增量是自己的,但和全量一样
    hc.sql(Const_mcht_full_incre.insert_saleinvoice_tmp).show(10)

    //备份临时表 增量多做这个
    // hc.sql(Const_Common.insert_bak_saleinvoice_tmp)


    println("=========  插入saleinvoice_tmp成功  =========")


    /*****************************************商户表********************************************/

    if ( ! isIncre ) {
      println(Const_Common.truncate_mcht_tax) // 增量不做这步
      hc.sql(Const_Common.truncate_mcht_tax).show(10)
    }

    println(Const_mcht_full_incre.insert_mcht_tax) // 增量和全量SQL这步相同
    hc.sql(Const_mcht_full_incre.insert_mcht_tax).show(10)

    /*****************************************交易对手表********************************************/

    println(Const_Common.truncate_latest_month_tmp)
    hc.sql(Const_Common.truncate_latest_month_tmp).show(10)

    println(Const_counterparty.insert_latest_month_tmp)
    hc.sql(Const_counterparty.insert_latest_month_tmp).show(10)

    if ( ! isIncre ) {
      println(Const_Common.truncate_counterparty) // 增量不做这步
      hc.sql(Const_Common.truncate_counterparty).show(10)
    }
    //增量多做一个：备份临时表  hc.sql(Const_Common.insert_bak_latest_montn_tmp)

    println(Const_counterparty.insert_counterparty)
    hc.sql(Const_counterparty.insert_counterparty).show(10)

    println("=========  交易对手表插入成功  =========")

    /*****************************************交易对手分类表****************************************/

    if ( ! isIncre ) {
      println(Const_Common.truncate_counterparty_classify) // 增量不做这步
      hc.sql(Const_Common.truncate_counterparty_classify).show(10)
    }

    println(Const_counterparty_classify.insert_counterparty_classify)
    hc.sql(Const_counterparty_classify.insert_counterparty_classify).show(10)



    /*****************************************月统计表********************************************/

    //    Const_Common.create_cross_month_tmp
    println(Const_Common.truncate_cross_month_tmp)
    hc.sql(Const_Common.truncate_cross_month_tmp).show(10)


    if ( isIncre ) {

      println(Const_mcht_full_incre.insert_cross_month_tmp_incre) // 增量全量使用不同的,使用各自的
      hc.sql(Const_mcht_full_incre.insert_cross_month_tmp_incre).show(10)
    } else {

      println(Const_mcht_full_incre.insert_cross_month_tmp_full) // 增量全量使用不同的,使用各自的
      hc.sql(Const_mcht_full_incre.insert_cross_month_tmp_full).show(10)
    }

    println("=========  cross_month_tmp插入成功  =========")


    //备份临时表
    // 增量多做一次备份 hc.sql(Const_Common.insert_bak_cross_montn_tmp)



    //  Create
    println(Const_Common.truncate_p2)
    hc.sql(Const_Common.truncate_p2).show(10)




    println(Const_statistics_month.insert_p2)
    hc.sql(Const_statistics_month.insert_p2).show(10)

    println("=========  p2插入完成  =========")



    if ( ! isIncre ) {  // 增量不做这步
      println(Const_Common.truncate_statistics_month)  // 增量不做这步
      hc.sql(Const_Common.truncate_statistics_month).show(10)
    }


    println(Const_statistics_month.insert_statistics_month)
    hc.sql(Const_statistics_month.insert_statistics_month).show(10)

    println("=========  月统计表插入成功  =========")


    /*****************************************跨月统计表********************************************/

    println(Const_Common.truncate_c_p2)
    hc.sql(Const_Common.truncate_c_p2).show(10)

    println(Const_statistics_crossmonth.insert_c_p2)
    hc.sql(Const_statistics_crossmonth.insert_c_p2).show(10)

    println("=========  c_p2成功  =========")

    if ( ! isIncre ) {  // 增量不做这步
      println(Const_Common.truncate_statistics_crossmonth)
      hc.sql(Const_Common.truncate_statistics_crossmonth).show(10)
    }

    println(Const_statistics_crossmonth.insert_statistics_crossmonth)
    hc.sql(Const_statistics_crossmonth.insert_statistics_crossmonth).show(10)
    println("=========  跨月统计表插入完成  =========")


    if ( isIncre ) {
      /**************************************更新增量导出表********************************************/

      hc.sql(Const_table_export.insert_counterparty_incre)
      printf("***********************交易对手表增量数据导出到增量辅助表完成************************")
      hc.sql(Const_table_export.insert_counterparty_classify_incre)
      printf("***********************交易对手分类表增量数据导出到增量辅助表完成************************")
      hc.sql(Const_table_export.insert_statistics_month_incre)
      printf("***********************月统计表增量数据导出到增量辅助表完成************************")
      hc.sql(Const_table_export.insert_statistics_crossmonth_incre)
      printf("***********************跨月统计表增量数据导出到增量辅助表完成************************")
      hc.sql(Const_table_export.insert_mcht_tax_incre)
      printf("***********************商户表增量数据导出到增量辅助表完成************************")

    }


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

  }
}
