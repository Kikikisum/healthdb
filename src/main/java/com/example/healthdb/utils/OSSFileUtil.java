package com.example.healthdb.utils;

import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * OSS文件上传类
 */

@Slf4j
public class OSSFileUtil {
    private static final String END_POINT = ConstantPropertiesUtil.END_POINT;
    private static final String ACCESS_KEY_ID = ConstantPropertiesUtil.ACCESS_KEY_ID;
    private static final String ACCESS_KEY_SECRET = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
    private static final String BUCKET_NAME = ConstantPropertiesUtil.BUCKET_NAME;
    private static final String URL_PREFIX = ConstantPropertiesUtil.URL_PREFIX;
    /**
     * OSS上的文件夹名
     */
    private static final String FILE_HOST = "healthdb";

    /**
     * 删除单个图片
     *
     * @param url 图片URL
     */
    public static void deleteImg(String url) {
        OSS ossClient = new OSSClientBuilder().build(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        //获取文件名在URL中的下标
        int index = url.lastIndexOf("/") + 1;
        //得到文件名
        String fileName = FILE_HOST + "/" + url.substring(index);
        ossClient.deleteObject(BUCKET_NAME, fileName);
        log.info(fileName);
        ossClient.shutdown();
    }


    /**
     * 上传多文件
     *
     * @param files 文件数组
     */
    public static String uploadFiles(MultipartFile[] files){
        List<String> urlList = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String url = uploadFile(file);
                urlList.add(url);
            }
        } catch (Exception e) {
            log.error("上传文件时发生错误", e);
        }
        return StringUtils.join(urlList, ">");
    }

    /**
     * 上传文件
     *
     * @param file 文件
     */
    public static String uploadFile(MultipartFile file) throws IOException {
        try {
            // 原始文件名称，如a.png
            String originalFilename = file.getOriginalFilename();

            // 唯一的文件名称
            String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(originalFilename, ".");
            InputStream inputStream = file.getInputStream();
            OSSFileUtil.uploadFileToOSS(inputStream, fileName);
            String url = "https://" + URL_PREFIX + "/" + FILE_HOST + "/" + fileName;
            return url;
        } catch (Exception e) {
            log.error("上传文件时发生错误", e);
            throw new IOException("文件上传失败");
        }
    }

    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param mainstream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public static String uploadFileToOSS(InputStream mainstream, String fileName) {
        String ret = "";
        try {
            OSS ossClient = new OSSClientBuilder().build(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(mainstream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(BUCKET_NAME, FILE_HOST + "/" + fileName, mainstream, objectMetadata);
            ret = putResult.getETag();

        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (mainstream != null) {
                    mainstream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 判断OSS服务文件上传时文件的contentType
     */
    public static String getcontentType(String filenameExtension) {
        if ("bmp".equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(filenameExtension) || "jpg".equalsIgnoreCase(filenameExtension)
                || "png".equalsIgnoreCase(filenameExtension)) {
            return "image/jpeg";
        }
        if ("html".equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        if ("txt".equalsIgnoreCase(filenameExtension)) {
            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        if ("pptx".equalsIgnoreCase(filenameExtension) || "ppt".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if ("docx".equalsIgnoreCase(filenameExtension) || "doc".equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        if ("xml".equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        // pdf
        if ("pdf".equalsIgnoreCase(filenameExtension)) {
            return "application/pdf";
        }
        // 视频 mp4
        if ("mp4".equalsIgnoreCase(filenameExtension)) {
            return "video/mp4";
        }
        // mov
        if ("mov".equalsIgnoreCase(filenameExtension)) {
            return "video/quicktime";
        }
        // 视频 avi
        if ("avi".equalsIgnoreCase(filenameExtension)) {
            return "video/x-msvideo";
        }
        // 视频 wmv
        if ("wmv".equalsIgnoreCase(filenameExtension)) {
            return "video/x-ms-wmv";
        }

        return "image/jpg";
    }

}