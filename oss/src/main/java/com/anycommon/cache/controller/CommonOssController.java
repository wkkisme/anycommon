package com.anycommon.cache.controller;

import com.aliyun.oss.model.CannedAccessControlList;
import com.anycommon.cache.dto.OssDTO;
import com.anycommon.cache.service.OssService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.constant.DownloadEnum;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.ResponseBodyWrapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.Map.*;

@RestController
@RequestMapping("/platform/common/oss")
public class CommonOssController {

    @Resource
    private OssService ossService;

    /**
     * 私密文件上传
     *
     * @return ResponseBody<Object>
     */
    @RequestMapping("/uploadPrivate")
    public ResponseBody<Object> upload(HttpServletRequest request) {
        ResponseBody<Object> results = new ResponseBody<>();
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;

            Map<String, MultipartFile> fileMap = mRequest.getFileMap();
            Iterator<Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
            List<Object> result = new ArrayList<>();
            while (it.hasNext()) {
                Entry<String, MultipartFile> entry = it.next();
                if (entry.getValue() instanceof CommonsMultipartFile) {
                    CommonsMultipartFile file = (CommonsMultipartFile) entry.getValue();
                    FileItem fileItem = file.getFileItem();
                    OssDTO store = ossService.storePrivate(fileItem);
                    result.add(store.getKey());
                } else {
                    ResponseBodyWrapper.fail("请上传文件","40001");
                }
            }

            results.setData(result);
        } else {
            ResponseBodyWrapper.fail("请上传文件","40001");
        }
        return results;
    }

    /**
     * 公开文件上传
     *
     * @return ResponseBody<Object>
     */
    @RequestMapping("/uploadPublic")
    public ResponseBody<Object>  uploadPublic(HttpServletRequest request) {
        ResponseBody<Object> results = new ResponseBody<>();
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;

            Map<String, MultipartFile> fileMap = mRequest.getFileMap();
            Iterator it = fileMap.entrySet().iterator();
            List<Object> result = new ArrayList<>();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                if (entry.getValue() instanceof CommonsMultipartFile) {
                    CommonsMultipartFile file = (CommonsMultipartFile) entry.getValue();
                    FileItem fileItem = file.getFileItem();
                    OssDTO store = ossService.storePublic(fileItem);
                    result.add(store.getKey());
                } else {
                    ResponseBodyWrapper.fail("请上传文件","40001");
                }
            }

            results.setData(result);
        } else {
            ResponseBodyWrapper.fail("请上传文件","40001");
        }
        return results;
    }

    /**
     * 文件下载
     *
     * @return ResponseBody<Object>
     */
    @RequestMapping(value = "/download")
    public ResponseBody<Object>  download(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "key") String key, @RequestParam(value = "fileName",required = false) String fileName) throws AppSystemException {
        // 查看登录信息
        InputStream inputStream = ossService.getInputStreamByKey(key);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            int index = key.lastIndexOf(".") + 1;
            if (index < key.length()){
                String suffix = key.substring(index);
                response.setContentType(DownloadEnum.getContentTpye(suffix));
                System.out.println(DownloadEnum.getContentTpye(suffix));
            }

            if (StringUtils.isNotBlank(fileName)){
                key = fileName+"."+ FilenameUtils.getExtension(key);
            }
            String s = new String(key.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1);
            response.setHeader("Content-Disposition", "inline; filename=\"" + s + "\" ;charset=UTF-8");
            int len;
            byte[] buffer = new byte[1024 * 10];
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}
