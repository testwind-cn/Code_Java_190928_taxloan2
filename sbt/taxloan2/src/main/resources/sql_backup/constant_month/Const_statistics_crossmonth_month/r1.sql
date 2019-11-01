select
    *
from ${Const_Common.DATABASE}.month_pre1 m
         left join r1_3
                   on m.month_pre1=r1_3.data_month