

with cte_cjlog_tmp1 as -- 从cjlog表中取出新老税号，并过滤
(
    select
        distinct tmp_cjlog.taxno,
        ( CASE
            WHEN tmp_cjlog.oldtaxno='null' or tmp_cjlog.oldtaxno='' THEN ''
            ELSE tmp_cjlog.oldtaxno
        END) AS oldtaxno
    from (
        select
            taxno,
            oldtaxno,
            mflag,
            row_number() over(
                partition by taxno,oldtaxno
                order by finish_time desc
            ) AS rank
        from ${hivevar:DATABASE_SRC}.cjlog
        where finish_time!='null'
    ) tmp_cjlog
    where tmp_cjlog.rank=1 and tmp_cjlog.mflag=1
),
cte_cjlog_tmp as -- 处理两条记录中taxno相同，或者一条记录taxno与另一条记录oldtaxno相同的记录
(
    select
       a.taxno1 as taxno
        ,a.oldtaxno1 as oldtaxno
    from (
        select
            c1.taxno as taxno1
             ,c1.oldtaxno as oldtaxno1
             ,c2.taxno as taxno2
             ,c2.oldtaxno as oldtaxno2
        from cte_cjlog_tmp1 c1
        left join cte_cjlog_tmp1 c2
        on c1.taxno=c2.oldtaxno
    ) a
    where a.taxno2 is null
)
insert into ${hivevar:DATABASE_DEST}.cjlog_tmp select * from cte_cjlog_tmp

