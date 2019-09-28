





select
    p1.mcht_cd,
    p1.month,

    (CASE WHEN p2.invoice_r_all_cnt_l3m is null or p2.invoice_cnt_l3m is null THEN -9998
          WHEN  p2.invoice_r_all_cnt_l3m=0 AND p2.invoice_cnt_l3m=0 THEN -9997
          WHEN p2.invoice_r_all_cnt_l3m!=0 AND p2.invoice_cnt_l3m=0 THEN -9996
          ELSE p2.invoice_r_all_cnt_l3m / p2.invoice_cnt_l3m
    END ) AS invoice_red_cnt_rate_l3m, -- 近3月红冲发票张数占比

    (CASE WHEN p2.invoice_c_all_cnt_l3m is null or p2.invoice_cnt_l3m is null THEN -9998
          WHEN  p2.invoice_c_all_cnt_l3m=0 AND p2.invoice_cnt_l3m=0 THEN -9997
          WHEN p2.invoice_c_all_cnt_l3m!=0 AND p2.invoice_cnt_l3m=0 THEN -9996
          ELSE p2.invoice_c_all_cnt_l3m / p2.invoice_cnt_l3m
    END ) AS invoice_cancel_cnt_rate_l3m, -- 近3月作废发票张数占比

    (CASE WHEN p2.invoice_total_r_sum_l3m is null or p2.invoice_total_sum_l3m is null THEN -9998
          WHEN  p2.invoice_total_r_sum_l3m=0 AND p2.invoice_total_sum_l3m=0 THEN -9997
          WHEN p2.invoice_total_r_sum_l3m!=0 AND p2.invoice_total_sum_l3m=0 THEN -9996
          ELSE p2.invoice_total_r_sum_l3m / p2.invoice_total_sum_l3m
    END ) AS invoice_red_total_sum_rate_l3m,     -- 近3月红冲发票合计金额税额总和比率

    (CASE WHEN p2.invoice_total_c_sum_l3m is null or p2.invoice_total_sum_l3m is null THEN -9998
          WHEN  p2.invoice_total_c_sum_l3m=0 AND p2.invoice_total_sum_l3m=0 THEN -9997
          WHEN p2.invoice_total_c_sum_l3m!=0 AND p2.invoice_total_sum_l3m=0 THEN -9996
          ELSE p2.invoice_total_c_sum_l3m / p2.invoice_total_sum_l3m
    END ) AS invoice_cancel_total_sum_rate_l3m, -- 近3月作废发票合计金额税金总和比率

    (CASE WHEN p2.invoice_r_all_cnt_l6m is null or p2.invoice_cnt_l6m is null THEN -9998
          WHEN  p2.invoice_r_all_cnt_l6m=0 AND p2.invoice_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_r_all_cnt_l6m!=0 AND p2.invoice_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_r_all_cnt_l6m / p2.invoice_cnt_l6m
    END ) AS invoice_red_cnt_rate_l6m, -- 近6月红冲发票张数占比

    (CASE WHEN p2.invoice_c_all_cnt_l6m is null or p2.invoice_cnt_l6m is null THEN -9998
          WHEN  p2.invoice_c_all_cnt_l6m=0 AND p2.invoice_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_c_all_cnt_l6m!=0 AND p2.invoice_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_c_all_cnt_l6m / p2.invoice_cnt_l6m
    END ) AS invoice_cancel_cnt_rate_l6m, -- 近6月作废发票张数占比

    (CASE WHEN p2.invoice_total_r_sum_l6m is null or p2.invoice_total_sum_l6m is null THEN -9998
          WHEN  p2.invoice_total_r_sum_l6m=0 AND p2.invoice_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_total_r_sum_l6m!=0 AND p2.invoice_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m
    END ) AS invoice_red_total_sum_rate_l6m,     -- 近6月红冲发票合计金额税额总和比率

    (CASE WHEN p2.invoice_total_c_sum_l6m is null or p2.invoice_total_sum_l6m is null THEN -9998
          WHEN  p2.invoice_total_c_sum_l6m=0 AND p2.invoice_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_total_c_sum_l6m!=0 AND p2.invoice_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m
    END ) AS invoice_cancel_total_sum_rate_l6m, -- 近6月作废发票合计金额税金总和比率

    (CASE WHEN p2.invoice_r_all_cnt_l12m is null or p2.invoice_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_r_all_cnt_l12m=0 AND p2.invoice_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_r_all_cnt_l12m!=0 AND p2.invoice_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_r_all_cnt_l12m / p2.invoice_cnt_l12m
    END ) AS invoice_red_cnt_rate_l12m, -- 近12月红冲发票张数占比

    (CASE WHEN p2.invoice_c_all_cnt_l12m is null or p2.invoice_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_c_all_cnt_l12m=0 AND p2.invoice_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_c_all_cnt_l12m!=0 AND p2.invoice_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_c_all_cnt_l12m / p2.invoice_cnt_l12m
    END ) AS invoice_cancel_cnt_rate_l12m, -- 近12月作废发票张数占比

    (CASE WHEN p2.invoice_total_r_sum_l12m is null or p2.invoice_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_total_r_sum_l12m=0 AND p2.invoice_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_total_r_sum_l12m!=0 AND p2.invoice_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m
    END ) AS invoice_red_total_sum_rate_l12m,     -- 近12月红冲发票合计金额税额总和比率

    (CASE WHEN p2.invoice_total_c_sum_l12m is null or p2.invoice_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_total_c_sum_l12m=0 AND p2.invoice_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_total_c_sum_l12m!=0 AND p2.invoice_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m
    END ) AS invoice_cancel_total_sum_rate_l12m, -- 近12月作废发票合计金额税金总和比率

    (CASE WHEN p2.invoice_r_all_cnt_l24m is null or p2.invoice_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_r_all_cnt_l24m=0 AND p2.invoice_cnt_l24m=0 THEN -9997
          WHEN p2.invoice_r_all_cnt_l24m!=0 AND p2.invoice_cnt_l24m=0 THEN -9996
          ELSE p2.invoice_r_all_cnt_l24m / p2.invoice_cnt_l24m
    END ) AS invoice_red_cnt_rate_l24m, -- 近24月红冲发票张数占比

    (CASE WHEN p2.invoice_c_all_cnt_l24m is null or p2.invoice_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_c_all_cnt_l24m=0 AND p2.invoice_cnt_l24m=0 THEN -9997
          WHEN p2.invoice_c_all_cnt_l24m!=0 AND p2.invoice_cnt_l24m=0 THEN -9996
          ELSE p2.invoice_c_all_cnt_l24m / p2.invoice_cnt_l24m
    END ) AS invoice_cancel_cnt_rate_l24m, -- 近24月作废发票张数占比

    (CASE WHEN p2.invoice_total_r_sum_l24m is null or p2.invoice_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_total_r_sum_l24m=0 AND p2.invoice_total_sum_l24m=0 THEN -9997
          WHEN p2.invoice_total_r_sum_l24m!=0 AND p2.invoice_total_sum_l24m=0 THEN -9996
          ELSE p2.invoice_total_r_sum_l24m / p2.invoice_total_sum_l24m
    END ) AS invoice_red_total_sum_rate_l24m,     -- 近24月红冲发票合计金额税额总和比率

    (CASE WHEN p2.invoice_total_c_sum_l24m is null or p2.invoice_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_total_c_sum_l24m=0 AND p2.invoice_total_sum_l24m=0 THEN -9997
          WHEN p2.invoice_total_c_sum_l24m!=0 AND p2.invoice_total_sum_l24m=0 THEN -9996
          ELSE p2.invoice_total_c_sum_l24m / p2.invoice_total_sum_l24m
    END ) AS invoice_cancel_total_sum_rate_l24m, -- 近24月作废发票合计金额税金总和比率

    (CASE WHEN p2.invoice_sc_cnt_l6m is null or p2.invoice_sc_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_sc_cnt_l6m=0 AND p2.invoice_sc_cnt_l12m-p2.invoice_sc_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_sc_cnt_l6m!=0 AND p2.invoice_sc_cnt_l12m-p2.invoice_sc_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_sc_cnt_l6m / (p2.invoice_sc_cnt_l12m-p2.invoice_sc_cnt_l6m)
    END ) AS invoice_sc_cnt_ratio_6m, -- 增值税开票张数6个月环比

    (CASE WHEN p2.invoice_sc_cancel_cnt_l6m is null or p2.invoice_sc_cancel_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_sc_cancel_cnt_l6m=0 AND p2.invoice_sc_cancel_cnt_l12m-p2.invoice_sc_cancel_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_sc_cancel_cnt_l6m!=0 AND p2.invoice_sc_cnt_l12m-p2.invoice_sc_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_sc_cancel_cnt_l6m / (p2.invoice_sc_cancel_cnt_l12m-p2.invoice_sc_cancel_cnt_l6m)
    END ) AS invoice_sc_cancel_cnt_ratio_6m, -- 增值税发票作废张数6个月环比

    (CASE WHEN p2.invoice_sc_red_cnt_l6m is null or p2.invoice_sc_red_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_sc_red_cnt_l6m=0 AND p2.invoice_sc_red_cnt_l12m-p2.invoice_sc_red_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_sc_red_cnt_l6m!=0 AND p2.invoice_sc_red_cnt_l12m-p2.invoice_sc_red_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_sc_red_cnt_l6m / (p2.invoice_sc_red_cnt_l12m-p2.invoice_sc_red_cnt_l6m)
    END ) AS invoice_sc_red_cnt_ratio_6m, -- 增值税发票红冲张数6个月环比

    (CASE WHEN p2.invoice_sc_total_sum_l6m is null or p2.invoice_sc_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_sc_total_sum_l6m=0 AND p2.invoice_sc_total_sum_l12m-p2.invoice_sc_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_sc_total_sum_l6m!=0 AND p2.invoice_sc_total_sum_l12m-p2.invoice_sc_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_sc_total_sum_l6m / (p2.invoice_sc_total_sum_l12m-p2.invoice_sc_total_sum_l6m)
    END ) AS invoice_sc_total_sum_ratio_6m, -- 增值税发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_cnt_l6m is null or p2.invoice_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_cnt_l6m=0 AND p2.invoice_cnt_l12m - p2.invoice_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_cnt_l6m!=0 AND p2.invoice_cnt_l12m - p2.invoice_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_cnt_l6m / (p2.invoice_cnt_l12m - p2.invoice_cnt_l6m)
    END ) AS invoice_cnt_ratio_6m, -- 所有发票开票张数6个月环比

    (CASE WHEN p2.invoice_total_sum_l6m is null or p2.invoice_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_total_sum_l6m=0 AND p2.invoice_total_sum_l12m - p2.invoice_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_total_sum_l6m!=0 AND p2.invoice_total_sum_l12m - p2.invoice_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_total_sum_l6m / (p2.invoice_total_sum_l12m - p2.invoice_total_sum_l6m)
    END ) AS invoice_total_sum_ratio_6m, -- 所有发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_s_cnt_l6m is null or p2.invoice_s_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_s_cnt_l6m=0 AND p2.invoice_s_cnt_l12m - p2.invoice_s_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_s_cnt_l6m!=0 AND p2.invoice_s_cnt_l12m - p2.invoice_s_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_s_cnt_l6m / (p2.invoice_s_cnt_l12m - p2.invoice_s_cnt_l6m)
    END ) AS invoice_s_cnt_ratio_6m, -- 增值税专用发票开票张数6个月环比

    (CASE WHEN p2.invoice_s_total_sum_l6m is null or p2.invoice_s_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_s_total_sum_l6m=0 AND p2.invoice_s_total_sum_l12m - p2.invoice_s_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_s_total_sum_l6m!=0 AND p2.invoice_s_total_sum_l12m - p2.invoice_s_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_s_total_sum_l6m / (p2.invoice_s_total_sum_l12m - p2.invoice_s_total_sum_l6m)
    END) AS invoice_s_total_sum_ratio_6m, -- 增值税专用发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_c_cnt_l6m is null or p2.invoice_c_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_c_cnt_l6m=0 AND p2.invoice_c_cnt_l12m - p2.invoice_c_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_c_cnt_l6m!=0 AND p2.invoice_c_cnt_l12m - p2.invoice_c_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_c_cnt_l6m / (p2.invoice_c_cnt_l12m - p2.invoice_c_cnt_l6m)
    END ) AS invoice_c_cnt_ratio_6m, -- 增值税普通发票开票张数6个月环比

    (CASE WHEN p2.invoice_c_total_sum_l6m is null or p2.invoice_c_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_c_total_sum_l6m=0 AND p2.invoice_c_total_sum_l12m - p2.invoice_c_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_c_total_sum_l6m!=0 AND p2.invoice_c_total_sum_l12m - p2.invoice_c_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_c_total_sum_l6m / (p2.invoice_c_total_sum_l12m - p2.invoice_c_total_sum_l6m)
    END ) AS invoice_c_total_sum_ratio_6m, -- 增值税普通发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_t_cnt_l6m is null or p2.invoice_t_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_t_cnt_l6m=0 AND p2.invoice_t_cnt_l12m - p2.invoice_t_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_t_cnt_l6m!=0 AND p2.invoice_t_cnt_l12m - p2.invoice_t_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_t_cnt_l6m / (p2.invoice_t_cnt_l12m - p2.invoice_t_cnt_l6m) END) AS invoice_t_cnt_ratio_6m, -- 运输发票开票张数6个月环比

    (CASE WHEN p2.invoice_t_total_sum_l6m is null or p2.invoice_t_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_t_total_sum_l6m=0 AND p2.invoice_t_total_sum_l12m - p2.invoice_t_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_t_total_sum_l6m!=0 AND p2.invoice_t_total_sum_l12m - p2.invoice_t_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_t_total_sum_l6m / (p2.invoice_t_total_sum_l12m - p2.invoice_t_total_sum_l6m) END) AS invoice_t_total_sum_ratio_6m, -- 运输发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_p_cnt_l6m is null or p2.invoice_p_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_p_cnt_l6m=0 AND p2.invoice_p_cnt_l12m - p2.invoice_p_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_p_cnt_l6m!=0 AND p2.invoice_p_cnt_l12m - p2.invoice_p_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_p_cnt_l6m / (p2.invoice_p_cnt_l12m - p2.invoice_p_cnt_l6m)
    END ) AS invoice_p_cnt_ratio_6m, -- 纸质发票开票张数6个月环比

    (CASE WHEN p2.invoice_p_total_sum_l6m is null or p2.invoice_p_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_p_total_sum_l6m=0 AND p2.invoice_p_total_sum_l12m - p2.invoice_p_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_p_total_sum_l6m!=0 AND p2.invoice_p_total_sum_l12m - p2.invoice_p_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_p_total_sum_l6m / (p2.invoice_p_total_sum_l12m - p2.invoice_p_total_sum_l6m)
    END ) AS invoice_p_total_sum_ratio_6m, -- 纸质发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_e_cnt_l6m is null or p2.invoice_e_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_e_cnt_l6m=0 AND p2.invoice_e_cnt_l12m - p2.invoice_e_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_e_cnt_l6m!=0 AND p2.invoice_e_cnt_l12m - p2.invoice_e_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_e_cnt_l6m / (p2.invoice_e_cnt_l12m - p2.invoice_e_cnt_l6m)
    END ) AS invoice_e_cnt_ratio_6m, -- 电子发票开票张数6个月环比

    (CASE WHEN p2.invoice_e_total_sum_l6m is null or p2.invoice_e_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_e_total_sum_l6m=0 AND p2.invoice_e_total_sum_l12m - p2.invoice_e_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_e_total_sum_l6m!=0 AND p2.invoice_e_total_sum_l12m - p2.invoice_e_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_e_total_sum_l6m / (p2.invoice_e_total_sum_l12m - p2.invoice_e_total_sum_l6m) END) AS invoice_e_total_sum_ratio_6m, -- 电子发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_r_cnt_l6m is null or p2.invoice_r_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_r_cnt_l6m=0 AND p2.invoice_r_cnt_l12m - p2.invoice_r_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_r_cnt_l6m!=0 AND p2.invoice_r_cnt_l12m - p2.invoice_r_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_r_cnt_l6m / (p2.invoice_r_cnt_l12m - p2.invoice_r_cnt_l6m)
    END) AS invoice_r_cnt_ratio_6m, -- 卷式发票开票张数6个月环比

    (CASE WHEN p2.invoice_r_total_sum_l6m is null or p2.invoice_r_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_r_total_sum_l6m=0 AND p2.invoice_r_total_sum_l12m - p2.invoice_r_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_r_total_sum_l6m!=0 AND p2.invoice_r_total_sum_l12m - p2.invoice_r_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_r_total_sum_l6m / (p2.invoice_r_total_sum_l12m - p2.invoice_r_total_sum_l6m)
    END ) AS invoice_r_total_sum_ratio_6m, -- 卷式发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_r_all_cnt_l6m is null or p2.invoice_cnt_l6m is null or p2.invoice_r_all_cnt_l712m is null or p2.invoice_cnt_l712m is null THEN -9998
          WHEN  p2.invoice_r_all_cnt_l6m=0 AND p2.invoice_r_all_cnt_l712m =0 THEN -9997
          WHEN p2.invoice_r_all_cnt_l6m!=0 AND p2.invoice_r_all_cnt_l712m=0 THEN -9996
          ELSE (p2.invoice_r_all_cnt_l6m / p2.invoice_cnt_l6m ) / (p2.invoice_r_all_cnt_l712m / p2.invoice_cnt_l712m)
    END ) AS invoice_red_cnt_rate_ratio_6m, -- 红冲发票张数占比6个月环比

    (CASE WHEN p2.invoice_c_all_cnt_l6m is null or p2.invoice_cnt_l6m is null or p2.invoice_c_all_cnt_l712m is null or p2.invoice_cnt_l712m is null THEN -9998
          WHEN  p2.invoice_c_all_cnt_l6m=0 AND p2.invoice_c_all_cnt_l712m =0 THEN -9997
          WHEN p2.invoice_c_all_cnt_l6m!=0 AND p2.invoice_c_all_cnt_l712m=0 THEN -9996
          ELSE (p2.invoice_c_all_cnt_l6m / p2.invoice_cnt_l6m ) / (p2.invoice_c_all_cnt_l712m / p2.invoice_cnt_l712m)
    END ) AS invoice_cancel_cnt_rate_ratio_6m, -- 作废发票张数占比6个月环比


    (CASE WHEN p2.invoice_total_r_sum_l6m is null or p2.invoice_total_sum_l6m is null or p2.invoice_total_r_sum_l12m is null or p2.invoice_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_total_r_sum_l6m=0 AND (p2.invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m=0) THEN -9997
          WHEN p2.invoice_total_r_sum_l6m!=0 AND (p2.invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m=0) THEN -9996
          ELSE (invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m ) / (invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_r_sum_l6m / p2.invoice_total_sum_l6m )
    END ) AS invoice_red_total_sum_rate_ratio_6m, -- 红冲发票合计金额税额总和比率6个月环比

    (CASE WHEN p2.invoice_total_c_sum_l6m is null or p2.invoice_total_sum_l6m is null or p2.invoice_total_c_sum_l12m is null or p2.invoice_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_total_c_sum_l6m=0 AND (p2.invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m=0) THEN -9997
          WHEN p2.invoice_total_c_sum_l6m!=0 AND (p2.invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m=0) THEN -9996
          ELSE (invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m ) / (invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m-invoice_total_c_sum_l6m / p2.invoice_total_sum_l6m )
    END ) AS invoice_cancel_total_sum_rate_ratio_6m, -- 作废发票合计金额税额总和比率6个月环比

    (CASE WHEN p2.invoice_lt100_cnt_l6m is null or p2.invoice_lt100_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_lt100_cnt_l6m=0 AND p2.invoice_lt100_cnt_l12m - p2.invoice_lt100_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_lt100_cnt_l6m!=0 AND p2.invoice_lt100_cnt_l12m - p2.invoice_lt100_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_lt100_cnt_l6m / (p2.invoice_lt100_cnt_l12m - p2.invoice_lt100_cnt_l6m)
    END ) AS invoice_lt100_cnt_ratio_6m, -- 金额小于100发票张数6个月环比

    (CASE WHEN p2.invoice_lt100_total_sum_l6m is null or p2.invoice_lt100_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_lt100_total_sum_l6m=0 AND p2.invoice_lt100_total_sum_l12m - p2.invoice_lt100_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_lt100_total_sum_l6m!=0 AND p2.invoice_lt100_total_sum_l12m - p2.invoice_lt100_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_lt100_total_sum_l6m / (p2.invoice_lt100_total_sum_l12m - p2.invoice_lt100_total_sum_l6m)
    END ) AS invoice_lt100_total_sum_ratio_6m, -- 金额小于100发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_lt1000_cnt_l6m is null or p2.invoice_lt1000_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_lt1000_cnt_l6m=0 AND p2.invoice_lt1000_cnt_l12m - p2.invoice_lt1000_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_lt1000_cnt_l6m!=0 AND p2.invoice_lt1000_cnt_l12m - p2.invoice_lt1000_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_lt1000_cnt_l6m / (p2.invoice_lt1000_cnt_l12m - p2.invoice_lt1000_cnt_l6m)
    END ) AS invoice_lt1000_cnt_ratio_6m, -- 金额小于1000（且大于等于100）发票张数6个月环比

    (CASE WHEN p2.invoice_lt1000_total_sum_l6m is null or p2.invoice_lt1000_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_lt1000_total_sum_l6m=0 AND p2.invoice_lt1000_total_sum_l12m - p2.invoice_lt1000_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_lt1000_total_sum_l6m!=0 AND p2.invoice_lt1000_total_sum_l12m - p2.invoice_lt1000_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_lt1000_total_sum_l6m / (p2.invoice_lt1000_total_sum_l12m - p2.invoice_lt1000_total_sum_l6m)
    END ) AS invoice_lt1000_total_sum_ratio_6m, -- 金额小于1000（且大于等100）发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_lt2500_cnt_l6m is null or p2.invoice_lt2500_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_lt2500_cnt_l6m=0 AND p2.invoice_lt2500_cnt_l12m - p2.invoice_lt2500_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_lt2500_cnt_l6m!=0 AND p2.invoice_lt2500_cnt_l12m - p2.invoice_lt2500_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_lt2500_cnt_l6m / (p2.invoice_lt2500_cnt_l12m - p2.invoice_lt2500_cnt_l6m)
    END ) AS invoice_lt2500_cnt_ratio_6m, -- 金额小于2500（且大于等于1000）发票张数6个月环比

    (CASE WHEN p2.invoice_lt2500_total_sum_l6m is null or p2.invoice_lt2500_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_lt2500_total_sum_l6m=0 AND p2.invoice_lt2500_total_sum_l12m - p2.invoice_lt2500_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_lt2500_total_sum_l6m!=0 AND p2.invoice_lt2500_total_sum_l12m - p2.invoice_lt2500_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_lt2500_total_sum_l6m / (p2.invoice_lt2500_total_sum_l12m - p2.invoice_lt2500_total_sum_l6m)
    END ) AS invoice_lt2500_total_sum_ratio_6m, -- 金额小于2500（且大于等于1000）发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_lt5000_cnt_l6m is null or p2.invoice_lt5000_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_lt5000_cnt_l6m=0 AND p2.invoice_lt5000_cnt_l12m - p2.invoice_lt5000_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_lt5000_cnt_l6m!=0 AND p2.invoice_lt5000_cnt_l12m - p2.invoice_lt5000_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_lt5000_cnt_l6m / (p2.invoice_lt5000_cnt_l12m - p2.invoice_lt5000_cnt_l6m)
    END ) AS invoice_lt5000_cnt_ratio_6m, -- 金额小于5000（且大于等于2500）发票张数6个月环比

    (CASE WHEN p2.invoice_lt5000_total_sum_l6m is null or p2.invoice_lt5000_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_lt5000_total_sum_l6m=0 AND p2.invoice_lt5000_total_sum_l12m - p2.invoice_lt5000_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_lt5000_total_sum_l6m!=0 AND p2.invoice_lt5000_total_sum_l12m - p2.invoice_lt5000_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_lt5000_total_sum_l6m / (p2.invoice_lt5000_total_sum_l12m - p2.invoice_lt5000_total_sum_l6m)
    END ) AS invoice_lt5000_total_sum_ratio_6m, -- 金额小于5000（且大于等于2500）发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_gt10000_cnt_l6m is null or p2.invoice_gt10000_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_gt10000_cnt_l6m=0 AND p2.invoice_gt10000_cnt_l12m - p2.invoice_gt10000_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_gt10000_cnt_l6m!=0 AND p2.invoice_gt10000_cnt_l12m - p2.invoice_gt10000_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_gt10000_cnt_l6m / (p2.invoice_gt10000_cnt_l12m - p2.invoice_gt10000_cnt_l6m)
    END ) AS invoice_gt10000_cnt_ratio_6m, -- 金额大于10000发票张数6个月环比

    (CASE WHEN p2.invoice_gt10000_total_sum_l6m is null or p2.invoice_gt10000_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_gt10000_total_sum_l6m=0 AND p2.invoice_gt10000_total_sum_l12m - p2.invoice_gt10000_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_gt10000_total_sum_l6m!=0 AND p2.invoice_gt10000_total_sum_l12m - p2.invoice_gt10000_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_gt10000_total_sum_l6m / (p2.invoice_gt10000_total_sum_l12m - p2.invoice_gt10000_total_sum_l6m)
    END ) AS invoice_gt10000_total_sum_ratio_6m, -- 金额大于10000发票合计金额税额总和6个月环比

    (CASE WHEN p2.invoice_detail_cnt_l6m is null or p2.invoice_detail_cnt_l12m is null THEN -9998
          WHEN  p2.invoice_detail_cnt_l6m=0 AND p2.invoice_detail_cnt_l12m - p2.invoice_detail_cnt_l6m=0 THEN -9997
          WHEN p2.invoice_detail_cnt_l6m!=0 AND p2.invoice_detail_cnt_l12m - p2.invoice_detail_cnt_l6m=0 THEN -9996
          ELSE p2.invoice_detail_cnt_l6m / (p2.invoice_detail_cnt_l12m - p2.invoice_detail_cnt_l6m)
    END ) AS invoice_detail_cnt_ratio_6m, -- 附有清单的发票张数6个月环比

    (CASE WHEN p2.invoice_detail_total_sum_l6m is null or p2.invoice_detail_total_sum_l12m is null THEN -9998
          WHEN  p2.invoice_detail_total_sum_l6m=0 AND p2.invoice_detail_total_sum_l12m - p2.invoice_detail_total_sum_l6m=0 THEN -9997
          WHEN p2.invoice_detail_total_sum_l6m!=0 AND p2.invoice_detail_total_sum_l12m - p2.invoice_detail_total_sum_l6m=0 THEN -9996
          ELSE p2.invoice_detail_total_sum_l6m / (p2.invoice_detail_total_sum_l12m - p2.invoice_detail_total_sum_l6m)
    END ) AS invoice_detail_total_sum_ratio_6m, -- 附有清单的发票合计金额税额总和6个月环比

    (CASE WHEN p2.buyer_cnt_l6m is null or p2.buyer_cnt_l712m is null THEN -9998
          WHEN  p2.buyer_cnt_l6m=0 AND p2.buyer_cnt_l712m=0 THEN -9997
          WHEN p2.buyer_cnt_l6m!=0 AND p2.buyer_cnt_l712m=0 THEN -9996
          ELSE (p2.buyer_cnt_l6m / p2.buyer_cnt_l712m -1)
    END ) AS buyer_cnt_chg_rate_6m, -- 交易对手数目6个月环比变化率


    (CASE WHEN p2.invoice_sc_cnt_l12m is null or p2.invoice_sc_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_sc_cnt_l12m=0 AND p2.invoice_sc_cnt_l24m-p2.invoice_sc_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_sc_cnt_l12m!=0 AND p2.invoice_sc_cnt_l24m-p2.invoice_sc_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_sc_cnt_l12m / (p2.invoice_sc_cnt_l24m-p2.invoice_sc_cnt_l12m)
    END ) AS invoice_sc_cnt_ratio_12m, -- 增值税开票张数12个月环比

    (CASE WHEN p2.invoice_sc_cancel_cnt_l12m is null or p2.invoice_sc_cancel_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_sc_cancel_cnt_l12m=0 AND p2.invoice_sc_cancel_cnt_l24m-p2.invoice_sc_cancel_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_sc_cancel_cnt_l12m!=0 AND p2.invoice_sc_cnt_l24m-p2.invoice_sc_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_sc_cancel_cnt_l12m / (p2.invoice_sc_cancel_cnt_l24m-p2.invoice_sc_cancel_cnt_l12m)
    END ) AS invoice_sc_cancel_cnt_ratio_12m, -- 增值税发票作废张数12个月环比

    (CASE WHEN p2.invoice_sc_red_cnt_l12m is null or p2.invoice_sc_red_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_sc_red_cnt_l12m=0 AND p2.invoice_sc_red_cnt_l24m-p2.invoice_sc_red_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_sc_red_cnt_l12m!=0 AND p2.invoice_sc_red_cnt_l24m-p2.invoice_sc_red_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_sc_red_cnt_l12m / (p2.invoice_sc_red_cnt_l24m-p2.invoice_sc_red_cnt_l12m)
    END ) AS invoice_sc_red_cnt_ratio_12m, -- 增值税发票红冲张数12个月环比

    (CASE WHEN p2.invoice_sc_total_sum_l12m is null or p2.invoice_sc_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_sc_total_sum_l12m=0 AND p2.invoice_sc_total_sum_l24m-p2.invoice_sc_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_sc_total_sum_l12m!=0 AND p2.invoice_sc_total_sum_l24m-p2.invoice_sc_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_sc_total_sum_l12m / (p2.invoice_sc_total_sum_l24m-p2.invoice_sc_total_sum_l12m)
    END ) AS invoice_sc_total_sum_ratio_12m, -- 增值税发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_cnt_l12m is null or p2.invoice_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_cnt_l12m=0 AND p2.invoice_cnt_l24m - p2.invoice_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_cnt_l12m!=0 AND p2.invoice_cnt_l24m - p2.invoice_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_cnt_l12m / (p2.invoice_cnt_l24m - p2.invoice_cnt_l12m)
    END ) AS invoice_cnt_ratio_12m, -- 所有发票开票张数12个月环比

    (CASE WHEN p2.invoice_total_sum_l12m is null or p2.invoice_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_total_sum_l12m=0 AND p2.invoice_total_sum_l24m - p2.invoice_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_total_sum_l12m!=0 AND p2.invoice_total_sum_l24m - p2.invoice_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_total_sum_l12m / (p2.invoice_total_sum_l24m - p2.invoice_total_sum_l12m)
    END ) AS invoice_total_sum_ratio_12m, -- 所有发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_s_cnt_l12m is null or p2.invoice_s_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_s_cnt_l12m=0 AND p2.invoice_s_cnt_l24m - p2.invoice_s_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_s_cnt_l12m!=0 AND p2.invoice_s_cnt_l24m - p2.invoice_s_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_s_cnt_l12m / (p2.invoice_s_cnt_l24m - p2.invoice_s_cnt_l12m)
    END ) AS invoice_s_cnt_ratio_12m, -- 增值税专用发票开票张数12个月环比

    (CASE WHEN p2.invoice_s_total_sum_l12m is null or p2.invoice_s_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_s_total_sum_l12m=0 AND p2.invoice_s_total_sum_l24m - p2.invoice_s_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_s_total_sum_l12m!=0 AND p2.invoice_s_total_sum_l24m - p2.invoice_s_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_s_total_sum_l12m / (p2.invoice_s_total_sum_l24m - p2.invoice_s_total_sum_l12m)
    END ) AS invoice_s_total_sum_ratio_12m, -- 增值税专用发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_c_cnt_l12m is null or p2.invoice_c_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_c_cnt_l12m=0 AND p2.invoice_c_cnt_l24m - p2.invoice_c_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_c_cnt_l12m!=0 AND p2.invoice_c_cnt_l24m - p2.invoice_c_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_c_cnt_l12m / (p2.invoice_c_cnt_l24m - p2.invoice_c_cnt_l12m)
    END ) AS invoice_c_cnt_ratio_12m, -- 增值税普通发票开票张数12个月环比

    (CASE WHEN p2.invoice_c_total_sum_l12m is null or p2.invoice_c_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_c_total_sum_l12m=0 AND p2.invoice_c_total_sum_l24m - p2.invoice_c_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_c_total_sum_l12m!=0 AND p2.invoice_c_total_sum_l24m - p2.invoice_c_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_c_total_sum_l12m / (p2.invoice_c_total_sum_l24m - p2.invoice_c_total_sum_l12m)
    END ) AS invoice_c_total_sum_ratio_12m, -- 增值税普通发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_t_cnt_l12m is null or p2.invoice_t_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_t_cnt_l12m=0 AND p2.invoice_t_cnt_l24m - p2.invoice_t_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_t_cnt_l12m!=0 AND p2.invoice_t_cnt_l24m - p2.invoice_t_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_t_cnt_l12m / (p2.invoice_t_cnt_l24m - p2.invoice_t_cnt_l12m) END) AS invoice_t_cnt_ratio_12m, -- 运输发票开票张数12个月环比

    (CASE WHEN p2.invoice_t_total_sum_l12m is null or p2.invoice_t_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_t_total_sum_l12m=0 AND p2.invoice_t_total_sum_l24m - p2.invoice_t_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_t_total_sum_l12m!=0 AND p2.invoice_t_total_sum_l24m - p2.invoice_t_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_t_total_sum_l12m / (p2.invoice_t_total_sum_l24m - p2.invoice_t_total_sum_l12m)
    END ) AS invoice_t_total_sum_ratio_12m, -- 运输发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_p_cnt_l12m is null or p2.invoice_p_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_p_cnt_l12m=0 AND p2.invoice_p_cnt_l24m - p2.invoice_p_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_p_cnt_l12m!=0 AND p2.invoice_p_cnt_l24m - p2.invoice_p_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_p_cnt_l12m / (p2.invoice_p_cnt_l24m - p2.invoice_p_cnt_l12m)
    END ) AS invoice_p_cnt_ratio_12m, -- 纸质发票开票张数12个月环比

    (CASE WHEN p2.invoice_p_total_sum_l12m is null or p2.invoice_p_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_p_total_sum_l12m=0 AND p2.invoice_p_total_sum_l24m - p2.invoice_p_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_p_total_sum_l12m!=0 AND p2.invoice_p_total_sum_l24m - p2.invoice_p_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_p_total_sum_l12m / (p2.invoice_p_total_sum_l24m - p2.invoice_p_total_sum_l12m)
    END ) AS invoice_p_total_sum_ratio_12m, -- 纸质发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_e_cnt_l12m is null or p2.invoice_e_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_e_cnt_l12m=0 AND p2.invoice_e_cnt_l24m - p2.invoice_e_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_e_cnt_l12m!=0 AND p2.invoice_e_cnt_l24m - p2.invoice_e_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_e_cnt_l12m / (p2.invoice_e_cnt_l24m - p2.invoice_e_cnt_l12m)
    END ) AS invoice_e_cnt_ratio_12m, -- 电子发票开票张数12个月环比

    (CASE WHEN p2.invoice_e_total_sum_l12m is null or p2.invoice_e_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_e_total_sum_l12m=0 AND p2.invoice_e_total_sum_l24m - p2.invoice_e_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_e_total_sum_l12m!=0 AND p2.invoice_e_total_sum_l24m - p2.invoice_e_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_e_total_sum_l12m / (p2.invoice_e_total_sum_l24m - p2.invoice_e_total_sum_l12m)
    END ) AS invoice_e_total_sum_ratio_12m, -- 电子发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_r_cnt_l12m is null or p2.invoice_r_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_r_cnt_l12m=0 AND p2.invoice_r_cnt_l24m - p2.invoice_r_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_r_cnt_l12m!=0 AND p2.invoice_r_cnt_l24m - p2.invoice_r_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_r_cnt_l12m / (p2.invoice_r_cnt_l24m - p2.invoice_r_cnt_l12m)
    END ) AS invoice_r_cnt_ratio_12m, -- 卷式发票开票张数12个月环比

    (CASE WHEN p2.invoice_r_total_sum_l12m is null or p2.invoice_r_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_r_total_sum_l12m=0 AND p2.invoice_r_total_sum_l24m - p2.invoice_r_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_r_total_sum_l12m!=0 AND p2.invoice_r_total_sum_l24m - p2.invoice_r_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_r_total_sum_l12m / (p2.invoice_r_total_sum_l24m - p2.invoice_r_total_sum_l12m)
    END ) AS invoice_r_total_sum_ratio_12m, -- 卷式发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_r_all_cnt_l12m is null or p2.invoice_cnt_l12m is null or p2.invoice_r_all_cnt_l1324m is null or p2.invoice_cnt_l1324m is null THEN -9998
          WHEN  p2.invoice_r_all_cnt_l12m=0 AND p2.invoice_r_all_cnt_l1324m =0 THEN -9997
          WHEN p2.invoice_r_all_cnt_l12m!=0 AND p2.invoice_r_all_cnt_l1324m=0 THEN -9996
          ELSE (p2.invoice_r_all_cnt_l12m / p2.invoice_cnt_l12m ) / (p2.invoice_r_all_cnt_l1324m / p2.invoice_cnt_l1324m)
    END ) AS invoice_red_cnt_rate_ratio_12m, -- 红冲发票张数占比12个月环比

    (CASE WHEN p2.invoice_c_all_cnt_l12m is null or p2.invoice_cnt_l12m is null or p2.invoice_c_all_cnt_l1324m is null or p2.invoice_cnt_l1324m is null THEN -9998
          WHEN  p2.invoice_c_all_cnt_l12m=0 AND p2.invoice_c_all_cnt_l1324m =0 THEN -9997
          WHEN p2.invoice_c_all_cnt_l12m!=0 AND p2.invoice_c_all_cnt_l1324m=0 THEN -9996
          ELSE (p2.invoice_c_all_cnt_l12m / p2.invoice_cnt_l12m ) / (p2.invoice_c_all_cnt_l1324m / p2.invoice_cnt_l1324m)
    END ) AS invoice_cancel_cnt_rate_ratio_12m, -- 作废发票张数占比12个月环比

    (CASE WHEN p2.invoice_total_r_sum_l12m is null or p2.invoice_total_sum_l12m is null or p2.invoice_total_r_sum_l24m is null or p2.invoice_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_total_r_sum_l12m=0 AND (p2.invoice_total_r_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m=0) THEN -9997
          WHEN p2.invoice_total_r_sum_l12m!=0 AND (p2.invoice_total_r_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m=0) THEN -9996
          ELSE (invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m ) / (invoice_total_r_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_r_sum_l12m / p2.invoice_total_sum_l12m )
    END ) AS invoice_red_total_sum_rate_ratio_12m, -- 红冲发票合计金额税额总和比率12个月环比

    (CASE WHEN p2.invoice_total_c_sum_l12m is null or p2.invoice_total_sum_l12m is null or p2.invoice_total_c_sum_l24m is null or p2.invoice_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_total_c_sum_l12m=0 AND (p2.invoice_total_c_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m=0) THEN -9997
          WHEN p2.invoice_total_c_sum_l12m!=0 AND (p2.invoice_total_c_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m=0) THEN -9996
          ELSE (invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m ) / (invoice_total_c_sum_l24m / p2.invoice_total_sum_l24m-invoice_total_c_sum_l12m / p2.invoice_total_sum_l12m )
    END ) AS invoice_cancel_total_sum_rate_ratio_12m, -- 作废发票合计金额税额总和比率12个月环比

    (CASE WHEN p2.invoice_lt100_cnt_l12m is null or p2.invoice_lt100_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_lt100_cnt_l12m=0 AND p2.invoice_lt100_cnt_l24m - p2.invoice_lt100_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_lt100_cnt_l12m!=0 AND p2.invoice_lt100_cnt_l24m - p2.invoice_lt100_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_lt100_cnt_l12m / (p2.invoice_lt100_cnt_l24m - p2.invoice_lt100_cnt_l12m)
    END ) AS invoice_lt100_cnt_ratio_12m, -- 金额小于100发票张数12个月环比

    (CASE WHEN p2.invoice_lt100_total_sum_l12m is null or p2.invoice_lt100_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_lt100_total_sum_l12m=0 AND p2.invoice_lt100_total_sum_l24m - p2.invoice_lt100_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_lt100_total_sum_l12m!=0 AND p2.invoice_lt100_total_sum_l24m - p2.invoice_lt100_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_lt100_total_sum_l12m / (p2.invoice_lt100_total_sum_l24m - p2.invoice_lt100_total_sum_l12m)
    END ) AS invoice_lt100_total_sum_ratio_12m, -- 金额小于100发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_lt1000_cnt_l12m is null or p2.invoice_lt1000_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_lt1000_cnt_l12m=0 AND p2.invoice_lt1000_cnt_l24m - p2.invoice_lt1000_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_lt1000_cnt_l12m!=0 AND p2.invoice_lt1000_cnt_l24m - p2.invoice_lt1000_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_lt1000_cnt_l12m / (p2.invoice_lt1000_cnt_l24m - p2.invoice_lt1000_cnt_l12m)
    END ) AS invoice_lt1000_cnt_ratio_12m, -- 金额小于1000（且大于等于100）发票张数12个月环比

    (CASE WHEN p2.invoice_lt1000_total_sum_l12m is null or p2.invoice_lt1000_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_lt1000_total_sum_l12m=0 AND p2.invoice_lt1000_total_sum_l24m - p2.invoice_lt1000_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_lt1000_total_sum_l12m!=0 AND p2.invoice_lt1000_total_sum_l24m - p2.invoice_lt1000_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_lt1000_total_sum_l12m / (p2.invoice_lt1000_total_sum_l24m - p2.invoice_lt1000_total_sum_l12m)
    END ) AS invoice_lt1000_total_sum_ratio_12m, -- 金额小于1000（且大于等100）发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_lt2500_cnt_l12m is null or p2.invoice_lt2500_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_lt2500_cnt_l12m=0 AND p2.invoice_lt2500_cnt_l24m - p2.invoice_lt2500_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_lt2500_cnt_l12m!=0 AND p2.invoice_lt2500_cnt_l24m - p2.invoice_lt2500_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_lt2500_cnt_l12m / (p2.invoice_lt2500_cnt_l24m - p2.invoice_lt2500_cnt_l12m)
    END ) AS invoice_lt2500_cnt_ratio_12m, -- 金额小于2500（且大于等于1000）发票张数12个月环比

    (CASE WHEN p2.invoice_lt2500_total_sum_l12m is null or p2.invoice_lt2500_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_lt2500_total_sum_l12m=0 AND p2.invoice_lt2500_total_sum_l24m - p2.invoice_lt2500_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_lt2500_total_sum_l12m!=0 AND p2.invoice_lt2500_total_sum_l24m - p2.invoice_lt2500_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_lt2500_total_sum_l12m / (p2.invoice_lt2500_total_sum_l24m - p2.invoice_lt2500_total_sum_l12m)
    END ) AS invoice_lt2500_total_sum_ratio_12m, -- 金额小于2500（且大于等于1000）发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_lt5000_cnt_l12m is null or p2.invoice_lt5000_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_lt5000_cnt_l12m=0 AND p2.invoice_lt5000_cnt_l24m - p2.invoice_lt5000_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_lt5000_cnt_l12m!=0 AND p2.invoice_lt5000_cnt_l24m - p2.invoice_lt5000_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_lt5000_cnt_l12m / (p2.invoice_lt5000_cnt_l24m - p2.invoice_lt5000_cnt_l12m)
    END ) AS invoice_lt5000_cnt_ratio_12m, -- 金额小于5000（且大于等于2500）发票张数12个月环比

    (CASE WHEN p2.invoice_lt5000_total_sum_l12m is null or p2.invoice_lt5000_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_lt5000_total_sum_l12m=0 AND p2.invoice_lt5000_total_sum_l24m - p2.invoice_lt5000_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_lt5000_total_sum_l12m!=0 AND p2.invoice_lt5000_total_sum_l24m - p2.invoice_lt5000_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_lt5000_total_sum_l12m / (p2.invoice_lt5000_total_sum_l24m - p2.invoice_lt5000_total_sum_l12m)
    END ) AS invoice_lt5000_total_sum_ratio_12m, -- 金额小于5000（且大于等于2500）发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_gt10000_cnt_l12m is null or p2.invoice_gt10000_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_gt10000_cnt_l12m=0 AND p2.invoice_gt10000_cnt_l24m - p2.invoice_gt10000_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_gt10000_cnt_l12m!=0 AND p2.invoice_gt10000_cnt_l24m - p2.invoice_gt10000_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_gt10000_cnt_l12m / (p2.invoice_gt10000_cnt_l24m - p2.invoice_gt10000_cnt_l12m)
    END ) AS invoice_gt10000_cnt_ratio_12m, -- 金额大于10000发票张数12个月环比

    (CASE WHEN p2.invoice_gt10000_total_sum_l12m is null or p2.invoice_gt10000_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_gt10000_total_sum_l12m=0 AND p2.invoice_gt10000_total_sum_l24m - p2.invoice_gt10000_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_gt10000_total_sum_l12m!=0 AND p2.invoice_gt10000_total_sum_l24m - p2.invoice_gt10000_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_gt10000_total_sum_l12m / (p2.invoice_gt10000_total_sum_l24m - p2.invoice_gt10000_total_sum_l12m)
    END ) AS invoice_gt10000_total_sum_ratio_12m, -- 金额大于10000发票合计金额税额总和12个月环比

    (CASE WHEN p2.invoice_detail_cnt_l12m is null or p2.invoice_detail_cnt_l24m is null THEN -9998
          WHEN  p2.invoice_detail_cnt_l12m=0 AND p2.invoice_detail_cnt_l24m - p2.invoice_detail_cnt_l12m=0 THEN -9997
          WHEN p2.invoice_detail_cnt_l12m!=0 AND p2.invoice_detail_cnt_l24m - p2.invoice_detail_cnt_l12m=0 THEN -9996
          ELSE p2.invoice_detail_cnt_l12m / (p2.invoice_detail_cnt_l24m - p2.invoice_detail_cnt_l12m)
    END ) AS invoice_detail_cnt_ratio_12m, -- 附有清单的发票张数12个月环比

    (CASE WHEN p2.invoice_detail_total_sum_l12m is null or p2.invoice_detail_total_sum_l24m is null THEN -9998
          WHEN  p2.invoice_detail_total_sum_l12m=0 AND p2.invoice_detail_total_sum_l24m - p2.invoice_detail_total_sum_l12m=0 THEN -9997
          WHEN p2.invoice_detail_total_sum_l12m!=0 AND p2.invoice_detail_total_sum_l24m - p2.invoice_detail_total_sum_l12m=0 THEN -9996
          ELSE p2.invoice_detail_total_sum_l12m / (p2.invoice_detail_total_sum_l24m - p2.invoice_detail_total_sum_l12m)
    END ) AS invoice_detail_total_sum_ratio_12m, -- 附有清单的发票合计金额税额总和12个月环比

    (CASE WHEN p2.buyer_cnt_l12m is null or p2.buyer_cnt_l1324m is null THEN -9998
          WHEN  p2.buyer_cnt_l12m=0 AND p2.buyer_cnt_l1324m=0 THEN -9997
          WHEN p2.buyer_cnt_l12m!=0 AND p2.buyer_cnt_l1324m=0 THEN -9996
          ELSE (p2.buyer_cnt_l12m / p2.buyer_cnt_l1324m -1)
    END ) AS buyer_cnt_chg_rate_12m -- 交易对手数目12个月环比变化率

from
    dm_taxloan.cross_month_tmp p1
left join dm_taxloan.c_p2 p2  -- 从hive中读出p2表使用
on
    p1.mcht_cd=p2.mcht_cd and p1.month=p2.month

