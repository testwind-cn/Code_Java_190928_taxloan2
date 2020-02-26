

with t2 as (

    SELECT
        sellertaxno AS mcht_cd,
        data_month,
        sum(CASE WHEN invoicetype in ('s','c') THEN 1	ELSE 0	END) AS invoice_sc_cnt,
        sum(CASE WHEN invoicetype in ('s','c') AND cancelflag='1' THEN 1	ELSE 0	END) AS invoice_sc_cancel_cnt,
        sum(CASE WHEN invoicetype in ('s','c') AND totalamount<0 AND totaltax<0 THEN 1	ELSE 0	END) AS invoice_sc_red_cnt,
        sum(CASE WHEN invoicetype in ('s','c') AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount+totaltax ELSE 0 END) AS invoice_sc_total_sum,
        COUNT(*) AS invoice_cnt,
        sum(CASE WHEN (totalamount<0 AND totaltax<0) OR cancelflag='1' THEN 1 ELSE 0 END) AS invoice_cr_cnt,
        sum(CASE WHEN totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount ELSE 0 END) AS invoice_amt_sum,
        sum(CASE WHEN totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totaltax ELSE 0 END) AS invoice_tax_sum,
        sum(CASE WHEN totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount+totaltax ELSE 0 END) AS invoice_total_sum,
        --计算新增加的4个统计指标
        sum(CASE WHEN cancelflag='1' THEN 1 ELSE 0 END) AS invoice_cancel_cnt,
        sum(CASE WHEN totalamount<0 OR totaltax<0 THEN 1 ELSE 0 END) AS invoice_red_cnt,
        sum(CASE WHEN cancelflag='1' THEN totalamount+totaltax ELSE 0 END) AS invoice_total_c_sum,
        sum(CASE WHEN totalamount<0 OR totaltax<0 THEN totalamount+totaltax ELSE 0 END) AS invoice_total_r_sum,

        --发票种类为s
        sum(CASE WHEN invoicetype='s' THEN 1 ELSE 0	END) AS invoice_s_cnt,
        sum(CASE WHEN invoicetype='s'AND cancelflag='1' THEN 1 ELSE 0	END) AS invoice_s_cancel_cnt,
        sum(CASE WHEN invoicetype='s'AND totalamount<0 AND totaltax<0 THEN 1 ELSE 0	END) AS invoice_s_red_cnt,
        sum(CASE WHEN invoicetype='s'AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount ELSE 0 END) AS invoice_s_amt_sum,
        sum(CASE WHEN invoicetype='s'AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totaltax ELSE 0 END) AS invoice_s_tax_sum,
        sum(CASE WHEN invoicetype='s'AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount+totaltax ELSE 0 END) AS invoice_s_total_sum,
        --发票种类为c
        sum(CASE WHEN invoicetype='c' THEN 1 ELSE 0	END) AS invoice_c_cnt,
        sum(CASE WHEN invoicetype='c'AND cancelflag='1' THEN 1 ELSE 0	END) AS invoice_c_cancel_cnt,
        sum(CASE WHEN invoicetype='c'AND totalamount<0 AND totaltax<0 THEN 1 ELSE 0	END) AS invoice_c_red_cnt,
        sum(CASE WHEN invoicetype='c'AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount ELSE 0 END) AS invoice_c_amt_sum,
        sum(CASE WHEN invoicetype='c'AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totaltax ELSE 0 END) AS invoice_c_tax_sum,
        sum(CASE WHEN invoicetype='c'AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount+totaltax ELSE 0 END) AS invoice_c_total_sum,
        --发票种类为j
        sum(CASE WHEN invoicetype='j' THEN 1 ELSE 0	END) AS invoice_t_cnt,
        sum(CASE WHEN invoicetype='j' AND cancelflag='1' THEN 1 ELSE 0	END) AS invoice_t_cancel_cnt,
        sum(CASE WHEN invoicetype='j' AND totalamount<0 AND totaltax<0 THEN 1 ELSE 0	END) AS invoice_t_red_cnt,
        sum(CASE WHEN invoicetype='j' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount ELSE 0 END) AS invoice_t_amt_sum,
        sum(CASE WHEN invoicetype='j' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totaltax ELSE 0 END) AS invoice_t_tax_sum,
        sum(CASE WHEN invoicetype='j' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount+totaltax ELSE 0 END) AS invoice_t_total_sum,
        --纸质发票
        sum(CASE WHEN jztype='纸质发票' THEN 1 ELSE 0	END) AS invoice_p_cnt,
        sum(CASE WHEN jztype='纸质发票' AND cancelflag='1' THEN 1 ELSE 0	END) AS invoice_p_cancel_cnt,
        sum(CASE WHEN jztype='纸质发票' AND totalamount<0 AND totaltax<0 THEN 1 ELSE 0	END) AS invoice_p_red_cnt,
        sum(CASE WHEN jztype='纸质发票' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount ELSE 0 END) AS invoice_p_amt_sum,
        sum(CASE WHEN jztype='纸质发票' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totaltax ELSE 0 END) AS invoice_p_tax_sum,
        sum(CASE WHEN jztype='纸质发票' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount+totaltax ELSE 0 END) AS invoice_p_total_sum,
        --电子发票
        sum(CASE WHEN jztype='电子发票' THEN 1 ELSE 0	END) AS invoice_e_cnt,
        sum(CASE WHEN jztype='电子发票' AND cancelflag='1' THEN 1 ELSE 0	END) AS invoice_e_cancel_cnt,
        sum(CASE WHEN jztype='电子发票' AND totalamount<0 AND totaltax<0 THEN 1 ELSE 0	END) AS invoice_e_red_cnt,
        sum(CASE WHEN jztype='电子发票' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount ELSE 0 END) AS invoice_e_amt_sum,
        sum(CASE WHEN jztype='电子发票' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totaltax ELSE 0 END) AS invoice_e_tax_sum,
        sum(CASE WHEN jztype='电子发票' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount+totaltax ELSE 0 END) AS invoice_e_total_sum,
        --卷式发票
        sum(CASE WHEN jztype='卷式发票' THEN 1 ELSE 0	END) AS invoice_r_cnt,
        sum(CASE WHEN jztype='卷式发票' AND cancelflag='1' THEN 1 ELSE 0	END) AS invoice_r_cancel_cnt,
        sum(CASE WHEN jztype='卷式发票' AND totalamount<0 AND totaltax<0 THEN 1 ELSE 0	END) AS invoice_r_red_cnt,
        sum(CASE WHEN jztype='卷式发票' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount ELSE 0 END) AS invoice_r_amt_sum,
        sum(CASE WHEN jztype='卷式发票' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totaltax ELSE 0 END) AS invoice_r_tax_sum,
        sum(CASE WHEN jztype='卷式发票' AND totalamount>0 AND totaltax>=0 AND cancelflag='0' THEN totalamount+totaltax ELSE 0 END) AS invoice_r_total_sum,

        sum(CASE WHEN totalamount<0 AND totaltax<0 THEN 1 ELSE 0 END)/count(*) AS invoice_red_cnt_rate, --红冲发票张数占比
        sum(CASE WHEN cancelflag='1' THEN 1 ELSE 0 END)/ count(*) AS invoice_cancel_cnt_rate,  --作废发票张数占比
        sum(CASE WHEN totalamount<0 AND totaltax<0 THEN totalamount+totaltax ELSE 0 END)/sum(totalamount+totaltax) AS invoice_red_total_sum_rate, --红冲发票合计金额税额总和比率
        sum(CASE WHEN cancelflag='1' THEN totalamount+totaltax ELSE 0 END)/sum(totalamount+totaltax) AS invoice_cancel_total_sum_rate, --作废发票合计金额税金总和比率

        sum(CASE WHEN totalamount<100 THEN 1 ELSE 0	END) AS invoice_lt100_cnt, --金额小于100发票张数
        sum(CASE WHEN (totalamount<100 AND cancelflag='1') OR totalamount<0 THEN 1 ELSE 0	END) AS invoice_lt100_cr_cnt, --金额小于100发票作废红冲张数
        sum(CASE WHEN totalamount<100 AND totalamount>0 AND cancelflag='0' THEN totalamount+totaltax ELSE 0	END) AS invoice_lt100_total_sum, --金额小于100发票合计金额税额总和

        --金额小于1000（且大于等于100）
        sum(CASE WHEN totalamount<1000 AND totalamount>=100 THEN 1 ELSE 0	END) AS invoice_lt1000_cnt, --金金额小于1000（且大于等于100）发票张数
        sum(CASE WHEN (totalamount<1000 AND totalamount>=100 AND cancelflag='1') OR totalamount<0 THEN 1 ELSE 0	END) AS invoice_lt1000_cr_cnt, --金额小于1000（且大于等于100）发票作废红冲张数
        sum(CASE WHEN totalamount<1000 AND totalamount>=100 AND cancelflag='0' THEN totalamount+totaltax ELSE 0	END) AS invoice_lt1000_total_sum, --金额小于1000（且大于等于100）发票合计金额税额总和

        --金额小于2500（且大于等于1000）
        sum(CASE WHEN totalamount<2500 AND totalamount>=1000 THEN 1 ELSE 0	END) AS invoice_lt2500_cnt, --金额小于2500（且大于等于1000）发票张数
        sum(CASE WHEN (totalamount<2500 AND totalamount>=1000 AND cancelflag='1') OR totalamount<0 THEN 1 ELSE 0	END) AS invoice_lt2500_cr_cnt, --金额小于2500（且大于等于1000）发票作废红冲张数
        sum(CASE WHEN totalamount<2500 AND totalamount>=1000 AND cancelflag='0' THEN totalamount+totaltax ELSE 0	END) AS invoice_lt2500_total_sum, --金额小于2500（且大于等于1000）发票合计金额税额总和

        --金额小于5000（且大于等于2500）
        sum(CASE WHEN totalamount<5000 AND totalamount>=2500 THEN 1 ELSE 0	END) AS invoice_lt5000_cnt, --金额小于5000（且大于等于2500）发票张数
        sum(CASE WHEN (totalamount<5000 AND totalamount>=2500 AND cancelflag='1') OR totalamount<0 THEN 1 ELSE 0	END) AS invoice_lt5000_cr_cnt, --金额小于5000（且大于等于2500）发票作废红冲张数
        sum(CASE WHEN totalamount<5000 AND totalamount>=2500 AND cancelflag='0' THEN totalamount+totaltax ELSE 0	END) AS invoice_lt5000_total_sum, --金额小于5000（且大于等于2500）发票合计金额税额总和

        --金额小于10000（且大于等于5000）
        sum(CASE WHEN totalamount<10000 AND totalamount>=5000 THEN 1 ELSE 0	END) AS invoice_lt10000_cnt, --金额小于10000（且大于等于5000）发票张数
        sum(CASE WHEN (totalamount<10000 AND totalamount>=5000 AND cancelflag='1') OR totalamount<0 THEN 1 ELSE 0	END) AS invoice_lt10000_cr_cnt, --金额小于10000（且大于等于5000）发票作废红冲张数
        sum(CASE WHEN totalamount<10000 AND totalamount>=5000 AND cancelflag='0' THEN totalamount+totaltax ELSE 0	END) AS invoice_lt10000_total_sum, --金额小于10000（且大于等于5000）发票合计金额税额总和

        --金额大于10000发票
        sum(CASE WHEN totalamount>10000  THEN 1 ELSE 0	END) AS invoice_gt10000_cnt, --金额大于10000发票张数
        sum(CASE WHEN (totalamount>10000 AND cancelflag='1') OR totalamount<0 THEN 1 ELSE 0	END) AS invoice_gt10000_cr_cnt, --金额大于10000发票作废红冲张数
        sum(CASE WHEN totalamount>10000 AND cancelflag='0' THEN totalamount+totaltax ELSE 0	END) AS invoice_gt10000_total_sum --金额大于10000发票合计金额税额总和
    FROM
    (
        SELECT
             data_month,
             sellertaxno,
             buyertaxno,
             invoicetype,
             cancelflag,
             totalamount,
             jztype,
             totaltax
         FROM
             ${hivevar:DATABASE_DEST}.saleinvoice_tmp
    )t1 GROUP BY sellertaxno,data_month

),
     
     
t3 as (
    select
        sellertaxno,
        data_month,
        count(distinct buyertaxno) as buyer_cnt
    from ${hivevar:DATABASE_DEST}.saleinvoice_tmp
    group by sellertaxno,data_month
),


t4 as (
    select
        s3.sellertaxno,
        s3.data_month,
        sum(s3.total1) over (partition by s3.sellertaxno order by to_date(from_unixtime(unix_timestamp(s3.data_month,'yyyyMM')))) as total2
    from
        (
            select
                s2.sellertaxno,
                s2.data_month,
                sum(case when rank=1 then 1 else 0 end) as total1
            from
                (
                    select
                        s1.sellertaxno,
                        s1.data_month,
                        s1.buyertaxno,
                        row_number() over(partition by s1.buyertaxno,s1.buyertaxno order by to_date(from_unixtime(unix_timestamp(s1.data_month,'yyyyMM'))) ) as rank
                    from
                        (
                            select
                                distinct sellertaxno,
                                         data_month,
                                         buyertaxno
                            from ${hivevar:DATABASE_DEST}.saleinvoice_tmp
                        )s1
                )s2 group by s2.sellertaxno,s2.data_month
        )s3
),
     
t5 as (
    select
        a5.mcht_cd,
        a5.data_month,
        sum(CASE WHEN a5.buyer_type='A' THEN 1 ELSE 0 END) AS buyer_a_cnt,
        sum(CASE WHEN a5.buyer_type='B' THEN 1 ELSE 0 END) AS buyer_b_cnt,
        sum(CASE WHEN a5.buyer_type='C' THEN 1 ELSE 0 END) AS buyer_c_cnt,
        sum(CASE WHEN a5.buyer_type='F' THEN 1 ELSE 0 END) AS buyer_f_cnt
    from ${hivevar:DATABASE_DEST}.counterparty_classify a5
             left semi join  ${hivevar:DATABASE_DEST}.cjlog_tmp c1 on (a5.mcht_cd=c1.taxno)
    group by a5.mcht_cd,a5.data_month
),
     
     
t6 as (
    select
        c1.sellertaxno,
        c1.data_month,
        sum(case when c2.detaillistflag=1 then 1 else 0 end) as invoice_detail_cnt,
        sum(CASE WHEN c2.detaillistflag=1 AND ((c1.totalamount<0 AND c1.totaltax<0) OR c1.cancelflag='1') THEN 1 ELSE 0 END) AS invoice_detail_cr_cnt,
        sum(CASE WHEN c2.detaillistflag=1 AND c1.totalamount>0 AND c1.totaltax>=0 AND c1.cancelflag='0' THEN c1.totalamount+c1.totaltax ELSE 0 END) AS invoice_detail_total_sum
    from
        ${hivevar:DATABASE_DEST}.saleinvoice_tmp c1
            left join
        ${hivevar:DATABASE_SRC}.saleinvoicedetail c2
        on c1.invoiceid=c2.invoiceid
    group by c1.sellertaxno,c1.data_month
),


cte_p2 as (
    select
        t5.mcht_cd,
        t5.data_month,
        t2.invoice_sc_cnt,
        t2.invoice_sc_cancel_cnt,
        t2.invoice_sc_red_cnt,
        t2.invoice_sc_total_sum,
        t2.invoice_cnt,
        t2.invoice_cr_cnt,
        t2.invoice_amt_sum,
        t2.invoice_tax_sum,
        t2.invoice_total_sum,
        --以下为新增加的4个字段
        t2.invoice_cancel_cnt,
        t2.invoice_red_cnt,
        t2.invoice_total_c_sum,
        t2.invoice_total_r_sum,

        t2.invoice_s_cnt,
        t2.invoice_s_cancel_cnt,
        t2.invoice_s_red_cnt,
        t2.invoice_s_amt_sum,
        t2.invoice_s_tax_sum,
        t2.invoice_s_total_sum,
        t2.invoice_c_cnt,
        t2.invoice_c_cancel_cnt,
        t2.invoice_c_red_cnt,
        t2.invoice_c_amt_sum,
        t2.invoice_c_tax_sum,
        t2.invoice_c_total_sum,
        t2.invoice_t_cnt,
        t2.invoice_t_cancel_cnt,
        t2.invoice_t_red_cnt,
        t2.invoice_t_amt_sum,
        t2.invoice_t_tax_sum,
        t2.invoice_t_total_sum,
        t2.invoice_p_cnt,
        t2.invoice_p_cancel_cnt,
        t2.invoice_p_red_cnt,
        t2.invoice_p_amt_sum,
        t2.invoice_p_tax_sum,
        t2.invoice_p_total_sum,
        t2.invoice_e_cnt,
        t2.invoice_e_cancel_cnt,
        t2.invoice_e_red_cnt,
        t2.invoice_e_amt_sum,
        t2.invoice_e_tax_sum,
        t2.invoice_e_total_sum,
        t2.invoice_r_cnt,
        t2.invoice_r_cancel_cnt,
        t2.invoice_r_red_cnt,
        t2.invoice_r_amt_sum,
        t2.invoice_r_tax_sum,
        t2.invoice_r_total_sum,

        (CASE WHEN t2.invoice_red_cnt=0 AND t2.invoice_cnt=0 THEN -9997
              WHEN t2.invoice_red_cnt!=0 AND t2.invoice_cnt=0 THEN -9996
              ELSE t2.invoice_red_cnt/t2.invoice_cnt END) AS invoice_red_cnt_rate, --红冲发票张数占比
        (CASE WHEN t2.invoice_cancel_cnt=0 AND t2.invoice_cnt=0 THEN -9997
              WHEN t2.invoice_cancel_cnt!=0 AND t2.invoice_cnt=0 THEN -9996
              ELSE t2.invoice_cancel_cnt/t2.invoice_cnt END) AS invoice_cancel_cnt_rate, --作废发票张数占比
        (CASE WHEN t2.invoice_total_r_sum=0 AND t2.invoice_total_sum=0 THEN -9997
              WHEN t2.invoice_total_r_sum!=0 AND t2.invoice_total_sum=0 THEN -9996
              ELSE t2.invoice_total_r_sum/t2.invoice_total_sum END) AS invoice_red_total_sum_rate, --红冲发票合计金额税额总和比率
        (CASE WHEN t2.invoice_total_c_sum=0 AND t2.invoice_total_sum=0 THEN -9997
              WHEN t2.invoice_total_c_sum!=0 AND t2.invoice_total_sum=0 THEN -9996
              ELSE t2.invoice_total_c_sum/t2.invoice_total_sum END) AS invoice_cancel_total_sum_rate, --作废发票合计金额税金总和比率

        t2.invoice_lt100_cnt,
        t2.invoice_lt100_cr_cnt,
        t2.invoice_lt100_total_sum,
        t2.invoice_lt1000_cnt,
        t2.invoice_lt1000_cr_cnt,
        t2.invoice_lt1000_total_sum,
        t2.invoice_lt2500_cnt,
        t2.invoice_lt2500_cr_cnt,
        t2.invoice_lt2500_total_sum,
        t2.invoice_lt5000_cnt,
        t2.invoice_lt5000_cr_cnt,
        t2.invoice_lt5000_total_sum,
        t2.invoice_lt10000_cnt,
        t2.invoice_lt10000_cr_cnt,
        t2.invoice_lt10000_total_sum,
        t2.invoice_gt10000_cnt,
        t2.invoice_gt10000_cr_cnt,
        t2.invoice_gt10000_total_sum,
        t6.invoice_detail_cnt,
        t6.invoice_detail_cr_cnt,
        t6.invoice_detail_total_sum,

        t3.buyer_cnt,  --统计月交易对手数
        t4.total2 AS buyer_cnt_all, --截止统计月累计的交易对手数
        --在统计月归为A/B/C/F类的交易对手数目
        t5.buyer_a_cnt,
        t5.buyer_b_cnt,
        t5.buyer_c_cnt,
        t5.buyer_f_cnt
    from t5
             left join t2
                       on t2.mcht_cd=t5.mcht_cd and t2.data_month=t5.data_month
             left join t3
                       on t2.mcht_cd=t3.sellertaxno and t2.data_month=t3.data_month
             left join t4
                       on t3.sellertaxno=t4.sellertaxno and t3.data_month=t4.data_month
             left join t6
                       on t6.sellertaxno=t4.sellertaxno and t6.data_month=t4.data_month

)


insert into ${hivevar:DATABASE_DEST}.p2 select * from cte_p2