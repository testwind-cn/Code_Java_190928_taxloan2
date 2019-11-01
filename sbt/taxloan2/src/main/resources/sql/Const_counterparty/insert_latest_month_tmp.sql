with
latest_month_tmp as
(
    select
        t2.mcht_cd,
        t2.month,
        t2.buyertaxno as buyer_tax_cd,
        t2.buyername
    from
    (
        select
            t1.sellertaxno as mcht_cd,
            t1.buyertaxno,
            t1.buyername,
            d.month,
            t1.data_month AS data_month
        from ${hivevar:DATABASE_DEST}.dim_date d cross join
         (
             select
                 tt.sellertaxno,
                 tt.data_month,
                 tt.buyertaxno,
                 tt.buyername
             from
             (
                 select
                     sellertaxno,
                     data_month,
                     buyertaxno,
                     buyername,
                     row_number() over(partition by sellertaxno, buyertaxno,buyername order by invoicedate) as rank  --取该交易对手最早的交易时间
                 from ${hivevar:DATABASE_DEST}.saleinvoice_tmp
             )tt where tt.rank=1
         ) t1
    )t2
    where datediff(to_date(from_unixtime(unix_timestamp(t2.month,'yyyyMM'))),to_date(from_unixtime(unix_timestamp(t2.data_month,'yyyyMM'))))>=0 --从最早交易月份开始
        and datediff(to_date(from_unixtime(unix_timestamp(t2.month,'yyyyMM'))),to_date(from_unixtime(unix_timestamp( substr(current_date(),1,7),'yyyy-MM'))))<-1
)

insert into ${hivevar:DATABASE_DEST}.latest_month_tmp select * from latest_month_tmp

-- 构造出每个商户号和每个买家，从最早交易月，到上月的全表
