

drop table if EXISTS  flink2.`saleinvoice`;

CREATE TABLE if not exists flink2.`saleinvoice` (
    `CID` varchar(50) NOT NULL,
    `INVOICECODE` varchar(12) NOT NULL,
    `INVOICEDATE` date NOT NULL,
    `INVOICEID` varchar(50) NOT NULL,
    `INVOICENO` varchar(12) NOT NULL,
    `SELLERNAME` varchar(200) DEFAULT NULL,
    `SELLERTAXNO` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `SRCTYPE` int(11) DEFAULT NULL,
    `INVOICEDATEMONTH` int(11) NOT NULL,
    PRIMARY KEY (`CID`,`sellertaxno`,`INVOICEDATEMONTH`),
    KEY `INVOICEID` (`INVOICEID`),
    KEY `INVOICECODE` (`INVOICECODE`,`INVOICENO`),
    KEY `idx_saleinvoice_invociedate` (`INVOICEDATE`),
    KEY `idx_saleinvoice_srctype` (`SRCTYPE`),
    KEY `idx_saleinvoice_sellertaxno` (`SELLERTAXNO`),
    KEY `idx_saleinvoice_sellername` (`SELLERNAME`)
/*                                  ,
    UNIQUE KEY `unique_taxno_date` (`sellertaxno`,`INVOICEDATE`,`INVOICENO`,`INVOICECODE`) USING BTREE
    MySQL分区表对分区字段的限制
    分区的字段，必须是表上所有的唯一索引（或者主键索引）包含的字段的子集
    换句话说就是：（所有的）字段必须出现在（所有的）唯一索引或者主键索引的字段中，
    或者更通俗讲就是，一个表上有一个或者多个唯一索引的情况下，分区的字段必须被包含在所有的主键或者唯一索引字段中。

    // WJ : 使用 唯一键UNIQUE KEY没有作用，必须放在主键PRIMARY KEY中
 */
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
/* 用原始日期的函数做分区，不是很好用，见下面第二个示例*/
/* PARTITION by range(year(INVOICEDATE)*12+month(INVOICEDATE)) */
PARTITION by range(INVOICEDATEMONTH)
subpartition BY KEY(SELLERTAXNO) subpartitions 10
(
    partition p24210 values less than (24210),
    partition p24211 values less than (24211),
    partition p24212 values less than (24212),
    partition p24213 values less than (24213),
    partition p24214 values less than (24214),
    partition p24215 values less than (24215),
    partition p24216 values less than (24216),
    partition p24217 values less than (24217),
    partition p24218 values less than (24218),
    partition p24219 values less than (24219),
    partition p24220 values less than (24220),
    partition p0 values less than maxvalue
);


EXPLAIN
SELECT * from flink2.saleinvoice
where ( INVOICEDATEMONTH >= 2018*12+1 and INVOICEDATEMONTH <= 2018*12+3  ) and sellertaxno in (  '23sdsxx','xxcsad');
/* 最佳，但要多加一个字段*/
/*
有3个月，2个商户号，共6个分区
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                            | type  | possible_keys               | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p24218_p24218sp4,p24218_p24218sp8,p24219_p24219sp4,p24219_p24219sp8,p24220_p24220sp4,p24220_p24220sp8 | range | idx_saleinvoice_sellertaxno | idx_saleinvoice_sellertaxno | 202     | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.10 sec)
*/

EXPLAIN
SELECT * from flink2.saleinvoice
where ( INVOICEDATEMONTH = 2018*12+1 ) and sellertaxno in (  '23sdsxx','xxcsad');

/*
有1个月，2个商户号，共2个分区
+----+-------------+-------------+-----------------------------------+------+-----------------------------+------+---------+------+------+----------+-------------+
| id | select_type | table       | partitions                        | type | possible_keys               | key  | key_len | ref  | rows | filtered | Extra       |
+----+-------------+-------------+-----------------------------------+------+-----------------------------+------+---------+------+------+----------+-------------+
|  1 | SIMPLE      | saleinvoice | p24218_p24218sp4,p24218_p24218sp8 | ALL  | idx_saleinvoice_sellertaxno | NULL | NULL    | NULL |    1 |   100.00 | Using where |
+----+-------------+-------------+-----------------------------------+------+-----------------------------+------+---------+------+------+----------+-------------+
1 row in set (0.02 sec)
*/




-- =============================================================================
-- 第二个示例 ---------------------------------------




drop table if EXISTS  flink2.`saleinvoice`;

CREATE TABLE if not exists flink2.`saleinvoice` (
    `CID` varchar(50) NOT NULL,
    `INVOICECODE` varchar(12) NOT NULL,
    `INVOICEDATE` date NOT NULL,
    `INVOICEID` varchar(50) NOT NULL,
    `INVOICENO` varchar(12) NOT NULL,
    `SELLERNAME` varchar(200) DEFAULT NULL,
    `SELLERTAXNO` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `SRCTYPE` int(11) DEFAULT NULL,
    `INVOICEDATEMONTH` int(11) NOT NULL,
    PRIMARY KEY (`CID`,`sellertaxno`,`INVOICEDATE`),
    KEY `INVOICEID` (`INVOICEID`),
    KEY `INVOICECODE` (`INVOICECODE`,`INVOICENO`),
    KEY `idx_saleinvoice_invociedate` (`INVOICEDATE`),
    KEY `idx_saleinvoice_srctype` (`SRCTYPE`),
    KEY `idx_saleinvoice_sellertaxno` (`SELLERTAXNO`),
    KEY `idx_saleinvoice_sellername` (`SELLERNAME`)
/*                                  ,
    UNIQUE KEY `unique_taxno_date` (`sellertaxno`,`INVOICEDATE`,`INVOICENO`,`INVOICECODE`) USING BTREE
    MySQL分区表对分区字段的限制
    分区的字段，必须是表上所有的唯一索引（或者主键索引）包含的字段的子集
    换句话说就是：（所有的）字段必须出现在（所有的）唯一索引或者主键索引的字段中，
    或者更通俗讲就是，一个表上有一个或者多个唯一索引的情况下，分区的字段必须被包含在所有的主键或者唯一索引字段中。

    // WJ : 使用 唯一键UNIQUE KEY没有作用，必须放在主键PRIMARY KEY中
 */
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
/* 用原始日期的函数做分区，不是很好用，见下面第三个示例*/
PARTITION by range(to_days(INVOICEDATE))
/* PARTITION by range(INVOICEDATEMONTH) */
subpartition BY KEY(SELLERTAXNO) subpartitions 10
(
    partition p199901 values less than (TO_DAYS('2000-01-01 00:00:00')),
    partition p201708 values less than (TO_DAYS('2017-09-01 00:00:00')),
    partition p201709 values less than (TO_DAYS('2017-10-01 00:00:00')),
    partition p201710 values less than (TO_DAYS('2017-11-01 00:00:00')),
    partition p201711 values less than (TO_DAYS('2017-12-01 00:00:00')),
    partition p201712 values less than (TO_DAYS('2018-01-01 00:00:00')),
    partition p201801 values less than (TO_DAYS('2018-02-01 00:00:00')),
    partition p201802 values less than (TO_DAYS('2018-03-01 00:00:00')),
    partition p201803 values less than (TO_DAYS('2018-04-01 00:00:00')),
    partition p201804 values less than (TO_DAYS('2018-05-01 00:00:00')),
    partition p201805 values less than (TO_DAYS('2018-06-01 00:00:00')),
    partition p201806 values less than (TO_DAYS('2018-07-01 00:00:00')),
    partition p201807 values less than (TO_DAYS('2018-08-01 00:00:00')),
    partition p201808 values less than (TO_DAYS('2018-09-01 00:00:00')),
    partition p201809 values less than (TO_DAYS('2018-10-01 00:00:00')),
    partition p0 values less than maxvalue
);



/* 可以查用，但不是最佳 */
EXPLAIN
SELECT * from flink2.saleinvoice
where ( INVOICEDATE >= '2018-01-01 00:00:00' and INVOICEDATE < '2018-03-01 00:00:00'  ) and sellertaxno in (  '23sdsxx','xxcsad');
/* 正常
 3个月，2个商户号区，199901, 201801,201802几个区。   为什么有第一个分区 199901 ？
 */
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                                        | type  | possible_keys                                           | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p199901_p199901sp4,p199901_p199901sp8,p201801_p201801sp4,p201801_p201801sp8,p201802_p201802sp4,p201802_p201802sp8 | range | idx_saleinvoice_invociedate,idx_saleinvoice_sellertaxno | idx_saleinvoice_invociedate | 3       | NULL |    1 |   100.00 | Using index condition |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.18 sec)




EXPLAIN
SELECT * from flink2.saleinvoice
where ( TO_DAYS(INVOICEDATE) >= TO_DAYS('2018-01-01 00:00:00') and TO_DAYS(INVOICEDATE) < TO_DAYS('2018-03-01 00:00:00')  ) and sellertaxno in (  '23sdsxx','xxcsad');
/* 没有效果
 全部个月，2个商户号区
 */
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | type  | possible_keys                                           | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p199901_p199901sp4,p199901_p199901sp8,p201708_p201708sp4,p201708_p201708sp8,p201709_p201709sp4,p201709_p201709sp8,p201710_p201710sp4,p201710_p201710sp8,p201711_p201711sp4,p201711_p201711sp8,p201712_p201712sp4,p201712_p201712sp8,p201801_p201801sp4,p201801_p201801sp8,p201802_p201802sp4,p201802_p201802sp8,p201803_p201803sp4,p201803_p201803sp8,p201804_p201804sp4,p201804_p201804sp8,p201805_p201805sp4,p201805_p201805sp8,p201806_p201806sp4,p201806_p201806sp8,p201807_p201807sp4,p201807_p201807sp8,p201808_p201808sp4,p201808_p201808sp8,p201809_p201809sp4,p201809_p201809sp8,p0_p0sp4,p0_p0sp8 | range | idx_saleinvoice_invociedate,idx_saleinvoice_sellertaxno | idx_saleinvoice_sellertaxno | 202     | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.38 sec)



EXPLAIN
SELECT * from flink2.saleinvoice
where ( TO_DAYS(INVOICEDATE) = TO_DAYS('2018-01-01 00:00:00') or TO_DAYS(INVOICEDATE) = TO_DAYS('2018-03-01 00:00:00')  ) and sellertaxno in (  '23sdsxx','xxcsad');
/* 没有效果
 全部个月，2个商户号区
 */
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | type  | possible_keys               | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p199901_p199901sp4,p199901_p199901sp8,p201708_p201708sp4,p201708_p201708sp8,p201709_p201709sp4,p201709_p201709sp8,p201710_p201710sp4,p201710_p201710sp8,p201711_p201711sp4,p201711_p201711sp8,p201712_p201712sp4,p201712_p201712sp8,p201801_p201801sp4,p201801_p201801sp8,p201802_p201802sp4,p201802_p201802sp8,p201803_p201803sp4,p201803_p201803sp8,p201804_p201804sp4,p201804_p201804sp8,p201805_p201805sp4,p201805_p201805sp8,p201806_p201806sp4,p201806_p201806sp8,p201807_p201807sp4,p201807_p201807sp8,p201808_p201808sp4,p201808_p201808sp8,p201809_p201809sp4,p201809_p201809sp8,p0_p0sp4,p0_p0sp8 | range | idx_saleinvoice_sellertaxno | idx_saleinvoice_sellertaxno | 202     | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.03 sec)





-- =============================================================================
-- 第三个示例 ---------------------------------------

drop table if EXISTS  flink2.`saleinvoice`;

CREATE TABLE if not exists flink2.`saleinvoice` (
    `CID` varchar(50) NOT NULL,
    `INVOICECODE` varchar(12) NOT NULL,
    `INVOICEDATE` date NOT NULL,
    `INVOICEID` varchar(50) NOT NULL,
    `INVOICENO` varchar(12) NOT NULL,
    `SELLERNAME` varchar(200) DEFAULT NULL,
    `SELLERTAXNO` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `SRCTYPE` int(11) DEFAULT NULL,
    `INVOICEDATEMONTH` int(11) NOT NULL,
    PRIMARY KEY (`CID`,`sellertaxno`,`INVOICEDATE`),
    KEY `INVOICEID` (`INVOICEID`),
    KEY `INVOICECODE` (`INVOICECODE`,`INVOICENO`),
    KEY `idx_saleinvoice_invociedate` (`INVOICEDATE`),
    KEY `idx_saleinvoice_srctype` (`SRCTYPE`),
    KEY `idx_saleinvoice_sellertaxno` (`SELLERTAXNO`),
    KEY `idx_saleinvoice_sellername` (`SELLERNAME`)
/*                                  ,
    UNIQUE KEY `unique_taxno_date` (`sellertaxno`,`INVOICEDATE`,`INVOICENO`,`INVOICECODE`) USING BTREE
    MySQL分区表对分区字段的限制
    分区的字段，必须是表上所有的唯一索引（或者主键索引）包含的字段的子集
    换句话说就是：（所有的）字段必须出现在（所有的）唯一索引或者主键索引的字段中，
    或者更通俗讲就是，一个表上有一个或者多个唯一索引的情况下，分区的字段必须被包含在所有的主键或者唯一索引字段中。

    // WJ : 使用 唯一键UNIQUE KEY没有作用，必须放在主键PRIMARY KEY中
 */
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
/* 用原始日期的函数做分区，不是很好用，见下面第二个示例*/
PARTITION by range(year(INVOICEDATE)*12+month(INVOICEDATE))
/* PARTITION by range(INVOICEDATEMONTH) */
subpartition BY KEY(SELLERTAXNO) subpartitions 10
(
    partition p24210 values less than (24210),
    partition p24211 values less than (24211),
    partition p24212 values less than (24212),
    partition p24213 values less than (24213),
    partition p24214 values less than (24214),
    partition p24215 values less than (24215),
    partition p24216 values less than (24216),
    partition p24217 values less than (24217),
    partition p24218 values less than (24218),
    partition p24219 values less than (24219),
    partition p24220 values less than (24220),
    partition p0 values less than maxvalue
);



select
    table_schema,
    table_name,
    partition_name,
    partition_ordinal_position,
    partition_method,
    partition_expression,
    table_rows
from information_schema.`PARTITIONS` where table_schema = 'flink2' and table_name = 'saleinvoice';
/*
+--------------+-------------+----------------+----------------------------+------------------+-----------------------------------------+------------+
| table_schema | table_name  | partition_name | partition_ordinal_position | partition_method | partition_expression                    | table_rows |
+--------------+-------------+----------------+----------------------------+------------------+-----------------------------------------+------------+
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24210         |                          1 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24211         |                          2 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24212         |                          3 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24213         |                          4 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24214         |                          5 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24215         |                          6 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24216         |                          7 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24217         |                          8 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24218         |                          9 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24219         |                         10 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p24220         |                         11 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
| flink2       | saleinvoice | p0             |                         12 | RANGE            | year(INVOICEDATE)*12+month(INVOICEDATE) |          0 |
+--------------+-------------+----------------+----------------------------+------------------+-----------------------------------------+------------+
120 rows in set (0.11 sec)
*/





EXPLAIN
SELECT * from flink2.saleinvoice
where ( invoicedate = '2018-01-10 00:00:00' or invoicedate = '2018-02-10 00:00:00' ) and sellertaxno in (  '23sdsxx','xxcsad');
/*  有效果
区间分区，是使用日期的函数，查询时候只单独使用日期字段的值等于什么、in 什么，才有效果
在 2个日期分区 * 2个商户号分区 中查询了
*/
+----+-------------+-------------+---------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                          | type  | possible_keys                                           | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+---------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p24218_p24218sp4,p24218_p24218sp8,p24219_p24219sp4,p24219_p24219sp8 | range | idx_saleinvoice_invociedate,idx_saleinvoice_sellertaxno | idx_saleinvoice_invociedate | 3       | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+---------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.04 sec)




EXPLAIN
SELECT * from flink2.saleinvoice
where invoicedate in ( '2018-01-10 00:00:00' , '2018-02-10 00:00:00' ) and sellertaxno in (  '23sdsxx','xxcsad');
/*  有效果
区间分区，是使用日期的函数，查询时候只单独使用日期字段的值等于什么、in 什么，才有效果
在 2个日期分区 * 2个商户号分区 中查询了
*/
+----+-------------+-------------+---------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                          | type  | possible_keys                                           | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+---------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p24218_p24218sp4,p24218_p24218sp8,p24219_p24219sp4,p24219_p24219sp8 | range | idx_saleinvoice_invociedate,idx_saleinvoice_sellertaxno | idx_saleinvoice_invociedate | 3       | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+---------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.04 sec)




EXPLAIN
SELECT * from flink2.saleinvoice
where ( invoicedate >= '2018-01-10 00:00:00' and invoicedate <= '2018-02-10 00:00:00' ) and sellertaxno in (  '23sdsxx','xxcsad');

/*  完全没有效果！！！！！
区间分区，是使用日期的函数，查询时候只单独使用日期字段的范围，没有效果，查询了全部分区
在 12个日期分区 * 10个商户号分区 中查询了
*/
+----+-------------+-------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              | type  | possible_keys                                           | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p24210_p24210sp0,p24210_p24210sp1,p24210_p24210sp2,p24210_p24210sp3,p24210_p24210sp4,p24210_p24210sp5,p24210_p24210sp6,p24210_p24210sp7,p24210_p24210sp8,p24210_p24210sp9,p24211_p24211sp0,p24211_p24211sp1,p24211_p24211sp2,p24211_p24211sp3,p24211_p24211sp4,p24211_p24211sp5,p24211_p24211sp6,p24211_p24211sp7,p24211_p24211sp8,p24211_p24211sp9,p24212_p24212sp0,p24212_p24212sp1,p24212_p24212sp2,p24212_p24212sp3,p24212_p24212sp4,p24212_p24212sp5,p24212_p24212sp6,p24212_p24212sp7,p24212_p24212sp8,p24212_p24212sp9,p24213_p24213sp0,p24213_p24213sp1,p24213_p24213sp2,p24213_p24213sp3,p24213_p24213sp4,p24213_p24213sp5,p24213_p24213sp6,p24213_p24213sp7,p24213_p24213sp8,p24213_p24213sp9,p24214_p24214sp0,p24214_p24214sp1,p24214_p24214sp2,p24214_p24214sp3,p24214_p24214sp4,p24214_p24214sp5,p24214_p24214sp6,p24214_p24214sp7,p24214_p24214sp8,p24214_p24214sp9,p24215_p24215sp0,p24215_p24215sp1,p24215_p24215sp2,p24215_p24215sp3,p24215_p24215sp4,p24215_p24215sp5,p24215_p24215sp6,p24215_p24215sp7,p24215_p24215sp8,p24215_p24215sp9,p24216_p24216sp0,p24216_p24216sp1,p24216_p24216sp2,p24216_p24216sp3,p24216_p24216sp4,p24216_p24216sp5,p24216_p24216sp6,p24216_p24216sp7,p24216_p24216sp8,p24216_p24216sp9,p24217_p24217sp0,p24217_p24217sp1,p24217_p24217sp2,p24217_p24217sp3,p24217_p24217sp4,p24217_p24217sp5,p24217_p24217sp6,p24217_p24217sp7,p24217_p24217sp8,p24217_p24217sp9,p24218_p24218sp0,p24218_p24218sp1,p24218_p24218sp2,p24218_p24218sp3,p24218_p24218sp4,p24218_p24218sp5,p24218_p24218sp6,p24218_p24218sp7,p24218_p24218sp8,p24218_p24218sp9,p24219_p24219sp0,p24219_p24219sp1,p24219_p24219sp2,p24219_p24219sp3,p24219_p24219sp4,p24219_p24219sp5,p24219_p24219sp6,p24219_p24219sp7,p24219_p24219sp8,p24219_p24219sp9,p24220_p24220sp0,p24220_p24220sp1,p24220_p24220sp2,p24220_p24220sp3,p24220_p24220sp4,p24220_p24220sp5,p24220_p24220sp6,p24220_p24220sp7,p24220_p24220sp8,p24220_p24220sp9,p0_p0sp0,p0_p0sp1,p0_p0sp2,p0_p0sp3,p0_p0sp4,p0_p0sp5,p0_p0sp6,p0_p0sp7,p0_p0sp8,p0_p0sp9 | range | idx_saleinvoice_invociedate,idx_saleinvoice_sellertaxno | idx_saleinvoice_invociedate | 3       | NULL |    1 |   100.00 | Using index condition |
+----+-------------+-------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+---------------------------------------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (1.49 sec)




EXPLAIN
SELECT * from flink2.saleinvoice
where ( year(invoicedate)*12+ MONTH(invoicedate)  >= 2018*12+1 and year(invoicedate)*12+ MONTH(invoicedate)  <= 2018*12+2 ) and sellertaxno in (  '23sdsxx','xxcsad');

/* 效果不正常
   日期函数分区，使用 大于小于区间， 商户号使用 in
在 12个日期分区 * 2个商户号分区 中查询了

*/
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                                                                                                                                                                                                                                                                                                                              | type  | possible_keys               | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p24210_p24210sp4,p24210_p24210sp8,p24211_p24211sp4,p24211_p24211sp8,p24212_p24212sp4,p24212_p24212sp8,p24213_p24213sp4,p24213_p24213sp8,p24214_p24214sp4,p24214_p24214sp8,p24215_p24215sp4,p24215_p24215sp8,p24216_p24216sp4,p24216_p24216sp8,p24217_p24217sp4,p24217_p24217sp8,p24218_p24218sp4,p24218_p24218sp8,p24219_p24219sp4,p24219_p24219sp8,p24220_p24220sp4,p24220_p24220sp8,p0_p0sp4,p0_p0sp8 | range | idx_saleinvoice_sellertaxno | idx_saleinvoice_sellertaxno | 202     | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.18 sec)




EXPLAIN
SELECT * from flink2.saleinvoice
where ( year(invoicedate)*12+ MONTH(invoicedate)= 2018*12+1 or year(invoicedate)*12+ MONTH(invoicedate)= 2018*12+2 ) and sellertaxno in (  '23sdsxx','xxcsad');

/* 效果不正常
   日期函数分区，使用两个等于， 商户号使用 in
在 12个日期分区 * 2个商户号分区 中查询了
*/
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                                                                                                                                                                                                                                                                                                                              | type  | possible_keys               | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p24210_p24210sp4,p24210_p24210sp8,p24211_p24211sp4,p24211_p24211sp8,p24212_p24212sp4,p24212_p24212sp8,p24213_p24213sp4,p24213_p24213sp8,p24214_p24214sp4,p24214_p24214sp8,p24215_p24215sp4,p24215_p24215sp8,p24216_p24216sp4,p24216_p24216sp8,p24217_p24217sp4,p24217_p24217sp8,p24218_p24218sp4,p24218_p24218sp8,p24219_p24219sp4,p24219_p24219sp8,p24220_p24220sp4,p24220_p24220sp8,p0_p0sp4,p0_p0sp8 | range | idx_saleinvoice_sellertaxno | idx_saleinvoice_sellertaxno | 202     | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.07 sec)




EXPLAIN
SELECT * from flink2.saleinvoice
where year(invoicedate)*12+ MONTH(invoicedate) in ( 2018*12+1, 2018*12+2 ) and sellertaxno in (  '23sdsxx','xxcsad');

/* 效果不正常
   日期函数分区，使用 in， 商户号使用 in
在 12个日期分区 * 2个商户号分区 中查询了
*/
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                                                                                                                                                                                                                                                                                                                              | type  | possible_keys               | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p24210_p24210sp4,p24210_p24210sp8,p24211_p24211sp4,p24211_p24211sp8,p24212_p24212sp4,p24212_p24212sp8,p24213_p24213sp4,p24213_p24213sp8,p24214_p24214sp4,p24214_p24214sp8,p24215_p24215sp4,p24215_p24215sp8,p24216_p24216sp4,p24216_p24216sp8,p24217_p24217sp4,p24217_p24217sp8,p24218_p24218sp4,p24218_p24218sp8,p24219_p24219sp4,p24219_p24219sp8,p24220_p24220sp4,p24220_p24220sp8,p0_p0sp4,p0_p0sp8 | range | idx_saleinvoice_sellertaxno | idx_saleinvoice_sellertaxno | 202     | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.04 sec)





EXPLAIN
SELECT * from flink2.saleinvoice
where year(invoicedate)*12+ MONTH(invoicedate)= 2018*12+1  and sellertaxno in (  '23sdsxx','xxcsad');

/* 效果不正常
   日期函数分区，使用两个等于， 商户号使用 in
在 12个日期分区 * 2个商户号分区 中查询了
*/
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                                                                                                                                                                                                                                                                                                                              | type  | possible_keys               | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p24210_p24210sp4,p24210_p24210sp8,p24211_p24211sp4,p24211_p24211sp8,p24212_p24212sp4,p24212_p24212sp8,p24213_p24213sp4,p24213_p24213sp8,p24214_p24214sp4,p24214_p24214sp8,p24215_p24215sp4,p24215_p24215sp8,p24216_p24216sp4,p24216_p24216sp8,p24217_p24217sp4,p24217_p24217sp8,p24218_p24218sp4,p24218_p24218sp8,p24219_p24219sp4,p24219_p24219sp8,p24220_p24220sp4,p24220_p24220sp8,p0_p0sp4,p0_p0sp8 | range | idx_saleinvoice_sellertaxno | idx_saleinvoice_sellertaxno | 202     | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.02 sec)
