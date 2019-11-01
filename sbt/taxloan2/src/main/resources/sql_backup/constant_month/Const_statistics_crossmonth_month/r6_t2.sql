select * from(
                 select
                     *,
                     row_number() over(partition by aaa.mcht_cd,aaa.data_month,aaa.buyer_type order by aaa.data_month )as rank
                 from (
                          select
                              t3.mcht_cd,
                              t3.data_month,
                              --t3.buyer_tax_cd,
                              t3.buyer_type,

                              --按照商户号，月份，交易对手类型分组求和
                              sum(t3.buyer_invoice_cnt_l12m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
                                  order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_cnt_l12m_all,
                              sum(t3.buyer_invoice_total_sum_l12m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
                                  order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l12m_all,

                              sum(t3.buyer_invoice_cnt_l712m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
                                  order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_cnt_l712m_all,
                              sum(t3.buyer_invoice_total_sum_l712m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
                                  order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l712m_all,
                              sum(t3.buyer_invoice_cnt_l1324m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
                                  order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_cnt_l1324m_all,
                              sum(t3.buyer_invoice_total_sum_l1324m) over(partition by t3.mcht_cd,t3.data_month,t3.buyer_type
                                  order by to_date(from_unixtime(unix_timestamp(t3.data_month,'yyyyMM')))) as buyer_invoice_total_sum_l1324m_all

                          from r6_t1 t3
                      )aaa
             )bbb where bbb.rank=1