-- saleinvoice_tmp_all 不过滤月份数据

select
    sellertaxno,sellername,selleraddtel,invoicedate,data_month,sellerbankno,
    oldtaxno,invoiceid,buyername,buyertaxno,totalamount,totaltax,cancelflag,invoicetype,jztype

from (
         select sellertaxno,sellername,selleraddtel,sellerbankno,invoicedate,data_month,
                oldtaxno,invoiceid,buyername,buyertaxno,totalamount,totaltax,cancelflag,invoicetype,jztype,
                row_number() over(partition by invoiceid order by invoicedate) as rank
         from ${Const_Common.DATABASE}.saleinvoice_tmp3
     )a where a.rank=1 and a.invoiceid!='NULL'