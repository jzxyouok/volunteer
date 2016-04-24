/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : db_ehr

Target Server Type    : MYSQL
Target Server Version : 50532
File Encoding         : 65001

Date: 2014-07-11 16:40:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `act_ge_bytearray`
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '文件编号',
  `REV_` int(11) DEFAULT NULL COMMENT '版本号',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '部署的文件名称',
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '部署表ID',
  `BYTES_` longblob COMMENT '部署文件',
  `GENERATED_` tinyint(4) DEFAULT NULL COMMENT '是否是引擎生成',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT二进制数据表';

-- ----------------------------
-- Records of act_ge_bytearray
-- ----------------------------

-- ----------------------------
-- Table structure for `act_ge_property`
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '属性名称',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '属性值',
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT属性数据表';

-- ----------------------------
-- Records of act_ge_property
-- ----------------------------
INSERT INTO `act_ge_property` VALUES ('next.dbid', '1', '1');
INSERT INTO `act_ge_property` VALUES ('schema.history', 'create(5.14)', '1');
INSERT INTO `act_ge_property` VALUES ('schema.version', '5.14', '1');

-- ----------------------------
-- Table structure for `act_hi_actinst`
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '流程定义ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '流程实例ID',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '执行实例ID',
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '节点ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务实例ID',
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '调用外部的流程实例ID',
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '节点名称',
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '节点类型',
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '签收人',
  `START_TIME_` datetime NOT NULL COMMENT '开始时间',
  `END_TIME_` datetime DEFAULT NULL COMMENT '结束时间',
  `DURATION_` bigint(20) DEFAULT NULL COMMENT '耗时',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT历史节点表';

-- ----------------------------
-- Records of act_hi_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for `act_hi_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务实例ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT 'URL_',
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字节表的ID',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT历史附件表';

-- ----------------------------
-- Records of act_hi_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `act_hi_comment`
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `TIME_` datetime NOT NULL COMMENT '时间',
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '节点任务ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '行为类型',
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '基本内容',
  `FULL_MSG_` longblob COMMENT '全部内容',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT历史意见表';

-- ----------------------------
-- Records of act_hi_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `act_hi_detail`
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '类型',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务实例ID',
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '节点实例ID',
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '参数类型',
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `TIME_` datetime NOT NULL COMMENT '时间戳',
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字节表ID',
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT历史详情表';

-- ----------------------------
-- Records of act_hi_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `act_hi_identitylink`
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT历史流程人员表';

-- ----------------------------
-- Records of act_hi_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for `act_hi_procinst`
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '流程实例ID',
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务主键',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '流程定义ID',
  `START_TIME_` datetime NOT NULL COMMENT '开始时间',
  `END_TIME_` datetime DEFAULT NULL COMMENT '结束时间',
  `DURATION_` bigint(20) DEFAULT NULL COMMENT '耗时',
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '起草人',
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开始节点ID',
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '结束节点ID',
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父流程实例ID',
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '删除原因',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  UNIQUE KEY `ACT_UNIQ_HI_BUS_KEY` (`PROC_DEF_ID_`,`BUSINESS_KEY_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT历史流程实例表';

-- ----------------------------
-- Records of act_hi_procinst
-- ----------------------------

-- ----------------------------
-- Table structure for `act_hi_taskinst`
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键ID',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义ID',
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '节点定义ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点实例ID',
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '任务的拥有者',
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '签收人或被委托',
  `START_TIME_` datetime NOT NULL COMMENT '开始时间',
  `CLAIM_TIME_` datetime DEFAULT NULL COMMENT '提醒时间',
  `END_TIME_` datetime DEFAULT NULL COMMENT '结束时间',
  `DURATION_` bigint(20) DEFAULT NULL COMMENT '耗时',
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '删除原因',
  `PRIORITY_` int(11) DEFAULT NULL COMMENT '优先级别',
  `DUE_DATE_` datetime DEFAULT NULL COMMENT '过期时间',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '节点定义formkey',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT历史任务实例表';

-- ----------------------------
-- Records of act_hi_taskinst
-- ----------------------------

-- ----------------------------
-- Table structure for `act_hi_varinst`
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '任务实例ID',
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '参数类型',
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字节表ID',
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT历史变量表';

-- ----------------------------
-- Records of act_hi_varinst
-- ----------------------------

-- ----------------------------
-- Table structure for `act_id_group`
-- ----------------------------
DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT用户组信息表';

-- ----------------------------
-- Records of act_id_group
-- ----------------------------

-- ----------------------------
-- Table structure for `act_id_info`
-- ----------------------------
DROP TABLE IF EXISTS `act_id_info`;
CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT用户扩展信息表';

-- ----------------------------
-- Records of act_id_info
-- ----------------------------

-- ----------------------------
-- Table structure for `act_id_membership`
-- ----------------------------
DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户ID',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户组ID',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT用户与用户组对应信息表';

-- ----------------------------
-- Records of act_id_membership
-- ----------------------------

-- ----------------------------
-- Table structure for `act_id_user`
-- ----------------------------
DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '姓',
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名',
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮件',
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '图片ID',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT用户信息表';

-- ----------------------------
-- Records of act_id_user
-- ----------------------------

-- ----------------------------
-- Table structure for `act_re_deployment`
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '部署名称',
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '分类',
  `DEPLOY_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '部署时间',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT部署信息表';

-- ----------------------------
-- Records of act_re_deployment
-- ----------------------------

-- ----------------------------
-- Table structure for `act_re_model`
-- ----------------------------
DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '分类',
  `CREATE_TIME_` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `LAST_UPDATE_TIME_` timestamp NULL DEFAULT NULL COMMENT '最新修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '以json格式保存流程定义的信息',
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '部署ID',
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT流程设计模型部署表';

-- ----------------------------
-- Records of act_re_model
-- ----------------------------

-- ----------------------------
-- Table structure for `act_re_procdef`
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '分类',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '定义的KEY',
  `VERSION_` int(11) NOT NULL COMMENT '版本',
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '部署表ID',
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT 'bpmn文件名称',
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT 'png图片名称',
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL COMMENT '是否存在开始节点formKey',
  `SUSPENSION_STATE_` int(11) DEFAULT NULL COMMENT '是否挂起',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT流程定义数据表\r\n业务流程定义数据表。此表和ACT_RE_DEPLOYMENT是多对一的关系，\r\n即，一个部署的bar包里可能包含多个流程定义文件，\r\n每个流程定义文件都会有一条记录在ACT_REPROCDEF表内，\r\n每个流程定义的数据，都会对于ACT_GE_BYTEARRAY表内的一个资源文件和PNG图片文件。\r\n和ACT_GE_BYTEARRAY的关联是通过程序用ACT_GE_BYTEARRAY.NAME与\r\nACT_RE_PROCDEF.NAME_完成的，在数据库表结构中没有体现。';

-- ----------------------------
-- Records of act_re_procdef
-- ----------------------------

-- ----------------------------
-- Table structure for `act_ru_event_subscr`
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本',
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '事件类型',
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '事件名称',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '活动实例ID',
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '配置',
  `CREATED_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '是否创建',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT监听信息表';

-- ----------------------------
-- Records of act_ru_event_subscr
-- ----------------------------

-- ----------------------------
-- Table structure for `act_ru_execution`
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务主键ID',
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点实例ID',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义ID',
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '节点实例ID',
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL COMMENT '是否存活',
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL COMMENT '是否并行',
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL COMMENT '是否挂起',
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_RU_BUS_KEY` (`PROC_DEF_ID_`,`BUSINESS_KEY_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT运行时流程执行实例表';

-- ----------------------------
-- Records of act_ru_execution
-- ----------------------------

-- ----------------------------
-- Table structure for `act_ru_identitylink`
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '组ID',
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '节点实例ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义ID',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT运行时流程人员表';

-- ----------------------------
-- Records of act_ru_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for `act_ru_job`
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '版本',
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '类型',
  `LOCK_EXP_TIME_` timestamp NULL DEFAULT NULL COMMENT '锁定释放时间',
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '挂起者',
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义ID',
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '异常信息ID',
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '异常信息',
  `DUEDATE_` timestamp NULL DEFAULT NULL COMMENT '到期时间',
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '重复',
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '处理类型',
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '处理配置',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT运行时定时任务数据表';

-- ----------------------------
-- Records of act_ru_job
-- ----------------------------

-- ----------------------------
-- Table structure for `act_ru_task`
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程定义ID',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '节点定义名称',
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点实例ID',
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '节点定义描述',
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '节点定义的KEY',
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '实际签收人',
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '签收人或委托人',
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '委托类型',
  `PRIORITY_` int(11) DEFAULT NULL COMMENT '优先级别',
  `CREATE_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DUE_DATE_` datetime DEFAULT NULL COMMENT '过期时间',
  `SUSPENSION_STATE_` int(11) DEFAULT NULL COMMENT '是否挂起',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT运行时任务节点表';

-- ----------------------------
-- Records of act_ru_task
-- ----------------------------

-- ----------------------------
-- Table structure for `act_ru_variable`
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL COMMENT '乐观锁',
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '类型',
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '执行实例ID',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '流程实例ID',
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '节点实例ID',
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字节表ID',
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ACT运行时流程变量数据表';

-- ----------------------------
-- Records of act_ru_variable
-- ----------------------------

-- ----------------------------
-- Table structure for `att_empinfo`
-- ----------------------------
DROP TABLE IF EXISTS `att_empinfo`;
CREATE TABLE `att_empinfo` (
  `id` bigint(20) NOT NULL,
  `createBy` varchar(20) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateBy` varchar(20) DEFAULT NULL COMMENT '更新人',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `tenantId` bigint(20) NOT NULL COMMENT '租户ID',
  `empId` varchar(20) DEFAULT NULL COMMENT '员工编号',
  `empName` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `postid` bigint(20) DEFAULT NULL COMMENT '职位ID',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮件',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `workDate` date DEFAULT NULL COMMENT '参加工作日期',
  `entryDate` date DEFAULT NULL COMMENT '入职日期',
  `positiveDate` date DEFAULT NULL COMMENT '转正日期',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态：0 禁用; 1启用',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考勤档案表';

-- ----------------------------
-- Records of att_empinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL,
  `ownmodule` varchar(20) DEFAULT NULL COMMENT '所属模块',
  `opType` varchar(10) DEFAULT NULL,
  `opUser` varchar(50) DEFAULT NULL COMMENT '操作人',
  `opDate` datetime DEFAULT NULL COMMENT '操作时间',
  `opContent` varchar(100) DEFAULT NULL COMMENT '操作内容',
  `opIp` varchar(20) DEFAULT NULL COMMENT '操作IP',
  `tenantId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', '人事模块', null, 'admin', '2014-07-10 16:49:47', 'test', '127.0.0.1', '1');
INSERT INTO `sys_log` VALUES ('2', '考勤模块', null, 'ydwcn', '2014-07-10 16:50:37', 'aaa', '192.168.1.1', '1');
INSERT INTO `sys_log` VALUES ('3', null, null, null, null, null, null, '1');
INSERT INTO `sys_log` VALUES ('4', null, null, null, null, null, null, '1');
INSERT INTO `sys_log` VALUES ('5', null, null, null, null, null, null, '1');
INSERT INTO `sys_log` VALUES ('6', null, null, null, null, null, null, '1');
INSERT INTO `sys_log` VALUES ('7', null, null, null, null, null, null, '1');
INSERT INTO `sys_log` VALUES ('8', null, null, null, null, null, null, '1');
INSERT INTO `sys_log` VALUES ('9', null, null, null, null, null, null, '1');
INSERT INTO `sys_log` VALUES ('10', null, null, null, null, null, null, '1');
INSERT INTO `sys_log` VALUES ('11', null, null, null, null, null, null, '1');
INSERT INTO `sys_log` VALUES ('12', null, null, null, null, null, null, '1');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL,
  `metaId` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `tenantId` bigint(20) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `HR_PK_MENU_META` (`metaId`),
  CONSTRAINT `HR_PK_MENU_META` FOREIGN KEY (`metaId`) REFERENCES `sys_menu_meta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '-1', '1');
INSERT INTO `sys_menu` VALUES ('2', '9000', '1');
INSERT INTO `sys_menu` VALUES ('3', '9001', '1');
INSERT INTO `sys_menu` VALUES ('4', '9002', '1');
INSERT INTO `sys_menu` VALUES ('5', '9003', '1');
INSERT INTO `sys_menu` VALUES ('6', '9004', '1');
INSERT INTO `sys_menu` VALUES ('7', '9005', '1');
INSERT INTO `sys_menu` VALUES ('8', '3000', '1');
INSERT INTO `sys_menu` VALUES ('9', '3001', '1');
INSERT INTO `sys_menu` VALUES ('10', '3002', '1');
INSERT INTO `sys_menu` VALUES ('11', '3003', '1');
INSERT INTO `sys_menu` VALUES ('12', '3004', '1');
INSERT INTO `sys_menu` VALUES ('13', '3005', '1');
INSERT INTO `sys_menu` VALUES ('14', '3006', '1');
INSERT INTO `sys_menu` VALUES ('15', '3007', '1');
INSERT INTO `sys_menu` VALUES ('16', '3008', '1');
INSERT INTO `sys_menu` VALUES ('17', '9006', '1');

-- ----------------------------
-- Table structure for `sys_menu_meta`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_meta`;
CREATE TABLE `sys_menu_meta` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `iconCls` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `pid` bigint(20) DEFAULT NULL COMMENT '父菜单',
  `isVisble` tinyint(1) DEFAULT NULL COMMENT '是否显示',
  `isLeaf` tinyint(1) DEFAULT NULL COMMENT '是否叶节点',
  `url` varchar(256) DEFAULT NULL COMMENT 'URL',
  `sn` int(5) DEFAULT NULL COMMENT '序列号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu_meta
-- ----------------------------
INSERT INTO `sys_menu_meta` VALUES ('-1', '人力资源', null, null, '0', '0', null, '-1');
INSERT INTO `sys_menu_meta` VALUES ('3000', '考勤管理', 'icon-system', '-1', '1', '0', null, '3000');
INSERT INTO `sys_menu_meta` VALUES ('3001', '考勤档案', 'icon-sysuser', '3000', '1', '1', null, '3001');
INSERT INTO `sys_menu_meta` VALUES ('3002', '节假日管理', 'icon-sysrole', '3000', '1', '1', null, '3002');
INSERT INTO `sys_menu_meta` VALUES ('3003', '考勤排班', 'icon-syslog', '3000', '1', '1', null, '3003');
INSERT INTO `sys_menu_meta` VALUES ('3004', '请假管理', 'icon-sysparam', '3000', '1', '1', null, '3004');
INSERT INTO `sys_menu_meta` VALUES ('3005', '加班管理', 'icon-sysdict', '3000', '1', '1', null, '3005');
INSERT INTO `sys_menu_meta` VALUES ('3006', '出差管理', 'icon-systenant', '3000', '1', '1', null, '3006');
INSERT INTO `sys_menu_meta` VALUES ('3007', '考勤数据', 'icon-sysuser', '3000', '1', '1', null, '3007');
INSERT INTO `sys_menu_meta` VALUES ('3008', '考勤计算', 'icon-sysrole', '3000', '1', '1', null, '3008');
INSERT INTO `sys_menu_meta` VALUES ('9000', '系统管理', 'icon-system', '-1', '1', '0', null, '9000');
INSERT INTO `sys_menu_meta` VALUES ('9001', '用户管理', 'icon-sysuser', '9000', '1', '1', '/system/sysuser/manage', '9001');
INSERT INTO `sys_menu_meta` VALUES ('9002', '角色管理', 'icon-sysrole', '9000', '1', '1', '/system/sysrole/manage', '9002');
INSERT INTO `sys_menu_meta` VALUES ('9003', '日志管理', 'icon-syslog', '9000', '1', '1', '/system/syslog/manage', '9003');
INSERT INTO `sys_menu_meta` VALUES ('9004', '参数管理', 'icon-sysparam', '9000', '1', '1', '/system/sysparam/manage', '9004');
INSERT INTO `sys_menu_meta` VALUES ('9005', '字典管理', 'icon-sysdict', '9000', '1', '1', '/system/sysdict/manage', '9005');
INSERT INTO `sys_menu_meta` VALUES ('9006', '租户管理', 'icon-systenant', '-1', '0', '1', '/tenant/manage', '9006');

-- ----------------------------
-- Table structure for `sys_menu_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role` (
  `menuId` bigint(20) NOT NULL COMMENT '菜单ID',
  `roleId` bigint(20) NOT NULL COMMENT '角色ID',
  KEY `HR_PK_MENUID_ID` (`menuId`),
  KEY `HR_PK_ROLEID_ID` (`roleId`),
  CONSTRAINT `HR_PK_MENUID_ID` FOREIGN KEY (`menuId`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `HR_PK_ROLEID_ID` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_param`
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `paramId` bigint(20) DEFAULT NULL COMMENT '系统参数ID',
  `tenantId` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `HR_PK_PARAM_META` (`paramId`),
  CONSTRAINT `HR_PK_PARAM_META` FOREIGN KEY (`paramId`) REFERENCES `sys_param_meta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户系统参数表';

-- ----------------------------
-- Records of sys_param
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_param_meta`
-- ----------------------------
DROP TABLE IF EXISTS `sys_param_meta`;
CREATE TABLE `sys_param_meta` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `paramKey` varchar(20) DEFAULT NULL COMMENT '参数KEY',
  `paramName` varchar(20) DEFAULT NULL COMMENT '参数名称',
  `paramValue` varchar(50) DEFAULT NULL COMMENT '参数值',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表';

-- ----------------------------
-- Records of sys_param_meta
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL,
  `createBy` varchar(20) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateBy` varchar(20) DEFAULT NULL COMMENT '更新人',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `roleName` varchar(50) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_tenant`
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `id` bigint(20) NOT NULL,
  `code` varchar(20) DEFAULT NULL COMMENT '租户代码',
  `name` varchar(50) DEFAULT NULL COMMENT '租户名称',
  `companyName` varchar(100) DEFAULT NULL COMMENT '单位名称',
  `address` varchar(100) DEFAULT NULL COMMENT '单位地址',
  `telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `email` varchar(50) DEFAULT NULL COMMENT '邮件',
  `startDate` date DEFAULT NULL COMMENT '租赁开始时间',
  `endDate` date DEFAULT NULL COMMENT '租赁到期时间',
  `status` int(2) DEFAULT NULL COMMENT '租户状态',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES ('1', '1', '云创智企', '云创智企科技有限公司', '北京', '131000000', '010-8888888', 'admin@cloudkd.com.cn', null, null, '1', null);
INSERT INTO `sys_tenant` VALUES ('2', '2', '2', '2', '2', '2', null, '2', null, null, '1', null);

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `createBy` varchar(20) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateBy` varchar(20) DEFAULT NULL COMMENT '更新人',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `tenantId` bigint(20) NOT NULL COMMENT '租户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮件',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `isAdmin` tinyint(1) DEFAULT NULL COMMENT '是否管理员',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `HR_PK_USER_TENANT` (`tenantId`),
  CONSTRAINT `HR_PK_USER_TENANT` FOREIGN KEY (`tenantId`) REFERENCES `sys_tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'admin', '15210885623', 'admin@163.com', '1', '1', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('2', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '2', 'test', '1', 'test@163.com', '1', '1', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191457435648', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user3', null, 'user3@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191457435649', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user4', null, 'user4@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191461629952', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user5', null, 'user5@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191465824256', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user6', null, 'user6@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191470018560', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user7', null, 'user7@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191474212864', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user8', null, 'user8@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191482601472', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user10', null, 'user10@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191482601473', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user11', null, 'user11@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191486795776', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user12', null, 'user12@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191490990080', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user13', null, 'user13@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191495184384', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user14', null, 'user14@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191495184385', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user15', null, 'user15@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191516155904', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user16', null, 'user16@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191520350208', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user17', null, 'user17@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191524544512', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user18', null, 'user18@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191524544513', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user19', null, 'user19@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191528738816', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user20', null, 'user20@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191532933120', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user21', null, 'user21@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191541321728', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user22', null, 'user22@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191541321729', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user23', null, 'user23@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191545516032', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user24', null, 'user24@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191549710336', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user25', null, 'user25@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191549710337', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user26', null, 'user26@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191553904640', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user27', null, 'user27@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191558098944', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user28', null, 'user28@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191558098945', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user29', null, 'user29@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191562293248', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user30', null, 'user30@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191566487552', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user31', null, 'user31@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191570681856', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user32', null, 'user32@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191570681857', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user33', null, 'user33@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191574876160', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user34', null, 'user34@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191579070464', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user35', null, 'user35@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191583264768', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user36', null, 'user36@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191583264769', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user37', null, 'user37@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191587459072', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user38', null, 'user38@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191591653376', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user39', null, 'user39@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191591653377', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user40', null, 'user40@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191595847680', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user41', null, 'user41@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191600041984', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user42', null, 'user42@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191600041985', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user43', null, 'user43@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191604236288', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user44', null, 'user44@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191608430592', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user45', null, 'user45@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191608430593', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user46', null, 'user46@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191612624896', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user47', null, 'user47@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191616819200', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user48', null, 'user48@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191616819201', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user49', null, 'user49@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191621013504', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user50', null, 'user50@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191625207808', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user51', null, 'user51@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191629402112', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user52', null, 'user52@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191629402113', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user53', null, 'user53@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191633596416', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user54', null, 'user54@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191633596417', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user55', null, 'user55@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191637790720', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user56', null, 'user56@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191641985024', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user57', null, 'user57@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191641985025', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user58', null, 'user58@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191646179328', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user59', null, 'user59@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191650373632', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user60', null, 'user60@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191650373633', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user61', null, 'user61@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191654567936', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user62', null, 'user62@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191654567937', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user63', null, 'user63@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191658762240', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user64', null, 'user64@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191662956544', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user65', null, 'user65@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191662956545', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user66', null, 'user66@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191667150848', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user67', null, 'user67@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191671345152', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user68', null, 'user68@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191671345153', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user69', null, 'user69@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191675539456', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user70', null, 'user70@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191679733760', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user71', null, 'user71@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191679733761', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user72', null, 'user72@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191683928064', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user73', null, 'user73@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191683928065', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user74', null, 'user74@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191688122368', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user75', null, 'user75@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191692316672', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user76', null, 'user76@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191692316673', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user77', null, 'user77@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191696510976', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user78', null, 'user78@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191700705280', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user79', null, 'user79@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191700705281', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user80', null, 'user80@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191704899584', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user81', null, 'user81@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191709093888', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user82', null, 'user82@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191709093889', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user83', null, 'user83@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191713288192', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user84', null, 'user84@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191717482496', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user85', null, 'user85@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191717482497', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user86', null, 'user86@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191721676800', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user87', null, 'user87@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191725871104', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user88', null, 'user88@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191725871105', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user89', null, 'user89@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191730065408', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user90', null, 'user90@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191734259712', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user91', null, 'user91@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191734259713', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user92', null, 'user92@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191738454016', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user93', null, 'user93@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191738454017', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user94', null, 'user94@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191742648320', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user95', null, 'user95@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191746842624', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user96', null, 'user96@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191746842625', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user97', null, 'user97@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191751036928', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user98', null, 'user98@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191751036929', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user99', null, 'user99@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');
INSERT INTO `sys_user` VALUES ('254439191755231232', 'admin', '2014-07-11 09:49:24', 'admin', '2014-07-11 09:49:24', '1', 'user100', null, 'user100@163.com', '1', '0', 'ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `roleId` bigint(20) NOT NULL COMMENT '角色ID',
  KEY `HR_PK_USERID_ID` (`userId`),
  KEY `HR_PK_ROLE_ID` (`roleId`),
  CONSTRAINT `HR_PK_ROLE_ID` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `HR_PK_USERID_ID` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
