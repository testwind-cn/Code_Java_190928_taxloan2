package com.plj.scala.tools

import java.io.{BufferedInputStream, BufferedOutputStream, BufferedReader, BufferedWriter, File, FileInputStream, FileNotFoundException, FileOutputStream, IOException, InputStream, InputStreamReader, OutputStream, OutputStreamWriter}
import java.util.Properties

/*
1、优先读 jar 所在目录，或 CLASS根目录
2、再读命令行运行目录
3、再读JAR里面 res 根目录下的
*/

object loadString {

  def getInputSteamByFilePath(name:String): InputStream = {
    var inputStream:InputStream = null

    try {
      inputStream = new BufferedInputStream(new FileInputStream(name))
      println("读文件打开 :    " + name)
    }
    catch{
      case ex: FileNotFoundException =>{
        println("异常错误: Missing file exception:   " + name)
      }
      case ex: IOException => {
        println("异常错误: IO Exception")
      }
    }

    inputStream
  }

  def getOutputSteamByFilePath(name:String): OutputStream = {
    var outputStream:OutputStream = null

    try {
      outputStream = new BufferedOutputStream(new FileOutputStream(name))
      println("写文件打开 :    " + name)
    }
    catch{
      case ex: FileNotFoundException =>{
        println("异常错误: Missing file exception:   " + name)
      }
      case ex: IOException => {
        println("异常错误: IO Exception")
      }
    }

    outputStream
  }

  def testCheckPath(name:String, obj:Object=null) = {
    //获取当前jar 的执行路径
    //    ApplicationHome home = new ApplicationHome(getClass());
    //    File jarFile = home.getSource();
    //    String parent = jarFile.getParent();
    //    System.out.println(parent);


    /*
    val filePath =System.getProperty("user.dir")//设定为jar包的绝对路径 在IDE中运行时为project的绝对路径
    val postgprop = new Properties
    val inputStream = new BufferedInputStream(new FileInputStream(filePath+"/conf/config.properties"))
    postgprop.load(inputStream)
    */
    var theobj = if (obj==null) this else obj

    println(theobj)

    println("===================")
    println(theobj.getClass.getProtectionDomain.getCodeSource.getLocation)
    println(theobj.getClass.getProtectionDomain.getCodeSource.getLocation.getPath)
    println(theobj.getClass.getProtectionDomain.getCodeSource.getLocation.getFile)
    println("===================")

    var workPath:String  =System.getProperty("user.dir")
    var rootPath:String = theobj.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    rootPath = rootPath.substring(0,rootPath.lastIndexOf("/"))
    // configPath = java.net.URLDecoder.decode(configPath,"utf-8");

    println("JAR包根目录，rootPath:      ",rootPath)
    println("工作目录，   workPath:      ",workPath)
    println("===================")

    var file1 = rootPath + File.separator +  name.stripPrefix("/").stripPrefix("\\")
    var file2 =workPath + File.separator + name.stripPrefix("/").stripPrefix("\\")

    println("尝试文件 1:      ",file1)
    println("尝试文件 2:      ",file2)
    println("===================")
  }


  def getOutputSteamByFile(name:String, obj:Object=null): OutputStream = {
    // 用文件方式读取资源 OutputStream
    var theobj = if (obj==null) this else obj
    testCheckPath(name,obj)
    var workPath:String  =System.getProperty("user.dir")
    var rootPath:String = theobj.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    rootPath=rootPath.substring(0,rootPath.lastIndexOf("/"))

    var outputStream:OutputStream = null

    outputStream = getOutputSteamByFilePath(rootPath + File.separator +  name.stripPrefix("/").stripPrefix("\\"))

    if ( outputStream == null) {
      outputStream = getOutputSteamByFilePath(workPath + File.separator + name.stripPrefix("/").stripPrefix("\\"))
    }
    outputStream
  }

  def getInputSteamByFile(name:String, obj:Object=null): InputStream = {
    // 用文件方式读取资源 InputStream
    var theobj = if (obj==null) this else obj
    testCheckPath(name,obj)
    var workPath:String  =System.getProperty("user.dir")
    var rootPath:String = theobj.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    rootPath=rootPath.substring(0,rootPath.lastIndexOf("/"))

    var inputStream:InputStream = null

    inputStream = getInputSteamByFilePath(rootPath + File.separator +  name.stripPrefix("/").stripPrefix("\\"))

    if ( inputStream == null) {
      inputStream = getInputSteamByFilePath(workPath + File.separator + name.stripPrefix("/").stripPrefix("\\"))
    }

    inputStream
  }

  def getInputSteamByResource(name:String, obj:Object=null): InputStream ={
    // 用类包资源方式读取 inputStream
    /*
    val postgprop = new Properties()
    val inputStream: InputStream = this.getClass.getResourceAsStream("/sql/Const_mcht_incre/cjlog_tmp.sql1")
    postgprop.load(inputStream)
    */

    var theobj = if (obj==null) this else obj

    val inputStream: InputStream = theobj.getClass.getResourceAsStream(name)

    if ( inputStream !=null )
      println("从类资源文件中读取: Read Class Resource:   "+inputStream.toString)
    inputStream
  }


  def getString(fileName:String, obj:Object=null):String = {
    var inputStream: InputStream = getInputSteamByFile(fileName, obj)
    // 1、用文件方式读取

    // 2、如果没有读取到，用类包资源方式读取
    if ( inputStream == null ) {
      inputStream = getInputSteamByResource(fileName, obj)
    }

    if ( inputStream == null ) {
      return null
    }

    var reader = new InputStreamReader(inputStream, "UTF-8")
    var br = new BufferedReader(reader)

    var lines,line=""

    line= br.readLine
    while (line != null) {
      lines += ( line + "\n" )
      line= br.readLine
    }

    br.close()

    println( "========读取到文件 BEGING======" )
    println( lines )
    println( "========读取到文件 END======" )

    return lines
  }

  def clearFile(fileName:String, obj:Object=null):Boolean = {
    var outputStream: OutputStream = getOutputSteamByFile(fileName, obj)
    // 1、用文件方式读取
    if ( outputStream == null ) {
      return false
    }
    var swriter = new OutputStreamWriter(outputStream, "UTF-8")
    var bwriter = new BufferedWriter(swriter)
    bwriter.write("")
    bwriter.close
    return true
  }

  def getStringList(fileName:String, obj:Object=null):Array[String] = {

    var res: List[String]=List()
    var lines:String=getString(fileName, obj)
//    println(lines)
    if ( lines==null )
      return res.toArray

    var s1: Array[String]=lines.split("\n")


    s1.foreach( x =>
      if ( x.length > 0 ) {
        res = res :+ x
      }
    )

    res.toArray
  }

  def getHiveStringList(fileName:String, obj:Object=null):Array[String] = {

    var res: List[String]=List()
    var lines:String=getString(fileName, obj)
    //    println(lines)
    if ( lines==null )
      return res.toArray

    var s1: Array[String]=lines.split("\n")


    s1.foreach( x =>
      if ( x.length > 5 && ! x.trim.startsWith("--") ) {
        //        println("sds:  "+ x ) ;
        var s3=x.trim

        if ( s3.indexOf(" --") > 0 )
          s3 = s3.substring(0, s3.indexOf(" --"))
        else
          s3 = s3.substring(0, s3.length)

        res = res :+ s3.trim.stripSuffix(";").trim
      }
    )

    res.toArray
  }

}
