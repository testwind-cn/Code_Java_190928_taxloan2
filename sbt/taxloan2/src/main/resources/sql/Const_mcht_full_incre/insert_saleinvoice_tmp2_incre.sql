


-- 处理oldtaxno!='null'，且oldtaxno!=''的发票数据，合并新老税号数据


with cte_saleinvoice_tmp2 as (
    select
        distinct
        s3.taxno AS sellertaxno,
        s3.sellername,
        s3.selleraddtel,
        s3.invoicedate,
        (case when  length (CAST(MONTH (s3.invoicedate) AS STRING)) =1 then concat(YEAR (s3.invoicedate),"0", MONTH (s3.invoicedate))
              else concat(YEAR (s3.invoicedate),MONTH (s3.invoicedate))
        end) AS data_month,
        s3.sellerbankno,
        s3.oldtaxno,
        s3.invoiceid,
        s3.buyername,
        s3.buyertaxno,
        s3.totalamount,
        s3.totaltax,
        s3.cancelflag,
        s3.invoicetype,
        s3.jztype
    from (
        select
            c1.taxno,
            s1.sellertaxno,
            s1.sellername,
            s1.selleraddtel,
            s1.invoicedate,
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

        from ${hivevar:DATABASE_DEST}.cjlog_tmp c1
        left join ${hivevar:DATABASE_SRC}.saleinvoice s1
            on c1.taxno=s1.sellertaxno
        where c1.oldtaxno!='null' and c1.oldtaxno!=''

        union all

        select
            c2.taxno,
            s2.sellertaxno,
            s2.sellername,
            s2.selleraddtel,
            s2.invoicedate,
            s2.sellerbankno,
            c2.oldtaxno,
            s2.invoiceid,
            s2.buyername,
            s2.buyertaxno,
            s2.totalamount,
            s2.totaltax,
            s2.cancelflag,
            s2.invoicetype,
            s2.jztype

        from ${hivevar:DATABASE_DEST}.cjlog_tmp c2
        left join ${hivevar:DATABASE_SRC}.saleinvoice s2
            on c2.oldtaxno=s2.sellertaxno
        where c2.oldtaxno!='null' and c2.oldtaxno!=''
    ) s3
)

insert into ${hivevar:DATABASE_DEST}.saleinvoice_tmp2 select * from cte_saleinvoice_tmp2