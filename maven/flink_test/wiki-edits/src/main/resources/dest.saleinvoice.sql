

drop table if EXISTS  data_warehouse2.`saleinvoice`;

CREATE TABLE if not exists data_warehouse2.`saleinvoice` (
    `CID` varchar(50) NOT NULL,
    `AGENTCODE` varchar(20) DEFAULT NULL,
    `AUTHSTATE` int(11) DEFAULT NULL,
    `AUTHTIME` datetime DEFAULT NULL,
    `AUTHUSERNAME` varchar(50) DEFAULT NULL,
    `BATCHID` varchar(50) DEFAULT NULL,
    `BILLID` varchar(50) DEFAULT NULL,
    `BILLNO` longtext,
    `BUYERADDTEL` varchar(200) DEFAULT NULL,
    `BUYERBANKNO` varchar(200) DEFAULT NULL,
    `BUYERNAME` varchar(200) DEFAULT NULL,
    `BUYERNO` varchar(16) DEFAULT NULL,
    `BUYERTAXNO` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `CANCELFLAG` bit(1) NOT NULL,
    `CARRIERTAXNO` varchar(20) DEFAULT NULL,
    `CD` varchar(255) DEFAULT NULL,
    `CHECKCODE` varchar(200) DEFAULT NULL,
    `CHECKRESULT` longtext,
    `CHECKSTATE` int(11) DEFAULT NULL,
    `CHECKTIME` datetime DEFAULT NULL,
    `CIPHERTEXT` longtext,
    `CJHM` varchar(100) DEFAULT NULL,
    `COMMENTS` longtext,
    `CONFIRMSTATE` int(11) DEFAULT NULL,
    `CREATETIME` datetime DEFAULT NULL,
    `DETAILLISTFLAG` bit(1) DEFAULT NULL,
    `EMAIL` varchar(200) DEFAULT NULL,
    `ENCRYPTIONVER` varchar(50) DEFAULT NULL,
    `FDJHM` varchar(100) DEFAULT NULL,
    `FHRSBH` varchar(50) DEFAULT NULL,
    `FORWORDSTATE` int(11) DEFAULT NULL,
    `FORWORDTIME` datetime DEFAULT NULL,
    `GATHERINGPERSON` varchar(20) DEFAULT NULL,
    `HGZH` varchar(100) DEFAULT NULL,
    `IMGSTATE` int(11) DEFAULT NULL,
    `INCOMINGSTATE` int(11) DEFAULT NULL,
    `INCOMINGTIME` datetime DEFAULT NULL,
    `INCOMINGUSERNAME` varchar(50) DEFAULT NULL,
    `INPUTPERSON` varchar(20) DEFAULT NULL,
    `INVOICECODE` varchar(12) NOT NULL,
    `INVOICECONTENT` varchar(20) DEFAULT NULL,
    `INVOICEDATE` date NOT NULL,
    `INVOICEID` varchar(50) NOT NULL,
    `INVOICENO` varchar(12) NOT NULL,
    `INVOICETYPE` varchar(50) NOT NULL,
    `JZTYPE` varchar(20) DEFAULT NULL,
    `KPZDBS` varchar(50) DEFAULT NULL,
    `LOGNO` varchar(45) DEFAULT NULL,
    `MACHINENO` varchar(20) DEFAULT NULL,
    `MAKEINVOICEDEVICENO` smallint(6) DEFAULT NULL,
    `MAKEINVOICEPERSON` varchar(20) DEFAULT NULL,
    `MOBILENO` varchar(20) DEFAULT NULL,
    `PDFURL` longtext,
    `PRINTFLAG` bit(1) DEFAULT NULL,
    `PUSHSTATE` int(11) DEFAULT NULL,
    `PUSHTIME` datetime DEFAULT NULL,
    `QYD` varchar(255) DEFAULT NULL,
    `RECHECKPERSON` varchar(20) DEFAULT NULL,
    `REPAIRFLAG` bit(1) DEFAULT NULL,
    `RESPONECODE` varchar(60) DEFAULT NULL,
    `RESPONEEXPLAIN` varchar(60) DEFAULT NULL,
    `SALEBILLNO` varchar(50) DEFAULT NULL,
    `SELLERADDTEL` varchar(200) DEFAULT NULL,
    `SELLERBANKNO` varchar(200) DEFAULT NULL,
    `SELLERNAME` varchar(200) DEFAULT NULL,
    `SELLERTAXNO` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `SENTERRTIMES` int(11) DEFAULT NULL,
    `SENTOPERATOR` varchar(45) DEFAULT NULL,
    `SENTTIME` varchar(20) DEFAULT NULL,
    `SENTTOBANK` int(11) DEFAULT NULL,
    `SHRSBH` varchar(50) DEFAULT NULL,
    `SKTYPE` varchar(20) DEFAULT NULL,
    `SOURCE` varchar(100) DEFAULT NULL,
    `SPSM` varchar(50) DEFAULT NULL,
    `SRCTYPE` int(11) DEFAULT NULL,
    `TAXOFFICECODE` varchar(20) DEFAULT NULL,
    `TAXRATE` double DEFAULT NULL,
    `TOTALAMOUNT` double DEFAULT NULL,
    `TOTALTAX` double DEFAULT NULL,
    `WRITEBACKSTATE` int(11) DEFAULT NULL,
    `WRITEBACKTIME` datetime DEFAULT NULL,
    `XCRS` varchar(200) DEFAULT NULL,
    `XFDH` varchar(100) DEFAULT NULL,
    `XFDZ` varchar(200) DEFAULT NULL,
    `YSHWXX` varchar(255) DEFAULT NULL,
    `YYZZH` varchar(100) DEFAULT NULL,
    `ZH` varchar(100) DEFAULT NULL,
    `ZYSPMC` varchar(200) DEFAULT NULL,
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
    partition p201612	 values less than (	24205	),
    partition p201701	 values less than (	24206	),
    partition p201702	 values less than (	24207	),
    partition p201703	 values less than (	24208	),
    partition p201704	 values less than (	24209	),
    partition p201705	 values less than (	24210	),
    partition p201706	 values less than (	24211	),
    partition p201707	 values less than (	24212	),
    partition p201708	 values less than (	24213	),
    partition p201709	 values less than (	24214	),
    partition p201710	 values less than (	24215	),
    partition p201711	 values less than (	24216	),
    partition p201712	 values less than (	24217	),
    partition p201801	 values less than (	24218	),
    partition p201802	 values less than (	24219	),
    partition p201803	 values less than (	24220	),
    partition p201804	 values less than (	24221	),
    partition p201805	 values less than (	24222	),
    partition p201806	 values less than (	24223	),
    partition p201807	 values less than (	24224	),
    partition p201808	 values less than (	24225	),
    partition p201809	 values less than (	24226	),
    partition p201810	 values less than (	24227	),
    partition p201811	 values less than (	24228	),
    partition p201812	 values less than (	24229	),
    partition p201901	 values less than (	24230	),
    partition p201902	 values less than (	24231	),
    partition p201903	 values less than (	24232	),
    partition p201904	 values less than (	24233	),
    partition p201905	 values less than (	24234	),
    partition p201906	 values less than (	24235	),
    partition p201907	 values less than (	24236	),
    partition p201908	 values less than (	24237	),
    partition p201909	 values less than (	24238	),
    partition p201910	 values less than (	24239	),
    partition p201911	 values less than (	24240	),
    partition p201912	 values less than (	24241	),
    partition p202001	 values less than (	24242	),
    partition p202002	 values less than (	24243	),
    partition p202003	 values less than (	24244	),
    partition p202004	 values less than (	24245	),
    partition p202005	 values less than (	24246	),
    partition p202006	 values less than (	24247	),
    partition p202007	 values less than (	24248	),
    partition p202008	 values less than (	24249	),
    partition p202009	 values less than (	24250	),
    partition p202010	 values less than (	24251	),
    partition p202011	 values less than (	24252	),
    partition p202012	 values less than (	24253	),
    partition p202101	 values less than (	24254	),
    partition p202102	 values less than (	24255	),
    partition p202103	 values less than (	24256	),
    partition p202104	 values less than (	24257	),
    partition p202105	 values less than (	24258	),
    partition p202106	 values less than (	24259	),
    partition p202107	 values less than (	24260	),
    partition p202108	 values less than (	24261	),
    partition p202109	 values less than (	24262	),
    partition p202110	 values less than (	24263	),
    partition p202111	 values less than (	24264	),
    partition p202112	 values less than (	24265	),
    partition p202201	 values less than (	24266	),
    partition p202202	 values less than (	24267	),
    partition p202203	 values less than (	24268	),
    partition p202204	 values less than (	24269	),
    partition p202205	 values less than (	24270	),
    partition p202206	 values less than (	24271	),
    partition p202207	 values less than (	24272	),
    partition p202208	 values less than (	24273	),
    partition p202209	 values less than (	24274	),
    partition p202210	 values less than (	24275	),
    partition p202211	 values less than (	24276	),
    partition p202212	 values less than (	24277	),
    partition p202301	 values less than (	24278	),
    partition p202302	 values less than (	24279	),
    partition p202303	 values less than (	24280	),
    partition p202304	 values less than (	24281	),
    partition p202305	 values less than (	24282	),
    partition p202306	 values less than (	24283	),
    partition p202307	 values less than (	24284	),
    partition p202308	 values less than (	24285	),
    partition p202309	 values less than (	24286	),
    partition p202310	 values less than (	24287	),
    partition p202311	 values less than (	24288	),
    partition p202312	 values less than (	24289	),
    partition p202401	 values less than (	24290	),
    partition p202402	 values less than (	24291	),
    partition p202403	 values less than (	24292	),
    partition p202404	 values less than (	24293	),
    partition p202405	 values less than (	24294	),
    partition p202406	 values less than (	24295	),
    partition p202407	 values less than (	24296	),
    partition p202408	 values less than (	24297	),
    partition p202409	 values less than (	24298	),
    partition p202410	 values less than (	24299	),
    partition p202411	 values less than (	24300	),
    partition p202412	 values less than (	24301	),
    partition p202501	 values less than (	24302	),
    partition p202502	 values less than (	24303	),
    partition p202503	 values less than (	24304	),
    partition p202504	 values less than (	24305	),
    partition p202505	 values less than (	24306	),
    partition p202506	 values less than (	24307	),
    partition p202507	 values less than (	24308	),
    partition p202508	 values less than (	24309	),
    partition p202509	 values less than (	24310	),
    partition p202510	 values less than (	24311	),
    partition p202511	 values less than (	24312	),
    partition p202512	 values less than (	24313	),
    partition p202601	 values less than (	24314	),
    partition p202602	 values less than (	24315	),
    partition p202603	 values less than (	24316	),
    partition p202604	 values less than (	24317	),
    partition p202605	 values less than (	24318	),
    partition p202606	 values less than (	24319	),
    partition p202607	 values less than (	24320	),
    partition p202608	 values less than (	24321	),
    partition p202609	 values less than (	24322	),
    partition p202610	 values less than (	24323	),
    partition p202611	 values less than (	24324	),
    partition p202612	 values less than (	24325	),
    partition p202701	 values less than (	24326	),
    partition p202702	 values less than (	24327	),
    partition p202703	 values less than (	24328	),
    partition p202704	 values less than (	24329	),
    partition p202705	 values less than (	24330	),
    partition p202706	 values less than (	24331	),
    partition p202707	 values less than (	24332	),
    partition p202708	 values less than (	24333	),
    partition p202709	 values less than (	24334	),
    partition p202710	 values less than (	24335	),
    partition p202711	 values less than (	24336	),
    partition p202712	 values less than (	24337	),
    partition p202801	 values less than (	24338	),
    partition p202802	 values less than (	24339	),
    partition p202803	 values less than (	24340	),
    partition p202804	 values less than (	24341	),
    partition p202805	 values less than (	24342	),
    partition p202806	 values less than (	24343	),
    partition p202807	 values less than (	24344	),
    partition p202808	 values less than (	24345	),
    partition p202809	 values less than (	24346	),
    partition p202810	 values less than (	24347	),
    partition p202811	 values less than (	24348	),
    partition p202812	 values less than (	24349	),
    partition p99999 values less than maxvalue
);


EXPLAIN
SELECT * from flink2.saleinvoice
where ( INVOICEDATEMONTH >= 2018*12+1 and INVOICEDATEMONTH <= 2018*12+3  ) and sellertaxno in (  '23sdsxx','xxcsad');

/*
有3个月，2个商户号，共6个分区
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
| id | select_type | table       | partitions                                                                                            | type  | possible_keys               | key                         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | saleinvoice | p24218_p24218sp4,p24218_p24218sp8,p24219_p24219sp4,p24219_p24219sp8,p24220_p24220sp4,p24220_p24220sp8 | range | idx_saleinvoice_sellertaxno | idx_saleinvoice_sellertaxno | 202     | NULL |    2 |   100.00 | Using index condition |
+----+-------------+-------------+-------------------------------------------------------------------------------------------------------+-------+-----------------------------+-----------------------------+---------+------+------+----------+-----------------------+
1 row in set (0.11 sec)
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

-- 第二个示例 ---------------------------------------
EXPLAIN
SELECT * from flink2.saleinvoice
where ( invoicedate > '2018-01-10 00:00:00' and invoicedate < '2018-02-10 00:00:00' ) and sellertaxno in (  '23sdsxx','xxcsad');



EXPLAIN
SELECT * from flink2.saleinvoice
where ( year(invoicedate)*12+ MONTH(invoicedate)  >= 2018*12+1 and year(invoicedate)*12+ MONTH(invoicedate)  <= 2018*12+2 ) and sellertaxno in (  '23sdsxx','xxcsad');




