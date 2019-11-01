insert overwrite table ${hivevar:DATABASE_DEST}.mcht_tax_incre
select mcht_cd,
       mcht_name,
       mcht_tax_cd,
       mcht_addtel,
       mcht_bankno,
       first_invoicedate,
       initial_get_flag,
       is_delete,
       create_time,
       create_user,
       modify_time,
       modify_user
from ${hivevar:DATABASE_DEST}.mcht_tax e,
     (select table_name, max(export_date) as last_export_date from ${hivevar:DATABASE_DEST}.control_table group by table_name) c
where c.table_name = 'mcht_tax'
  and e.modify_time > c.last_export_date
