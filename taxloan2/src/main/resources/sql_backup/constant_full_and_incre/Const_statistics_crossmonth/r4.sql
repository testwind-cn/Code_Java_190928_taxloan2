




select
    t1.sellertaxno,
    t1.data_month,
    -- 在统计月归为A/B/C/F类的交易对手近6月开票张数
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_a_invoice_cnt_l6m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_b_invoice_cnt_l6m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_c_invoice_cnt_l6m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_cnt_l6m ELSE 0 END) AS buyer_f_invoice_cnt_l6m,
    -- 在统计月归为A/B/C/F类的交易对手近6月合计金额总和
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_a_amt_sum_l6m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_b_amt_sum_l6m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_c_amt_sum_l6m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_total_sum_l6m ELSE 0 END) AS buyer_f_amt_sum_l6m,

    -- 在统计月归为A/B/C/F类的交易对手近12月开票张数
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_a_invoice_cnt_l12m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_b_invoice_cnt_l12m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_c_invoice_cnt_l12m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_cnt_l12m ELSE 0 END) AS buyer_f_invoice_cnt_l12m,
    -- 在统计月归为A/B/C/F类的交易对手近12月合计金额总和
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_a_amt_sum_l12m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_b_amt_sum_l12m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_c_amt_sum_l12m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_total_sum_l12m ELSE 0 END) AS buyer_f_amt_sum_l12m,

    -- 在统计月归为A/B/C/F类的交易对手近24月开票张数
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_a_invoice_cnt_l24m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_b_invoice_cnt_l24m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_c_invoice_cnt_l24m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_cnt_l24m ELSE 0 END) AS buyer_f_invoice_cnt_l24m,
    -- 在统计月归为A/B/C/F类的交易对手近24月合计金额总和
    sum(CASE WHEN t1.buyer_type='A' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_a_amt_sum_l24m,
    sum(CASE WHEN t1.buyer_type='B' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_b_amt_sum_l24m,
    sum(CASE WHEN t1.buyer_type='C' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_c_amt_sum_l24m,
    sum(CASE WHEN t1.buyer_type='F' THEN buyer_invoice_total_sum_l24m ELSE 0 END) AS buyer_f_amt_sum_l24m

from
(
    select
        c3.mcht_cd as sellertaxno,
        c3.data_month,
        c3.buyer_type,
        c3.buyer_invoice_cnt_l6m,
        c3.buyer_invoice_total_sum_l6m,
        c3.buyer_invoice_cnt_l12m,
        c3.buyer_invoice_total_sum_l12m,
        c3.buyer_invoice_cnt_l24m,
        c3.buyer_invoice_total_sum_l24m
    from (
        select
            c1.mcht_cd,
            c1.data_month,
            c1.buyer_type,
            c1.buyer_invoice_cnt_l6m,
            c1.buyer_invoice_total_sum_l6m,
            c1.buyer_invoice_cnt_l12m,
            c1.buyer_invoice_total_sum_l12m,
            c1.buyer_invoice_cnt_l24m,
            c1.buyer_invoice_total_sum_l24m,
            c2.taxno

        from dm_taxloan.counterparty_classify c1
        left join
        ( select distinct taxno from dm_taxloan.cjlog_tmp) c2  --j oin cjlog_tmp表，找出需要统计的税号
        on c1.mcht_cd=c2.taxno
    ) c3
    where c3.taxno is not null
) t1
group by t1.sellertaxno,t1.data_month

