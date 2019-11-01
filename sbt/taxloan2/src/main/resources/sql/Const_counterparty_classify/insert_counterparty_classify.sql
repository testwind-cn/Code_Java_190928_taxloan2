
with cte_s4 as
(
    select
        *
    from
    (
        select
            s3.mcht_cd,
            s2.sellertaxno,
            s3.data_month,
            s3.buyer_tax_cd as buyertaxno,
            s2.buyername,
            ((year(to_date(from_unixtime(unix_timestamp(s3.data_month,'yyyyMM'))))-year(to_date(from_unixtime(unix_timestamp(s2.data_month,'yyyyMM')))))*12+month(to_date(from_unixtime(unix_timestamp(s3.data_month,'yyyyMM'))))-month(to_date(from_unixtime(unix_timestamp(s2.data_month,'yyyyMM'))))) AS buyer_hist_month,
            s3.buyer_invoice_cnt_l24m ,
            s3.buyer_invoice_total_sum_l24m ,
            s3.buyer_invoice_cnt_l12m ,
            s3.buyer_invoice_total_sum_l12m ,
            s3.buyer_invoice_cnt_l6m,
            s3.buyer_invoice_total_sum_l6m,
            s3.buyer_invoice_cnt_l3m ,
            s3.buyer_invoice_total_sum_l3m ,

            s3.buyer_invoice_cnt_l712m ,
            s3.buyer_invoice_total_sum_l712m ,
            s3.buyer_invoice_cnt_l1324m ,
            s3.buyer_invoice_total_sum_l1324m ,

            s3.buyer_invoice_cnt_l1m,
            s3.buyer_invoice_total_sum_l1m
        from

        (
            select
                t3.mcht_cd,
                t3.data_month,
                t3.buyer_tax_cd,
                t3.buyer_name,
                --交易对手近24月统计
                sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 23 preceding and current row) as buyer_invoice_cnt_l24m,   --交易对手近24月开票张数，含作废红冲发票
                sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 23 preceding and current row) as buyer_invoice_total_sum_l24m,   --交易对手近24月合计金额税额总和，不含作废红冲发票
                --交易对手近12月统计
                sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 11 preceding and current row) as buyer_invoice_cnt_l12m,   --交易对手近12月开票张数，含作废红冲发票
                sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 11 preceding and current row) as buyer_invoice_total_sum_l12m,   --交易对手近12月合计金额税额总和，不含作废红冲发票
                --交易对手近6月统计
                sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 5 preceding and current row) as buyer_invoice_cnt_l6m,   --交易对手近6月开票张数，含作废红冲发票
                sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 5 preceding and current row) as buyer_invoice_total_sum_l6m,   --交易对手近6月合计金额税额总和，不含作废红冲发票
                --交易对手近3月统计
                sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 2 preceding and current row) as buyer_invoice_cnt_l3m,   --交易对手近3月开票张数，含作废红冲发票
                sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 2 preceding and current row) as buyer_invoice_total_sum_l3m,   --交易对手近3月合计金额税额总和，不含作废红冲发票

                --交易对手近7-12月统计
                sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 12 preceding and 7 preceding) as buyer_invoice_cnt_l712m,   --交易对手近7-12月开票张数，含作废红冲发票
                sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 12 preceding and 7 preceding) as buyer_invoice_total_sum_l712m,   --交易对手近7-12月合计金额税额总和，不含作废红冲发票

                --交易对手近13-24月统计
                sum(case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) over (partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 24 preceding and 13 preceding) as buyer_invoice_cnt_l1324m,   --交易对手近13-24月开票张数，含作废红冲发票
                sum(case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) over (partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 24 preceding and 13 preceding) as buyer_invoice_total_sum_l1324m,   --交易对手近13-24月合计金额税额总和，不含作废红冲发票

                --交易对手近1月统计
                t3.buyer_invoice_cnt as buyer_invoice_cnt_l1m,
                t3.buyer_invoice_total_sum as buyer_invoice_total_sum_l1m

            from ${hivevar:DATABASE_DEST}.counterparty t3

        ) s3
        left join
        (
            select
                sellertaxno,
                data_month,
                buyertaxno,
                buyername,
                invoicedate,
                row_number() over(partition by sellertaxno, buyertaxno,buyername order by invoicedate) as rank  --取该交易对手最早的交易时间
            from ${hivevar:DATABASE_DEST}.saleinvoice_tmp
        ) s2
        on s3.mcht_cd=s2.sellertaxno and s3.buyer_tax_cd=s2.buyertaxno and s3.buyer_name=s2.buyername  where s2.rank=1
    ) s5 where s5.sellertaxno is not null
)

insert into ${hivevar:DATABASE_DEST}.counterparty_classify
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
        distinct
        p1.mcht_cd,
        p1.month,
        p1.buyername,
        p1.buyer_tax_cd,
        (case when p2.buyer_hist_month is null then 0 else p2.buyer_hist_month end) as buyer_hist_month,

        (case when p2.buyer_invoice_cnt_l24m is null then 0 else p2.buyer_invoice_cnt_l24m end) as buyer_invoice_cnt_l24m,
        (case when p2.buyer_invoice_total_sum_l24m is null then 0 else p2.buyer_invoice_total_sum_l24m end) as buyer_invoice_total_sum_l24m,

        (case when p2.buyer_invoice_cnt_l12m is null then 0 else p2.buyer_invoice_cnt_l12m end) as buyer_invoice_cnt_l12m,
        (case when p2.buyer_invoice_total_sum_l12m is null then 0 else p2.buyer_invoice_total_sum_l12m end) as buyer_invoice_total_sum_l12m,

        (case when p2.buyer_invoice_cnt_l6m is null then 0 else p2.buyer_invoice_cnt_l6m end) as buyer_invoice_cnt_l6m,
        (case when p2.buyer_invoice_total_sum_l6m is null then 0 else p2.buyer_invoice_total_sum_l6m end) as buyer_invoice_total_sum_l6m,

        (case when p2.buyer_invoice_cnt_l3m is null then 0 else p2.buyer_invoice_cnt_l3m end) as buyer_invoice_cnt_l3m,
        (case when p2.buyer_invoice_total_sum_l3m is null then 0 else p2.buyer_invoice_total_sum_l3m end) as buyer_invoice_total_sum_l3m,

        (case when p2.buyer_invoice_cnt_l712m is null then 0 else p2.buyer_invoice_cnt_l712m end) as buyer_invoice_cnt_l712m,
        (case when p2.buyer_invoice_total_sum_l712m is null then 0 else p2.buyer_invoice_total_sum_l712m end) as buyer_invoice_total_sum_l712m,

        (case when p2.buyer_invoice_cnt_l1324m is null then 0 else p2.buyer_invoice_cnt_l1324m end) as buyer_invoice_cnt_l1324m,
        (case when p2.buyer_invoice_total_sum_l1324m is null then 0 else p2.buyer_invoice_total_sum_l1324m end) as buyer_invoice_total_sum_l1324m,

        (case when p2.buyer_invoice_cnt_l1m is null then 0 else p2.buyer_invoice_cnt_l1m end) as buyer_invoice_cnt_l1m,
        (case when p2.buyer_invoice_total_sum_l1m is null then 0 else p2.buyer_invoice_total_sum_l1m end) as buyer_invoice_total_sum_l1m,

        (case when p2.buyer_type is null then 'NULL' else p2.buyer_type end) as buyer_type  ---对应的月份没有发票信息，则统计发票类型为'NULL' 字符串
    from ${hivevar:DATABASE_DEST}.latest_month_tmp p1
    left join
    (
        select
            distinct
            cte_s4.sellertaxno,
            cte_s4.data_month,
            cte_s4.buyername,
            cte_s4.buyertaxno,
            cte_s4.buyer_hist_month,
            cte_s4.buyer_invoice_cnt_l24m,
            cte_s4.buyer_invoice_total_sum_l24m,
            cte_s4.buyer_invoice_cnt_l12m,
            cte_s4.buyer_invoice_total_sum_l12m,
            cte_s4.buyer_invoice_cnt_l6m,
            cte_s4.buyer_invoice_total_sum_l6m,
            cte_s4.buyer_invoice_cnt_l3m,
            cte_s4.buyer_invoice_total_sum_l3m,
            --新增的4个字段
            cte_s4.buyer_invoice_cnt_l712m,
            cte_s4.buyer_invoice_total_sum_l712m,
            cte_s4.buyer_invoice_cnt_l1324m,
            cte_s4.buyer_invoice_total_sum_l1324m,

            cte_s4.buyer_invoice_cnt_l1m,
            cte_s4.buyer_invoice_total_sum_l1m,

            (CASE
                WHEN cte_s4.buyer_hist_month>18 AND cte_s4.buyer_invoice_cnt_l12m>=3 AND cte_s4.buyer_invoice_cnt_l6m>0 THEN 'A'
                WHEN cte_s4.buyer_hist_month<=18 AND cte_s4.buyer_invoice_cnt_l12m>=3 AND cte_s4.buyer_invoice_cnt_l6m>0 THEN 'B'
                ELSE 'C'
                END
            ) AS buyer_type    --判断交易对手类型
        from cte_s4
    ) p2
    on p1.mcht_cd=p2.sellertaxno and p1.month=p2.data_month and p1.buyer_tax_cd=p2.buyertaxno and p1.buyername=p2.buyername
) g2