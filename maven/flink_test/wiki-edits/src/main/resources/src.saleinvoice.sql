drop table if EXISTS  invoice.`saleinvoice`;

CREATE TABLE if not exists invoice.`saleinvoice` (
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
    `INVOICEDATE` datetime NOT NULL,
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
    `SELLERTAXNO` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
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
    PRIMARY KEY (`CID`),
    KEY `INVOICEID` (`INVOICEID`),
    KEY `INVOICECODE` (`INVOICECODE`,`INVOICENO`),
    KEY `idx_saleinvoice_invociedate` (`INVOICEDATE`),
    KEY `idx_saleinvoice_srctype` (`SRCTYPE`),
    KEY `idx_saleinvoice_sellertaxno` (`SELLERTAXNO`),
    KEY `idx_saleinvoice_sellername` (`SELLERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;