

-- 生成辅助表，后面需要用到

select
    t2.mcht_cd,
    t2.month
from (
    select
        t1.mcht_cd,
        d.month,
        (
            case
                when length(CAST(MONTH (t1.first_invoicedate) AS STRING))=1
                then concat(YEAR (t1.first_invoicedate),"0",MONTH (t1.first_invoicedate))
                else concat(YEAR (t1.first_invoicedate),MONTH (t1.first_invoicedate))
            end
        ) AS data_month
    from
         dm_taxloan.dim_date d
    cross join (
        select
            distinct c.taxno as mcht_cd,
            m.first_invoicedate,
            m.mcht_cd_m
        from
            dm_taxloan.cjlog_tmp c
        left join (
            select
                distinct mcht_cd as mcht_cd_m,
                first_invoicedate
            from dm_taxloan.mcht_tax
        ) m
        on c.taxno=m.mcht_cd_m
    ) t1
) t2
where
    datediff( to_date(from_unixtime(unix_timestamp(t2.month,'yyyyMM'))),
                to_date(from_unixtime(unix_timestamp(t2.data_month,'yyyyMM'))) ) >= 0
    and datediff( to_date(from_unixtime(unix_timestamp(t2.month,'yyyyMM'))),
        to_date(from_unixtime(unix_timestamp( substr(current_date(),1,7),'yyyy-MM'))) ) < -1
