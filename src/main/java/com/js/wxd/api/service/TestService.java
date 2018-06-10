package com.js.wxd.api.service;

import com.js.wxd.result.BaseResult;
import com.js.wxd.result.PageResult;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-06-10 12:00
 */
public interface TestService {
    BaseResult getTest();

    BaseResult postTest(String param);

    PageResult<String> getPageTest(Integer pageNo, Integer pageSize);
}
