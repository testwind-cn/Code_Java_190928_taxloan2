select
    sellertaxno,
    data_month,
    count(distinct buyertaxno) as buyer_cnt
from ${Const_Common.DATABASE}.saleinvoice_tmp
group by sellertaxno,data_month