package com.js.wxd.result;

/**
 * 返回result实体
 *
 * @author adming
 */
public class BaseResult {

    public static int SUCCESS = 200;

    public static int FAIL = 0;

    private int result;

    public BaseResult() {
    }

    public BaseResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static BaseResult success(Object object) {
        return new SuccessResult(object);
    }

    public static BaseResult error(String code, String message) {
        return new ErrorResult(code, message);
    }

    public static BaseResult parameterError() {
        return new ErrorResult("0002", "参数错误");
    }

    public static BaseResult notFound() {
        return new ErrorResult("0003", "查无结果");
    }

    public static BaseResult authorizationError() {
        return new ErrorResult("0004", "授权信息失效，重新登录");
    }

    public static BaseResult needAuthorization() {
        return new ErrorResult("0009", "需要auth");
    }

    public static BaseResult loginTimeOut() {
        return new ErrorResult("0008", "登录失效，请重新登录");
    }

    public static BaseResult permissionDenied() {
        return new ErrorResult("0005", "拒绝访问");
    }

    public static BaseResult systemError() {
        return new ErrorResult("0006", "系统错误");
    }

    public static BaseResult limitError() {
        return new ErrorResult("0007", "访问过于频繁,请一分钟后再试");
    }


}
