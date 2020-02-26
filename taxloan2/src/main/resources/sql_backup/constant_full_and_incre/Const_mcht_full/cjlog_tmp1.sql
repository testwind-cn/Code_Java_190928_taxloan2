
-- 从cjlog表中取出新老税号，并过滤

select
    distinct cjlog_tmp.taxno,
    ( CASE
        WHEN cjlog_tmp.oldtaxno='null' or cjlog_tmp.oldtaxno='' THEN ''
        ELSE cjlog_tmp.oldtaxno
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
    from dm_taxloan.cjlog
    where finish_time!='null'
) cjlog_tmp
where cjlog_tmp.rank=1 and cjlog_tmp.mflag=1