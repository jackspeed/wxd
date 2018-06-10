package com.js.wxd.api.controller;


import com.js.wxd.api.service.TestService;
import com.js.wxd.result.BaseResult;
import com.js.wxd.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-04-28 14:48
 */
@RestController
@RequestMapping("test/")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "postTest.do", method = RequestMethod.POST)
    public BaseResult postTest(String param) {
        return testService.postTest(param);
    }

    @RequestMapping(value = "getTest.do", method = RequestMethod.GET)
    public BaseResult getTest() {
        return testService.getTest();
    }

    @RequestMapping(value = "getPageTest.do", method = RequestMethod.GET)
    public PageResult<String> getPageTest(Integer pageNo, Integer pageSize) {
        return testService.getPageTest(pageNo, pageSize);
    }
}
