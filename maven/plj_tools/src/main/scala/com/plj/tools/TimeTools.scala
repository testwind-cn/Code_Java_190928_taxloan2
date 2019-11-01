package com.plj.scala.tools

import java.util._
import java.text._

object TimeTools {


  /**
   * 获取现在时间
   *
   * @return返回短时间格式 yyyy-MM-dd
   */
  def getDateTime(s: String=null, pattern:String="yyyy-MM-dd HH:mm:ss"): Calendar =
  {
    var resultDate:Date = null
    val currentTime:Date = new Date
    val formatter = new SimpleDateFormat(pattern)
    var dateString:String = null
    val timeZone = java.util.TimeZone.getTimeZone("Asia/Shanghai")
    formatter.setTimeZone(timeZone)
    val calendar = Calendar.getInstance(timeZone, java.util.Locale.ENGLISH)
    if (s == null || s.length <= 0) dateString = formatter.format(currentTime)
    else dateString = s
    try {
      resultDate = formatter.parse(dateString)
      calendar.setTime(resultDate)
    } catch {
      case e: ParseException =>
        e.printStackTrace()
        dateString = formatter.format(currentTime)
        try {
          resultDate = formatter.parse(dateString)
          calendar.setTime(resultDate)
        } catch {
          case e2: ParseException =>
            calendar.setTime(currentTime)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
    }
    calendar
  }

  def getDate(s: String=null, pattern:String="yyyy-MM-dd"): Calendar =
  {
    getDateTime(s, pattern)
  }

  def getDateTimeStr(dt: Calendar=null, pattern:String="yyyy-MM-dd HH:mm:ss"): String = {
    var theDate:Calendar=null
    val formatter = new SimpleDateFormat(pattern)
    val timeZone = java.util.TimeZone.getTimeZone("Asia/Shanghai")
    formatter.setTimeZone(timeZone)
    theDate= if ( dt==null) getDateTime() else dt
    val dateString = formatter.format(theDate.getTime)
    dateString
  }

  def getDateStr(dt: Calendar=null, pattern:String="yyyy-MM-dd"): String = {
    getDateTimeStr(dt, pattern)
  }

  def addDate(dt: Calendar, day: Int): Calendar = {
    val newDate = dt.clone.asInstanceOf[Calendar]
    newDate.add(Calendar.DATE, day)
    newDate
  }


  def main(args: Array[String]): Unit = {


    println(TimeTools.getDate("2019-01-12 12:33:44"))
    println(TimeTools.getDateTime("2019-01-12 12:33:44"))

    println(TimeTools.getDateTimeStr())
    println(TimeTools.getDateTimeStr(null, "yyyy==MM==dd====HH:mm:ss"))
    println(TimeTools.getDateStr(null, "yyyy=MM=dd--HH:mm:ss"))
    println(TimeTools.getDateStr())
  }



}
