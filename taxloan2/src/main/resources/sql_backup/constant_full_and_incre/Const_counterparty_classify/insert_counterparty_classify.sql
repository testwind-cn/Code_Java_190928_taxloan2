


insert into dm_taxloan.counterparty_classify
partition(create_time)
select
	g2.mcht_cd,
	g2.month as data_month,
	g2.buyername as buyer_name,
	g2.buyer_tax_cd,

	g2.buyer_hist_month,

	g2.buyer_invoice_cnt_l24m,
	ROUND(g2.buyer_invoice_total_sum_l24m,2) AS buyer_invoice_total_sum_l24m,

	g2.buyer_invoice_cnt_l12m,
	ROUND(g2.buyer_invoice_total_sum_l12m,2) AS buyer_invoice_total_sum_l12m,

	g2.buyer_invoice_cnt_l6m,
	ROUND(g2.buyer_invoice_total_sum_l6m,2) AS buyer_invoice_total_sum_l6m,

	g2.buyer_invoice_cnt_l3m,
	ROUND(g2.buyer_invoice_total_sum_l3m,2) AS buyer_invoice_total_sum_l3m,

	g2.buyer_invoice_cnt_l712m,
	ROUND(g2.buyer_invoice_total_sum_l712m,2) AS buyer_invoice_total_sum_l712m,

	g2.buyer_invoice_cnt_l1324m,
	ROUND(g2.buyer_invoice_total_sum_l1324m,2) AS buyer_invoice_total_sum_l1324m,

	g2.buyer_invoice_cnt_l1m,
	ROUND(g2.buyer_invoice_total_sum_l1m,2) AS buyer_invoice_total_sum_l1m,

	g2.buyer_type,

	'0' AS is_delete,
	current_user() AS create_user,
	from_unixtime(unix_timestamp()) as modify_time,
	current_user() AS modify_user,
	current_date() AS create_time
from
(
    select
        distinct p1.mcht_cd,
        p1.month,
        p1.buyername,
        p1.buyer_tax_cd,
        (case when p2.buyer_hist_month is null then 0 else p2.buyer_hist_month end)
            as buyer_hist_month,

        (case when p2.buyer_invoice_cnt_l24m is null then 0 else p2.buyer_invoice_cnt_l24m end)
            as buyer_invoice_cnt_l24m,

        (case when p2.buyer_invoice_total_sum_l24m is null then 0 else p2.buyer_invoice_total_sum_l24m end)
            as buyer_invoice_total_sum_l24m,

        (case when p2.buyer_invoice_cnt_l12m is null then 0 else p2.buyer_invoice_cnt_l12m end)
            as buyer_invoice_cnt_l12m,

        (case when p2.buyer_invoice_total_sum_l12m is null then 0 else p2.buyer_invoice_total_sum_l12m end)
            as buyer_invoice_total_sum_l12m,

        (case when p2.buyer_invoice_cnt_l6m is null then 0 else p2.buyer_invoice_cnt_l6m end)
            as buyer_invoice_cnt_l6m,

        (case when p2.buyer_invoice_total_sum_l6m is null then 0 else p2.buyer_invoice_total_sum_l6m end)
            as buyer_invoice_total_sum_l6m,

        (case when p2.buyer_invoice_cnt_l3m is null then 0 else p2.buyer_invoice_cnt_l3m end)
            as buyer_invoice_cnt_l3m,

        (case when p2.buyer_invoice_total_sum_l3m is null then 0 else p2.buyer_invoice_total_sum_l3m end)
            as buyer_invoice_total_sum_l3m,

        (case when p2.buyer_invoice_cnt_l712m is null then 0 else p2.buyer_invoice_cnt_l712m end)
            as buyer_invoice_cnt_l712m,

        (case when p2.buyer_invoice_total_sum_l712m is null then 0 else p2.buyer_invoice_total_sum_l712m end)
            as buyer_invoice_total_sum_l712m,

        (case when p2.buyer_invoice_cnt_l1324m is null then 0 else p2.buyer_invoice_cnt_l1324m end)
            as buyer_invoice_cnt_l1324m,

        (case when p2.buyer_invoice_total_sum_l1324m is null then 0 else p2.buyer_invoice_total_sum_l1324m end)
            as buyer_invoice_total_sum_l1324m,

        (case when p2.buyer_invoice_cnt_l1m is null then 0 else p2.buyer_invoice_cnt_l1m end)
            as buyer_invoice_cnt_l1m,

        (case when p2.buyer_invoice_total_sum_l1m is null then 0 else p2.buyer_invoice_total_sum_l1m end)
            as buyer_invoice_total_sum_l1m,

        (case when p2.buyer_type is null then 'NULL' else p2.buyer_type end)
            as buyer_type
        -- 对应的月份没有发票信息，则统计发票类型为'NULL' 字符串
    from
         dm_taxloan.latest_month_tmp p1
    left join (
        select
            distinct s4.sellertaxno,
            s4.data_month,
            s4.buyername,
            s4.buyertaxno,
            s4.buyer_hist_month,
            s4.buyer_invoice_cnt_l24m,
            s4.buyer_invoice_total_sum_l24m,
            s4.buyer_invoice_cnt_l12m,
            s4.buyer_invoice_total_sum_l12m,
            s4.buyer_invoice_cnt_l6m,
            s4.buyer_invoice_total_sum_l6m,
            s4.buyer_invoice_cnt_l3m,
            s4.buyer_invoice_total_sum_l3m,
            --新增的4个字段
            s4.buyer_invoice_cnt_l712m,
            s4.buyer_invoice_total_sum_l712m,
            s4.buyer_invoice_cnt_l1324m,
            s4.buyer_invoice_total_sum_l1324m,

            s4.buyer_invoice_cnt_l1m,
            s4.buyer_invoice_total_sum_l1m,

            (CASE WHEN s4.buyer_hist_month>18 AND s4.buyer_invoice_cnt_l12m>=3 AND s4.buyer_invoice_cnt_l6m>0 THEN 'A'
              WHEN s4.buyer_hist_month<=18 AND s4.buyer_invoice_cnt_l12m>=3 AND s4.buyer_invoice_cnt_l6m>0 THEN 'B'
              ELSE 'C' END
            )AS buyer_type    --判断交易对手类型

        from s4
    ) p2
    on
        p1.mcht_cd=p2.sellertaxno and p1.month=p2.data_month and p1.buyer_tax_cd=p2.buyertaxno and p1.buyername=p2.buyername
) g2


-- 交易对手分类表插入成功