package com.anycommon.mq.rocketmq.config;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;

/**
 * rocket config
 * @author mac
 */
@ExtRocketMQTemplateConfiguration(nameServer = "${platform.rocketmq.extNameServer}")
public class RocketConfig {
}
