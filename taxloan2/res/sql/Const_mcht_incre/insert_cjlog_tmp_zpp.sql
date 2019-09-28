


with cte_cjlog_tmp1 as -- 从cjlog表中取出新老税号，并过滤
(
    select
        distinct p.taxno,
                 p.oldtaxno
    from
        (
            select
                c.taxno,
                c.oldtaxno,
                m.mcht_cd
            from
                (
                    select
                        taxno,
                        oldtaxno,
                        mflag,
                        finish_time,
                        row_number() over(partition by taxno,oldtaxno order by finish_time desc) AS rank
                    from ${hivevar:DATABASE_SRC}.cjlog where finish_time!='null'
                )c
                    left join ${hivevar:DATABASE_DEST}.mcht_tax m  --判断增量数据税号是否已在mcht_tax表中存在
                              on c.taxno=m.mcht_cd
            where c.rank=1 and c.mflag=1 --判断是否采集完成
    --and cast(substr(c.finish_time,1,10) as date)=current_date() --判断采集日期是否为当天(增量跑时加上此条件)
        )p
    where p.mcht_cd is null
),
cte_cjlog_tmp as
(
    select
        a.taxno1 as taxno,
        a.oldtaxno1 as oldtaxno
    from
    (
        select
            c1.taxno as taxno1
             ,c1.oldtaxno as oldtaxno1
             ,c2.taxno as taxno2
             ,c2.oldtaxno as oldtaxno2
        from cjlog_tmp1 c1
        left join cjlog_tmp1 c2
        on c1.taxno=c2.oldtaxno
    ) a
    where a.taxno2 is null
)
insert into ${hivevar:DATABASE_DEST}.cjlog_tmp select * from cte_cjlog_tmp