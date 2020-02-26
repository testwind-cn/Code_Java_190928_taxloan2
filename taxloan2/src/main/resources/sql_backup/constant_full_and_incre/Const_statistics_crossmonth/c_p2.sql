



select
    distinct r1.mcht_cd,
    r1.month,

-- 以下为新补充的字段
    r1.invoice_r_all_cnt_l3m,
    r1.invoice_c_all_cnt_l3m,
    r1.invoice_total_r_sum_l3m,
    r1.invoice_total_c_sum_l3m,
    r1.invoice_r_all_cnt_l6m,
    r1.invoice_c_all_cnt_l6m,
    r1.invoice_total_r_sum_l6m,
    r1.invoice_total_c_sum_l6m,
    r1.invoice_r_all_cnt_l12m,
    r1.invoice_c_all_cnt_l12m,
    r1.invoice_total_r_sum_l12m,
    r1.invoice_total_c_sum_l12m,
    r1.invoice_r_all_cnt_l24m,
    r1.invoice_c_all_cnt_l24m,
    r1.invoice_total_r_sum_l24m,
    r1.invoice_total_c_sum_l24m,
    r1.invoice_r_all_cnt_l712m,
    r1.invoice_c_all_cnt_l712m,
    r1.invoice_cnt_l712m,
    r1.invoice_cnt_l1324m,
    r1.invoice_r_all_cnt_l1324m,
    r1.invoice_c_all_cnt_l1324m,
    r1.buyer_cnt_l712m,
    r1.buyer_cnt_l1324m,

    r1.invoice_sc_cnt_l3m, -- 近3月增值税开票张数
    r1.invoice_sc_cancel_cnt_l3m, -- 近3月增值税发票作废张数
    r1.invoice_sc_red_cnt_l3m, -- 近3月增值税发票红冲张数
    r1.invoice_sc_total_sum_l3m, -- 近3月增值税发票合计金额税额总和
    r1.invoice_cnt_l3m, -- 近3月所有发票开票张数
    r1.invoice_cr_cnt_l3m, -- 近3月所有发票作废红冲张数
    r1.invoice_total_sum_l3m, -- 近3月所有发票合计金额税额总和
    r1.invoice_s_cnt_l3m, -- 近3月增值税专用发票开票张数
    r1.invoice_s_cancel_cnt_l3m, -- 近3月增值税专用发票作废张数
    r1.invoice_s_red_cnt_l3m, -- 近3月增值税专用发票红冲张数
    r1.invoice_s_total_sum_l3m, -- 近3月增值税专用发票合计金额税额总和
    r1.invoice_c_cnt_l3m, -- 近3月增值税普通发票开票张数
    r1.invoice_c_cancel_cnt_l3m, -- 近3月增值税普通发票作废张数
    r1.invoice_c_red_cnt_l3m, -- 近3月增值税普通发票红冲张数
    r1.invoice_c_total_sum_l3m, -- 近3月增值税普通发票合计金额税额总和
    r1.invoice_t_cnt_l3m, -- 近3月运输发票开票张数
    r1.invoice_t_cancel_cnt_l3m, -- 近3月运输发票作废张数
    r1.invoice_t_red_cnt_l3m, -- 近3月运输发票红冲张数
    r1.invoice_t_total_sum_l3m, -- 近3月运输发票合计金额税额总和
    r1.invoice_p_cnt_l3m, -- 近3月纸质发票开票张数
    r1.invoice_p_cancel_cnt_l3m, -- 近3月纸质发票作废张数
    r1.invoice_p_red_cnt_l3m, -- 近3月纸质发票红冲张数
    r1.invoice_p_total_sum_l3m, -- 近3月纸质发票合计金额税额总和
    r1.invoice_e_cnt_l3m, -- 近3月电子发票开票张数
    r1.invoice_e_cancel_cnt_l3m, -- 近3月电子发票作废张数
    r1.invoice_e_red_cnt_l3m, -- 近3月电子发票红冲张数
    r1.invoice_e_total_sum_l3m, -- 近3月电子发票合计金额税额总和
    r1.invoice_r_cnt_l3m, -- 近3月卷式发票开票张数
    r1.invoice_r_cancel_cnt_l3m, -- 近3月卷式发票作废张数
    r1.invoice_r_red_cnt_l3m, -- 近3月卷式发票红冲张数
    r1.invoice_r_total_sum_l3m, -- 近3月卷式发票合计金额税额总和

    r1.invoice_lt100_cnt_l3m, -- 近3月金额小于100发票张数
    r1.invoice_lt100_cr_cnt_l3m, -- 近3月金额小于100发票作废红冲张数
    r1.invoice_lt100_total_sum_l3m, -- 近3月金额小于100发票合计金额税额总和
    r1.invoice_lt1000_cnt_l3m, -- 近3月金额小于1000（且大于等于100）发票张数
    r1.invoice_lt1000_cr_cnt_l3m, -- 近3月金额小于1000（且大于等于100）发票作废红冲张数
    r1.invoice_lt1000_total_sum_l3m, -- 近3月金额小于1000（且大于等于100）发票合计金额税额总和
    r1.invoice_lt2500_cnt_l3m, -- 近3月金额小于2500且（且大于等于1000）发票张数
    r1.invoice_lt2500_cr_cnt_l3m, -- 近3月金额小于2500（且大于等于1000）发票作废红冲张数
    r1.invoice_lt2500_total_sum_l3m, -- 近3月金额小于2500（且大于等于1000）发票合计金额税额总和
    r1.invoice_lt5000_cnt_l3m, -- 近3月金额小于5000（且大于等于2500）发票张数
    r1.invoice_lt5000_cr_cnt_l3m, -- 近3月金额小于5000（且大于等于2500）发票作废红冲张数
    r1.invoice_lt5000_total_sum_l3m, -- 近3月金额小于5000（且大于等于2500）发票合计金额税额总和
    r1.invoice_lt10000_cnt_l3m, -- 近3月金额小于10000（且大于等于5000）发票张数
    r1.invoice_lt10000_cr_cnt_l3m, -- 近3月金额小于10000（且大于等于5000）发票作废红冲张数
    r1.invoice_lt10000_total_sum_l3m, -- 近3月金额小于10000（且大于等于5000）发票合计金额税额总和
    r1.invoice_gt10000_cnt_l3m, -- 近3月金额大于10000发票张数
    r1.invoice_gt10000_cr_cnt_l3m, -- 近3月金额大于10000发票作废红冲张数
    r1.invoice_gt10000_total_sum_l3m, -- 近3月金额大于10000发票合计金额税额总和

    r1.invoice_detail_cnt_l3m, -- 近3月附有清单的发票张数
    r1.invoice_detail_cr_cnt_l3m, -- 近3月附有清单的发票作废红冲张数
    r1.invoice_detail_total_sum_l3m, -- 近3月附有清单的发票合计金额税额总和
    r1.buyer_cnt_l3m, -- 近3月交易对手数目

    r1.invoice_sc_cnt_l6m, -- 近6月增值税开票张数
    r1.invoice_sc_cancel_cnt_l6m, -- 近6月增值税发票作废张数
    r1.invoice_sc_red_cnt_l6m, -- 近6月增值税发票红冲张数
    r1.invoice_sc_total_sum_l6m, -- 近6月增值税发票合计金额税额总和
    r1.invoice_cnt_l6m, -- 近6月所有发票开票张数
    r1.invoice_cr_cnt_l6m, -- 近6月所有发票作废红冲张数
    r1.invoice_total_sum_l6m, -- 近6月所有发票合计金额税额总和
    r1.invoice_s_cnt_l6m, -- 近6月增值税专用发票开票张数
    r1.invoice_s_cancel_cnt_l6m, -- 近6月增值税专用发票作废张数
    r1.invoice_s_red_cnt_l6m, -- 近6月增值税专用发票红冲张数
    r1.invoice_s_total_sum_l6m, -- 近6月增值税专用发票合计金额税额总和
    r1.invoice_c_cnt_l6m, -- 近6月增值税普通发票开票张数
    r1.invoice_c_cancel_cnt_l6m, -- 近6月增值税普通发票作废张数
    r1.invoice_c_red_cnt_l6m, -- 近6月增值税普通发票红冲张数
    r1.invoice_c_total_sum_l6m, -- 近6月增值税普通发票合计金额税额总和
    r1.invoice_t_cnt_l6m, -- 近6月运输发票开票张数
    r1.invoice_t_cancel_cnt_l6m, -- 近6月运输发票作废张数
    r1.invoice_t_red_cnt_l6m, -- 近6月运输发票红冲张数
    r1.invoice_t_total_sum_l6m, -- 近6月运输发票合计金额税额总和
    r1.invoice_p_cnt_l6m, -- 近6月纸质发票开票张数
    r1.invoice_p_cancel_cnt_l6m, -- 近6月纸质发票作废张数
    r1.invoice_p_red_cnt_l6m, -- 近6月纸质发票红冲张数
    r1.invoice_p_total_sum_l6m, -- 近6月纸质发票合计金额税额总和
    r1.invoice_e_cnt_l6m, -- 近6月电子发票开票张数
    r1.invoice_e_cancel_cnt_l6m, -- 近6月电子发票作废张数
    r1.invoice_e_red_cnt_l6m, -- 近6月电子发票红冲张数
    r1.invoice_e_total_sum_l6m, -- 近6月电子发票合计金额税额总和
    r1.invoice_r_cnt_l6m, -- 近6月卷式发票开票张数
    r1.invoice_r_cancel_cnt_l6m, -- 近6月卷式发票作废张数
    r1.invoice_r_red_cnt_l6m, -- 近6月卷式发票红冲张数
    r1.invoice_r_total_sum_l6m, -- 近6月卷式发票合计金额税额总和


    r1.invoice_lt100_cnt_l6m, -- 近6月金额小于100发票张数
    r1.invoice_lt100_cr_cnt_l6m, -- 近6月金额小于100发票作废红冲张数
    r1.invoice_lt100_total_sum_l6m, -- 近6月金额小于100发票合计金额税额总和
    r1.invoice_lt1000_cnt_l6m, -- 近6月金额小于1000（且大于等于100）发票张数
    r1.invoice_lt1000_cr_cnt_l6m, -- 近6月金额小于1000（且大于等于100）发票作废红冲张数
    r1.invoice_lt1000_total_sum_l6m, -- 近6月金额小于1000（且大于等于100）发票合计金额税额总和
    r1.invoice_lt2500_cnt_l6m, -- 近6月金额小于2500且（且大于等于1000）发票张数
    r1.invoice_lt2500_cr_cnt_l6m, -- 近6月金额小于2500（且大于等于1000）发票作废红冲张数
    r1.invoice_lt2500_total_sum_l6m, -- 近6月金额小于2500（且大于等于1000）发票合计金额税额总和
    r1.invoice_lt5000_cnt_l6m, -- 近6月金额小于5000（且大于等于2500）发票张数
    r1.invoice_lt5000_cr_cnt_l6m, -- 近6月金额小于5000（且大于等于2500）发票作废红冲张数
    r1.invoice_lt5000_total_sum_l6m, -- 近6月金额小于5000（且大于等于2500）发票合计金额税额总和
    r1.invoice_lt10000_cnt_l6m, -- 近6月金额小于10000（且大于等于5000）发票张数
    r1.invoice_lt10000_cr_cnt_l6m, -- 近6月金额小于10000（且大于等于5000）发票作废红冲张数
    r1.invoice_lt10000_total_sum_l6m, -- 近6月金额小于10000（且大于等于5000）发票合计金额税额总和
    r1.invoice_gt10000_cnt_l6m, -- 近6月金额大于10000发票张数
    r1.invoice_gt10000_cr_cnt_l6m, -- 近6月金额大于10000发票作废红冲张数
    r1.invoice_gt10000_total_sum_l6m, -- 近6月金额大于10000发票合计金额税额总和
    r1.invoice_detail_cnt_l6m, -- 近6月附有清单的发票张数
    r1.invoice_detail_cr_cnt_l6m, -- 近6月附有清单的发票作废红冲张数
    r1.invoice_detail_total_sum_l6m, -- 近6月附有清单的发票合计金额税额总和
    r1.buyer_cnt_l6m, -- 近6月交易对手数目

    r4.buyer_a_invoice_cnt_l6m, -- 在统计月归为A类的交易对手近6月开票张数
    r4.buyer_b_invoice_cnt_l6m, -- 在统计月归为B类的交易对手近6月开票张数
    r4.buyer_c_invoice_cnt_l6m, -- 在统计月归为C类的交易对手近6月开票张数
    r4.buyer_f_invoice_cnt_l6m, -- 在统计月归为F类的交易对手近6月开票张数
    r4.buyer_a_amt_sum_l6m, -- 在统计月归为A类的交易对手近6月合计金额总和
    r4.buyer_b_amt_sum_l6m, -- 在统计月归为B类的交易对手近6月合计金额总和
    r4.buyer_c_amt_sum_l6m, -- 在统计月归为C类的交易对手近6月合计金额总和
    r4.buyer_f_amt_sum_l6m, -- 在统计月归为F类的交易对手近6月合计金额总和

    r1.invoice_sc_cnt_l12m, -- 近12月增值税开票张数
    r1.invoice_sc_cancel_cnt_l12m, -- 近12月增值税发票作废张数
    r1.invoice_sc_red_cnt_l12m, -- 近12月增值税发票红冲张数
    r1.invoice_sc_total_sum_l12m, -- 近12月增值税发票合计金额税额总和
    r1.invoice_cnt_l12m, -- 近12月所有发票开票张数
    r1.invoice_cr_cnt_l12m, -- 近12月所有发票作废红冲张数
    r1.invoice_total_sum_l12m, -- 近12月所有发票合计金额税额总和
    r1.invoice_s_cnt_l12m, -- 近12月增值税专用发票开票张数
    r1.invoice_s_cancel_cnt_l12m, -- 近12月增值税专用发票作废张数
    r1.invoice_s_red_cnt_l12m, -- 近12月增值税专用发票红冲张数
    r1.invoice_s_total_sum_l12m, -- 近12月增值税专用发票合计金额税额总和
    r1.invoice_c_cnt_l12m, -- 近12月增值税普通发票开票张数
    r1.invoice_c_cancel_cnt_l12m, -- 近12月增值税普通发票作废张数
    r1.invoice_c_red_cnt_l12m, -- 近12月增值税普通发票红冲张数
    r1.invoice_c_total_sum_l12m, -- 近12月增值税普通发票合计金额税额总和
    r1.invoice_t_cnt_l12m, -- 近12月运输发票开票张数
    r1.invoice_t_cancel_cnt_l12m, -- 近12月运输发票作废张数
    r1.invoice_t_red_cnt_l12m, -- 近12月运输发票红冲张数
    r1.invoice_t_total_sum_l12m, -- 近12月运输发票合计金额税额总和
    r1.invoice_p_cnt_l12m, -- 近12月纸质发票开票张数
    r1.invoice_p_cancel_cnt_l12m, -- 近12月纸质发票作废张数
    r1.invoice_p_red_cnt_l12m, -- 近12月纸质发票红冲张数
    r1.invoice_p_total_sum_l12m, -- 近12月纸质发票合计金额税额总和
    r1.invoice_e_cnt_l12m, -- 近12月电子发票开票张数
    r1.invoice_e_cancel_cnt_l12m, -- 近12月电子发票作废张数
    r1.invoice_e_red_cnt_l12m, -- 近12月电子发票红冲张数
    r1.invoice_e_total_sum_l12m, -- 近12月电子发票合计金额税额总和
    r1.invoice_r_cnt_l12m, -- 近12月卷式发票开票张数
    r1.invoice_r_cancel_cnt_l12m, -- 近12月卷式发票作废张数
    r1.invoice_r_red_cnt_l12m, -- 近12月卷式发票红冲张数
    r1.invoice_r_total_sum_l12m, -- 近12月卷式发票合计金额税额总和

    r1.invoice_lt100_cnt_l12m, -- 近12月金额小于100发票张数
    r1.invoice_lt100_cr_cnt_l12m, -- 近12月金额小于100发票作废红冲张数
    r1.invoice_lt100_total_sum_l12m, -- 近12月金额小于100发票合计金额税额总和
    r1.invoice_lt1000_cnt_l12m, -- 近12月金额小于1000（且大于等于100）发票张数
    r1.invoice_lt1000_cr_cnt_l12m, -- 近12月金额小于1000（且大于等于100）发票作废红冲张数
    r1.invoice_lt1000_total_sum_l12m, -- 近12月金额小于1000（且大于等于100）发票合计金额税额总和
    r1.invoice_lt2500_cnt_l12m, -- 近12月金额小于2500且（且大于等于1000）发票张数
    r1.invoice_lt2500_cr_cnt_l12m, -- 近12月金额小于2500（且大于等于1000）发票作废红冲张数
    r1.invoice_lt2500_total_sum_l12m, -- 近12月金额小于2500（且大于等于1000）发票合计金额税额总和
    r1.invoice_lt5000_cnt_l12m, -- 近12月金额小于5000（且大于等于2500）发票张数
    r1.invoice_lt5000_cr_cnt_l12m, -- 近12月金额小于5000（且大于等于2500）发票作废红冲张数
    r1.invoice_lt5000_total_sum_l12m, -- 近12月金额小于5000（且大于等于2500）发票合计金额税额总和
    r1.invoice_lt10000_cnt_l12m, -- 近12月金额小于10000（且大于等于5000）发票张数
    r1.invoice_lt10000_cr_cnt_l12m, -- 近12月金额小于10000（且大于等于5000）发票作废红冲张数
    r1.invoice_lt10000_total_sum_l12m, -- 近12月金额小于10000（且大于等于5000）发票合计金额税额总和
    r1.invoice_gt10000_cnt_l12m, -- 近12月金额大于10000发票张数
    r1.invoice_gt10000_cr_cnt_l12m, -- 近12月金额大于10000发票作废红冲张数
    r1.invoice_gt10000_total_sum_l12m, -- 近12月金额大于10000发票合计金额税额总和
    r1.invoice_detail_cnt_l12m, -- 近12月附有清单的发票张数
    r1.invoice_detail_cr_cnt_l12m, -- 近12月附有清单的发票作废红冲张数
    r1.invoice_detail_total_sum_l12m, -- 近12月附有清单的发票合计金额税额总和
    r1.buyer_cnt_l12m, -- 近12月交易对手数目
    r4.buyer_a_invoice_cnt_l12m, -- 在统计月归为A类的交易对手近12月开票张数
    r4.buyer_b_invoice_cnt_l12m, -- 在统计月归为B类的交易对手近12月开票张数
    r4.buyer_c_invoice_cnt_l12m, -- 在统计月归为C类的交易对手近12月开票张数
    r4.buyer_f_invoice_cnt_l12m, -- 在统计月归为F类的交易对手近12月开票张数
    r4.buyer_a_amt_sum_l12m, -- 在统计月归为A类的交易对手近12月合计金额总和
    r4.buyer_b_amt_sum_l12m, -- 在统计月归为B类的交易对手近12月合计金额总和
    r4.buyer_c_amt_sum_l12m, -- 在统计月归为C类的交易对手近12月合计金额总和
    r4.buyer_f_amt_sum_l12m, -- 在统计月归为F类的交易对手近12月合计金额总和

    r1.invoice_sc_cnt_l24m, -- 近24月增值税开票张数
    r1.invoice_sc_cancel_cnt_l24m, -- 近24月增值税发票作废张数
    r1.invoice_sc_red_cnt_l24m, -- 近24月增值税发票红冲张数
    r1.invoice_sc_total_sum_l24m, -- 近24月增值税发票合计金额税额总和
    r1.invoice_cnt_l24m, -- 近24月所有发票开票张数
    r1.invoice_cr_cnt_l24m, -- 近24月所有发票作废红冲张数
    r1.invoice_total_sum_l24m, -- 近24月所有发票合计金额税额总和
    r1.invoice_s_cnt_l24m, -- 近24月增值税专用发票开票张数
    r1.invoice_s_cancel_cnt_l24m, -- 近24月增值税专用发票作废张数
    r1.invoice_s_red_cnt_l24m, -- 近24月增值税专用发票红冲张数
    r1.invoice_s_total_sum_l24m, -- 近24月增值税专用发票合计金额税额总和
    r1.invoice_c_cnt_l24m, -- 近24月增值税普通发票开票张数
    r1.invoice_c_cancel_cnt_l24m, -- 近24月增值税普通发票作废张数
    r1.invoice_c_red_cnt_l24m, -- 近24月增值税普通发票红冲张数
    r1.invoice_c_total_sum_l24m, -- 近24月增值税普通发票合计金额税额总和
    r1.invoice_t_cnt_l24m, -- 近24月运输发票开票张数
    r1.invoice_t_cancel_cnt_l24m, -- 近24月运输发票作废张数
    r1.invoice_t_red_cnt_l24m, -- 近24月运输发票红冲张数
    r1.invoice_t_total_sum_l24m, -- 近24月运输发票合计金额税额总和
    r1.invoice_p_cnt_l24m, -- 近24月纸质发票开票张数
    r1.invoice_p_cancel_cnt_l24m, -- 近24月纸质发票作废张数
    r1.invoice_p_red_cnt_l24m, -- 近24月纸质发票红冲张数
    r1.invoice_p_total_sum_l24m, -- 近24月纸质发票合计金额税额总和
    r1.invoice_e_cnt_l24m, -- 近24月电子发票开票张数
    r1.invoice_e_cancel_cnt_l24m, -- 近24月电子发票作废张数
    r1.invoice_e_red_cnt_l24m, -- 近24月电子发票红冲张数
    r1.invoice_e_total_sum_l24m, -- 近24月电子发票合计金额税额总和
    r1.invoice_r_cnt_l24m, -- 近24月卷式发票开票张数
    r1.invoice_r_cancel_cnt_l24m, -- 近24月卷式发票作废张数
    r1.invoice_r_red_cnt_l24m, -- 近24月卷式发票红冲张数
    r1.invoice_r_total_sum_l24m, -- 近24月卷式发票合计金额税额总和

    r1.invoice_lt100_cnt_l24m, -- 近24月金额小于100发票张数
    r1.invoice_lt100_cr_cnt_l24m, -- 近24月金额小于100发票作废红冲张数
    r1.invoice_lt100_total_sum_l24m, -- 近24月金额小于100发票合计金额税额总和
    r1.invoice_lt1000_cnt_l24m, -- 近24月金额小于1000（且大于等于100）发票张数
    r1.invoice_lt1000_cr_cnt_l24m, -- 近24月金额小于1000（且大于等于100）发票作废红冲张数
    r1.invoice_lt1000_total_sum_l24m, -- 近24月金额小于1000（且大于等于100）发票合计金额税额总和
    r1.invoice_lt2500_cnt_l24m, -- 近24月金额小于2500且（且大于等于1000）发票张数
    r1.invoice_lt2500_cr_cnt_l24m, -- 近24月金额小于2500（且大于等于1000）发票作废红冲张数
    r1.invoice_lt2500_total_sum_l24m, -- 近24月金额小于2500（且大于等于1000）发票合计金额税额总和
    r1.invoice_lt5000_cnt_l24m, -- 近24月金额小于5000（且大于等于2500）发票张数
    r1.invoice_lt5000_cr_cnt_l24m, -- 近24月金额小于5000（且大于等于2500）发票作废红冲张数
    r1.invoice_lt5000_total_sum_l24m, -- 近24月金额小于5000（且大于等于2500）发票合计金额税额总和
    r1.invoice_lt10000_cnt_l24m, -- 近24月金额小于10000（且大于等于5000）发票张数
    r1.invoice_lt10000_cr_cnt_l24m, -- 近24月金额小于10000（且大于等于5000）发票作废红冲张数
    r1.invoice_lt10000_total_sum_l24m, -- 近24月金额小于10000（且大于等于5000）发票合计金额税额总和
    r1.invoice_gt10000_cnt_l24m, -- 近24月金额大于10000发票张数
    r1.invoice_gt10000_cr_cnt_l24m, -- 近24月金额大于10000发票作废红冲张数
    r1.invoice_gt10000_total_sum_l24m, -- 近24月金额大于10000发票合计金额税额总和
    r1.invoice_detail_cnt_l24m, -- 近24月附有清单的发票张数
    r1.invoice_detail_cr_cnt_l24m, -- 近24月附有清单的发票作废红冲张数
    r1.invoice_detail_total_sum_l24m, -- 近24月附有清单的发票合计金额税额总和
    r1.buyer_cnt_l24m, -- 近24月交易对手数目

    r4.buyer_a_invoice_cnt_l24m, -- 在统计月归为A类的交易对手近24月开票张数
    r4.buyer_b_invoice_cnt_l24m, -- 在统计月归为B类的交易对手近24月开票张数
    r4.buyer_c_invoice_cnt_l24m, -- 在统计月归为C类的交易对手近24月开票张数
    r4.buyer_f_invoice_cnt_l24m, -- 在统计月归为F类的交易对手近24月开票张数
    r4.buyer_a_amt_sum_l24m, -- 在统计月归为A类的交易对手近24月合计金额总和
    r4.buyer_b_amt_sum_l24m, -- 在统计月归为B类的交易对手近24月合计金额总和
    r4.buyer_c_amt_sum_l24m, -- 在统计月归为C类的交易对手近24月合计金额总和
    r4.buyer_f_amt_sum_l24m, -- 在统计月归为F类的交易对手近24月合计金额总和

    r5.buyer_a_cnt_chg_rate_6m, -- A类交易对手数目6个月变化率
    r5.buyer_b_cnt_chg_rate_6m, -- B类交易对手数目6个月变化率
    r5.buyer_c_cnt_chg_rate_6m, -- C类交易对手数目6个月变化率
    r5.buyer_f_cnt_chg_rate_6m, -- F类交易对手数目6个月变化率

    r6.buyer_a_invoice_cnt_l12m_chg_rate_6m, -- 近12月A类交易对手开票张数6个月变化率
    r6.buyer_b_invoice_cnt_l12m_chg_rate_6m, -- 近12月B类交易对手开票张数6个月变化率
    r6.buyer_c_invoice_cnt_l12m_chg_rate_6m, -- 近12月C类交易对手开票张数6个月变化率
    r6.buyer_f_invoice_cnt_l12m_chg_rate_6m, -- 近12月F类交易对手开票张数6个月变化率
    r6.buyer_a_amt_sum_l12m_chg_rate_6m, -- 近12月A类交易对手合计金额总和6个月变化率
    r6.buyer_b_amt_sum_l12m_chg_rate_6m, -- 近12月B类交易对手合计金额总和6个月变化率
    r6.buyer_c_amt_sum_l12m_chg_rate_6m, -- 近12月C类交易对手合计金额总和6个月变化率
    r6.buyer_f_amt_sum_l12m_chg_rate_6m, -- 近12月F类交易对手合计金额总和6个月变化率

    r5.buyer_a_cnt_chg_rate_12m, -- A类交易对手数目12个月环比变化率
    r5.buyer_b_cnt_chg_rate_12m, -- B类交易对手数目12个月环比变化率
    r5.buyer_c_cnt_chg_rate_12m, -- C类交易对手数目12个月环比变化率
    r5.buyer_f_cnt_chg_rate_12m, -- F类交易对手数目12个月变化率

    r6.buyer_a_invoice_cnt_l12m_chg_rate_12m, -- 近12月A类交易对手开票张数12个月变化率
    r6.buyer_b_invoice_cnt_l12m_chg_rate_12m, -- 近12月B类交易对手开票张数12个月变化率
    r6.buyer_c_invoice_cnt_l12m_chg_rate_12m, -- 近12月C类交易对手开票张数12个月变化率
    r6.buyer_f_invoice_cnt_l12m_chg_rate_12m, -- 近12月F类交易对手开票张数12个月变化率
    r6.buyer_a_amt_sum_l12m_chg_rate_12m, -- 近12月A类交易对手合计金额总和12个月变化率
    r6.buyer_b_amt_sum_l12m_chg_rate_12m, -- 近12月B类交易对手合计金额总和12个月变化率
    r6.buyer_c_amt_sum_l12m_chg_rate_12m, -- 近12月C类交易对手合计金额总和12个月变化率
    r6.buyer_f_amt_sum_l12m_chg_rate_12m -- 近12月F类交易对手合计金额总和12个月变化率
from r4
left join r1
on
    r1.mcht_cd=r4.sellertaxno and r1.month=r4.data_month
left join r5
on
    r1.mcht_cd=r5.mcht_cd and r1.month=r5.month
left join r6
on
    r5.mcht_cd=r6.mcht_cd and r5.month=r6.month

