1180



insert into data_warehouse.saleinvoice2 select * from  data_warehouse.saleinvoice;
insert into data_warehouse.mcht_tax2 select * from  data_warehouse.mcht_tax;
insert into data_warehouse.counterparty2 select * from  data_warehouse.counterparty;


insert into data_warehouse.counterparty_classify2 select * from  data_warehouse.counterparty_classify;
insert into data_warehouse.statistics_month2 select * from  data_warehouse.statistics_month;
insert into data_warehouse.statistics_crossmonth2 select * from  data_warehouse.statistics_crossmonth;



SELECT count(*) from data_warehouse.saleinvoice;







-- 手工插入运行日志
insert into dm_taxloan2.control_table
    select 'cjlog_last' as table_name, '2019-10-12 18:48:25' as export_date;

-- 查看运行时间日志
select control_table.*, INPUT__FILE__NAME from dm_taxloan2.control_table order by export_date desc;

cjlog_last	2019-09-30 09:56:19.0
cjlog_last	2019-10-12 18:48:25.0 全量
cjlog_last	2019-10-13 08:34:41.0 增量
cjlog_last	2019-10-13 19:55:10.0 增量


    任务          开始时间                结束时间            时长  商户数量
taxloan2 全量, 2019-10-12 18:48:25  - 2019-10-12 22:41:57   3:53:00   6780
taxloan2 增量, 2019-10-13 08:34:41    2019-10-13 09:07:56     33:14   2
taxloan2 增量, 2019-10-13 19:55:10    2019-10-13 20:20:01     24:50   34
taxloan2 增量, 2019-10-14 13:21:05    2019-10-14 13:38:11     17:05   113
taxloan2 增量, 2019-10-14 14:07:39    2019-10-14 14:40:03     32:23   12
taxloan2 增量, 2019-10-14 14:56:12    2019-10-14 15:10:11     15:58   31
taxloan2 增量, 2019-10-14 15:12:56     c_p2 步骤堆栈溢出
taxloan2 增量, 2019-10-14 15:38:15    2019-10-14 15:50:31     12:15   27+1 (手工补计算一个)
taxloan2 增量, 2019-10-14 15:58:00    2019-10-14 16:11:34     13:34   14
taxloan2 增量, 2019-10-14 16:14:12    2019-10-14 16:26:23     12:11   9
taxloan2 增量, 2019-10-14 16:35:36    2019-10-14 16:49:25     13:49   12
taxloan2 增量, 2019-10-14 16:52:31    2019-10-14 17:04:54     12:22   9
            增加 saleinvoice 表 Hive 导出到 Mysql
taxloan2 增量, 2019-10-14 17:09:12    2019-10-14 17:26:54     17:42   8



last_time -5 min <= finish < this_time

-- 查询两次时间间隔中的增量商户

SELECT  * from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-12 18:48:25' and FINISH_TIME < '2019-10-13 08:34:41'
ORDER BY cjlx, FINISH_TIME;
-- 2个商户

SELECT  * from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-13 08:34:41' and FINISH_TIME < '2019-10-13 19:55:10'
ORDER BY cjlx, FINISH_TIME;
-- 34个商户

SELECT  * from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-13 19:55:10' and FINISH_TIME < '2019-10-14 13:21:05'
ORDER BY cjlx, FINISH_TIME;
-- 113个商户

SELECT DISTINCT taxno from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-14 13:21:05' and FINISH_TIME < '2019-10-14 14:07:39'
ORDER BY cjlx, FINISH_TIME;
-- 12个商户

SELECT DISTINCT taxno from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-14 14:07:39' and FINISH_TIME < '2019-10-14 14:56:12'
ORDER BY cjlx, FINISH_TIME;
-- 31个商户

SELECT DISTINCT taxno from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-14 14:56:12' and FINISH_TIME < '2019-10-14 15:38:15'
ORDER BY cjlx, FINISH_TIME;
-- 27+1个商户

SELECT DISTINCT taxno from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-14 15:38:15' and FINISH_TIME < '2019-10-14 15:58:00'
ORDER BY cjlx, FINISH_TIME;
-- 14个商户

SELECT DISTINCT taxno from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-14 15:58:00' and FINISH_TIME < '2019-10-14 16:14:12'
ORDER BY cjlx, FINISH_TIME;
-- 9个商户


SELECT DISTINCT taxno from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-14 16:14:12' and FINISH_TIME < '2019-10-14 16:35:36'
ORDER BY cjlx, FINISH_TIME;
-- 12个商户

SELECT DISTINCT taxno from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-14 16:35:36' and FINISH_TIME < '2019-10-14 16:52:31'
ORDER BY cjlx, FINISH_TIME;
-- 9个商户


SELECT DISTINCT taxno from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-14 16:52:31' and FINISH_TIME < '2019-10-14 17:09:12'
ORDER BY cjlx, FINISH_TIME;
-- 9个商户


SELECT DISTINCT taxno from invoice.cjlog
where cjlx !='日常采集' and FINISH_TIME >= '2019-10-14 20:30:25'   and FINISH_TIME < '2019-10-14 21:30:25'
ORDER BY cjlx, FINISH_TIME;
-- 2个商户






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
    on s1.sellertaxno=s2.buyertaxno;

245 224 188






select
    TAXNO '商户号',
    count(*) '【首次采集】次数',
    min(FINISH_TIME) '最早一次首次采集',
    MAX(FINISH_TIME) '最近一次首次采集'
from cjlog
where
    FINISH_TIME is not null and MFLAG=1 and cjlx='首次采集'
GROUP BY TAXNO
HAVING count(*) > 1
ORDER BY MAX(FINISH_TIME) desc;


商户号	【首次采集】次数	最早一次首次采集	最近一次首次采集
91441625325177111U	2	2019-09-25 16:33:35	2019-09-30 11:31:57
91510114MA6C5B4D82	2	2019-09-29 11:31:31	2019-09-29 15:40:40
91140100713607689C	221	2019-08-07 16:50:21	2019-09-25 11:36:09
911408823444291340	2	2019-09-24 16:01:09	2019-09-24 17:23:23
9137028216395241X0	58	2019-07-18 11:23:33	2019-09-21 16:19:19
91130981MA09FAFY83	2	2019-09-11 15:57:59	2019-09-20 16:01:58
91370214568596732X	2	2019-09-20 10:57:36	2019-09-20 11:01:18
91370303751794868G	3	2019-07-26 11:28:56	2019-09-17 17:25:11
914412830795594478	370	2019-07-10 15:36:38	2019-09-17 10:09:50
91420800730871748C	2	2019-08-20 16:20:26	2019-09-11 15:11:45
911302005590931137	193	2019-06-20 14:08:25	2019-09-07 14:37:34
914206823097491404	2	2019-06-11 11:16:18	2019-09-06 16:02:17
91532328MA6KW78D7H	1244	2019-07-31 14:05:37	2019-09-04 23:22:38
914107000638498611	3790	2019-07-09 18:19:21	2019-09-04 23:22:01
91411323MA3X5GNL18	52	2019-07-02 19:09:54	2019-09-04 23:20:10
911405005635962824	151	2019-08-01 16:32:37	2019-09-04 23:19:50
91410702396116260L	3623	2019-07-09 18:54:58	2019-09-04 23:19:07
91410782MA40U59Y78	1421	2019-07-03 16:55:00	2019-09-04 23:10:28




-- 商户号 91451002664808993R 的采集记录
SELECT
    TAXNO '商户号',
    CJLX '采集类型',
    ALLNUM '采集笔数',
    BEGIN_TIME '开始时间',
    FINISH_TIME '结束时间',
    MFLAG '完成标志'
from invoice.cjlog
where TAXNO='91451002664808993R' ORDER BY begin_time;


商户号	            采集类型	采集笔数	    开始时间	            结束时间	            完成标志
91451002664808993R	首次采集	384	        2019-09-24 18:07:28	2019-09-24 18:08:10	    1
91451002664808993R	日常采集	30	        2019-09-24 18:28:16	2019-09-24 18:28:18	    1
91451002664808993R	日常采集	10	        2019-09-24 18:48:16	2019-09-24 18:48:19	    1
91451002664808993R	日常采集	1	        2019-09-25 11:30:35	2019-09-25 11:30:36	    1
91451002664808993R	日常采集	116	        2019-09-25 12:05:34	2019-09-25 12:05:40	    1
91451002664808993R	日常采集	21	        2019-09-25 12:09:03	2019-09-25 12:09:06	    1
91451002664808993R	日常采集	342	        2019-09-25 14:22:45	2019-09-25 14:22:56	    1


-- 这是24日的【首次采集】，384笔
select
    year(INVOICEDATE)*100+month(INVOICEDATE) '发票月份',
    count(*) '当月发票数'
from invoice.saleinvoice
where
    SELLERTAXNO='91451002664808993R'
    and CREATETIME>='2019-09-24 18:07:28'and CREATETIME<='2019-09-24 18:08:10'
GROUP BY
    year(INVOICEDATE)*100+month(INVOICEDATE);

发票月份	当月发票数
201811	    36
201812	    70
201901	    68
201902	    20
201903	    27
201904	    21
201905	    37
201906	    19
201907	    46
201908	    29
201909	    11


-- 这是25日的第六次【日常采集】，342笔
select
    year(INVOICEDATE)*100+month(INVOICEDATE) '发票月份',
    count(*) '当月发票数'
from invoice.saleinvoice
where
    SELLERTAXNO='91451002664808993R'
    and CREATETIME>='2019-09-25 14:22:45'and CREATETIME<='2019-09-25 14:22:56'
GROUP BY
    year(INVOICEDATE)*100+month(INVOICEDATE);

发票月份	当月发票数
201712	    37
201801	    55
201802	    56
201803	    36
201804	    28
201805	    17
201806	    13
201807	    36
201808	    36
201809	    24
201810	    4