package com.js.wxd.api.controller;

import com.js.wxd.api.service.FileService;
import com.js.wxd.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("file/")
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping(value = "uploadImg.do", method = RequestMethod.POST)
    public BaseResult uploadHeadIcon(@RequestParam MultipartFile file) throws IOException {
        return fileService.uploadHeadIcon(file);
    }

    @RequestMapping(value = "lastFile.do", method = RequestMethod.GET)
    public BaseResult lastFile() throws IOException {
        return fileService.getLastFile();
    }

    @RequestMapping(value = "imgList.do", method = RequestMethod.GET)
    public BaseResult getImageList() throws IOException {
        return fileService.getImageList();
    }
}
