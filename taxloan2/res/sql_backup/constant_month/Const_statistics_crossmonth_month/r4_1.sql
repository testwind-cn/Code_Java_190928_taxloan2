select
    t1.sellertaxno,
    t1.data_month,
    ----在统计月归为A/B/C/F类的交易对手近6月开票张数
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_a_invoice_cnt_l6m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_b_invoice_cnt_l6m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_c_invoice_cnt_l6m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_f_invoice_cnt_l6m,
    --在统计月归为A/B/C/F类的交易对手近6月合计金额总和
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_a_amt_sum_l6m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_b_amt_sum_l6m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_c_amt_sum_l6m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_f_amt_sum_l6m,

    ----在统计月归为A/B/C/F类的交易对手近12月开票张数
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_a_invoice_cnt_l12m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_b_invoice_cnt_l12m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_c_invoice_cnt_l12m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_f_invoice_cnt_l12m,
    --在统计月归为A/B/C/F类的交易对手近12月合计金额总和
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_a_amt_sum_l12m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_b_amt_sum_l12m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_c_amt_sum_l12m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_f_amt_sum_l12m,

    ----在统计月归为A/B/C/F类的交易对手近24月开票张数
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_a_invoice_cnt_l24m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_b_invoice_cnt_l24m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_c_invoice_cnt_l24m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_f_invoice_cnt_l24m,
    --在统计月归为A/B/C/F类的交易对手近24月合计金额总和
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_a_amt_sum_l24m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_b_amt_sum_l24m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_c_amt_sum_l24m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_f_amt_sum_l24m

from
    (
        select
            mcht_cd as sellertaxno,
            data_month,
            buyer_type,
            buyer_invoice_cnt_l6m,
            buyer_invoice_total_sum_l6m,
            buyer_invoice_cnt_l12m,
            buyer_invoice_total_sum_l12m,
            buyer_invoice_cnt_l24m,
            buyer_invoice_total_sum_l24m

        from ${Const_Common.DATABASE}.counterparty_classify
    )t1 group by t1.sellertaxno,t1.data_month