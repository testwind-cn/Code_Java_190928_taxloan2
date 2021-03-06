select
    distinct t2.mcht_cd,
             t2.data_month,
             t2.buyer_tax_cd,
             t2.buyer_type,
             t2.buyer_invoice_cnt_l12m,
             t2.buyer_invoice_cnt_l712m,
             t2.buyer_invoice_cnt_l1324m,
             t2.buyer_invoice_total_sum_l12m,
             t2.buyer_invoice_total_sum_l712m,
             t2.buyer_invoice_total_sum_l1324m
from ${Const_Common.DATABASE}.counterparty_classify t2