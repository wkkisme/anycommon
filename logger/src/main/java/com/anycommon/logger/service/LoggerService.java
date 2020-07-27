package com.anycommon.logger.service;


import com.anycommon.logger.dp.PlatformBusinessLogDP;
import com.anycommon.logger.qo.LoggerQO;
import com.anycommon.poi.common.ResponseBody;

import java.util.List;

/**
 * oss 服务 默认为阿里云服务，如果有扩展请实现此类
 * @author wangkai
 */
public interface LoggerService {

    void logger(PlatformBusinessLogDP dp);

    ResponseBody<List<PlatformBusinessLogDP>> getLogs(LoggerQO qo);
}
