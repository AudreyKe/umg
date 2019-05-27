DROP TABLE IF EXISTS `EMAIL`;
CREATE TABLE `EMAIL`(
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
	`unique_id` varchar(64) NOT NULL COMMENT '唯一标识',
	`channel_code` int(8) DEFAULT NULL COMMENT '通道',
	`oper_code`int(8) DEFAULT NULL COMMENT '操作码',
	`tran_protocol_type`int(8) DEFAULT NULL COMMENT '传输协议：1.同步、2.异步',
	`reply_to` varchar(255) DEFAULT NULL COMMENT '信件回复的收件人',
	`sent_date` bigint(20) DEFAULT NULL COMMENT '发送时间戳',
	`to` varchar(255) DEFAULT NULL COMMENT '收件人',
	`cc` varchar(1024) DEFAULT NULL COMMENT '抄送人',
	`bcc` varchar(1024) DEFAULT NULL COMMENT '密抄送人',
	`subject` varchar(1024) DEFAULT NULL COMMENT '邮件主题',
	`content` text DEFAULT NULL COMMENT '邮件内容',
	`status` int(2) NOT NULL COMMENT '0，未发送,1，已发送，2.警告，-1，异常',
	`retry` int(8) DEFAULT NULL COMMENT '重试次数',
	`max_retry` int(8) DEFAULT NULL COMMENT '最大重试次',
	`app_code` varchar(64) DEFAULT NULL COMMENT '系统编号',
	`warm_msg` varchar(1024) DEFAULT NULL COMMENT '警告信息',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
PRIMARY KEY (`id`),
UNIQUE KEY `unique_index_id` (`unique_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='邮件';