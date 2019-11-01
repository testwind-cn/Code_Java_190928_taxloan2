
select
c2.mcht_cd,
c2.buyer_name,
c2.buyer_tax_cd
from
(
select
	mcht_cd,
	data_month,
	buyer_name,
	buyer_tax_cd,
	(case when month(current_date())=1 then concat(year(current_date())-1,12)
		when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
		else concat(year(current_date()),"0",month(current_date())-1) end) as month_pre1,
	row_number() over(partition by mcht_cd,buyer_name,buyer_tax_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM'))) desc) as rank
from -- 取当前表中最近一个月的数据
${Const_Common.DATABASE}.counterparty
)c2 where c2.rank=1 and c2.data_month=c2.month_pre1
