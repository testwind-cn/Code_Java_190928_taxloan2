




select
    t3.mcht_cd,
    t3.month,
    t3.buyer_a_cnt,
    t3.buyer_b_cnt,
    t3.buyer_c_cnt,
    t3.buyer_f_cnt,
    lag(t3.buyer_a_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.month,'yyyyMM')))) as buyer_a_cnt_pre7,
    lag(t3.buyer_b_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.month,'yyyyMM')))) as buyer_b_cnt_pre7,
    lag(t3.buyer_c_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.month,'yyyyMM')))) as buyer_c_cnt_pre7,
    lag(t3.buyer_f_cnt,7,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.month,'yyyyMM')))) as buyer_f_cnt_pre7,

    lag(t3.buyer_a_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.month,'yyyyMM')))) as buyer_a_cnt_pre13,
    lag(t3.buyer_b_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.month,'yyyyMM')))) as buyer_b_cnt_pre13,
    lag(t3.buyer_c_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.month,'yyyyMM')))) as buyer_c_cnt_pre13,
    lag(t3.buyer_f_cnt,13,0) over(partition by t3.mcht_cd order by to_date(from_unixtime(unix_timestamp(t3.month,'yyyyMM')))) as buyer_f_cnt_pre13

from (
    select
        t1.mcht_cd,
        t1.month,
        -- 统计月交易对手数目
        t2.buyer_a_cnt,
        t2.buyer_b_cnt,
        t2.buyer_c_cnt,
        t2.buyer_f_cnt
    from (
        select * from dm_taxloan.cross_month_tmp
    ) t1
    left join (
        select * from dm_taxloan.statistics_month
    ) t2
    on t1.mcht_cd=t2.mcht_cd and t1.month=t2.data_month
) t3

