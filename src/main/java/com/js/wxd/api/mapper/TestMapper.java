package com.js.wxd.api.mapper;

import com.js.wxd.result.BaseResult;
import com.js.wxd.result.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-06-10 12:00
 */
@Mapper
public interface TestMapper {

    String getTest();

    int postTest(@Param("params") String params);

    List<String> getPageTest();

}
