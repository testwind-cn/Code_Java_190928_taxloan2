



select
    s3.sellertaxno,
    s3.data_month,
    sum(s3.total1) over (
        partition by s3.sellertaxno
        order by to_date(from_unixtime(unix_timestamp(s3.data_month,'yyyyMM')))
    ) as total2
from (
    select
        s2.sellertaxno,
        s2.data_month,
        sum(case when rank=1 then 1 else 0 end) as total1
    from (
        select
            s1.sellertaxno,
            s1.data_month,
            s1.buyertaxno,
            row_number() over (
                partition by s1.buyertaxno,s1.buyertaxno
                order by to_date(from_unixtime(unix_timestamp(s1.data_month,'yyyyMM')))
            ) as rank
        from (
            select
                distinct sellertaxno,
                data_month,
                buyertaxno
            from dm_taxloan.saleinvoice_tmp
        ) s1
    ) s2
    group by s2.sellertaxno,s2.data_month
) s3
