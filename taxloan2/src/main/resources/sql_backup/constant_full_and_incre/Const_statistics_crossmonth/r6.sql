





select
    a1.mcht_cd,
    a1.month,
    a1.buyer_a_invoice_cnt_l12m_chg_rate_6m,
    a1.buyer_a_amt_sum_l12m_chg_rate_6m,
    a1.buyer_a_invoice_cnt_l12m_chg_rate_12m,
    a1.buyer_a_amt_sum_l12m_chg_rate_12m,
    
    b1.buyer_b_invoice_cnt_l12m_chg_rate_6m,
    b1.buyer_b_amt_sum_l12m_chg_rate_6m,
    b1.buyer_b_invoice_cnt_l12m_chg_rate_12m,
    b1.buyer_b_amt_sum_l12m_chg_rate_12m,
    
    c1.buyer_c_invoice_cnt_l12m_chg_rate_6m,
    c1.buyer_c_amt_sum_l12m_chg_rate_6m,
    c1.buyer_c_invoice_cnt_l12m_chg_rate_12m,
    c1.buyer_c_amt_sum_l12m_chg_rate_12m,
    
    f1.buyer_f_invoice_cnt_l12m_chg_rate_6m,
    f1.buyer_f_amt_sum_l12m_chg_rate_6m,
    f1.buyer_f_invoice_cnt_l12m_chg_rate_12m,
    f1.buyer_f_amt_sum_l12m_chg_rate_12m

from (
    select
        p1.mcht_cd,
        p1.month,
        ( CASE
            WHEN p1.buyer_type='A' and (p1.buyer_invoice_cnt_l12m_all is null or p1.buyer_invoice_cnt_l712m_pre7 is null) THEN -9998
            WHEN p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all=0 AND p1.buyer_invoice_cnt_l712m_pre7=0 THEN -9997
            WHEN p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all!=0 AND p1.buyer_invoice_cnt_l712m_pre7=0 THEN -9996
            WHEN  p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all!=0 AND p1.buyer_invoice_cnt_l712m_pre7!=0
                THEN(p1.buyer_invoice_cnt_l12m_all / p1.buyer_invoice_cnt_l712m_pre7 -1)
            ELSE 0
        END ) AS buyer_a_invoice_cnt_l12m_chg_rate_6m,

        ( CASE
            WHEN p1.buyer_type='A' and (p1.buyer_invoice_total_sum_l12m_all is null or p1.buyer_invoice_total_sum_l712m_pre7 is null) THEN -9998
            WHEN p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all=0 AND p1.buyer_invoice_total_sum_l712m_pre7=0 THEN -9997
            WHEN p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all!=0 AND p1.buyer_invoice_total_sum_l712m_pre7=0 THEN -9996
            WHEN  p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all!=0 AND p1.buyer_invoice_total_sum_l712m_pre7!=0
                THEN (p1.buyer_invoice_total_sum_l12m_all / p1.buyer_invoice_total_sum_l712m_pre7 -1)
            ELSE 0
        END ) AS buyer_a_amt_sum_l12m_chg_rate_6m,

        ( CASE
            WHEN p1.buyer_type='A' and (p1.buyer_invoice_cnt_l12m_all is null or p1.buyer_invoice_cnt_l1324m_pre13 is null) THEN -9998
            WHEN p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all=0 AND p1.buyer_invoice_cnt_l1324m_pre13=0 THEN -9997
            WHEN p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all!=0 AND p1.buyer_invoice_cnt_l1324m_pre13=0 THEN -9996
            WHEN  p1.buyer_type='A' and p1.buyer_invoice_cnt_l12m_all!=0 AND p1.buyer_invoice_cnt_l1324m_pre13!=0
                THEN(p1.buyer_invoice_cnt_l12m_all / p1.buyer_invoice_cnt_l1324m_pre13 -1)
            ELSE 0
        END ) AS buyer_a_invoice_cnt_l12m_chg_rate_12m,

        ( CASE
            WHEN p1.buyer_type='A' and (p1.buyer_invoice_total_sum_l12m_all is null or p1.buyer_invoice_total_sum_l1324m_pre13 is null) THEN -9998
            WHEN p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all=0 AND p1.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9997
            WHEN p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all!=0 AND p1.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9996
            WHEN  p1.buyer_type='A' and p1.buyer_invoice_total_sum_l12m_all!=0 AND p1.buyer_invoice_total_sum_l1324m_pre13!=0
                THEN (p1.buyer_invoice_total_sum_l12m_all / p1.buyer_invoice_total_sum_l1324m_pre13 -1)
            ELSE 0
        END ) AS buyer_a_amt_sum_l12m_chg_rate_12m

    from r6_t3 p1
    where p1.buyer_type='A'
) a1
left join
(
    -- --B----
    select
        p2.mcht_cd,
        p2.month,
        ( CASE
            WHEN p2.buyer_type='B' and (p2.buyer_invoice_cnt_l12m_all is null or p2.buyer_invoice_cnt_l712m_pre7 is null) THEN -9998
            WHEN p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all=0 AND p2.buyer_invoice_cnt_l712m_pre7=0 THEN -9997
            WHEN p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all!=0 AND p2.buyer_invoice_cnt_l712m_pre7=0 THEN -9996
            WHEN  p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all!=0 AND p2.buyer_invoice_cnt_l712m_pre7!=0
                THEN(p2.buyer_invoice_cnt_l12m_all / p2.buyer_invoice_cnt_l712m_pre7 -1)
            ELSE 0
        END ) AS buyer_b_invoice_cnt_l12m_chg_rate_6m,

        ( CASE
            WHEN p2.buyer_type='B' and (p2.buyer_invoice_total_sum_l12m_all is null or p2.buyer_invoice_total_sum_l712m_pre7 is null) THEN -9998
            WHEN p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all=0 AND p2.buyer_invoice_total_sum_l712m_pre7=0 THEN -9997
            WHEN p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all!=0 AND p2.buyer_invoice_total_sum_l712m_pre7=0 THEN -9996
            WHEN  p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all!=0 AND p2.buyer_invoice_total_sum_l712m_pre7!=0
                THEN (p2.buyer_invoice_total_sum_l12m_all / p2.buyer_invoice_total_sum_l712m_pre7 -1)
            ELSE 0
        END ) AS buyer_b_amt_sum_l12m_chg_rate_6m,

        ( CASE
            WHEN p2.buyer_type='B' and (p2.buyer_invoice_cnt_l12m_all is null or p2.buyer_invoice_cnt_l1324m_pre13 is null) THEN -9998
            WHEN p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all=0 AND p2.buyer_invoice_cnt_l1324m_pre13=0 THEN -9997
            WHEN p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all!=0 AND p2.buyer_invoice_cnt_l1324m_pre13=0 THEN -9996
            WHEN  p2.buyer_type='B' and p2.buyer_invoice_cnt_l12m_all!=0 AND p2.buyer_invoice_cnt_l1324m_pre13!=0
                THEN(p2.buyer_invoice_cnt_l12m_all / p2.buyer_invoice_cnt_l1324m_pre13 -1)
            ELSE 0
        END ) AS buyer_b_invoice_cnt_l12m_chg_rate_12m,

        ( CASE
            WHEN p2.buyer_type='B' and (p2.buyer_invoice_total_sum_l12m_all is null or p2.buyer_invoice_total_sum_l1324m_pre13 is null) THEN -9998
            WHEN p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all=0 AND p2.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9997
            WHEN p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all!=0 AND p2.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9996
            WHEN  p2.buyer_type='B' and p2.buyer_invoice_total_sum_l12m_all!=0 AND p2.buyer_invoice_total_sum_l1324m_pre13!=0
                THEN (p2.buyer_invoice_total_sum_l12m_all / p2.buyer_invoice_total_sum_l1324m_pre13 -1)
            ELSE 0
        END ) AS buyer_b_amt_sum_l12m_chg_rate_12m

    from r6_t3 p2
    where p2.buyer_type='B'
) b1
on a1.mcht_cd=b1.mcht_cd and a1.month=b1.month
left join
(
-- --C--
    select
        p3.mcht_cd,
        p3.month,
        ( CASE
            WHEN p3.buyer_type='C' and (p3.buyer_invoice_cnt_l12m_all is null or p3.buyer_invoice_cnt_l712m_pre7 is null) THEN -9998
            WHEN p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all=0 AND p3.buyer_invoice_cnt_l712m_pre7=0 THEN -9997
            WHEN p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all!=0 AND p3.buyer_invoice_cnt_l712m_pre7=0 THEN -9996
            WHEN  p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all!=0 AND p3.buyer_invoice_cnt_l712m_pre7!=0
                THEN(p3.buyer_invoice_cnt_l12m_all / p3.buyer_invoice_cnt_l712m_pre7 -1)
            ELSE 0
        END ) AS buyer_c_invoice_cnt_l12m_chg_rate_6m,

        ( CASE
            WHEN p3.buyer_type='C' and (p3.buyer_invoice_total_sum_l12m_all is null or p3.buyer_invoice_total_sum_l712m_pre7 is null) THEN -9998
            WHEN p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all=0 AND p3.buyer_invoice_total_sum_l712m_pre7=0 THEN -9997
            WHEN p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all!=0 AND p3.buyer_invoice_total_sum_l712m_pre7=0 THEN -9996
            WHEN  p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all!=0 AND p3.buyer_invoice_total_sum_l712m_pre7!=0
                THEN (p3.buyer_invoice_total_sum_l12m_all / p3.buyer_invoice_total_sum_l712m_pre7 -1)
            ELSE 0
        END ) AS buyer_c_amt_sum_l12m_chg_rate_6m,

        ( CASE
            WHEN p3.buyer_type='C' and (p3.buyer_invoice_cnt_l12m_all is null or p3.buyer_invoice_cnt_l1324m_pre13 is null) THEN -9998
            WHEN p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all=0 AND p3.buyer_invoice_cnt_l1324m_pre13=0 THEN -9997
            WHEN p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all!=0 AND p3.buyer_invoice_cnt_l1324m_pre13=0 THEN -9996
            WHEN  p3.buyer_type='C' and p3.buyer_invoice_cnt_l12m_all!=0 AND p3.buyer_invoice_cnt_l1324m_pre13!=0
                THEN(p3.buyer_invoice_cnt_l12m_all / p3.buyer_invoice_cnt_l1324m_pre13 -1)
            ELSE 0
        END ) AS buyer_c_invoice_cnt_l12m_chg_rate_12m,

        ( CASE
            WHEN p3.buyer_type='C' and (p3.buyer_invoice_total_sum_l12m_all is null or p3.buyer_invoice_total_sum_l1324m_pre13 is null) THEN -9998
            WHEN p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all=0 AND p3.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9997
            WHEN p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all!=0 AND p3.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9996
            WHEN  p3.buyer_type='C' and p3.buyer_invoice_total_sum_l12m_all!=0 AND p3.buyer_invoice_total_sum_l1324m_pre13!=0
                THEN (p3.buyer_invoice_total_sum_l12m_all / p3.buyer_invoice_total_sum_l1324m_pre13 -1)
            ELSE 0
        END ) AS buyer_c_amt_sum_l12m_chg_rate_12m

    from r6_t3 p3
    where p3.buyer_type='C'
) c1
on
    b1.mcht_cd=c1.mcht_cd and b1.month=c1.month
left join
(
 -- -f---
    select
        p4.mcht_cd,
        p4.month,
        ( CASE
            WHEN p4.buyer_type='F' and (p4.buyer_invoice_cnt_l12m_all is null or p4.buyer_invoice_cnt_l712m_pre7 is null) THEN -9998
            WHEN p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all=0 AND p4.buyer_invoice_cnt_l712m_pre7=0 THEN -9997
            WHEN p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all!=0 AND p4.buyer_invoice_cnt_l712m_pre7=0 THEN -9996
            WHEN  p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all!=0 AND p4.buyer_invoice_cnt_l712m_pre7!=0
                THEN(p4.buyer_invoice_cnt_l12m_all / p4.buyer_invoice_cnt_l712m_pre7 -1)
            ELSE 0
        END ) AS buyer_f_invoice_cnt_l12m_chg_rate_6m,

        ( CASE
            WHEN p4.buyer_type='F' and (p4.buyer_invoice_total_sum_l12m_all is null or p4.buyer_invoice_total_sum_l712m_pre7 is null) THEN -9998
            WHEN p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all=0 AND p4.buyer_invoice_total_sum_l712m_pre7=0 THEN -9997
            WHEN p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all!=0 AND p4.buyer_invoice_total_sum_l712m_pre7=0 THEN -9996
            WHEN  p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all!=0 AND p4.buyer_invoice_total_sum_l712m_pre7!=0
                THEN (p4.buyer_invoice_total_sum_l12m_all / p4.buyer_invoice_total_sum_l712m_pre7 -1)
            ELSE 0
        END ) AS buyer_f_amt_sum_l12m_chg_rate_6m,

        ( CASE
            WHEN p4.buyer_type='F' and (p4.buyer_invoice_cnt_l12m_all is null or p4.buyer_invoice_cnt_l1324m_pre13 is null) THEN -9998
            WHEN p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all=0 AND p4.buyer_invoice_cnt_l1324m_pre13=0 THEN -9997
            WHEN p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all!=0 AND p4.buyer_invoice_cnt_l1324m_pre13=0 THEN -9996
            WHEN  p4.buyer_type='F' and p4.buyer_invoice_cnt_l12m_all!=0 AND p4.buyer_invoice_cnt_l1324m_pre13!=0
                THEN(p4.buyer_invoice_cnt_l12m_all / p4.buyer_invoice_cnt_l1324m_pre13 -1)
            ELSE 0
        END ) AS buyer_f_invoice_cnt_l12m_chg_rate_12m,

        (CASE
            WHEN p4.buyer_type='F' and (p4.buyer_invoice_total_sum_l12m_all is null or p4.buyer_invoice_total_sum_l1324m_pre13 is null) THEN -9998
            WHEN p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all=0 AND p4.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9997
            WHEN p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all!=0 AND p4.buyer_invoice_total_sum_l1324m_pre13=0 THEN -9996
            WHEN  p4.buyer_type='F' and p4.buyer_invoice_total_sum_l12m_all!=0 AND p4.buyer_invoice_total_sum_l1324m_pre13!=0
                THEN (p4.buyer_invoice_total_sum_l12m_all / p4.buyer_invoice_total_sum_l1324m_pre13 -1)
            ELSE 0
        END ) AS buyer_f_amt_sum_l12m_chg_rate_12m

    from r6_t3 p4
    where p4.buyer_type='F'
) f1
on c1.mcht_cd=f1.mcht_cd and c1.month=f1.month

