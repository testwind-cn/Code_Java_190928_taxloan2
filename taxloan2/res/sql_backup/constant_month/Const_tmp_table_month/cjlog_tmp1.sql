
-- cjlog_tmp表
select
    distinct taxno,
             (CASE WHEN oldtaxno in('','null','Null') then '' else oldtaxno end) AS oldtaxno
from ${Const_Common.DATABASE}.cjlog where mflag=1