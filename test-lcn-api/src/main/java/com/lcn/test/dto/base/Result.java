package com.lcn.test.dto.base;

/**
 * [类描述]
 *
 * @author caican
 * 2018/7/18
 */
public class Result<T> extends BaseResult {

    /**
     * 返回数据，可为基本类型（包装类），可以为其它可序列化对象
     */
    private T data;

    public static <T> Result<T> create() {
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        return result;
    }

    public Result<T> success(){
        success(null);
        return this;
    }

    public Result<T> success(T data){
        this.setSuccess(true);
        this.data = data;
        return this;
    }

    public Result<T> fail(String code,String description){
        this.setSuccess(false);
        this.setCode(code);
        this.setDescription(description);
        return this;
    }

    public Result<T> fail(String code){
        fail(code, null);
        return this;
    }

    public Result<T> code(String code){
        this.setCode(code);
        return this;
    }

    public Result<T> description(String description){
        this.setDescription(description);
        return this;
    }

    public Result<T> sid(String sid){
        this.setSid(sid);
        return this;
    }

    public Result<T> data(T data){
        this.data = data;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
