


-- 过滤重复的发票数据


with cte_saleinvoice_tmp3 as (
    select *
    from ${hivevar:DATABASE_DEST}.saleinvoice_tmp1
    union all
    select *
    from ${hivevar:DATABASE_DEST}.saleinvoice_tmp2
),

cte_saleinvoice_tmp as (
    select
        sellertaxno,sellername,selleraddtel,invoicedate,data_month,sellerbankno,
        oldtaxno,invoiceid,buyername,buyertaxno,totalamount,totaltax,cancelflag,invoicetype,jztype
    from (
        select
            sellertaxno,sellername,selleraddtel,sellerbankno,invoicedate,data_month,
            oldtaxno,invoiceid,buyername,buyertaxno,totalamount,totaltax,cancelflag,invoicetype,jztype,
            row_number() over(partition by invoiceid order by invoicedate) as rank
        from cte_saleinvoice_tmp3
    ) a
    where a.rank=1 and a.invoiceid!='NULL'
)

insert into ${hivevar:DATABASE_DEST}.saleinvoice_tmp select * from cte_saleinvoice_tmp

-- 插入saleinvoice_tmp成功