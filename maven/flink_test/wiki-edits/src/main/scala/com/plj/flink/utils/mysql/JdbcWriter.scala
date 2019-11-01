package com.plj.flink.utils.mysql

import com.plj.flink.utils.config.ConfigKeys
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import scala.Tuple2
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement


/**
 * @Description mysql sink
 * @Author jiangxiaozhi
 * @Date 2018/10/15 18:31
 **/
class JdbcWriter extends RichSinkFunction[(String, String)] {
  private var connection:Connection = null
  private var preparedStatement:PreparedStatement = null

  @throws[Exception]
  override def open(parameters: Configuration): Unit = {
    super.open(parameters)
    // 加载JDBC驱动
    Class.forName(ConfigKeys.DRIVER_CLASS)
    // 获取数据库连接
    connection = DriverManager.getConnection(ConfigKeys.SINK_DRIVER_URL, ConfigKeys.SINK_USER, ConfigKeys.SINK_PASSWORD) //写入mysql数据库

    preparedStatement = connection.prepareStatement(ConfigKeys.SINK_SQL) //insert sql在配置文件中

    super.open(parameters)
  }

  @throws[Exception]
  override def close(): Unit = {
    super.close()
    if (preparedStatement != null) preparedStatement.close()
    if (connection != null) connection.close()
    super.close()
  }

  @throws[Exception]
  override def invoke(value: (String,String), context: SinkFunction.Context[_]): Unit = {
    try {
      val name = value._1 //获取JdbcReader发送过来的结果
      preparedStatement.setString(1, name)
      preparedStatement.executeUpdate
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}