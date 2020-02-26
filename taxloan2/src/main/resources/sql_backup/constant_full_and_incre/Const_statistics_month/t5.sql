



select
	a5.mcht_cd,
	a5.data_month,
	sum(CASE WHEN a5.buyer_type='A' THEN 1 ELSE 0 END) AS buyer_a_cnt,
	sum(CASE WHEN a5.buyer_type='B' THEN 1 ELSE 0 END) AS buyer_b_cnt,
	sum(CASE WHEN a5.buyer_type='C' THEN 1 ELSE 0 END) AS buyer_c_cnt,
	sum(CASE WHEN a5.buyer_type='F' THEN 1 ELSE 0 END) AS buyer_f_cnt
from
     dm_taxloan.counterparty_classify a5
left semi join
    dm_taxloan.cjlog_tmp c1
on (a5.mcht_cd=c1.taxno)
group by a5.mcht_cd,a5.data_month
