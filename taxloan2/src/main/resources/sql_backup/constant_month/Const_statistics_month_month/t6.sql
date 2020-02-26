select
    c1.sellertaxno,
    c1.data_month,
    sum(case when c2.detaillistflag=1 then 1 else 0 end) as invoice_detail_cnt,
    sum(CASE WHEN c2.detaillistflag=1 AND ((c1.totalamount<0 AND c1.totaltax<0) OR c1.cancelflag='1') THEN 1 ELSE 0 END) AS invoice_detail_cr_cnt,
    sum(CASE WHEN c2.detaillistflag=1 AND c1.totalamount>0 AND c1.totaltax>=0 AND c1.cancelflag='0' THEN c1.totalamount+c1.totaltax ELSE 0 END) AS invoice_detail_total_sum
from
    ${Const_Common.DATABASE}.saleinvoice_tmp c1
        left join
    ${Const_Common.DATABASE}.saleinvoicedetail c2
    on c1.invoiceid=c2.invoiceid
group by c1.sellertaxno,c1.data_month