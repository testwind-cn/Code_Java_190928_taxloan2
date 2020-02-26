


select
    a.sellertaxno,
    a.data_month as month_a,
    b.data_month as month_b,
    b.buyertaxno,
    b.buyername
from (
    select distinct sellertaxno,data_month from dm_taxloan.saleinvoice_tmp
) a
left join (
    select distinct sellertaxno,data_month,buyername,buyertaxno from dm_taxloan.saleinvoice_tmp
) b
on
    a.sellertaxno=b.sellertaxno
