package com.js.wxd.result;


/**
 * 返回 成功实体
 *
 * @author adming
 */
public class SuccessResult extends BaseResult {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public SuccessResult(Object data) {
        this();
        this.data = data;
    }

    public SuccessResult() {
        super(SUCCESS);
    }
}
