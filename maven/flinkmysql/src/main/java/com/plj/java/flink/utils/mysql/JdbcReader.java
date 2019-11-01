package com.plj.java.flink.utils.mysql;


import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.plj.java.flink.utils.config.ConfigKeys;

/**
 * @Description mysql source
 * @Author jiangxiaozhi
 * @Date 2018/10/15 17:05
 **/
public class JdbcReader extends RichSourceFunction<Tuple2<String,String>> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcReader.class);

    private Connection connection = null;
    private PreparedStatement ps = null;

    //该方法主要用于打开数据库连接，下面的ConfigKeys类是获取配置的类
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName(ConfigKeys.DRIVER_CLASS());//加载数据库驱动
        connection = DriverManager.getConnection(ConfigKeys.SOURCE_DRIVER_URL(),ConfigKeys.SOURCE_USER(), ConfigKeys.SOURCE_PASSWORD());//获取连接
        ps = connection.prepareStatement(ConfigKeys.SOURCE_SQL());
    }

    /*
    //该方法主要用于打开数据库连接，下面的ConfigKeys类是获取配置的类
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        flinkConstants fc = flinkConstants.getInstance();
        DriverManager.registerDriver(new Driver());
        String db_url = "jdbc:mysql://" + fc.JDBC_HOST + ":" + fc.JDBC_PORT + "/" + fc.JDBC_DATABASE;
        connection = DriverManager.getConnection(db_url, fc.JDBC_USERNAME, fc.JDBC_PASSWORD);//获取连接
        ps = connection.prepareStatement("select machID, gateMac from dac_machinestatus where operationFlag=9");
    }
    */

    //执行查询并获取结果
    @Override
    public void run(SourceContext<Tuple2<String, String>> ctx) throws Exception {
        try {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("nick");
                String id = resultSet.getString("user_id");
                logger.error("readJDBC name:{}", name);
                Tuple2<String,String> tuple2 = new Tuple2<>();
                tuple2.setFields(id,name);
                ctx.collect(tuple2);//发送结果，结果是tuple2类型，2表示两个元素，可根据实际情况选择
            }
        } catch (Exception e) {
            logger.error("runException:{}", e);
        }

    }

    //关闭数据库连接
    @Override
    public void cancel() {
        try {
            super.close();
            if (connection != null) {
                connection.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            logger.error("runException:{}", e);
        }
    }
}