
-- set hivevar:DATABASE_DEST=dm_taxloan2;

with aaat as
(
    select
        taxno,
        if( instr(oldtaxno, '.')  > 0 , substr(oldtaxno,1,instr(oldtaxno,'.')-1), if(oldtaxno='null','',oldtaxno))
        as new_oldtaxno
        , max(finish_time) last_time
    from ${hivevar:DATABASE_SRC}.cjlog
    where
        finish_time is not null and finish_time!='null' and mflag=1
    group by
        taxno,
        if( instr(oldtaxno, '.')  > 0 , substr(oldtaxno,1,instr(oldtaxno,'.')-1), if(oldtaxno='null','',oldtaxno))
)

insert into ${hivevar:DATABASE_DEST}.cjlog_tmp

select
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
) tt2
left join
    aaat as tt3
on tt2.taxno = tt3.new_oldtaxno



