select *
from ${Const_Common.DATABASE}.month_pre1 m
         left join r4_1
                   on m.month_pre1=r4_1.data_month