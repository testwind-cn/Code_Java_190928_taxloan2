package com.plj.flink.utils.mysql


import org.apache.flink.api.java.tuple.Tuple2
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.RichSourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import com.plj.flink.utils.config.ConfigKeys


/**
 * @Description mysql source
 * @Author jiangxiaozhi
 * @Date 2018/10/15 17:05
 **/
object JdbcReader {
  private val logger = LoggerFactory.getLogger(classOf[JdbcReader])
}

class JdbcReader extends RichSourceFunction[Tuple2[String, String]] {
  private var connection: Connection = null
  private var ps:PreparedStatement = null

  //该方法主要用于打开数据库连接，下面的ConfigKeys类是获取配置的类
  @throws[Exception]
  override def open(parameters: Configuration): Unit = {
    super.open(parameters)
    Class.forName(ConfigKeys.DRIVER_CLASS) //加载数据库驱动

    connection = DriverManager.getConnection(ConfigKeys.SOURCE_DRIVER_URL, ConfigKeys.SOURCE_USER, ConfigKeys.SOURCE_PASSWORD) //获取连接

    ps = connection.prepareStatement(ConfigKeys.SOURCE_SQL)
  }

  //执行查询并获取结果
  @throws[Exception]
  override def run(ctx: SourceFunction.SourceContext[Tuple2[String, String]]): Unit = {
    try {
      val resultSet = ps.executeQuery
      while ( resultSet.next )
      {
        val name = resultSet.getString("nick")
        val id = resultSet.getString("user_id")
        JdbcReader.logger.error("readJDBC name:{}", name)
        val tuple2 = new Tuple2[String, String]
        tuple2.setFields(id, name)
        ctx.collect(tuple2) //发送结果，结果是tuple2类型，2表示两个元素，可根据实际情况选择
      }
    } catch {
      case e: Exception =>
        JdbcReader.logger.error("runException:{}", e)
    }
  }

  //关闭数据库连接
  override def cancel(): Unit = {
    try {
      super.close()
      if (connection != null) connection.close()
      if (ps != null) ps.close()
    } catch {
      case e: Exception =>
        JdbcReader.logger.error("runException:{}", e)
    }
  }
}

