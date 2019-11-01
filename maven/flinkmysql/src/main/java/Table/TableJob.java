package Table;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

public class TableJob {

    public static void main(String[] args) throws Exception{
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        BatchTableEnvironment tableEnv = BatchTableEnvironment.create(env);

        //source,这里读取CSV文件，并转换为对应的Class
        DataSet<TopScorers> csvInput = env
                //.readCsvFile("E://flink-training-exercises//src//main//resource//2016_Chinese_Super_League_Top_Scorers.csv")
                .readCsvFile("D:\\git-workspace\\Code_Java\\Code_Java_190928_taxloan2\\maven\\flinkmysql\\src\\main\\resources\\score.csv")
                .ignoreFirstLine() .pojoType(TopScorers.class,"rank","player","country","club","total_score","total_score_home","total_score_visit","point_kick");

        //将DataSet转换为Table
        Table topScore = tableEnv.fromDataSet(csvInput);
        //将topScore注册为一个表
        tableEnv.registerTable("topScore",topScore);
        //查询球员所在的国家，以及这些国家的球员（内援和外援）的总进球数
        Table groupedByCountry = tableEnv.sqlQuery("select country,sum(total_score) as sum_total_score from topScore group by country order by 2 desc");
        //转换回dataset
        DataSet<Result> result = tableEnv.toDataSet(groupedByCountry,Result.class);

        //将dataset map成tuple输出
        result.map(new MapFunction<Result, Tuple2<String,Integer>>() {
            @Override
            public Tuple2<String, Integer> map(Result result) throws Exception {
                String country = result.country;
                int sum_total_score = result.sum_total_score;
                return Tuple2.of(country,sum_total_score);
            }
        }).print();


    //    env.execute();

    }

    /**
     * 源数据的映射类
     */
    public static class TopScorers {
        /**
         * 排名，球员，国籍，俱乐部，总进球，主场进球数，客场进球数，点球进球数
         */
        public int rank;
        public String player;
        public String country;
        public String club;
        public int total_score;
        public int total_score_home;
        public int total_score_visit;
        public int point_kick;

        public TopScorers() {
            super();
        }
    }

    /**
     * 统计结果对应的类
     */
    public static class Result {
        public String country;
        public int sum_total_score;

        public Result() {}
    }

}
