package com.js.wxd.api.iml;

import com.js.wxd.Constant;
import com.js.wxd.api.entity.SocketResult;
import com.js.wxd.api.service.FileService;
import com.js.wxd.api.websocket.WebSocket;
import com.js.wxd.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    WebSocket webSocket;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BaseResult getImageList() throws IOException {
        File file = new File(Constant.IMAGE_PATH);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                System.out.println("创建文件夹出错-------------------");
            }
        }
        Map<Long, String> imageMap = new TreeMap<>();
        File[] fileList = file.listFiles();
        if (fileList == null || fileList.length == 0) {
            return BaseResult.notFound();
        }
        for (File f : fileList) {
            BasicFileAttributes bAttributes = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
            long createTime = bAttributes.creationTime().toMillis();
            imageMap.put(createTime, Constant.SEVER_URL + f.getName());
        }
        //按Key进行排序
        Map<Long, String> resultMap = sortMapByKey(imageMap);
        List<String> imgList = new ArrayList<>();
        for (Map.Entry<Long, String> entry : resultMap.entrySet()) {
            System.out.println("key:" + entry.getKey() + ";value:" + entry.getValue());
            imgList.add(entry.getValue());
        }
        return BaseResult.success(imgList);
    }

    private static Map<Long, String> sortMapByKey(Map<Long, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<Long, String> sortMap = new TreeMap<>(Comparator.reverseOrder());
        sortMap.putAll(map);
        return sortMap;
    }

    @Override
    public BaseResult getLastFile() throws IOException {
        File file = new File(Constant.IMAGE_PATH);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                System.out.println("创建文件夹出错-------------------");
            }
        }
        Map<Long, String> imageMap = new TreeMap<>();
        File[] fileList = file.listFiles();
        if (fileList == null || fileList.length == 0) {
            return BaseResult.notFound();
        }
        for (File f : fileList) {
            BasicFileAttributes bAttributes = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
            long createTime = bAttributes.creationTime().toMillis();
            imageMap.put(createTime, Constant.SEVER_URL + f.getName());
        }
        //按Key进行排序
        Map<Long, String> resultMap = sortMapByKey(imageMap);
        List<String> imgList = new ArrayList<>();
        for (Map.Entry<Long, String> entry : resultMap.entrySet()) {
            System.out.println("key:" + entry.getKey() + ";value:" + entry.getValue());
            imgList.add(entry.getValue());
        }
        //获取最新的一张图片
        String url = imgList.get(0);
        return BaseResult.success(url);
    }

    @Override
    public BaseResult uploadHeadIcon(MultipartFile file) throws IOException {
        if (file == null) {
            return BaseResult.parameterError();
        }

        long size = file.getSize();
        if (size > Constant.IMAGE_MAX_SIZE) {
            return BaseResult.error("ERROR", "图片超出指定大小2MB!");
        }

        String contentType = file.getContentType();
        if (!Constant.IMAGE_TYPE.contains(contentType)) {
            return BaseResult.error("ERROR", "图片类型支持png,jpg,PNG,JPG,JPEG,jpeg,bmp,BMP,GIF,gif");
        }

        String name = file.getOriginalFilename();
        String resultName = System.currentTimeMillis() + "code." + name.substring(name.lastIndexOf(".") + 1);
        checkDir(Constant.IMAGE_PATH);
        String absPath = Constant.IMAGE_PATH + resultName;
        file.transferTo(new File(absPath));

        try {
            String url = Constant.SEVER_URL + resultName;
            redisTemplate.opsForValue().set(Constant.KEY_IMAGE_URL, url);
            ListOperations<String, String> ops = redisTemplate.opsForList();
            ops.rightPush(Constant.KEY_IMAGE_LIST, url);
            webSocket.sendMessage(new SocketResult(url, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.success(resultName);
    }

    private void checkDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                System.out.println("创建文件夹出错-------------------");
            }
        }
    }

}
