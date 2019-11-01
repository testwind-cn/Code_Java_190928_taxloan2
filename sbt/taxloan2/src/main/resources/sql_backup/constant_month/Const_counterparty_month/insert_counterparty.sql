
insert into ${Const_Common.DATABASE}.counterparty
    partition(create_time)
select
    c.mcht_cd,
    (case when month(current_date())=1 then concat(year(current_date())-1,12)
          when month(current_date()) in (11,12) then concat(year(current_date()),month(current_date())-1)
          else concat(year(current_date()),"0",month(current_date())-1) end) as data_month,
    c.buyer_name,
    c.buyer_tax_cd,
    (case when t3.buyer_invoice_cnt is null then 0 else t3.buyer_invoice_cnt end) as buyer_invoice_cnt,
    (case when t3.buyer_invoice_cr_cnt is null then 0 else t3.buyer_invoice_cr_cnt end) as buyer_invoice_cr_cnt,
    (case when t3.buyer_invoice_amt_sum is null then 0.00 else t3.buyer_invoice_amt_sum end) as buyer_invoice_amt_sum,
    (case when t3.buyer_invoice_tax_sum is null then 0.00 else t3.buyer_invoice_tax_sum end) as buyer_invoice_tax_sum,
    (case when t3.buyer_invoice_total_sum is null then 0 else t3.buyer_invoice_total_sum end) as buyer_invoice_total_sum,
    '0' AS is_delete,
    current_user() AS create_user,
    from_unixtime(unix_timestamp()) as modify_time,
    current_user() AS modify_user,
    current_date() AS create_time

from counterparty_tmp3 c
         left join
     (
         select
             t1.sellertaxno,  --商户编号
             t1.data_month,  --统计月份,取invoicedate的月份值
             t1.buyername,  --交易对手（购方）名称
             t1.buyertaxno,   --购方税号
             count( t1.buyertaxno) as buyer_invoice_cnt,   --交易对手发票张数
             SUM(CASE WHEN t1.totalamount < 0.0 OR t1.totaltax<0.0 OR t1.cancelflag='1' THEN 1	ELSE 0	END) AS buyer_invoice_cr_cnt,  --交易对手发票作废红冲张数
             SUM(CASE WHEN t1.totalamount > 0.0 AND t1.totaltax>=0.0 AND t1.cancelflag='0' THEN t1.totalamount ELSE 0	END) AS buyer_invoice_amt_sum,  --交易对手发票合计金额（不含作废及红冲发票）
             SUM(CASE WHEN t1.totalamount > 0.0 AND t1.totaltax>=0.0 AND t1.cancelflag='0' THEN t1.totaltax ELSE 0 END) AS buyer_invoice_tax_sum, --交易对手发票合计税额（不含作废及红冲发票）
             SUM(CASE WHEN t1.totalamount > 0.0 AND t1.totaltax>=0.0 AND t1.cancelflag='0' THEN t1.totalamount + t1.totaltax ELSE 0 END) AS buyer_invoice_total_sum --交易对手发票合计金额税额总和（不含作废及红冲发票）
         from
             ${Const_Common.DATABASE}.saleinvoice_tmp t1
         group by t1.sellertaxno,t1.data_month,t1.buyername,buyertaxno
     )t3 on c.mcht_cd=t3.sellertaxno and c.buyer_name=t3.buyername and c.buyer_tax_cd=t3.buyertaxno
