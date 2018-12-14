DROP TABLE IF EXISTS `tb_verify_code`;
CREATE TABLE `tb_verify_code` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `logic_delete` bit(1) DEFAULT 0,
  `update_time` datetime NOT NULL,
  `verify_template_code` varchar(255) NOT NULL,
  `verify_code_id` varchar(128) NOT NULL,
  `verify_code_value` varchar(255) NOT NULL,
  `biz_code` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `send_type` varchar(255) NOT NULL,
  `duration` bigint(20) NOT NULL DEFAULT 180000,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

ALTER TABLE `tb_verify_code` ADD INDEX `idx_verify_code_id` (`verify_code_id`) ;





DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `open_id` bigint(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `logic_delete` bit(1) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `master_open_id` bigint(64) DEFAULT NULL,
  `activation` bit(1) DEFAULT 1,
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `tb_identity`;
CREATE TABLE `tb_identity` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `partner` bigint(64) NOT NULL,
  `open_id` bigint(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `logic_delete` bit(1) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `identity_type` varchar(128) NOT NULL,
  `identity_value` varchar(128) NOT NULL,
  `validate_begin_date` datetime NOT NULL,
  `validate_end_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

ALTER TABLE `tb_identity` ADD UNIQUE INDEX `UNI_PAR_IDENTITY` (`partner`, `identity_type`, `identity_value`);
ALTER TABLE `tb_identity` ADD INDEX `IDX_PAR_OPENID_TYPE` (`partner`, `open_id`, `identity_type`);


DROP TABLE IF EXISTS `tb_meeting`;
CREATE TABLE `tb_meeting` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `partner` bigint(64) NOT NULL,
  `open_id` bigint(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `logic_delete` bit(1) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `meeting_type` varchar(128) NOT NULL,
  `rendezvous_type` varchar(128) NOT NULL,
  `rendezvous_value` varchar(128) NOT NULL,
  `meeting_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;




DROP TABLE IF EXISTS `tb_member`;
CREATE TABLE `tb_member` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `partner` bigint(64) NOT NULL,
  `open_id` bigint(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `logic_delete` bit(1) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `nick_name` varchar(255) NOT NULL,
  `img_url` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8mb4;


ALTER TABLE `tb_member` ADD UNIQUE INDEX `UNQ_PAR_OPENID` (`partner`, `open_id`);


