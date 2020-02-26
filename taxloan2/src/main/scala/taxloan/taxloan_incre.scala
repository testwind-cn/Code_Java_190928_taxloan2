package taxloan


import org.apache.spark.sql.DataFrame
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive.HiveContext
import constant_common.{Const_Common, Const_table_export}
import constant_full_and_incre.{Const_counterparty_classify, Const_mcht_incre, Const_statistics_crossmonth, Const_statistics_month}
import constant_full_and_incre.Const_counterparty



object taxloan_incre {

  def main(args:Array[String]):Unit = {

    val conf: SparkConf = new SparkConf().setAppName("taxloan_incre")
    val sc: SparkContext = new SparkContext(conf)
//    sc.setLogLevel("ERROR")
//    sc.setCheckpointDir("hdfs://10.91.1.21:8022/tmp/spark-job-data/taxloan")
    val hc: HiveContext = new HiveContext(sc)

    /** ***************************************辅助表 ********************************************/

    val cjlog_tmp_df1:DataFrame = hc.sql(Const_mcht_incre.cjlog_tmp1)
    cjlog_tmp_df1.registerTempTable("cjlog_tmp1")

      val cjlog_tmp_df:DataFrame = hc.sql(Const_mcht_incre.cjlog_tmp)
      cjlog_tmp_df.registerTempTable("cjlog_tmp")



//    val a_df1:DataFrame = hc.sql("select * from cjlog_tmp")
//    println("cjlog_tmp第一次显示")
//    a_df1.show()

    hc.sql(Const_Common.truncate_cjlog_tmp)
    hc.sql(Const_Common.insert_cjlog_tmp)
    //备份临时表
    hc.sql(Const_Common.insert_bak_cjlog_tmp)
//    println("插入cjlog_tmp成功")
//    val a_df2:DataFrame = hc.sql("select * from cjlog_tmp")
//    println("cjlog_tmp第二次显示")
//    a_df2.show()


    val saleinvoice_tmp1_df:DataFrame = hc.sql(Const_mcht_incre.saleinvoice_tmp1)
    saleinvoice_tmp1_df.registerTempTable("saleinvoice_tmp1")
    hc.sql(Const_Common.truncate_saleinvoice_tmp1)
    hc.sql(Const_Common.insert_saleinvoice_tmp1)

    val saleinvoice_tmp2_df:DataFrame = hc.sql(Const_mcht_incre.saleinvoice_tmp2)
    saleinvoice_tmp2_df.registerTempTable("saleinvoice_tmp2")
    hc.sql(Const_Common.truncate_saleinvoice_tmp2)
    hc.sql(Const_Common.insert_saleinvoice_tmp2)

    val saleinvoice_tmp3_df:DataFrame = hc.sql(Const_mcht_incre.saleinvoice_tmp3)
    saleinvoice_tmp3_df.registerTempTable("saleinvoice_tmp3")
    hc.sql(Const_Common.truncate_saleinvoice_tmp3)
    hc.sql(Const_Common.insert_saleinvoice_tmp3)

    val saleinvoice_tmp_df:DataFrame = hc.sql(Const_mcht_incre.saleinvoice_tmp)
    saleinvoice_tmp_df.registerTempTable("saleinvoice_tmp")
    //saleinvoice_tmp_df.cache()


    hc.sql(Const_Common.truncate_saleinvoice_tmp)
    hc.sql(Const_Common.insert_saleinvoice_tmp)
    println("插入saleinvoice_tmp成功")

    //备份临时表
    hc.sql(Const_Common.insert_bak_saleinvoice_tmp)

    /** ***************************************商户表 ********************************************/
    hc.sql(Const_mcht_incre.insert_mcht_tax)
    println("商户表插入成功")

    /** ***************************************交易对手表 ********************************************/

    val latest_month_tmp_df:DataFrame = hc.sql(Const_counterparty.latest_month_tmp)
    latest_month_tmp_df.registerTempTable("latest_month_tmp")
    //latest_month_tmp_df.cache()

    hc.sql(Const_Common.truncate_latest_month_tmp)
    hc.sql(Const_Common.insert_latest_month_tmp)
    //备份临时表
    hc.sql(Const_Common.insert_bak_latest_month_tmp)

    hc.sql(Const_counterparty.insert_counterparty)
    println("交易对手表插入成功")


    /** ***************************************交易对手分类表 ****************************************/

    val s4_df:DataFrame = hc.sql(Const_counterparty_classify.s4)
    s4_df.registerTempTable("s4")

    hc.sql(Const_counterparty_classify.insert_counterparty_classify)
    println("交易对手分类表插入成功")


    /** ***************************************月统计表 ********************************************/

    //cross_month_tmp表后面会用到
    val cross_month_tmp_df:DataFrame = hc.sql(Const_mcht_incre.cross_month_tmp)
    cross_month_tmp_df.registerTempTable("cross_month_tmp")
    //cross_month_tmp_df.cache()

    hc.sql(Const_Common.truncate_cross_month_tmp)
    hc.sql(Const_Common.insert_cross_month_tmp)
    println("cross_month_tmp插入成功")

    //备份临时表
    hc.sql(Const_Common.insert_bak_cross_month_tmp)

    val t2_df:DataFrame = hc.sql(Const_statistics_month.t2)
    t2_df.registerTempTable("t2")
    println("t2输出完成")
   // t2_df.show()

//   println("saleinvoice_tmp输出")
//   val s1_df:DataFrame = hc.sql("select * from saleinvoice_tmp")
//   s1_df.show()

    val t3_df:DataFrame = hc.sql(Const_statistics_month.t3)
    t3_df.registerTempTable("t3")
    println("t3输出完成")
    //t3_df.show()

    val t4_df:DataFrame = hc.sql(Const_statistics_month.t4)
    t4_df.registerTempTable("t4")
    println("t4输出完成")
    //t4_df.show()

//    val a_df3:DataFrame = hc.sql("select * from cjlog_tmp")
//    println("cjlog_tmp第三次显示")
//    a_df3.show()

    val t5_df:DataFrame = hc.sql(Const_statistics_month.t5)
    t5_df.registerTempTable("t5")
    println("t5输出完成")
   // t5_df.show()
 //   t5_df.count()
//    val query =  hc.sql(Const_statistics_month.t5)
//    query.explain()

    val t6_df:DataFrame = hc.sql(Const_statistics_month.t6)
    t6_df.registerTempTable("t6")
    println("t6输出完成")

    val p2_df:DataFrame = hc.sql(Const_statistics_month.p2)
    p2_df.show()
    p2_df.registerTempTable("p2")
    hc.sql(Const_Common.truncate_p2)
    hc.sql(Const_Common.insert_p2)
    println("p2插入完成")

    hc.sql(Const_statistics_month.insert_statistics_month)
    println("月统计表插入成功")

    /** ***************************************跨月统计表 ********************************************/

    //r1
    val c_t3_df:DataFrame = hc.sql(Const_statistics_crossmonth.c_t3)
    c_t3_df.registerTempTable("c_t3")

    val r1_1_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_1)
    r1_1_df.registerTempTable("r1_1")

    val r1_2_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_2)
    r1_2_df.registerTempTable("r1_2")

    //r1_4
    val r1_4_common_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_4_common)
    r1_4_common_df.registerTempTable("r1_4_common")

    val r1_4_3m_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_4_3m)
    r1_4_3m_df.registerTempTable("r1_4_3m")

    val r1_4_6m_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_4_6m)
    r1_4_6m_df.registerTempTable("r1_4_6m")

    val r1_4_12m_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_4_12m)
    r1_4_12m_df.registerTempTable("r1_4_12m")

    val r1_4_24m_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_4_24m)
    r1_4_24m_df.registerTempTable("r1_4_24m")

    val r1_4_712m_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_4_712m)
    r1_4_712m_df.registerTempTable("r1_4_712m")

    val r1_4_1324m_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_4_1324m)
    r1_4_1324m_df.registerTempTable("r1_4_1324m")

    val r1_4_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1_4)
    r1_4_df.registerTempTable("r1_4")

    val r1_df:DataFrame = hc.sql(Const_statistics_crossmonth.r1)
    r1_df.registerTempTable("r1")

    //r4
    val r4_df:DataFrame = hc.sql(Const_statistics_crossmonth.r4)
    r4_df.registerTempTable("r4")

    //r5
    val r5_t4_df:DataFrame = hc.sql(Const_statistics_crossmonth.r5_t4)
    r5_t4_df.registerTempTable("r5_t4")

    val r5_df:DataFrame = hc.sql(Const_statistics_crossmonth.r5)
    r5_df.registerTempTable("r5")

    //r6
    val r6_t1_df:DataFrame = hc.sql(Const_statistics_crossmonth.r6_t1)
    r6_t1_df.registerTempTable("r6_t1")

    val r6_t2_df:DataFrame = hc.sql(Const_statistics_crossmonth.r6_t2)
    r6_t2_df.registerTempTable("r6_t2")

    val r6_t3_df:DataFrame = hc.sql(Const_statistics_crossmonth.r6_t3)
    r6_t3_df.registerTempTable("r6_t3")

    val r6_df:DataFrame = hc.sql(Const_statistics_crossmonth.r6)
    r6_df.registerTempTable("r6")

    //c_p2
//    val c_p2_df:DataFrame = hc.sql(Const_statistics_crossmonth.c_p2)
//    c_p2_df.registerTempTable("c_p2")

    //hc.sql(Const_statistics_crossmonth.c_p2)
    val c_p2_df:DataFrame = hc.sql(Const_statistics_crossmonth.c_p2)
    c_p2_df.registerTempTable("c_p2")
    println("c_p2成功")
    hc.sql(Const_Common.truncate_c_p2)
    hc.sql(Const_Common.insert_c_p2)

    //cross_1,cross_2
    val cross_1_df:DataFrame = hc.sql(Const_statistics_crossmonth.cross_1)
    cross_1_df.registerTempTable("cross_1")

    val cross_2_df:DataFrame = hc.sql(Const_statistics_crossmonth.cross_2)
    cross_2_df.registerTempTable("cross_2")

    //插入跨月统计表
    hc.sql(Const_statistics_crossmonth.insert_statistics_crossmonth)
    println("跨月统计表插入完成")



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

    //更新控制表的时间戳

    hc.sql(s"insert into ${Const_Common.DATABASE}.control_table  select 'mcht_tax' as table_name, from_unixtime(unix_timestamp()) as export_date")
    hc.sql(s"insert into ${Const_Common.DATABASE}.control_table  select 'counterparty' as table_name, from_unixtime(unix_timestamp()) as export_date")
    hc.sql(s"insert into ${Const_Common.DATABASE}.control_table  select 'counterparty_classify' as table_name, from_unixtime(unix_timestamp()) as export_date")
    hc.sql(s"insert into ${Const_Common.DATABASE}.control_table  select 'statistics_month' as table_name, from_unixtime(unix_timestamp()) as export_date")
    hc.sql(s"insert into ${Const_Common.DATABASE}.control_table  select 'statistics_crossmonth' as table_name, from_unixtime(unix_timestamp()) as export_date")
    println("更新控制表时间戳完成")

  }



}
