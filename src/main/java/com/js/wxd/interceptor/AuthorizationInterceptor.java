package com.js.wxd.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.js.wxd.annotation.AccessSecret;
import com.js.wxd.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    /**
     * 缓存操作
     */
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {

//            跨域问题解决配置--start
            String url = request.getHeader("Origin");
            if (!StringUtils.isEmpty(url)) {
                String val = response.getHeader("Access-Control-Allow-Origin");
                if (StringUtils.isEmpty(val)) {
                    response.addHeader("Access-Control-Allow-Origin", url);
                } else {
                    response.setHeader("Access-Control-Allow-Origin", "*");
                }
            }
            response.setContentType("application/json;charset=UTF-8");
            response.addHeader("Access-Control-Allow-Credentials", "true");

            response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.addHeader("Access-Control-Allow-Headers",
                    "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");

//            跨域问题解决配置--end

            //接口请求校验
            AccessSecret access = ((HandlerMethod) handler).getMethodAnnotation(AccessSecret.class);
            if (null != access) {
                String headerAuth = request.getHeader("auth");
                if (StringUtils.isEmpty(headerAuth)) {
                    response.getWriter().write(JSON.toJSONString(BaseResult.needAuthorization()));
                    return false;
                }

                ValueOperations valueOperations = redisTemplate.opsForValue();
                String accessToken = (String) valueOperations.get("CACHE_ACCESS_TOKEN" + headerAuth);
                if (null != accessToken && accessToken.equals(headerAuth)) {
                    String lastAccessTime = (String) valueOperations.get("ONLINE" + accessToken);
                    if (StringUtils.isEmpty(lastAccessTime)) {
                        //十五分钟无操作，退出系统重新登录
                        response.getWriter().write(JSON.toJSONString(BaseResult.loginTimeOut()));
                        //清空缓存
                        redisTemplate.delete("CACHE_ACCESS_TOKEN" + headerAuth);
                        return false;
                    } else {
                        valueOperations.set("ONLINE" + headerAuth, headerAuth, 30, TimeUnit.MINUTES);
                        return true;
                    }
                }
                response.getWriter().write(JSON.toJSONString(BaseResult.authorizationError()));
                return false;
            } else {
                return true;
            }
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 两次请求的间隔时间是否在知道时间之内
     *
     * @param request
     * @param response
     * @param valueOperations
     * @return
     * @throws IOException
     */
    private boolean isTimeOut(HttpServletRequest request, HttpServletResponse response,
                              ValueOperations valueOperations) throws IOException {
//        HttpSession session = request.getSession(true);
//        long lastAccessedTime = session.getLastAccessedTime();
//        Long operateTime = (Long) session.getAttribute("operateTime");
//        System.out.println("-----> operateTime: " + operateTime);
//
//        //如果operateTime是空，说明是第一次链接，对operateTime进行初始化
//        if (operateTime == null) {
//            session.setAttribute("operateTime", lastAccessedTime);
        return false;
//        } else {
//            //计算最新链接时间和上次链接时间的差值
//            int intervalTime = (int) ((lastAccessedTime - operateTime) / 1000);
//            System.out.println("-----> intervalTime: " + intervalTime);
//            //如果超过十秒没有交互的话，就跳转到超时界面
//            if (intervalTime > 10) {
//                response.getWriter().write(JSON.toJSONString(BaseResult.loginTimeOut()));
//                return true;
//            }
//            //更新operateTime
//            session.setAttribute("operateTime", lastAccessedTime);
//            return false;
//        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
