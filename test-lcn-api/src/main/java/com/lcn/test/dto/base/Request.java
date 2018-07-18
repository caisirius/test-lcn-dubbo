package com.lcn.test.dto.base;

/**
 * [类描述]
 *
 * @author caican
 * 2018/7/18
 */
public class Request<T> extends BaseRequest {

    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * 请求数据，可为基本类型（包装类），可以为其它可序列化对象
     */
    private T data;

    public Request(){

    }

    public Request(T data){
        this.data = data;
    }

    public static <T> Request<T> create() {
        Request<T> result = new Request<T>();
        return result;
    }

    public Request<T> sid(String sid){
        this.setSid(sid);
        return this;
    }

    public Request<T> data(T data){
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

