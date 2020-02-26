select a.taxno1 as taxno,a.oldtaxno1 as oldtaxno from (
select c1.taxno as taxno1,c1.oldtaxno as oldtaxno1,c2.taxno as taxno2,c2.oldtaxno as oldtaxno2 from cjlog_tmp1 c1
left join cjlog_tmp1 c2
on c1.oldtaxno=c2.taxno
)a where a.taxno2 is null