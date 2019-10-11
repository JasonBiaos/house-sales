package com.soft.house.databussiness.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * @ClassName: FileService
 * @Description: 文件服务类
 * @Author: Jason Biao
 * @Date: 2019/8/15 14:21
 * @Version: 1.0
 **/
@Service
public class FileService {

    @Value("${file.path}")
    private String filePath;

    /**
     * 获取文件/图像 的路径
     * StringUtils.substringAfterLast(str,"xx")：截取str中最后一个分隔符为xx后的字符串
     * @param files
     * @return
     */
    public List<String> getImgPaths(List<MultipartFile> files){
        if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        List<String> paths = Lists.newArrayList();
        files.forEach(file -> {
            File localFile = null;
            if (!file.isEmpty()){
                try {
                    localFile = saveToLocal(file,filePath);
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(),filePath);
                    paths.add(path);
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return paths;
    }

    /**
     * 将文件(包含图像)保存到本地
     * @param file
     * @param filePath2
     * @return
     * @throws IOException
     */
    private File saveToLocal(MultipartFile file,String filePath2) throws IOException {
        /** Instant.now().getEpochSecond():获取当前以秒为单位的时间戳*/
        File newFile = new File(filePath + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
        if (!newFile.exists()){
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        /** 以字节的形式写入文件 */
        Files.write(file.getBytes(),newFile);
        return newFile;
    }

    public static String getResourcePath(){
        File file = new File(".");
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }
}
