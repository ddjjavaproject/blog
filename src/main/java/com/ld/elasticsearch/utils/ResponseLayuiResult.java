package com.ld.elasticsearch.utils;

import com.ld.elasticsearch.enums.ResultEnum;
import lombok.Data;

/**
 * layui 数据响应结构
 * @param <T>
 */
@Data
public class ResponseLayuiResult<T> {
    /**
     * 返回状态码
     */
    private int code;
    /**
     * 总条数
     */
    private long count;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    /**
     * 有数据构造方法
     */
    private ResponseLayuiResult(int code, long count, T data) {
        this.code = code;
        this.count = count;
        this.data = data;
    }

    /**
     * 空值构造方法
     */
    private ResponseLayuiResult(int code, long count, String msg) {
        this.code = code;
        this.count = count;
        this.msg = msg;
    }
    /**
     * 返回有数据信息
     */
    public static <T> ResponseLayuiResult<T> createBySuccess(long count, T data) {
        return new ResponseLayuiResult<T>(ResultEnum.LAYUI_SUCCESS.getCode(), count, data);
    }

    /**
     * 返回空数据信息
     */
    public static <T> ResponseLayuiResult<T> createByNull(long count, String msg) {
        return new ResponseLayuiResult<T>(ResultEnum.LAYUI_FAIL.getCode(), count, msg);
    }

    /**
     * 返回空数据信息（count为null）
     */
    public static <T> ResponseLayuiResult<T> createByNull(String msg) {
        return new ResponseLayuiResult<T>(ResultEnum.LAYUI_FAIL.getCode(), ResultEnum.LAYUI_SUCCESS.getCode(), msg);
    }
}
