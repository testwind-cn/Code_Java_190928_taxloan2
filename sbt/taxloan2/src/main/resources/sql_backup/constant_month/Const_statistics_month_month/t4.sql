select
    s4.sellertaxno,
    s4.data_month,
    s4.total2
from (
         select
             s3.sellertaxno,
             s3.data_month,
             (case when month(current_date())=1 then concat(year(current_date())-1,12)
                   when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
                   else concat(year(current_date()),"0",month(current_date())-1) end) as data_month_pre1,
             sum(s3.total1) over (partition by s3.sellertaxno order by to_date(from_unixtime(unix_timestamp(s3.data_month,'yyyyMM')))) as total2
         from
             (
                 select
                     s2.sellertaxno,
                     s2.data_month,
                     sum(case when rank=1 then 1 else 0 end) as total1
                 from
                     (
                         select
                             s1.sellertaxno,
                             s1.data_month,
                             s1.buyertaxno,
                             row_number() over(partition by s1.sellertaxno,s1.buyertaxno order by to_date(from_unixtime(unix_timestamp(s1.data_month,'yyyyMM'))) ) as rank
                         from
                             (
                                 select
                                     distinct sellertaxno,
                                              data_month,
                                              buyertaxno
                                 from ${Const_Common.DATABASE}.saleinvoice_tmp_all
                             )s1
                     )s2 group by s2.sellertaxno,s2.data_month
             )s3
     )s4 where s4.data_month=s4.data_month_pre1