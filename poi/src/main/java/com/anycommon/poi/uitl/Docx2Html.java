//package com.anycommon.poi.uitl;
//
//import org.apache.poi.xwpf.converter.core.IImageExtractor;
//import org.apache.poi.xwpf.converter.core.IURIResolver;
//import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
//import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Docx2Html {
//    /**
//     *
//     * 2007版本word转换成html
//     *
//     * @param input
//     * @param bucket
//     * @param directory
//     * @param visitPoint
//     * @return
//     * @throws IOException
//     */
//    public String Word2007ToHtml(InputStream input, String bucket, String directory, String visitPoint)
//            throws IOException {
//        XWPFDocument document = new XWPFDocument(input);
//        // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
//        XHTMLOptions options = XHTMLOptions.create();
//        Map<String, String> imgMap = new HashMap<>();
//        options.setExtractor(new IImageExtractor() {
//            @Override
//            public void extract(String imagePath, byte[] imageData) throws IOException {
//                //获取图片数据并且上传
//                String fileName = AliOssUtil.generateImageFileName() + imagePath.substring(imagePath.lastIndexOf("."));
//                String imgUrl = uploadFileUtil.uploadFile(imageData, bucket, directory, fileName, visitPoint);
//                imgMap.put(imagePath, imgUrl);
//            }
//        });
//        // html中图片的路径 相对路径
//        options.URIResolver(new IURIResolver() {
//            @Override
//            public String resolve(String uri) {
//                //设置图片路径
//                return imgMap.get(uri);
//            }
//        });
//        options.setIgnoreStylesIfUnused(false);
//        options.setFragment(true);
//        // 3) 将 XWPFDocument转换成XHTML
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        XHTMLConverter.getInstance().convert(document, baos, options);
//        String content = baos.toString();
//        baos.close();
//        return content;
//    }
//
//}
