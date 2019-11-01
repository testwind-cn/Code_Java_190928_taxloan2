


select
	sellertaxno,
	data_month,
	count(distinct buyertaxno) as buyer_cnt
from
     dm_taxloan.saleinvoice_tmp
group by sellertaxno,data_month
