package com.zt.blog.common.entity;

import com.zt.blog.common.constant.Constants;

import java.io.Serializable;


public class Result<T> implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 成功状态
     */
    private boolean success;
    /**
     * 状态响应码
     */
	private String code;
    /**
     * 提示信息
     */
	private String msg;
    /**
     * 返回数据
     */
	private T data = null;

	public Result() {

	}
	public Result(boolean success) {
		this.success = success;
	}

    public Result(Constants.Status status) {
        this.msg = status.getMsg();
        this.code=status.getCode();
    }

    public Result(boolean success, Constants.Status status) {
        this.success = success;
        this.msg = status.getMsg();
        this.code=status.getCode();
    }

    public Result(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public Result(boolean success, String code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	public Result(boolean success, String code, String msg, T data) {
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public Result<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getCode() {
		return code;
	}

	public Result<T> setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Result<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");
        sb.append("success=").append(success);
        sb.append(", code='").append(code).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
