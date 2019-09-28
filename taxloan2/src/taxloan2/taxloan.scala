package taxloan2

import com.plj.scala.tools.spark
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import taxloan2.Const.{
  Const_Common,
  Const_DDL,
  Const_counterparty,
  Const_counterparty_classify,
  Const_mcht_full,
  Const_mcht_incre,
  Const_statistics_crossmonth,
  Const_statistics_month,
  Const_table_export
}

object taxloan {

  var TheNewTime:String=""

  def runSqlCmd(hc: HiveContext, cmd:String): Unit = {
    println(" ============ 执行命令 B=================")
    println(cmd)
    println(" ============ 执行命令 E=================")
    hc.sql(cmd).show(10)

  }

  def runInit: Unit = {
// ############################## 日期
    println("=========  测试命令内容  =========")
    println(Const_Common.insert_control_table_mcht_tax)

    val appName: String = "taxloan2_Init"

    val conf: SparkConf = new SparkConf().setAppName(appName)
    val sc: SparkContext = new SparkContext(conf)
    val hc: HiveContext = new HiveContext(sc)

    spark.runSettings(hc, Const_Common.global_set_01)

    println(Const_DDL.create_control_table)
    runSqlCmd(hc, Const_DDL.create_control_table)

    runSqlCmd(hc, Const_DDL.create_cjlog_tmp)
    runSqlCmd(hc, Const_DDL.create_saleinvoice_tmp1)
    runSqlCmd(hc, Const_DDL.create_saleinvoice_tmp2)
    runSqlCmd(hc, Const_DDL.create_saleinvoice_tmp)
    runSqlCmd(hc, Const_DDL.create_mcht_tax)
    runSqlCmd(hc, Const_DDL.create_latest_month_tmp)
    runSqlCmd(hc, Const_DDL.create_counterparty)
    runSqlCmd(hc, Const_DDL.create_counterparty_classify)
    runSqlCmd(hc, Const_DDL.create_cross_month_tmp)
    runSqlCmd(hc, Const_DDL.create_p2)
    runSqlCmd(hc, Const_DDL.create_statistics_month)
    runSqlCmd(hc, Const_DDL.create_c_p2)
    runSqlCmd(hc, Const_DDL.create_statistics_crossmonth)

    runSqlCmd(hc, Const_DDL.create_counterparty_classify_incre)
    runSqlCmd(hc, Const_DDL.create_counterparty_incre)
    runSqlCmd(hc, Const_DDL.create_mcht_tax_incre)
    runSqlCmd(hc, Const_DDL.create_statistics_crossmonth_incre)
    runSqlCmd(hc, Const_DDL.create_statistics_month_incre)

    /*
    cjlog
    saleinvoice
    saleinvoicedetail
    dim_date
     */
  }

  def runFullIncre(isIncre:Boolean): Unit ={

    println("=========  测试命令内容  =========")
    println(Const_Common.insert_control_table_mcht_tax)
    println("=========  测试命令内容  =========")

    val appName:String=if ( isIncre ) "taxloan2_incre" else "taxloan2_full"

    val conf:SparkConf = new SparkConf().setAppName(appName)
    val sc:SparkContext = new SparkContext(conf)
    val hc:HiveContext = new HiveContext(sc)

    spark.runSettings(hc, Const_Common.global_set_01)


    //    Const_Common.create_cjlog_tmp.sql
    runSqlCmd(hc, Const_Common.truncate_cjlog_tmp)

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

      var cmd="set hivevar:LAST_TIME='"+sLastTime+"'"
      runSqlCmd(hc, cmd)

      runSqlCmd(hc, Const_mcht_incre.insert_cjlog_tmp) // 增量是自己的


    } else {

      runSqlCmd(hc, Const_mcht_full.insert_cjlog_tmp)

    }

    //备份临时表 增量多做这个
    // hc.sql(Const_Common.insert_bak_cjlog_tmp)



    //    Const_Common.create_saleinvoice_tmp1.sql

    runSqlCmd(hc, Const_Common.truncate_saleinvoice_tmp1)

    if ( isIncre ) {
      runSqlCmd(hc, Const_mcht_incre.insert_saleinvoice_tmp1) // 增量是自己的
    } else {
      runSqlCmd(hc, Const_mcht_full.insert_saleinvoice_tmp1) // 增量是自己的
    }


    //    Const_Common.create_saleinvoice_tmp2.sql
    runSqlCmd(hc, Const_Common.truncate_saleinvoice_tmp2)


    if ( isIncre ) {
      runSqlCmd(hc, Const_mcht_incre.insert_saleinvoice_tmp2) // 增量是自己的
    } else {
      runSqlCmd(hc, Const_mcht_full.insert_saleinvoice_tmp2)
    }


    //    Const_Common.create_saleinvoice_tmp.sql
    runSqlCmd(hc, Const_Common.truncate_saleinvoice_tmp)

    runSqlCmd(hc, Const_mcht_full.insert_saleinvoice_tmp) // 增量是自己的,但和全量一样

    //备份临时表 增量多做这个
    // hc.sql(Const_Common.insert_bak_saleinvoice_tmp)

    println("=========  插入saleinvoice_tmp成功  =========")

    /*****************************************商户表********************************************/

    if ( ! isIncre ) {
      runSqlCmd(hc, Const_Common.truncate_mcht_tax) // 增量不做这步
    }

    runSqlCmd(hc, Const_mcht_full.insert_mcht_tax) // 增量和全量SQL这步相同

    /*****************************************交易对手表********************************************/

    runSqlCmd(hc, Const_Common.truncate_latest_month_tmp)

    runSqlCmd(hc, Const_counterparty.insert_latest_month_tmp)


    if ( ! isIncre ) {
      runSqlCmd(hc, Const_Common.truncate_counterparty) // 增量不做这步
    }
    //增量多做一个：备份临时表  hc.sql(Const_Common.insert_bak_latest_montn_tmp)

    runSqlCmd(hc, Const_counterparty.insert_counterparty)

    println("=========  交易对手表插入成功  =========")

    /*****************************************交易对手分类表****************************************/

    if ( ! isIncre ) {
      runSqlCmd(hc, Const_Common.truncate_counterparty_classify) // 增量不做这步
    }


    runSqlCmd(hc, Const_counterparty_classify.insert_counterparty_classify)





    /*****************************************月统计表********************************************/

    //    Const_Common.create_cross_month_tmp
    runSqlCmd(hc, Const_Common.truncate_cross_month_tmp)


    if ( isIncre ) {
      runSqlCmd(hc, Const_mcht_incre.insert_cross_month_tmp) // 增量全量使用不同的,使用各自的
    } else {
      runSqlCmd(hc, Const_mcht_full.insert_cross_month_tmp) // 增量全量使用不同的,使用各自的
    }

    println("=========  cross_month_tmp插入成功  =========")


    //备份临时表
    // 增量多做一次备份 hc.sql(Const_Common.insert_bak_cross_montn_tmp)



    //  Create
    runSqlCmd(hc, Const_Common.truncate_p2)


    runSqlCmd(hc, Const_statistics_month.insert_p2)


    println("=========  p2插入完成  =========")



    if ( ! isIncre ) {  // 增量不做这步
      runSqlCmd(hc, Const_Common.truncate_statistics_month)  // 增量不做这步
    }

    runSqlCmd(hc, Const_statistics_month.insert_statistics_month)

    println("=========  月统计表插入成功  =========")


    /*****************************************跨月统计表********************************************/

    runSqlCmd(hc, Const_Common.truncate_c_p2)

    runSqlCmd(hc, Const_statistics_crossmonth.insert_c_p2)


    println("=========  c_p2成功  =========")

    if ( ! isIncre ) {  // 增量不做这步
      runSqlCmd(hc, Const_Common.truncate_statistics_crossmonth)
    }

    runSqlCmd(hc, Const_statistics_crossmonth.insert_statistics_crossmonth)

    println("=========  跨月统计表插入完成  =========")


    if ( isIncre ) {
      /**************************************更新增量导出表********************************************/

      runSqlCmd(hc, Const_table_export.insert_counterparty_incre)
      printf("***********************交易对手表增量数据导出到增量辅助表完成************************")

      runSqlCmd(hc, Const_table_export.insert_counterparty_classify_incre)
      printf("***********************交易对手分类表增量数据导出到增量辅助表完成************************")

      runSqlCmd(hc, Const_table_export.insert_statistics_month_incre)
      printf("***********************月统计表增量数据导出到增量辅助表完成************************")

      runSqlCmd(hc, Const_table_export.insert_statistics_crossmonth_incre)
      printf("***********************跨月统计表增量数据导出到增量辅助表完成************************")

      runSqlCmd(hc, Const_table_export.insert_mcht_tax_incre)
      printf("***********************商户表增量数据导出到增量辅助表完成************************")

    }


    /**************************************更新增量导出控制表时间挫*************************************/



    runSqlCmd(hc, Const_Common.insert_control_table_mcht_tax)
    runSqlCmd(hc, Const_Common.insert_control_table_counterparty)
    runSqlCmd(hc, Const_Common.insert_control_table_counterparty_classify)
    runSqlCmd(hc, Const_Common.insert_control_table_statistics_month)
    runSqlCmd(hc, Const_Common.insert_control_table_statistics_crossmonth)

    var cmd="insert into ${hivevar:DATABASE_DEST}.control_table  select 'cjlog_last' as table_name, '" +  TheNewTime + "' as export_date"
    runSqlCmd(hc, cmd)
  }


  def main(args:Array[String]):Unit = {

    var isIncre = false

    println("========获取到参数==BEGING=========")
    args.foreach(println)
    println("========获取到参数==END=======")

    var the_opt: Option[String] = null


    the_opt = args.find(x => x.toLowerCase.trim.equals("--init"))
    if (the_opt.isDefined) {
      println("========执行初始化=======")
      runInit
      return
    }

    the_opt = args.find(x => x.toLowerCase.trim.equals("--full"))
    if (the_opt.isDefined) isIncre = false

    the_opt = args.find(x => x.toLowerCase.trim.equals("--incre"))
    if (the_opt.isDefined) isIncre = true

    println("========执行 全量或者 增量======="+isIncre.toString)

    the_opt = args.find(x => x.length>=19 && x.startsWith("20"))
    TheNewTime=the_opt.getOrElse("2019-07-20 11:00:00")
    if (the_opt.isDefined) {
      TheNewTime=the_opt.get
    }
    println("==== 新的时间是："+TheNewTime)

    runFullIncre(isIncre)
  }

}
