select
    a.sellertaxno,
    a.data_month as month_a,
    b.data_month as month_b,
    b.buyertaxno,
    b.buyername
from

    (select distinct sellertaxno,data_month from ${Const_Common.DATABASE}.saleinvoice_tmp_all
    )a
        left join (
        select distinct sellertaxno,data_month,buyername,buyertaxno from ${Const_Common.DATABASE}.saleinvoice_tmp_all
    )b on a.sellertaxno=b.sellertaxno