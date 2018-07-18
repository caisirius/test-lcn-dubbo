package com.lcn.test.dto.base;

import java.io.Serializable;

/**
 * [类描述]
 *
 * @author caican
 * 2018/7/18
 */
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = -4496867430298036989L;
    /**
     * 请求标识号
     */
    private String sid;

    /**
     * @return the sid
     */
    public String getSid() {
        return sid;
    }
    /**
     * @param sid the sid to set
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

}
