
select
    distinct c1.taxno AS sellertaxno,
    s1.sellername,
    s1.selleraddtel,
    s1.invoicedate,
    (
        case
            when length (CAST(MONTH (s1.invoicedate) AS STRING)) =1 then concat(YEAR (s1.invoicedate),"0", MONTH (s1.invoicedate))
            else concat(YEAR (s1.invoicedate),MONTH (s1.invoicedate))
        end
    ) AS data_month,
    s1.sellerbankno,
    c1.oldtaxno,
    s1.invoiceid,
    s1.buyername,
    s1.buyertaxno,
    s1.totalamount,
    s1.totaltax,
    s1.cancelflag,
    s1.invoicetype,
    s1.jztype
from dm_taxloan.cjlog_tmp c1
left join dm_taxloan.saleinvoice s1
on c1.taxno=s1.sellertaxno
where c1.oldtaxno='null' or c1.oldtaxno=''
