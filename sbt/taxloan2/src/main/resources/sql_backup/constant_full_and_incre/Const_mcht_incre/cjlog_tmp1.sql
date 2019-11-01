select
    distinct p.taxno,
    p.oldtaxno
from (
    select
        c.taxno,
        c.oldtaxno,
        m.mcht_cd
    from (
        select
            taxno,
            oldtaxno,
            mflag,
            finish_time,
            row_number() over(partition by taxno,oldtaxno order by finish_time desc) AS rank
        from dm_taxloan.cjlog where finish_time!='null'
    ) c
    left join dm_taxloan.mcht_tax m  --判断增量数据税号是否已在mcht_tax表中存在
    on c.taxno=m.mcht_cd
    where c.rank=1 and c.mflag=1 --判断是否采集完成
--and cast(substr(c.finish_time,1,10) as date)=current_date() --判断采集日期是否为当天(增量跑时加上此条件)
) p
where p.mcht_cd is null