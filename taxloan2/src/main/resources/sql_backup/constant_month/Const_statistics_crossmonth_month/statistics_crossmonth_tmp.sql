select
    m.mcht_cd_m1 as mcht_cd,
    (case when month(current_date())=1 then concat(year(current_date())-1,12)
          when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
          else concat(year(current_date()),"0",month(current_date())-1) end) as month_pre1
from
    (
        select
            m1.mcht_cd as mcht_cd_m1,
            m2.mcht_cd as mcht_cd_m2
        from ${Const_Common.DATABASE}.mcht_tax m1
                 left join
             (
                 select
                     a.mcht_cd
                 from (
                          select
                              mcht_cd,
                              (case when month(current_date())=1 then concat(year(current_date())-1,12)
                                    when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
                                    else concat(year(current_date()),"0",month(current_date())-1) end) as data_month_pre1,
                              data_month,
                              row_number() over(partition by mcht_cd order by to_date(from_unixtime(unix_timestamp(data_month,'yyyyMM'))) desc) as rank --取当前表中最近一个月的数据
                          from ${Const_Common.DATABASE}.statistics_crossmonth
                      )a where a.data_month=a.data_month_pre1 and a.rank=1
             )m2 on m1.mcht_cd=m2.mcht_cd
    )m where m.mcht_cd_m2 is NULL