




select
    t4.mcht_cd,
    t4.month,
        -- 'A类交易对手数目6个月变化率'
        -- 'B类交易对手数目6个月变化率'
        -- 'C类交易对手数目6个月变化率'
        -- 'F类交易对手数目6个月变化率'
    ( case
        when t4.buyer_a_cnt is null or t4.buyer_a_cnt_pre7 is null then -9998
        when t4.buyer_a_cnt=0 and t4.buyer_a_cnt_pre7=0 then -9997
        when t4.buyer_a_cnt!=0 and t4.buyer_a_cnt_pre7=0 then -9996
        when t4.buyer_a_cnt!=0 and t4.buyer_a_cnt_pre7!=0 then (t4.buyer_a_cnt / t4.buyer_a_cnt_pre7 -1)
        else 0
    end ) as buyer_a_cnt_chg_rate_6m,

    ( case
        when t4.buyer_b_cnt is null or t4.buyer_b_cnt_pre7 is null then -9998
        when t4.buyer_b_cnt=0 and t4.buyer_b_cnt_pre7=0 then -9997
        when t4.buyer_b_cnt!=0 and t4.buyer_b_cnt_pre7=0 then -9996
        when t4.buyer_b_cnt!=0 and t4.buyer_b_cnt_pre7!=0 then (t4.buyer_b_cnt / t4.buyer_b_cnt_pre7 -1)
        else 0
    end ) as buyer_b_cnt_chg_rate_6m,

    ( case
        when t4.buyer_c_cnt is null or t4.buyer_c_cnt_pre7 is null then -9998
        when t4.buyer_c_cnt=0 and t4.buyer_c_cnt_pre7=0 then -9997
        when t4.buyer_c_cnt!=0 and t4.buyer_c_cnt_pre7=0 then -9996
        when t4.buyer_c_cnt!=0 and t4.buyer_c_cnt_pre7!=0 then (t4.buyer_c_cnt / t4.buyer_c_cnt_pre7 -1)
        else 0
    end ) as buyer_c_cnt_chg_rate_6m,

    ( case
        when t4.buyer_f_cnt is null or t4.buyer_f_cnt_pre7 is null then -9998
        when t4.buyer_f_cnt=0 and t4.buyer_f_cnt_pre7=0 then -9997
        when t4.buyer_f_cnt!=0 and t4.buyer_f_cnt_pre7=0 then -9996
        when t4.buyer_f_cnt!=0 and t4.buyer_f_cnt_pre7!=0 then (t4.buyer_f_cnt / t4.buyer_f_cnt_pre7 -1)
        else 0
    end ) as buyer_f_cnt_chg_rate_6m,

        -- 'A类交易对手数目12个月变化率'
        -- 'B类交易对手数目12个月变化率'
        -- 'C类交易对手数目12个月变化率'
        -- 'F类交易对手数目12个月变化率'

    ( case
        when t4.buyer_a_cnt is null or t4.buyer_a_cnt_pre13 is null then -9998
        when t4.buyer_a_cnt=0 and t4.buyer_a_cnt_pre13=0 then -9997
        when t4.buyer_a_cnt!=0 and t4.buyer_a_cnt_pre13=0 then -9996
        when t4.buyer_a_cnt!=0 and t4.buyer_a_cnt_pre13!=0 then (t4.buyer_a_cnt / t4.buyer_a_cnt_pre13 -1)
        else 0
    end ) as buyer_a_cnt_chg_rate_12m,

   ( case
       when t4.buyer_b_cnt is null or t4.buyer_b_cnt_pre13 is null then -9998
       when t4.buyer_b_cnt=0 and t4.buyer_b_cnt_pre13=0 then -9997
       when t4.buyer_b_cnt!=0 and t4.buyer_b_cnt_pre13=0 then -9996
       when t4.buyer_b_cnt!=0 and t4.buyer_b_cnt_pre13!=0 then (t4.buyer_b_cnt / t4.buyer_b_cnt_pre13 -1)
       else 0
   end ) as buyer_b_cnt_chg_rate_12m,

   ( case
       when t4.buyer_c_cnt is null or t4.buyer_c_cnt_pre13 is null then -9998
       when t4.buyer_c_cnt=0 and t4.buyer_c_cnt_pre13=0 then -9997
       when t4.buyer_c_cnt!=0 and t4.buyer_c_cnt_pre13=0 then -9996
       when t4.buyer_c_cnt!=0 and t4.buyer_c_cnt_pre13!=0 then (t4.buyer_c_cnt / t4.buyer_c_cnt_pre13 -1)
       else 0
   end ) as buyer_c_cnt_chg_rate_12m,

    ( case
        when t4.buyer_f_cnt is null or t4.buyer_f_cnt_pre13 is null then -9998
        when t4.buyer_f_cnt=0 and t4.buyer_f_cnt_pre13=0 then -9997
        when t4.buyer_f_cnt!=0 and t4.buyer_f_cnt_pre13=0 then -9996
        when t4.buyer_f_cnt!=0 and t4.buyer_f_cnt_pre13!=0 then (t4.buyer_f_cnt / t4.buyer_f_cnt_pre13 -1)
        else 0
    end ) as buyer_f_cnt_chg_rate_12m

from r5_t4 t4
