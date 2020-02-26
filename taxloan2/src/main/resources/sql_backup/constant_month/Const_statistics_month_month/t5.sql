select
    a.mcht_cd,
    a.data_month,
    a.buyer_a_cnt,
    a.buyer_b_cnt,
    a.buyer_c_cnt,
    a.buyer_f_cnt
from
    (
        select
            m.mcht_cd,
            m.data_month,
            (case when month(current_date())=1 then concat(year(current_date())-1,12)
                  when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
                  else concat(year(current_date()),"0",month(current_date())-1) end) as data_month_pre1,
            sum(CASE WHEN m.buyer_type='A' THEN 1 ELSE 0 END) AS buyer_a_cnt,
            sum(CASE WHEN m.buyer_type='B' THEN 1 ELSE 0 END) AS buyer_b_cnt,
            sum(CASE WHEN m.buyer_type='C' THEN 1 ELSE 0 END) AS buyer_c_cnt,
            sum(CASE WHEN m.buyer_type='F' THEN 1 ELSE 0 END) AS buyer_f_cnt
        from
            (
                select
                    mcht_cd,
                    data_month,
                    buyer_type
                from ${Const_Common.DATABASE}.counterparty_classify
            )m group by m.mcht_cd,m.data_month
    )a where a.data_month=a.data_month_pre1