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

--	sum(CASE WHEN totalamount<0 AND totaltax<0 THEN 1 ELSE 0 END)/count(*) AS invoice_red_cnt_rate, --红冲发票张数占比
--	sum(CASE WHEN cancelflag='1' THEN 1 ELSE 0 END)/ count(*) AS invoice_cancel_cnt_rate,  --作废发票张数占比
--	sum(CASE WHEN totalamount<0 AND totaltax<0 THEN totalamount+totaltax ELSE 0 END)/sum(totalamount+totaltax) AS invoice_red_total_sum_rate, --红冲发票合计金额税额总和比率
--	sum(CASE WHEN cancelflag='1' THEN totalamount+totaltax ELSE 0 END)/sum(totalamount+totaltax) AS invoice_cancel_total_sum_rate, --作废发票合计金额税金总和比率

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
    (SELECT
         data_month,
         sellertaxno,
         buyertaxno,
         invoicetype,
         cancelflag,
         totalamount,
         jztype,
         totaltax
     FROM
         ${Const_Common.DATABASE}.saleinvoice_tmp)t1 GROUP BY sellertaxno,data_month