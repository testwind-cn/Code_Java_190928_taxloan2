


select
    *
from (
    select
        s3.mcht_cd,
        s2.sellertaxno,
        s3.data_month,
        s3.buyer_tax_cd as buyertaxno,
        s2.buyername,
        ((year(to_date(from_unixtime(unix_timestamp(s3.data_month,'yyyyMM'))))-year(to_date(from_unixtime(unix_timestamp(s2.data_month,'yyyyMM')))))*12+month(to_date(from_unixtime(unix_timestamp(s3.data_month,'yyyyMM'))))-month(to_date(from_unixtime(unix_timestamp(s2.data_month,'yyyyMM')))))
            AS buyer_hist_month,
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
    from (
        select
            t3.mcht_cd,
            t3.data_month,
            t3.buyer_tax_cd,
            t3.buyer_name,

                -- 交易对手近24月统计
            sum(
                case
                    when t3.buyer_invoice_cnt is null then 0
                    else t3.buyer_invoice_cnt
                end
            ) over (
                partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 23 preceding and current row
            ) as buyer_invoice_cnt_l24m,

                -- 交易对手近24月开票张数，含作废红冲发票
            sum(
                case
                    when t3.buyer_invoice_total_sum is null then 0
                    else t3.buyer_invoice_total_sum
                end
            ) over (
                partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 23 preceding and current row
            ) as buyer_invoice_total_sum_l24m,
                -- 交易对手近24月合计金额税额总和，不含作废红冲发票

                -- 交易对手近12月统计
            sum(
                case
                    when t3.buyer_invoice_cnt is null then 0
                    else t3.buyer_invoice_cnt
                end
            ) over (
                partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 11 preceding and current row
            ) as buyer_invoice_cnt_l12m,
                -- 交易对手近12月开票张数，含作废红冲发票

            sum(
                case
                    when t3.buyer_invoice_total_sum is null then 0
                    else t3.buyer_invoice_total_sum
                end
            ) over (
                partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 11 preceding and current row
            ) as buyer_invoice_total_sum_l12m,
                -- 交易对手近12月合计金额税额总和，不含作废红冲发票

                -- 交易对手近6月统计
            sum(
                case
                    when t3.buyer_invoice_cnt is null then 0
                    else t3.buyer_invoice_cnt
                end
            ) over (
                partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 5 preceding and current row
            ) as buyer_invoice_cnt_l6m,
                -- 交易对手近6月开票张数，含作废红冲发票

            sum(
                case
                    when t3.buyer_invoice_total_sum is null then 0
                    else t3.buyer_invoice_total_sum end
                ) over (
                    partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name
                    order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                    rows between 5 preceding and current row
                ) as buyer_invoice_total_sum_l6m,
                -- 交易对手近6月合计金额税额总和，不含作废红冲发票

                -- 交易对手近3月统计
            sum(
                case
                    when t3.buyer_invoice_cnt is null then 0
                    else t3.buyer_invoice_cnt
                end
            ) over (
                partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 2 preceding and current row
            ) as buyer_invoice_cnt_l3m,
                -- 交易对手近3月开票张数，含作废红冲发票

            sum(
                case
                    when t3.buyer_invoice_total_sum is null then 0
                    else t3.buyer_invoice_total_sum
                end
            ) over (
                partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 2 preceding and current row
            ) as buyer_invoice_total_sum_l3m,
                -- 交易对手近3月合计金额税额总和，不含作废红冲发票

                -- 交易对手近7-12月统计
            sum(
                case
                    when t3.buyer_invoice_cnt is null then 0
                    else t3.buyer_invoice_cnt
                end
            ) over (
                partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 12 preceding and 7 preceding
            ) as buyer_invoice_cnt_l712m,
                -- 交易对手近7-12月开票张数，含作废红冲发票

            sum(
                case
                    when t3.buyer_invoice_total_sum is null then 0
                    else t3.buyer_invoice_total_sum
                end
            ) over (
                partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 12 preceding and 7 preceding
            ) as buyer_invoice_total_sum_l712m,
                -- 交易对手近7-12月合计金额税额总和，不含作废红冲发票

            -- 交易对手近13-24月统计
            sum(
                case
                    when t3.buyer_invoice_cnt is null then 0
                    else t3.buyer_invoice_cnt
                end
            ) over (
                partition by t3.mcht_cd,t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 24 preceding and 13 preceding
            ) as buyer_invoice_cnt_l1324m,
                -- 交易对手近13-24月开票张数，含作废红冲发票

            sum(
                case
                    when t3.buyer_invoice_total_sum is null then 0
                    else t3.buyer_invoice_total_sum
                end
            ) over (
                partition by t3.mcht_cd, t3.buyer_tax_cd,t3.buyer_name
                order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))
                rows between 24 preceding and 13 preceding
            ) as buyer_invoice_total_sum_l1324m,
                -- 交易对手近13-24月合计金额税额总和，不含作废红冲发票

            -- 交易对手近1月统计
            t3.buyer_invoice_cnt as buyer_invoice_cnt_l1m,
            t3.buyer_invoice_total_sum as buyer_invoice_total_sum_l1m

        from dm_taxloan.counterparty t3
    
    ) s3
    left join (
        select
            sellertaxno,
            data_month,
            buyertaxno,
            buyername,
            invoicedate,
            row_number() over (
                partition by sellertaxno, buyertaxno,buyername
                order by invoicedate
            ) as rank
              -- 取该交易对手最早的交易时间
        from dm_taxloan.saleinvoice_tmp
    ) s2
    on
        s3.mcht_cd=s2.sellertaxno and s3.buyer_tax_cd=s2.buyertaxno and s3.buyer_name=s2.buyername
    where s2.rank=1
) s5
where
    s5.sellertaxno is not null
