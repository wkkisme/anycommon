package com.anycommon.logger.controller;

import com.anycommon.logger.domain.PlatformBusinessLog;
import com.anycommon.logger.dp.PlatformBusinessLogDP;
import com.anycommon.logger.qo.LoggerQO;
import com.anycommon.logger.service.LoggerService;
import com.anycommon.response.common.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/platform/common/logger")
public class CommonLoggerController {

    @Resource
    private LoggerService loggerService;

    /**
     * 获取日志接口
     * @param qo  qo
     * @return ResponseBody<List<PlatformBusinessLogDP>>
     */
    public ResponseBody<List<PlatformBusinessLogDP>> getLoggers(LoggerQO qo){

        return loggerService.getLogs(qo);
    }
}
