package com.plj.scala.tools

//import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.hive.HiveContext

object spark {
    def runSettings(hc: HiveContext, cmd :Array[String]): Unit ={
      if ( cmd==null || hc == null) {
        println("============ 参数为空 ===========")
        return
      }
      cmd.foreach( x =>
        if ( x.length > 5 ) {
          println("============ 1.设置变量 ===========\n\t" + x )
          println("============ 2.设置变量 ===========")
          hc.sql(x).show
          println("============ 3.设置变量 ===========")
        }
      )
    }
/*
  def runSettings2(hc: SparkSession, cmd :Array[String]): Unit ={
    if ( cmd==null || hc == null) {
      println("============ 参数为空 ===========")
      return
    }
    cmd.foreach( x =>
      if ( x.length > 5 ) {
        println("============ 1.设置变量 ===========\n\t" + x )
        println("============ 2.设置变量 ===========")
        hc.sql(x).show
        println("============ 3.设置变量 ===========")
      }
    )
  }

*/
}
