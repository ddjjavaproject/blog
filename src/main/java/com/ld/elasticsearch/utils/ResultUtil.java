package com.ld.elasticsearch.utils;

import com.ld.elasticsearch.VO.ResultVO;
import com.ld.elasticsearch.enums.ResultEnum;

public class ResultUtil {
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
    public static ResultVO success(){
        ResultVO resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
    public static ResultVO error(ResultEnum resultEnum){
        ResultVO resultVO = new ResultVO<>();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }
}
