


select
    distinct t2.mcht_cd,
    t1.month,
    t2.buyer_tax_cd,
    t2.buyer_type,
    t2.buyer_invoice_cnt_l12m,
    t2.buyer_invoice_cnt_l712m,
    t2.buyer_invoice_cnt_l1324m,
    t2.buyer_invoice_total_sum_l12m,
    t2.buyer_invoice_total_sum_l712m,
    t2.buyer_invoice_total_sum_l1324m
from
     dm_taxloan.latest_month_tmp t1
left join (
    select
        mcht_cd,
        data_month,
        buyer_tax_cd,
        buyer_type,
        buyer_invoice_cnt_l12m,
        buyer_invoice_cnt_l712m,
        buyer_invoice_cnt_l1324m,
        buyer_invoice_total_sum_l12m,
        buyer_invoice_total_sum_l712m,
        buyer_invoice_total_sum_l1324m
    from dm_taxloan.counterparty_classify
) t2
on
    t1.mcht_cd=t2.mcht_cd
    and t1.buyer_tax_cd=t2.buyer_tax_cd
    and t1.month=t2.data_month
