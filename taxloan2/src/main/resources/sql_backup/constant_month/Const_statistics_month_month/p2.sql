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