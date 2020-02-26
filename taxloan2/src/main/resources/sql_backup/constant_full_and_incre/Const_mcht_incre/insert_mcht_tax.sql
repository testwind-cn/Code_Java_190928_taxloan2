
/* **************************************** 商户表 ******************************************* */

insert into dm_taxloan.mcht_tax
partition(create_time)
select
    distinct t3.sellertaxno AS mcht_cd,
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
        sellerbankno,
        row_number() over(partition by sellertaxno order by invoicedate) as rank
    from dm_taxloan.saleinvoice_tmp
    where invoicedate is not null
) t1
left join
(
    select
        sellertaxno,
        sellername,
        selleraddtel,
        invoicedate,
        sellerbankno,
        row_number() over(partition by sellertaxno order by invoicedate desc) as rank
    from dm_taxloan.saleinvoice_tmp
    where
        length(coalesce(invoicedate,'')) > 0
        and length(coalesce(sellername,'')) > 0
        and length(coalesce(selleraddtel,'')) > 0
        and length(coalesce(sellerbankno,'')) > 0
) t3
on t1.sellertaxno=t3.sellertaxno
where
    t1.rank=1 and t3.rank=1
