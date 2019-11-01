select
    t3.mcht_cd,
    t3.data_month,
    t3.buyer_a_cnt,
    t3.buyer_b_cnt,
    t3.buyer_c_cnt,
    t3.buyer_f_cnt,
    lag(t3.buyer_a_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_a_cnt_pre7,
    lag(t3.buyer_b_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_b_cnt_pre7,
    lag(t3.buyer_c_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_c_cnt_pre7,
    lag(t3.buyer_f_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_f_cnt_pre7,

    lag(t3.buyer_a_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_a_cnt_pre13,
    lag(t3.buyer_b_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_b_cnt_pre13,
    lag(t3.buyer_c_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_c_cnt_pre13,
    lag(t3.buyer_f_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_f_cnt_pre13

from ${Const_Common.DATABASE}.statistics_month t3