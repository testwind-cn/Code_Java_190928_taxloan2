select
    pp.mcht_cd,
    pp.data_month,
    pp.buyer_type,
    pp.buyer_invoice_cnt_l12m_all,
    pp.buyer_invoice_total_sum_l12m_all,

    lag(pp.buyer_invoice_cnt_l712m_all,7,0) over(partition by pp.mcht_cd,pp.data_month,pp.buyer_type,
        pp.buyer_invoice_cnt_l12m_all,buyer_invoice_total_sum_l12m_all
        order by to_date(from_unixtime(unix_timestamp(pp.data_month,'yyyyMM')))) as buyer_invoice_cnt_l712m_pre7,
    lag(pp.buyer_invoice_total_sum_l712m_all,7,0) over(partition by pp.mcht_cd,pp.data_month,pp.buyer_type,
        pp.buyer_invoice_cnt_l12m_all,buyer_invoice_total_sum_l12m_all
        order by to_date(from_unixtime(unix_timestamp(pp.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l712m_pre7,

    lag(pp.buyer_invoice_cnt_l1324m_all,13,0) over(partition by pp.mcht_cd,pp.data_month,pp.buyer_type,
        pp.buyer_invoice_cnt_l12m_all,buyer_invoice_total_sum_l12m_all
        order by to_date(from_unixtime(unix_timestamp(pp.data_month,'yyyyMM')))) as buyer_invoice_cnt_l1324m_pre13,
    lag(pp.buyer_invoice_total_sum_l1324m_all,13,0) over(partition by pp.mcht_cd,pp.data_month,pp.buyer_type,
        pp.buyer_invoice_cnt_l12m_all,buyer_invoice_total_sum_l12m_all
        order by to_date(from_unixtime(unix_timestamp(pp.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l1324m_pre13

from r6_t2 pp