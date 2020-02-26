DROP TABLE IF EXISTS dm_taxloan_rpt.taxloan_report_daily;
CREATE TABLE dm_taxloan_rpt.taxloan_report_daily(
	report_day date COMMENT '统计日期',
	mcht_cnt_total Int COMMENT '累计装插件企业数',
	mcht_cnt_incre Int COMMENT '企业数增加',
	saleinvoice_cnt_total bigint COMMENT '累计采集发票数',
	saleinvoice_cnt_incre bigint COMMENT '发票数增加',
	apply_cnt_total Int COMMENT '累计银行申请户数',
	apply_cnt_incre Int COMMENT '银行申请户数当日新增',
	bank_approved_cnt_total Int COMMENT '累计银行通过户数',
	bank_approved_cnt_incre Int COMMENT '银行通过户数当日新增',
	bank_amt_total decimal(20,2) COMMENT '累计银行授信金额',
	bank_amt_incre decimal(20,2) COMMENT '当日银行新增授信金额',
	preloan_approved_cnt_total Int COMMENT '累计贷前通过户数',
	preloan_approved_cnt_incre Int COMMENT '贷前通过户数当日新增',
	use_cnt_total Int COMMENT '累计放款户数',
	use_cnt_incre Int COMMENT '放款户数当日新增',
	use_amt_total decimal(20,2) COMMENT '累计放款金额',
	use_amt_incre decimal(20,2) COMMENT '当日新增放款金额',
	bank_approved_rate decimal(5,2) COMMENT '累计银行通过率',
	preloan_approved_rate decimal(5,2) COMMENT '累计贷前通过率',
	use_rate decimal(5,2) COMMENT '累计放款率',
	is_delete String COMMENT '逻辑删除，1.是，0.否',
	--create_time date COMMENT '创建时间',
	create_user String COMMENT '创建人',
	modify_time timestamp COMMENT '修改时间',
	modify_user String COMMENT '修改人'	
)COMMENT '【税金贷】统计日报表 ' partitioned by (create_time string) 
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\0001' stored AS orc;