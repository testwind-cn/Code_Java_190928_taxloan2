package taxloan

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import constant_common.{Const_Common, Const_table_export}
import constant_month._

/*
税金贷核心逻辑代码
 */

object taxloan_month {

  def main(args:Array[String]):Unit = {

    //创建SparkConf
    val conf: SparkConf = new SparkConf().setAppName("taxloan-month")
    //创建SparkContext
    val sc: SparkContext = new SparkContext(conf)
    //创建HiveContext
    val hc:HiveContext = new HiveContext(sc)

    /**************************************辅助表********************************************/

      val month_pre1_df:DataFrame = hc.sql(Const_tmp_table_month.month_pre1)
      month_pre1_df.registerTempTable("month_pre1")

    hc.sql(Const_tmp_table_month.truncate_month_pre1)
    hc.sql(Const_tmp_table_month.insert_month_pre1)

      //cjlog_tmp表
    val cjlog_tmp_df1:DataFrame = hc.sql(Const_tmp_table_month.cjlog_tmp1)
    cjlog_tmp_df1.registerTempTable("cjlog_tmp1")

    val cjlog_tmp_df:DataFrame = hc.sql(Const_tmp_table_month.cjlog_tmp)
    cjlog_tmp_df.registerTempTable("cjlog_tmp")
    hc.sql(Const_Common.truncate_cjlog_tmp)
    hc.sql(Const_Common.insert_cjlog_tmp)

    //saleinvoice_tmp表
    val saleinvoice_tmp1_df :DataFrame = hc.sql(Const_tmp_table_month.saleinvoice_tmp1)
    saleinvoice_tmp1_df.registerTempTable("saleinvoice_tmp1")
//    hc.sql("truncate table dm_taxloan.saleinvoice_tmp1")
//    hc.sql("insert into dm_taxloan.saleinvoice_tmp1 select * from saleinvoice_tmp1")
    hc.sql(Const_Common.truncate_saleinvoice_tmp1)
    hc.sql(Const_Common.insert_saleinvoice_tmp1)

    val saleinvoice_tmp2_df :DataFrame = hc.sql(Const_tmp_table_month.saleinvoice_tmp2)
    saleinvoice_tmp2_df.registerTempTable("saleinvoice_tmp2")
    hc.sql(Const_Common.truncate_saleinvoice_tmp2)
    hc.sql(Const_Common.insert_saleinvoice_tmp2)
//    hc.sql("truncate table dm_taxloan.saleinvoice_tmp2")
//    hc.sql("insert into dm_taxloan.saleinvoice_tmp2 select * from saleinvoice_tmp2")

    val saleinvoice_tmp3_df :DataFrame = hc.sql(Const_tmp_table_month.saleinvoice_tmp3)
    saleinvoice_tmp3_df.registerTempTable("saleinvoice_tmp3")
    hc.sql(Const_Common.truncate_saleinvoice_tmp3)
    hc.sql(Const_Common.insert_saleinvoice_tmp3)
//    hc.sql("truncate table dm_taxloan.saleinvoice_tmp3")
//    hc.sql("insert into dm_taxloan.saleinvoice_tmp3 select * from saleinvoice_tmp3")

    val saleinvoice_tmp_df :DataFrame = hc.sql(Const_tmp_table_month.saleinvoice_tmp)
    saleinvoice_tmp_df.registerTempTable("saleinvoice_tmp")
    hc.sql(Const_Common.truncate_saleinvoice_tmp)
    hc.sql(Const_Common.insert_saleinvoice_tmp)
    println("saleinvoice_tmp表注册完成")

    val saleinvoice_tmp_all_df:DataFrame = hc.sql(Const_tmp_table_month.saleinvoice_tmp_all)
    saleinvoice_tmp_all_df.registerTempTable("saleinvoice_tmp_all")
    hc.sql(Const_Common.truncate_saleinvoice_tmp_all)
    hc.sql(Const_Common.insert_saleinvoice_tmp_all)
    println("saleinvoice_tmp_all表注册完成")

  //  hc.sql("set hive.exec.dynamic.partition.mode=nonstrict")

    /**************************************交易对手表********************************************/

    //先从hive库里面读出counterparty表，作为交易对手临时表
    val counterparty_tmp_df1:DataFrame = hc.sql(Const_counterparty_month.counterparty_tmp1)
    counterparty_tmp_df1.registerTempTable("counterparty_tmp1")
    println("counterparty_tmp1表注册完成")

//    hc.sql("truncate table dm_taxloan.counterparty_tmp1")
//    hc.sql("insert into dm_taxloan.counterparty_tmp1 select * from counterparty_tmp1")

    val counterparty_tmp_df2:DataFrame = hc.sql(Const_counterparty_month.counterparty_tmp2)
    counterparty_tmp_df2.registerTempTable("counterparty_tmp2")
//    hc.sql("truncate table dm_taxloan.counterparty_tmp2")
//    hc.sql("insert into dm_taxloan.counterparty_tmp2 select * from counterparty_tmp2")
    println("counterparty_tmp2表注册完成")


    val counterparty_tmp_df3:DataFrame = hc.sql(Const_counterparty_month.counterparty_tmp3)
    counterparty_tmp_df3.registerTempTable("counterparty_tmp3")
//    hc.sql("truncate table dm_taxloan.counterparty_tmp3")
//    hc.sql("insert into dm_taxloan.counterparty_tmp3 select * from counterparty_tmp3")
    println("counterparty_tmp3表注册完成")

    hc.sql(Const_counterparty_month.INSERT_COUNTERPARTY)
    println("插入交易对手表数据完成")

    /**************************************交易对手分类表*****************************************/

      //这里直接利用counterparty_tmp1生成counterparty_classify_tmp1表
    val counterparty_classify_tmp_df1 :DataFrame = hc.sql(Const_counterparty_classify_month.counterparty_classify_tmp1)
    counterparty_classify_tmp_df1.registerTempTable("counterparty_classify_tmp1")

    val counterparty_classify_tmp_df2 :DataFrame = hc.sql(Const_counterparty_classify_month.counterparty_classify_tmp2)
    counterparty_classify_tmp_df2.registerTempTable("counterparty_classify_tmp2")

    val counterparty_classify_tmp_df3 :DataFrame = hc.sql(Const_counterparty_classify_month.counterparty_classify_tmp3)
    counterparty_classify_tmp_df3.registerTempTable("counterparty_classify_tmp3")

    val s4_df :DataFrame = hc.sql(Const_counterparty_classify_month.s4)
    s4_df.registerTempTable("s4")

    hc.sql(Const_counterparty_classify_month.INSERT_COUNTERPARTY_CLASSIFY)
    println("插入交易对手分类表数据完成")

    /**************************************月统计表********************************************/
    val statistics_month_tmp_df:DataFrame = hc.sql(Const_statistics_month_month.statistics_month_tmp)
    statistics_month_tmp_df.registerTempTable("statistics_month_tmp")

    val t2_df :DataFrame = hc.sql(Const_statistics_month_month.t2)
    t2_df.registerTempTable("t2")

    val t3_df :DataFrame = hc.sql(Const_statistics_month_month.t3)
    t3_df.registerTempTable("t3")

    val t4_df :DataFrame = hc.sql(Const_statistics_month_month.t4)
    t4_df.registerTempTable("t4")

    val t5_df :DataFrame = hc.sql(Const_statistics_month_month.t5)
    t5_df.registerTempTable("t5")

    val t6_df :DataFrame = hc.sql(Const_statistics_month_month.t6)
    t6_df.registerTempTable("t6")

    val p2_df :DataFrame = hc.sql(Const_statistics_month_month.p2)
    p2_df.registerTempTable("p2")

    hc.sql(Const_statistics_month_month.INSERT_STATISTICS_MONTH)
    println("插入月统计表数据完成")

    /**************************************跨月统计表********************************************/

    val statistics_crossmonth_tmp_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.statistics_crossmonth_tmp)
    statistics_crossmonth_tmp_df.registerTempTable("statistics_crossmonth_tmp")
    println("statistics_crossmonth_tmp表注册成功")

//    val c_t3_df:DataFrame = hc.sql(Const_statistics_crossmonth.c_t3)
//    c_t3_df.registerTempTable("c_t3")

    val r1_1_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_1)
    r1_1_df.registerTempTable("r1_1")

    val r1_2_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_2)
    r1_2_df.registerTempTable("r1_2")

    /*****************  r1_4  ****************/
    val r1_4_common_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_4_common)
    r1_4_common_df.registerTempTable("r1_4_common")

    val r1_4_3m_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_4_3m)
    r1_4_3m_df.registerTempTable("r1_4_3m")

    val r1_4_6m_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_4_6m)
    r1_4_6m_df.registerTempTable("r1_4_6m")

    val r1_4_12m_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_4_12m)
    r1_4_12m_df.registerTempTable("r1_4_12m")

    val r1_4_24m_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_4_24m)
    r1_4_24m_df.registerTempTable("r1_4_24m")

    val r1_4_712m_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_4_712m)
    r1_4_712m_df.registerTempTable("r1_4_712m")

    val r1_4_1324m_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_4_1324m)
    r1_4_1324m_df.registerTempTable("r1_4_1324m")

    val r1_4_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_4)
    r1_4_df.registerTempTable("r1_4")

    /*********************************/

    val r1_3_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1_3)
    r1_3_df.registerTempTable("r1_3")

    val r1_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r1)
    r1_df.registerTempTable("r1")
    println("r1表注册成功")

    //------------r4
    val r4_1_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r4_1)
    r4_1_df.registerTempTable("r4_1")

    val r4_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r4)
    r4_df.registerTempTable("r4")
    println("r4表注册成功")

    //----------r5
    val r5_t4_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r5_t4)
    r5_t4_df.registerTempTable("r5_t4")

    val r5_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r5)
    r5_df.registerTempTable("r5")
    println("r5表注册成功")

    //--------------r6
    val r6_t1_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r6_t1)
    r6_t1_df.registerTempTable("r6_t1")

    val r6_t2_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r6_t2)
    r6_t2_df.registerTempTable("r6_t2")

    val r6_t3_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r6_t3)
    r6_t3_df.registerTempTable("r6_t3")

    val r6_t4_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.r6_t4)
    r6_t4_df.registerTempTable("r6_t4")

    val r6_df :DataFrame = hc.sql(Const_statistics_crossmonth_month.r6)
    r6_df.registerTempTable("r6")
    println("r6表注册成功")

    //-----------c_p2

    val c_p2_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.c_p2)
    c_p2_df.registerTempTable("c_p2")
    println("p2表注册成功")
    hc.sql(Const_Common.truncate_c_p2)
    hc.sql(Const_Common.insert_c_p2)
//    hc.sql(s"truncate table ${Const_Common.DATABASE_NAME}.c_p2")
//    hc.sql(s"insert into ${Const_Common.DATABASE_NAME}.c_p2 select * from c_p2")

    val cross_1_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.cross_1)
    cross_1_df.registerTempTable("cross_1")
    println("cross_1表注册成功")

    val cross_2_df:DataFrame = hc.sql(Const_statistics_crossmonth_month.cross_2)
    cross_2_df.registerTempTable("cross_2")
    println("cross_2表注册成功")

    //-----------插入表
    hc.sql(Const_statistics_crossmonth_month.insert_statistics_crossmonth)
    println("插入跨月统计表完成")

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
    println("控制表时间戳更新完成")

  }
}
