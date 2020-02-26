
with
cte_t1 as
(
    select
        sellertaxno,
        sellername,
        selleraddtel,
        invoicedate,
        sellerbankno,
        row_number() over(partition by sellertaxno order by invoicedate) as rank
    from ${hivevar:DATABASE_DEST}.saleinvoice_tmp where invoicedate is not null
),
cte_t3 as
(
    select
        sellertaxno,
        sellername,
        selleraddtel,
        invoicedate,
        sellerbankno,
        row_number() over(partition by sellertaxno order by invoicedate desc) as rank
    from ${hivevar:DATABASE_DEST}.saleinvoice_tmp where invoicedate is not null
)

insert into ${hivevar:DATABASE_DEST}.mcht_tax
    partition(create_time)
select
    distinct
    t3.sellertaxno AS mcht_cd,
    t3.sellername,
    t3.sellertaxno,
    t3.selleraddtel,
    t3.sellerbankno,
    t1.invoicedate AS first_invoicedate,
    '1' AS initial_get_flag,
    '0' AS is_delete,
    current_user() AS create_user,
    from_unixtime(unix_timestamp()) as modify_time,
    current_user() AS modify_user,
    current_date() AS create_time
from
(
    select
        sellertaxno,
        sellername,
        selleraddtel,
        invoicedate,
        sellerbankno
    from cte_t1
    where cte_t1.rank=1
)t1
left join
(
    select
        sellertaxno,
        sellername,
        selleraddtel,
        invoicedate,
        sellerbankno
    from cte_t3
    where cte_t3.rank=1
)t3
on t1.sellertaxno=t3.sellertaxno
