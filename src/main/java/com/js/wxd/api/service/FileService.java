package com.js.wxd.api.service;

import com.js.wxd.result.BaseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    BaseResult getImageList() throws IOException;

    BaseResult getLastFile() throws IOException;

    BaseResult uploadHeadIcon(MultipartFile file) throws IOException;

}
