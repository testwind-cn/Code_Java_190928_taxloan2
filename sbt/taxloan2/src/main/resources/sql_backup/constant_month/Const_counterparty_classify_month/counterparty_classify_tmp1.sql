



select
distinct b.mcht_cd,
b.buyer_name,
b.buyer_tax_cd
from
(
select
distinct a.mcht_cd,
a.buyername as buyer_name,
a.buyertaxno as buyer_tax_cd
from
(
select
m1.mcht_cd,
s.buyertaxno,
s.buyername
from ${Const_Common.DATABASE}.mcht_tax m1
left join ${Const_Common.DATABASE}.saleinvoice_tmp s
on m1.mcht_cd=s.sellertaxno
)a where a.buyertaxno is not NULL

UNION ALL

select
distinct mcht_cd,
buyer_name,
buyer_tax_cd
from ${Const_Common.DATABASE}.counterparty_classify
where datediff(to_date(from_unixtime(unix_timestamp( substr(current_date(),1,7),'yyyy-MM'))),
		to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))) >50
	and datediff(to_date(from_unixtime(unix_timestamp( substr(current_date(),1,7),'yyyy-MM'))),
		to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM')))) <70
)b
