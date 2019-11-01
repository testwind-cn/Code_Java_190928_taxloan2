
-- set hivevar:DATABASE_DEST=dm_taxloan2
-- set hivevar:ADD_TAXNO=or taxno in ('9132021456175067XT','dsad','dsadsadsa','dsadsadsa')

with aaat as
(
    select
        taxno,
        if( instr(oldtaxno, '.')  > 0 , substr(oldtaxno,1,instr(oldtaxno,'.')-1), if(oldtaxno='null','',oldtaxno))
        as new_oldtaxno
        , max(from_unixtime(unix_timestamp(finish_time,'yyyy-MM-dd HH:mm:ss'))) last_time
        , min(from_unixtime(unix_timestamp(finish_time,'yyyy-MM-dd HH:mm:ss'))) first_time
    from ${hivevar:DATABASE_SRC}.cjlog
    where
        finish_time is not null and finish_time!='null' and length(finish_time)>0 and mflag=1
        and ( cjlx != '日常采集' ${hivevar:ADD_TAXNO} )
    group by
        taxno,
        if( instr(oldtaxno, '.')  > 0 , substr(oldtaxno,1,instr(oldtaxno,'.')-1), if(oldtaxno='null','',oldtaxno))
)

insert into ${hivevar:DATABASE_DEST}.cjlog_tmp

select
    distinct
    coalesce( tt3.taxno,tt2.taxno) as taxno
    ,coalesce( tt3.new_oldtaxno,tt2.new_oldtaxno) as oldtaxno
--    ,tt2.last_time
--    ,tt2.taxno
--    ,tt2.new_oldtaxno
--    ,tt3.taxno, tt3.new_oldtaxno, tt3.last_time
from
(
    select
        tt1.taxno, tt1.new_oldtaxno, tt1.last_time
    from
        aaat as tt1
    where
        last_time>=${hivevar:LAST_TIME}
        ${hivevar:ADD_TAXNO}
        -- and unix_timestamp(last_time,'yyyy-MM-dd HH:mm:ss') - unix_timestamp(first_time,'yyyy-MM-dd HH:mm:ss') < 3600*24*3
        -- and taxno='912102115880553004'
        -- ${hivevar:LAST_TIME} >'2019-09-18 13:42:00'
) tt2
left join
    aaat as tt3
on tt2.taxno = tt3.new_oldtaxno



