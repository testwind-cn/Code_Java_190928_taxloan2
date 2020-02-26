
select
c.mcht_cd_c1 as mcht_cd,
c.buyer_name_c1 as buyer_name,
c.buyer_tax_cd_c1 as buyer_tax_cd

from
(

select
c1.mcht_cd as mcht_cd_c1,
c1.buyer_name as buyer_name_c1,
c1.buyer_tax_cd as buyer_tax_cd_c1,
c2.mcht_cd as mcht_cd_c2
from counterparty_classify_tmp1 c1
left join
counterparty_classify_tmp2 c2
on c1.mcht_cd=c2.mcht_cd and c1.buyer_name=c2.buyer_name and c1.buyer_tax_cd=c2.buyer_tax_cd
)c where c.mcht_cd_c2 is NULL

