package com.plj.flink.utils.mysql

import java.sql.{Date, Types}

import com.plj.flink.utils.mysql.JDBCFullTest.getSqlType
// 获取类型信息
import scala.reflect.runtime.universe._

import org.apache.flink.api.common.typeinfo.BasicTypeInfo
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat
import org.apache.flink.api.java.io.jdbc.JDBCOutputFormat
import org.apache.flink.api.java.typeutils.RowTypeInfo
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala._

import org.apache.flink.types.Row

/*

CREATE DATABASE IF NOT EXISTS flink;

DROP TABLE IF EXISTS flink.user;

CREATE TABLE flink.user (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_unique` (`userid`,`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

*/

object JDBCFullTest {


  val driverClass = "com.mysql.jdbc.Driver"
  val dbUrl = "jdbc:mysql://localhost:3306/flink?useSSL=false"
  val userNmae = "root"
  val passWord = "thbl123"

  def main(args: Array[String]): Unit = {

    var tabName = "flink.user"
    var colNames = Array[String]("userid", "name", "password")
    var colTypes = Array[Int](Types.INTEGER, Types.VARCHAR, Types.VARCHAR)

    var colNames2 = Array[String]("password", "name")
    var colTypes2 = Array[Int]( Types.VARCHAR, Types.VARCHAR,Types.INTEGER)

    // 运行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 插入一组数据
    // 准备数据
    val row1 = new Row(3)
    row1.setField(0, 1)
    row1.setField(1, "maxiu")
    row1.setField(2, "123456")

    val row2 = new Row(3)
    row2.setField(0, 2)
    row2.setField(1, "蠢妹")
    row2.setField(2, "不知道")

    val rows: Array[Row] = Array(row1, row2)

    // 插入数据
    insertRows(tabName, colNames, colTypes, rows)

    // 查看所有数据
    selectAllFields(env)

    // 更新某行
    val row3 = new Row(3)
    row3.setField(0, "仙女")
    row3.setField(1, "哈哈哈")
    row3.setField(2, 1)

    updateRow("flink.user", colNames2, "userid=?", colTypes2, row3)
    //updateRow(row3)

    // 查看所有数据
    selectAllFields(env)


    // 插入一组数据
    // 准备数据
    val row5 = new Row(3)
    row5.setField(0, 3)
    row5.setField(1, "天使")
    row5.setField(2, "会飞的")

    val row6 = new Row(3)
    row6.setField(0, 1)
    row6.setField(1, "西瓜")
    row6.setField(2, "会滚的")

    val row4 = new Row(3)
    row4.setField(0, 2)
    row4.setField(1, "蠢妹")
    row4.setField(2, "牛呀牛")

    val rows2: Array[Row] = Array(row5, row6,row4)
    // UPSERT 更新某行
    insertRows(tabName, colNames, colTypes, rows2, true)

    // 查看所有数据
    selectAllFields(env)

  }

  def getSqlType(sqlTypes:Int): Type ={

/* 8.6.3 从 JDBC 类型到 Java Object 类型的映射
http://www.itdaan.com/blog/2012/01/30/e0023fee6bf67e016bf412ff4545ed71.html

JDBC 类型       Java Object 类型
CHAR 	          String
VARCHAR 	      String
LONGVARCHAR 	  String
NUMERIC 	      java.math.BigDecimal
DECIMAL 	      java.math.BigDecimal
BIT 	          Boolean
TINYINT 	      Integer
SMALLINT 	      Integer
INTEGER 	      Integer
BIGINT 	        Long
REAL 	          Float
FLOAT 	        Double
DOUBLE 	        Double
BINARY 	        byte[]
VARBINARY 	    byte[]
LONGVARBINARY 	byte[]
DATE 	          java.sql.Date
TIME 	          java.sql.Time
TIMESTAMP 	    java.sql.Timestamp
*/

/*
Scala 数据类型
  Scala 与 Java有着相同的数据类型，下表列出了 Scala 支持的数据类型：
https://www.runoob.com/scala/scala-data-types.html

数据类型 	描述
Byte 	    8位有符号补码整数。数值区间为 -128 到 127
Short 	  16位有符号补码整数。数值区间为 -32768 到 32767
Int 	    32位有符号补码整数。数值区间为 -2147483648 到 2147483647
Long 	    64位有符号补码整数。数值区间为 -9223372036854775808 到 9223372036854775807
Float 	  32 位, IEEE 754 标准的单精度浮点数
Double 	  64 位 IEEE 754 标准的双精度浮点数
Char 	    16位无符号Unicode字符, 区间值为 U+0000 到 U+FFFF
String 	  字符序列
Boolean 	true或false
Unit 	    表示无值，和其他语言中void等同。用作不返回任何结果的方法的结果类型。Unit只有一个实例值，写成()。
Null 	    null 或空引用
Nothing 	Nothing类型在Scala的类层级的最底端；它是任何其他类型的子类型。
Any 	    Any是所有其他类的超类
AnyRef 	  AnyRef类是Scala里所有引用类(reference class)的基类
*/

    var dataType =

    sqlTypes match {
      case Types.CHAR => typeOf[String]
      case Types.VARCHAR => typeOf[String]
      case Types.LONGVARCHAR => typeOf[String]
      case Types.NUMERIC => typeOf[BigDecimal]
      case Types.DECIMAL => typeOf[BigDecimal]
      case Types.BIT => typeOf[Boolean]
      case Types.TINYINT => typeOf[Byte]
      case Types.SMALLINT => typeOf[Short]
      case Types.INTEGER => typeOf[Int]
      case Types.BIGINT => typeOf[Long]
      case Types.REAL => typeOf[Float]
      case Types.FLOAT => typeOf[Double]
      case Types.DOUBLE => typeOf[Double]
      case Types.BINARY => typeOf[Array[Byte]]
      case Types.VARBINARY => typeOf[Array[Byte]]
      case Types.LONGVARBINARY => typeOf[Array[Byte]]
      case Types.DATE => typeOf[java.sql.Date]
      case Types.TIME => typeOf[java.sql.Time]
      case Types.TIMESTAMP => typeOf[java.sql.Timestamp]
      case _ => typeOf[Int]
    }

    return dataType
  }

  def convSqlType(data: Object,sqlTypes:Int): Any ={
    var newData =

      sqlTypes match {
        case Types.CHAR => data.asInstanceOf[String]
        case Types.VARCHAR => data.asInstanceOf[String]
        case Types.LONGVARCHAR => data.asInstanceOf[String]
        case Types.NUMERIC => data.asInstanceOf[BigDecimal]
        case Types.DECIMAL => data.asInstanceOf[BigDecimal]
        case Types.BIT => data.asInstanceOf[Boolean]
        case Types.TINYINT => data.asInstanceOf[Byte]
        case Types.SMALLINT => data.asInstanceOf[Short]
        case Types.INTEGER => data.asInstanceOf[Int]
        case Types.BIGINT => data.asInstanceOf[Long]
        case Types.REAL => data.asInstanceOf[Float]
        case Types.FLOAT => data.asInstanceOf[Double]
        case Types.DOUBLE => data.asInstanceOf[Double]
        case Types.BINARY => data.asInstanceOf[Array[Byte]]
        case Types.VARBINARY => data.asInstanceOf[Array[Byte]]
        case Types.LONGVARBINARY => data.asInstanceOf[Array[Byte]]
        case Types.DATE => data.asInstanceOf[java.sql.Date]
        case Types.TIME => data.asInstanceOf[java.sql.Time]
        case Types.TIMESTAMP => data.asInstanceOf[java.sql.Timestamp]
        case _ => data.asInstanceOf[Int]
      }
    return newData
  }

  /**
   * 插入数据
   */
  def insertRows(tableName:String,columns:Array[String],colTypes:Array[Int],rows: Array[Row], isUpsert:Boolean=false): Unit = {
    var sql_cmd:String=null
    var cmd1:String = "insert into %s ( %s ) values ( %s )"
    var cmd2:String = "insert into %s ( %s ) values ( %s ) ON DUPLICATE KEY UPDATE %s"
/*
"""
insert into flink.user
(userid, name,password)
values ( ?, ?, ? )
ON DUPLICATE KEY UPDATE
userid=?, name=?, password=?
"""

"insert into flink.user ( userid, name, password ) values ( ?, ?, ? )"
*/
    // 准备输出格式
    var jdbcOutputFormat:JDBCOutputFormat=null;
    val jdbcOutputFormatBuilder= JDBCOutputFormat.buildJDBCOutputFormat()
      .setDrivername(driverClass)
      .setDBUrl(dbUrl)
      .setUsername(userNmae)
      .setPassword(passWord);

    if ( isUpsert ) {

      sql_cmd=cmd2.format(
        tableName, // 表名
        columns.mkString(", "),  // 字段名
        Array.fill[String](columns.length)( "?").mkString(", "), // 多个 ?
        columns.mkString("","=?, ","=?") // 更新字段
      )

      // 需要对应到表中的字段
      // Array[Int](Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR)
      jdbcOutputFormat = jdbcOutputFormatBuilder.setQuery(sql_cmd)
        .setSqlTypes(Array.concat[Int](colTypes,colTypes))
        .finish();

    }
    else
    {

      sql_cmd=cmd1.format(
        tableName, // 表名
        columns.mkString(", "),  // 字段名
        Array.fill[String](columns.length)( "?").mkString(", ") // 多个 ?
      )

      // 需要对应到表中的字段
      // Array[Int](Types.INTEGER, Types.VARCHAR, Types.VARCHAR)
      jdbcOutputFormat = jdbcOutputFormatBuilder.setQuery(sql_cmd)
        .setSqlTypes(colTypes)
        .finish();
    }

    System.out.println(sql_cmd)

    // 连接到目标数据库，并初始化preparedStatement
    jdbcOutputFormat.open(0, 1)


    // 添加记录到 preparedStatement,此时jdbcOutputFormat需要确保是开启的
    // 未指定列类型时，此操作可能会失败
    for (row <- rows) {

      if ( isUpsert ) {
        val newRow = new Row( columns.length * 2 )
        for (  x <- 0 until row.getArity )
        {
          newRow.setField( x, row.getField(x) )
          newRow.setField( x + columns.length, row.getField(x) )
        }
        jdbcOutputFormat.writeRecord(newRow)
      }
      else
      {
        jdbcOutputFormat.writeRecord(row)
      }

    }

    // 执行preparedStatement，并关闭此实例的所有资源
    jdbcOutputFormat.close()
  }


  /**
   * 更新某行数据（官网没给出更新示例，不知道实际是不是这样更新的）
   *
   * @param row 更新后的数据
   */
  def updateRow(tableName:String, columns:Array[String], whereStr:String, colTypes:Array[Int], row: Row): Unit = {
    var cmd1:String = "update %s set %s where %s"
    var sql_cmd:String=cmd1.format(
      tableName,
      columns.mkString("","=?, ","=?"), // 更新字段
      whereStr
    )

    System.out.println(sql_cmd)

    // 准备输出格式
    val jdbcOutputFormat = JDBCOutputFormat.buildJDBCOutputFormat()
      .setDrivername(driverClass)
      .setDBUrl(dbUrl)
      .setUsername(userNmae)
      .setPassword(passWord)
      // .setQuery("update flink.user set name = ?, password = ? where userid = ?")
      .setQuery(sql_cmd)
      // 需要对应到行rowComb中的字段类型 Array[Int](Types.VARCHAR, Types.VARCHAR, Types.INTEGER)
      .setSqlTypes(colTypes)
      .finish()

    // 连接到目标数据库，并初始化preparedStatement
    jdbcOutputFormat.open(0, 1)

    // 组装sql中对应的字段，rowComb中的字段个数及类型需要与sql中的问号一致
    val rowComb = new Row(row.getArity)
    for (  x <- 0 until row.getArity )
    {
      // var tp:Type = getSqlType(colTypes(x))
  //    rowComb.setField( x, row.getField(x).asInstanceOf[tp.type ] )
      rowComb.setField(x, convSqlType( row.getField(x), colTypes(x)))
    }

    // rowComb.setField(1, row.getField(2).asInstanceOf[String])
    // rowComb.setField(2, row.getField(0).asInstanceOf[Int])

    // 添加记录到 preparedStatement,此时jdbcOutputFormat需要确保是开启的
    // 未指定列类型时，此操作可能会失败
    jdbcOutputFormat.writeRecord(rowComb)

    // 执行preparedStatement，并关闭此实例的所有资源
    jdbcOutputFormat.close()
  }

  /**
   * 查询所有字段
   *
   * @return
   */
  def selectAllFields(env: ExecutionEnvironment) = {
    val inputBuilder = JDBCInputFormat.buildJDBCInputFormat()
      .setDrivername(driverClass)
      .setDBUrl(dbUrl)
      .setUsername(userNmae)
      .setPassword(passWord)
      .setQuery("select userid, name, password from flink.user")
      // 这里第一个字段类型写int会报类型不会转换异常。（原作者说会出异常）
      .setRowTypeInfo(new RowTypeInfo(
        BasicTypeInfo.INT_TYPE_INFO,
        BasicTypeInfo.STRING_TYPE_INFO,
        BasicTypeInfo.STRING_TYPE_INFO))
      .finish()

    val source = env.createInput(inputBuilder)
    source.print()
  }


}
