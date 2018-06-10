package com.js.wxd.api.iml;

import com.github.pagehelper.PageHelper;
import com.js.wxd.api.mapper.TestMapper;
import com.js.wxd.api.service.TestService;
import com.js.wxd.result.BaseResult;
import com.js.wxd.result.PageResult;
import com.sun.prism.TextureMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-06-10 12:00
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestMapper testMapper;

    @Override
    public BaseResult getTest() {
        String result = testMapper.getTest();
        return BaseResult.success(result);
    }

    @Override
    public BaseResult postTest(String param) {
        int result = testMapper.postTest(param);
        return BaseResult.success("postTest提交成功");
    }

    @Override
    public PageResult<String> getPageTest(Integer pageNo, Integer pageSize) {
        //分页框架 PageHelper
        PageHelper.startPage(pageNo, pageSize);
        List<String> datas = testMapper.getPageTest();
        return new PageResult<>(datas);
    }

}
