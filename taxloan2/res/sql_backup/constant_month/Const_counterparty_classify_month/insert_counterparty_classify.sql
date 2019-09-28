


insert into ${Const_Common.DATABASE}.counterparty_classify
partition(create_time)
select
	c1.mcht_cd,
	(case when month(current_date())=1 then concat(year(current_date())-1,12)
		when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
		else concat(year(current_date()),"0",month(current_date())-1) end) as data_month,
	c1.buyer_name,
	c1.buyer_tax_cd,

	(CASE WHEN s4.buyer_hist_month IS NULL THEN 0 ELSE s4.buyer_hist_month END) AS buyer_hist_month,

	(CASE WHEN s4.buyer_invoice_cnt_l24m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l24m END) AS buyer_invoice_cnt_l24m,
	(CASE WHEN s4.buyer_invoice_total_sum_l24m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l24m END) AS buyer_invoice_total_sum_l24m,

	(CASE WHEN s4.buyer_invoice_cnt_l12m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l12m END) AS buyer_invoice_cnt_l12m,
	(CASE WHEN s4.buyer_invoice_total_sum_l12m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l12m END) AS buyer_invoice_total_sum_l12m,

	(CASE WHEN s4.buyer_invoice_cnt_l6m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l6m END) AS buyer_invoice_cnt_l6m,
	(CASE WHEN s4.buyer_invoice_total_sum_l6m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l6m END) AS buyer_invoice_total_sum_l6m,

	(CASE WHEN s4.buyer_invoice_cnt_l3m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l3m END) AS buyer_invoice_cnt_l3m,
	(CASE WHEN s4.buyer_invoice_total_sum_l3m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l3m END) AS buyer_invoice_total_sum_l3m,

	(CASE WHEN s4.buyer_invoice_cnt_l712m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l712m END) AS buyer_invoice_cnt_l712m,
	(CASE WHEN s4.buyer_invoice_total_sum_l712m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l712m END) AS buyer_invoice_total_sum_l712m,

	(CASE WHEN s4.buyer_invoice_cnt_l1324m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l1324m END) AS buyer_invoice_cnt_l1324m,
	(CASE WHEN s4.buyer_invoice_total_sum_l1324m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l1324m END) AS buyer_invoice_total_sum_l1324m,

	(CASE WHEN s4.buyer_invoice_cnt_l1m IS NULL THEN 0 ELSE s4.buyer_invoice_cnt_l1m END) AS buyer_invoice_cnt_l1m,
	(CASE WHEN s4.buyer_invoice_total_sum_l1m IS NULL THEN 0.00 ELSE s4.buyer_invoice_total_sum_l1m END) AS buyer_invoice_total_sum_l1m,


	(CASE WHEN s4.buyer_hist_month>18 AND s4.buyer_invoice_cnt_l12m>=3 AND s4.buyer_invoice_cnt_l6m>0 THEN 'A'
	  WHEN s4.buyer_hist_month<=18 AND s4.buyer_invoice_cnt_l12m>=3 AND s4.buyer_invoice_cnt_l6m>0 THEN 'B'
	  ELSE 'C' END
	)AS buyer_type,  --判断交易对手类型

	'0' AS is_delete,
	current_user() AS create_user,
	from_unixtime(unix_timestamp()) as modify_time,
	current_user() AS modify_user,
	current_date() AS create_time

from counterparty_classify_tmp3 c1
left join s4
on c1.mcht_cd=s4.mcht_cd and c1.buyer_name=s4.buyername and c1.buyer_tax_cd=s4.buyertaxno

