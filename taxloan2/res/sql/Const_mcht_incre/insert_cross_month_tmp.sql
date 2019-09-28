
-- 生成辅助表，后面需要用到 cross_month_tmp
-- 生成从首次开票，到当前月的每个月数据行

with cte_t1 as (
    select distinct c.taxno as mcht_cd,
                    m.first_invoicedate,
                    m.mcht_cd_m
    from ${hivevar:DATABASE_DEST}.cjlog_tmp c
             left join
         (
             select distinct mcht_cd as mcht_cd_m,
                             first_invoicedate
             from ${hivevar:DATABASE_DEST}.mcht_tax
         ) m
         on c.taxno = m.mcht_cd_m
),
cte_cross_month_tmp as (
    select t2.mcht_cd,
           t2.month
    from (
             select cte_t1.mcht_cd,
                    d.month,
                    (case
                         when length(CAST(MONTH(cte_t1.first_invoicedate) AS STRING)) = 1
                             then concat(YEAR(cte_t1.first_invoicedate), "0", MONTH(cte_t1.first_invoicedate))
                         else concat(YEAR(cte_t1.first_invoicedate), MONTH(cte_t1.first_invoicedate)) end) AS data_month
             from ${hivevar:DATABASE_DEST}.dim_date d
            cross join cte_t1
         ) t2
    where datediff(to_date(from_unixtime(unix_timestamp(t2.month, 'yyyyMM'))),
                   to_date(from_unixtime(unix_timestamp(t2.data_month, 'yyyyMM')))) >= 0
      and datediff(to_date(from_unixtime(unix_timestamp(t2.month, 'yyyyMM'))),
                   to_date(from_unixtime(unix_timestamp(substr(current_date(), 1, 7), 'yyyy-MM')))) < -1
)

insert into ${hivevar:DATABASE_DEST}.cross_month_tmp select * from cte_cross_month_tmp