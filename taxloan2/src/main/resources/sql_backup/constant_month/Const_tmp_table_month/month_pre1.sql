-- m-1月份表处理
select
    (case when month(current_date())=1 then concat(year(current_date())-1,12)
          when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
          else concat(year(current_date()),"0",month(current_date())-1) end) as month_pre1