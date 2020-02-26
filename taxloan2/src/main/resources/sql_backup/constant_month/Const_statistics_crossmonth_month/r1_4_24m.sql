select
    a.sellertaxno,
    a.month_a,
    count(distinct a.buyertaxno) as buyer_cnt_l24m
from
    (
        select
            *
        from r1_4_common
        where datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
                       to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))<732
          and datediff(to_date(from_unixtime(unix_timestamp(month_a,'yyyyMM'))),
                       to_date(from_unixtime(unix_timestamp(month_b,'yyyyMM'))))>0
    )a group by a.sellertaxno,a.month_a