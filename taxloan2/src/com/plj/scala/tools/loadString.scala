package com.plj.scala.tools

import java.io.{BufferedInputStream, BufferedReader, File, FileInputStream, FileNotFoundException, IOException, InputStream, InputStreamReader}
import java.util.Properties

/*
1、优先读 jar 所在目录，或 CLASS根目录
2、再读命令行运行目录
3、再读JAR里面 res 根目录下的
*/

object loadString {

  def readFileByName(name:String): InputStream = {
    var inputStream:InputStream = null

    try {
      inputStream = new BufferedInputStream(new FileInputStream(name))
      println("Read file :    " + name)
    } catch{

      case ex: FileNotFoundException =>{
        println("Missing file exception:   " + name)
      }
      case ex: IOException => {
        println("IO Exception")
      }
    }

    inputStream
  }

  def getFileStream(name:String): InputStream = {




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

    println("===================")
    println(this.getClass.getProtectionDomain.getCodeSource.getLocation)
    println(this.getClass.getProtectionDomain.getCodeSource.getLocation.getPath)
    println(this.getClass.getProtectionDomain.getCodeSource.getLocation.getFile)
    println("===================")


    var rootPath:String = this.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    rootPath = rootPath.substring(0,rootPath.lastIndexOf("/"))
    // configPath = java.net.URLDecoder.decode(configPath,"utf-8");
    val workPath =System.getProperty("user.dir")

    printf("rootPath:      %s\nworkPath:      %s \n===================\n",rootPath,workPath)



    var inputStream:InputStream = null

    inputStream = readFileByName(rootPath + File.separator +  name.stripPrefix("/").stripPrefix("\\"))

    if ( inputStream == null) {
      inputStream = readFileByName(workPath + File.separator + name.stripPrefix("/").stripPrefix("\\"))
    }

    inputStream
  }

  def getResourceStream(name:String): InputStream ={
    /*
    val postgprop = new Properties()
    val inputStream: InputStream = this.getClass.getResourceAsStream("/sql/Const_mcht_incre/cjlog_tmp.sql1")
    postgprop.load(inputStream)
    */

    val inputStream: InputStream = this.getClass.getResourceAsStream(name)

    if ( inputStream !=null )
      println("Read Class Resource:   "+inputStream.toString)
    inputStream
  }


  def getString(fileName:String):String = {
    var inputStream: InputStream = getFileStream(fileName)
    if ( inputStream == null ) {
      inputStream = getResourceStream(fileName)
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

  def getStringList(fileName:String):Array[String] = {

    var lines:String=getString(fileName)
//    println(lines)
    var s1: Array[String]=lines.split("\n")
    var s2: List[String]=List()

    s1.foreach( x =>
      if ( x.length > 5 && ! x.trim.startsWith("--") ) {
//        println("sds:  "+ x ) ;
        var s3=x.trim

        if ( s3.indexOf(" --") > 0 )
          s3 = s3.substring(0, s3.indexOf(" --"))
        else
          s3 = s3.substring(0, s3.length)

        s2 = s2 :+ s3.trim.stripSuffix(";").trim
      }
    )

    s2.toArray
  }

}
