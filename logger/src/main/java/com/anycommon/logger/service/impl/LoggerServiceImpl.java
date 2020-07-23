package com.anycommon.logger.service.impl;

import com.anycommon.logger.config.LoggerConfig;
import com.anycommon.logger.config.LoggerDataSourceConfig;
import com.anycommon.logger.domain.PlatformBusinessLog;
import com.anycommon.logger.domain.PlatformBusinessLogExample;
import com.anycommon.logger.dp.PlatformBusinessLogDP;
import com.anycommon.logger.mapper.PlatformBusinessLogMapper;
import com.anycommon.logger.qo.LoggerQO;
import com.anycommon.logger.service.LoggerService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * rizhi
 *
 * @author wangkai
 */
@Service
public class LoggerServiceImpl implements LoggerService {
    private final static Logger logger = LoggerFactory.getLogger(LoggerServiceImpl.class);

    @Resource
    private PlatformBusinessLogMapper platformBusinessLogMapper;

    @Resource
    private LoggerConfig loggerConfig;

    @Async
    @Override
    public void logger(PlatformBusinessLogDP log) {
        try {
            log.setAppId(loggerConfig.getAppId());
            log.setAppName(loggerConfig.getAppName());
            log.setOrgCode(loggerConfig.getOrgCode());
            platformBusinessLogMapper.insert(BeanUtil.insertConversion(log.addCheck(), new PlatformBusinessLog()));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public ResponseBody<List<PlatformBusinessLogDP>> getLogs(LoggerQO qo) {
        if (StringUtils.isBlank(qo.getAppId())) {
            qo.setAppId(loggerConfig.getAppId());
        }
        if (StringUtils.isBlank(qo.getAppName())) {

            qo.setAppName(loggerConfig.getAppName());
        }
        if (StringUtils.isBlank(loggerConfig.getOrgCode())) {

            qo.setOrgCode(loggerConfig.getOrgCode());
        }


        PlatformBusinessLogExample platformBusinessLogExample = new PlatformBusinessLogExample();
        try {
            return ResponseBodyWrapper.successListWrapper(platformBusinessLogMapper.selectByExample(platformBusinessLogExample), platformBusinessLogMapper.countByExample(platformBusinessLogExample), qo, PlatformBusinessLogDP.class);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return null;
    }
}
