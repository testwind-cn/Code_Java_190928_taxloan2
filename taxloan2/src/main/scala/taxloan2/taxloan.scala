package taxloan2

import com.plj.tools.scala.{TimeTools, loadString, spark}
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import taxloan2.Const.{Const_Common, Const_DDL, Const_counterparty, Const_counterparty_classify, Const_mcht_full_incre, Const_statistics_crossmonth, Const_statistics_month, Const_table_export}

object taxloan {

  var TheNewTime:String=""

  def runSqlCmd(hc: HiveContext, cmd:String, runSQL:Boolean=false): Unit = {
    println(" ============ 执行命令 B=================")
    println(cmd)
    println(" ============ 执行命令 E=================")
    if (runSQL)
      hc.sql(cmd).show(10)
  }

  def printCmd(cmd:String, hc: HiveContext=null, runSQL:Boolean=false): String = {
    println(" ============ 执行命令 B=================")
    println(cmd)
    println(" ============ 执行命令 E=================")
    if (runSQL && hc != null )
      hc.sql(cmd).show(10)

    return cmd
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

    hc.sql(printCmd(Const_DDL.create_control_table)).show()

    hc.sql(printCmd(Const_DDL.create_cjlog_tmp)).show()
    hc.sql(printCmd(Const_DDL.create_saleinvoice_tmp1)).show()
    hc.sql(printCmd(Const_DDL.create_saleinvoice_tmp2)).show()
    hc.sql(printCmd(Const_DDL.create_saleinvoice_tmp)).show()
    hc.sql(printCmd(Const_DDL.create_mcht_tax)).show()
    hc.sql(printCmd(Const_DDL.create_latest_month_tmp)).show()
    hc.sql(printCmd(Const_DDL.create_counterparty)).show()
    hc.sql(printCmd(Const_DDL.create_counterparty_classify)).show()
    hc.sql(printCmd(Const_DDL.create_cross_month_tmp)).show()
    hc.sql(printCmd(Const_DDL.create_p2)).show()
    hc.sql(printCmd(Const_DDL.create_statistics_month)).show()
    hc.sql(printCmd(Const_DDL.create_c_p2)).show()
    hc.sql(printCmd(Const_DDL.create_statistics_crossmonth)).show()

    hc.sql(printCmd(Const_DDL.create_counterparty_classify_incre)).show()
    hc.sql(printCmd(Const_DDL.create_counterparty_incre)).show()
    hc.sql(printCmd(Const_DDL.create_mcht_tax_incre)).show()
    hc.sql(printCmd(Const_DDL.create_statistics_crossmonth_incre)).show()
    hc.sql(printCmd(Const_DDL.create_statistics_month_incre)).show()

    hc.sql(printCmd(Const_DDL.create_bak_cjlog_tmp)).show()
    hc.sql(printCmd(Const_DDL.create_bak_cross_month_tmp)).show()
    hc.sql(printCmd(Const_DDL.create_bak_latest_month_tmp)).show()
    hc.sql(printCmd(Const_DDL.create_bak_saleinvoice_tmp)).show()


    /*
    cjlog
    saleinvoice
    saleinvoicedetail
    dim_date
     */
  }

  def runFullIncre(isFull:Boolean): Unit ={

    println("=========  测试命令内容 B  =========")
    println(Const_Common.insert_control_table_mcht_tax)
    println("=========  测试命令内容 E  =========")

    val appName:String=if ( isFull ) "taxloan2_full" else "taxloan2_incre"

    val conf:SparkConf = new SparkConf().setAppName(appName)
    val sc:SparkContext = new SparkContext(conf)
    val hc:HiveContext = new HiveContext(sc)

    spark.runSettings(hc, Const_Common.global_set_01)


    // ***获取上次时间*****************************************************************
    // set hivevar:DATABASE_DEST=dm_taxloan
    // var sss="insert into ${hivevar:DATABASE_DEST}.control_table  select 'cjlog_new' as table_name, from_unixtime(unix_timestamp()) as export_date union all select 'cjlog_last' as table_name, from_unixtime(unix_timestamp()) as export_date"
    // var sss="insert into ${hivevar:DATABASE_DEST}.control_table  select 'cjlog_new' as table_name, '2019-09-25 22:30:00' as export_date union all select 'cjlog_last' as table_name, '2019-09-25 22:30:00' as export_date"
    // sqlContext.sql(sss)
    // 上面两句，可以插入两行数据



    var getLastTime=hc.sql(
      """
select
  date_format( max(export_date),'yyyy-MM-dd HH:mm:ss')
  ,date_format( max(export_date), 'yyyy-MM-dd_HHmmss') as date_s
  ,table_name from ${hivevar:DATABASE_DEST}.control_table where table_name='cjlog_last'
group by table_name order by table_name
""")

    var sArray=getLastTime.take(2)
    sArray.foreach(println)
    var sLastTime=sArray(0).get(0).toString.substring(0,19)
    // var sNewTime=sArray(1).get(0).toString.substring(0,19)
    var sLastTime_S=sArray(0).get(1).toString.substring(0,17)

    var cmd:String="set hivevar:LAST_TIME='"+sLastTime+"'"
    hc.sql(printCmd(cmd)).show()

    cmd="set hivevar:LAST_TIME_S='"+sLastTime_S+"'"
    hc.sql(printCmd(cmd)).show()

    // ***获取上次时间*****************************************************************




    //    Const_Common.create_cjlog_tmp.sql


    if ( isFull ) {
      hc.sql(printCmd(Const_Common.truncate_cjlog_tmp)).show()
      hc.sql(printCmd(Const_mcht_full_incre.insert_cjlog_tmp_full)).show()
    } else {

      hc.sql(printCmd(Const_Common.insert_bak_cjlog_tmp)).show()
      hc.sql(printCmd(Const_Common.truncate_cjlog_tmp)).show()


      var add_taxno=loadString.getStringList("add_taxno.txt",this)
      if ( add_taxno.size > 0 ) {
        println("========= 查询到手动添加商户 =============")
        add_taxno.foreach(println)
        cmd = "set hivevar:ADD_TAXNO=or taxno in (" + add_taxno.map(x => "'" + x + "'").mkString(",") + ")"
        hc.sql(printCmd(cmd)).show()
      } else {
        println("========= 没有手动添加商户 =============")
        cmd = "set hivevar:ADD_TAXNO="
        hc.sql(printCmd(cmd)).show()
      }
      loadString.clearFile("add_taxno.txt",this)


      hc.sql(printCmd(Const_mcht_full_incre.insert_cjlog_tmp_incre)).show()  // 增量是自己的
      // hc.sql(printCmd(Const_mcht_full_incre.insert_cjlog_tmp_zpp)).show()  // 增量是自己的

    }

    //备份临时表 增量多做这个
    // hc.sql(printCmd(Const_Common.insert_bak_cjlog_tmp)).show()



    //    Const_Common.create_saleinvoice_tmp1.sql

    hc.sql(printCmd(Const_Common.truncate_saleinvoice_tmp1)).show()

    if ( isFull ) {
      hc.sql(printCmd(Const_mcht_full_incre.insert_saleinvoice_tmp1_full)).show() // 增量是自己的

    } else {
      hc.sql(printCmd(Const_mcht_full_incre.insert_saleinvoice_tmp1_incre)).show() // 增量是自己的，增量有 distinct 全量没有
    }


    //    Const_Common.create_saleinvoice_tmp2.sql
    hc.sql(printCmd(Const_Common.truncate_saleinvoice_tmp2)).show()


    if ( isFull ) {
      hc.sql(printCmd(Const_mcht_full_incre.insert_saleinvoice_tmp2_full)).show()
    } else {
      hc.sql(printCmd(Const_mcht_full_incre.insert_saleinvoice_tmp2_incre)).show()  // 增量是自己的，增量有 distinct 全量没有
    }

    if ( ! isFull ) //备份临时表
      hc.sql(printCmd(Const_Common.insert_bak_saleinvoice_tmp)).show()


    //    Const_Common.create_saleinvoice_tmp.sql
    hc.sql(printCmd(Const_Common.truncate_saleinvoice_tmp)).show()

    hc.sql(printCmd(Const_mcht_full_incre.insert_saleinvoice_tmp)).show() // 增量是自己的,但和全量一样

    //备份临时表 增量多做这个
    // hc.sql(printCmd(Const_Common.insert_bak_saleinvoice_tmp)).show()

    println("=========  插入saleinvoice_tmp成功  =========")

    /*****************************************商户表********************************************/

    if ( isFull ) {
      hc.sql(printCmd(Const_Common.truncate_mcht_tax)).show() // 增量不做这步
    }

    hc.sql(printCmd(Const_mcht_full_incre.insert_mcht_tax)).show()  // 增量是自己的,但和全量一样

    /*****************************************交易对手表********************************************/

    if ( ! isFull ) //备份临时表
      hc.sql(printCmd(Const_Common.insert_bak_latest_month_tmp)).show()
    //增量多做一个：备份临时表  hc.sql(Const_Common.insert_bak_latest_montn_tmp)

    hc.sql(printCmd(Const_Common.truncate_latest_month_tmp)).show()

    hc.sql(printCmd(Const_counterparty.insert_latest_month_tmp)).show()

    if ( isFull || true ) {
      hc.sql(printCmd(Const_Common.truncate_counterparty)).show() // 增量不做这步
    }

    hc.sql(printCmd(Const_counterparty.insert_counterparty)).show()

    println("=========  交易对手表插入成功  =========")

    /*****************************************交易对手分类表****************************************/

    if ( isFull || true ) {
      hc.sql(printCmd(Const_Common.truncate_counterparty_classify)).show() // 增量不做这步
    }


    hc.sql(printCmd(Const_counterparty_classify.insert_counterparty_classify)).show()





    /*****************************************月统计表********************************************/


    if ( ! isFull ) //备份临时表
      hc.sql(printCmd(Const_Common.insert_bak_cross_month_tmp)).show()

    //    Const_Common.create_cross_month_tmp
    hc.sql(printCmd(Const_Common.truncate_cross_month_tmp)).show()


    if ( isFull ) {
      hc.sql(printCmd(Const_mcht_full_incre.insert_cross_month_tmp_full)).show() // 增量全量使用不同的,使用各自的
    } else {
      hc.sql(printCmd(Const_mcht_full_incre.insert_cross_month_tmp_incre)).show() // 增量全量使用不同的,使用各自的
    }

    println("=========  cross_month_tmp插入成功  =========")


    //备份临时表
    // 增量多做一次备份 hc.sql(Const_Common.insert_bak_cross_montn_tmp)



    //  Create
    hc.sql(printCmd(Const_Common.truncate_p2)).show()

    hc.sql(printCmd(Const_statistics_month.insert_p2)).show()


    println("=========  p2插入完成  =========")



    if ( isFull || true) {  // 增量不做这步
      hc.sql(printCmd(Const_Common.truncate_statistics_month)).show()  // 增量不做这步
    }

    hc.sql(printCmd(Const_statistics_month.insert_statistics_month)).show()

    println("=========  月统计表插入成功  =========")


    /*****************************************跨月统计表********************************************/

    hc.sql(printCmd(Const_Common.truncate_c_p2)).show()

    hc.sql(printCmd(Const_statistics_crossmonth.insert_c_p2)).show()



    println("=========  c_p2成功  =========")

    if ( isFull || true ) {  // 增量不做这步
      hc.sql(printCmd(Const_Common.truncate_statistics_crossmonth)).show()
    }

    hc.sql(printCmd(Const_statistics_crossmonth.insert_statistics_crossmonth)).show()

    println("=========  跨月统计表插入完成  =========")


    if ( ! isFull ) {
      /**************************************更新增量导出表********************************************/

      hc.sql(printCmd(Const_table_export.insert_counterparty_incre)).show()
      printf("***********************交易对手表增量数据导出到增量辅助表完成************************")

      hc.sql(printCmd(Const_table_export.insert_counterparty_classify_incre)).show()
      printf("***********************交易对手分类表增量数据导出到增量辅助表完成************************")

      hc.sql(printCmd(Const_table_export.insert_statistics_month_incre)).show()
      printf("***********************月统计表增量数据导出到增量辅助表完成************************")

      hc.sql(printCmd(Const_table_export.insert_statistics_crossmonth_incre)).show()
      printf("***********************跨月统计表增量数据导出到增量辅助表完成************************")

      hc.sql(printCmd(Const_table_export.insert_mcht_tax_incre)).show()
      printf("***********************商户表增量数据导出到增量辅助表完成************************")

    }


    /**************************************更新增量导出控制表时间挫*************************************/



    hc.sql(printCmd(Const_Common.insert_control_table_mcht_tax)).show()
    hc.sql(printCmd(Const_Common.insert_control_table_counterparty)).show()
    hc.sql(printCmd(Const_Common.insert_control_table_counterparty_classify)).show()
    hc.sql(printCmd(Const_Common.insert_control_table_statistics_month)).show()
    hc.sql(printCmd(Const_Common.insert_control_table_statistics_crossmonth)).show()

    cmd="insert into ${hivevar:DATABASE_DEST}.control_table  select 'cjlog_last' as table_name, '" +  TheNewTime + "' as export_date"
    hc.sql(printCmd(cmd)).show()
  }


  def main(args:Array[String]):Unit = {

    var isFull = false

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
    if (the_opt.isDefined) isFull = true

    the_opt = args.find(x => x.toLowerCase.trim.equals("--incre"))
    if (the_opt.isDefined) isFull = false

    println("========执行 全量或者 增量=======是否全量:"+isFull.toString)

    the_opt = args.find(x => x.length>=19 && x.startsWith("20"))
    TheNewTime=the_opt.getOrElse(TimeTools.getDateTimeStr()) //"2019-07-20 11:00:00")
    if (the_opt.isDefined) {
      TheNewTime=the_opt.get
    }
    println("==== 新的时间是："+TheNewTime)

    runFullIncre(isFull)
  }

}
