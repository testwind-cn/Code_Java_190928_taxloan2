select
    m3.sellertaxno,
    m3.month_a,
    m3.buyer_cnt_l3m,
    m6.buyer_cnt_l6m,
    m12.buyer_cnt_l12m,
    m24.buyer_cnt_l24m,
    m712.buyer_cnt_l712m,
    m1324.buyer_cnt_l1324m

from
    (
        select distinct sellertaxno from ${Const_Common.DATABASE}.saleinvoice_tmp_all
    )a
        left join
    r1_4_3m m3
    on a.sellertaxno=m3.sellertaxno
        left join r1_4_6m m6
                  on 	m3.sellertaxno=m6.sellertaxno and m3.month_a=m6.month_a
        left join r1_4_12m m12
                  on 	m12.sellertaxno=m6.sellertaxno and m12.month_a=m6.month_a
        left join r1_4_24m m24
                  on 	m12.sellertaxno=m24.sellertaxno and m12.month_a=m24.month_a
        left join r1_4_712m m712
                  on 	m712.sellertaxno=m24.sellertaxno and m712.month_a=m24.month_a
        left join r1_4_1324m m1324
                  on 	m712.sellertaxno=m1324.sellertaxno and m712.month_a=m1324.month_a