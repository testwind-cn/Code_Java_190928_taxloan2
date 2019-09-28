1180



insert into data_warehouse.saleinvoice2 select * from  data_warehouse.saleinvoice;
insert into data_warehouse.mcht_tax2 select * from  data_warehouse.mcht_tax;
insert into data_warehouse.counterparty2 select * from  data_warehouse.counterparty;


insert into data_warehouse.counterparty_classify2 select * from  data_warehouse.counterparty_classify;
insert into data_warehouse.statistics_month2 select * from  data_warehouse.statistics_month;
insert into data_warehouse.statistics_crossmonth2 select * from  data_warehouse.statistics_crossmonth;



SELECT count(*) from data_warehouse.saleinvoice






insert into data_warehouse.saleinvoice2 select * from  data_warehouse.saleinvoice
4276704 Rows
Time: 168.810s


insert into data_warehouse.mcht_tax2 select * from  data_warehouse.mcht_tax
> Affected rows: 4402
> 时间: 0.38s


insert into data_warehouse.counterparty2 select * from  data_warehouse.counterparty
> Affected rows: 17668227
> 时间: 1509.044s



alter table data_warehouse.counterparty2 rename to data_warehouse.counterparty4;


select count(*) from data_warehouse.mcht_tax;
select count(*) from data_warehouse.mcht_tax_last;
select count(*) from data_warehouse.saleinvoice;
select count(*) from data_warehouse.saleinvoice_last;
select count(*) from data_warehouse.counterparty;
select count(*) from data_warehouse.counterparty_last;

select count(*) from data_warehouse.counterparty_classify;
select count(*) from data_warehouse.counterparty_classify_last;
select count(*) from data_warehouse.statistics_month;
select count(*) from data_warehouse.statistics_month_last;
select count(*) from data_warehouse.statistics_crossmonth;
select count(*) from data_warehouse.statistics_crossmonth_last;





    select s1.*,s2.* from
    data_warehouse2.saleinvoice s1
        left join
    data_warehouse2.saleinvoice s2
    on s1.sellertaxno=s2.buyertaxno